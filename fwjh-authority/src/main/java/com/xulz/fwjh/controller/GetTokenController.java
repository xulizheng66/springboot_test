package com.xulz.fwjh.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.fwjh.commons.GetSecretKeyByFwjh;
import com.xulz.fwjh.commons.SymmetricEncoderByFwjh;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @description: 获取省平台鉴权
 * @author: xulz
 * @date： 2019/10/31 19:08
 * @version: 1.0
 */
@Api(value = "获取服务聚合平台鉴权", tags = {"获取服务聚合平台鉴权（不存redis）"})
@RestController
@Log4j2
public class GetTokenController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取token
     * @param requestUrl
     * @param appId
     * @param appKey
     * @return
     */
    @RequestMapping(value = "/getTokenByFwjh", method = RequestMethod.POST)
    @ApiOperation(value = "获取服务聚合鉴权")
    public JSONObject getTokenByFwjh(
            @ApiParam(value = "获取token地址") @RequestParam String requestUrl,
            @ApiParam(value = "应用appid") @RequestParam String appId,
            @ApiParam(value = "应用公钥appkey") @RequestParam String appKey) {
        JSONObject token = getAuthority(requestUrl, appId, appKey);
        return token;
    }

    public JSONObject getAuthority(String url, String appid, String appkey) {
        JSONObject jsonObject = new JSONObject();
        try {
            String gateway_rtime = String.valueOf(System.currentTimeMillis());
            String gateway_sig = GetSecretKeyByFwjh.gatewaySignEncode(appid, appkey, gateway_rtime);
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            // header参数，应用id
            headers.add("gateway_appid", appid);
            // header参数，签名信息
            headers.add("gateway_sig", gateway_sig);
            // header参数，本次请求时间戳(毫秒值)
            headers.add("gateway_rtime", gateway_rtime);
            // 参数
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            // Spring的UriComponentsBuilder类。手动连接字符串更清洁，它会为您处理URL编码
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(map);
            HttpEntity<String> response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, httpEntity, String.class);
            String result = response.getBody();
            if (StringUtils.isNotBlank(result)) {
                JSONObject objects = JSONObject.parseObject(result);
                String code = objects.getString("code");
                String body = objects.getString("body");
                log.info("服务聚合code==================>" + code);
                log.info("服务聚合body==================>\n" + body);
                // 执行结果代码（成功： 0 ， 失败：详见错误码列表）
                if (StringUtils.isNoneBlank(code, body) && "0".equals(code)) {
                    JSONObject bodyObj = JSONObject.parseObject(body);
                    // 1.获取access_token
                    String accessToken = bodyObj.getString("access_token");
                    String expiresIn = bodyObj.getString("expires_in");
                    log.info("服务聚合access_token==================>" + accessToken);
                    log.info("服务聚合默认token有效时间==================>" + expiresIn);
                    // 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
                    String secretKey = SymmetricEncoderByFwjh.AESDncode(appkey, accessToken);
                    // 3.获取调用服务realSign
                    String realSign = GetSecretKeyByFwjh.gatewaySignEncode(appid, secretKey, gateway_rtime);
                    // 从redis获取token(默认有效期为10分钟)
//                    FwjhEntity token = getTokenFromRedis(appId, appKey, gateway_rtime, realSign, "580");
                    // 封装调用服务需要的参数
                    jsonObject.put("gateway_appid", appid);
                    jsonObject.put("gateway_sig", realSign);
                    jsonObject.put("gateway_rtime", gateway_rtime);
                    return jsonObject;
                }
                jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
                return jsonObject;
            }
            jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
            return jsonObject;
        } catch (Exception e) {
            log.error("获取token错误，错误信息：【" + e.getMessage() + "】", e);
            jsonObject.put("message", "获取token错误，错误信息：【" + e.getMessage() + "】");
            return jsonObject;
        }
    }

}

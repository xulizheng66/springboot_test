package com.xulz.fwjh.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.fwjh.commons.GetSecretKeyByFwjh;
import com.xulz.fwjh.commons.RedisUtils;
import com.xulz.fwjh.commons.SymmetricEncoderByFwjh;
import com.xulz.fwjh.entity.FwjhEntity;
import com.xulz.fwjh.exception.CustomException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO 获取服务聚合平台token
 * @Author xulz
 * @Date 2019/7/2 11:54
 */

@Api("服务聚合平台接口-获取签名密钥")
@RestController
@Log4j2
public class GetAuthByFwjhController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtil;


    @ApiOperation(value = "获取访问鉴权")
    @PostMapping("getAuthority")
    public JSONObject getAuthority(
            @ApiParam(value = "url", required = true, defaultValue = "http://10.50.108.42/proxy/auth/token") @RequestParam String requestUrl,
            @ApiParam(value = "appId(客户端ID)", required = true) @RequestParam(value = "") String appId,
            @ApiParam(value = "appKey(应用公钥)", required = true) @RequestParam(value = "") String appKey) {
        JSONObject jsonObject = new JSONObject();
        try {
            String gateway_rtime = String.valueOf(System.currentTimeMillis());
            String gateway_sig = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            // header参数，应用id
            headers.add("gateway_appid", appId);
            // header参数，签名信息
            headers.add("gateway_sig", gateway_sig);
            // header参数，本次请求时间戳(毫秒值)
            headers.add("gateway_rtime", gateway_rtime);
            // 参数
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            // Spring的UriComponentsBuilder类。手动连接字符串更清洁，它会为您处理URL编码
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUrl).queryParams(map);
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
                    String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, accessToken);
                    // 3.获取调用服务realSign
                    String realSign = GetSecretKeyByFwjh.gatewaySignEncode(appId, secretKey, gateway_rtime);
                    // 从redis获取token(默认有效期为10分钟)
                    FwjhEntity token = getTokenFromRedis(appId, appKey, gateway_rtime, realSign, "580");
                    // 封装调用服务需要的参数
                    jsonObject.put("gateway_appid", token.getAppId());
                    jsonObject.put("gateway_sig", token.getSign());
                    jsonObject.put("gateway_rtime", token.getRtime());
                    return jsonObject;
                }
                jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
                return jsonObject;
            }
            jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
            return jsonObject;
        } catch (CustomException e) {
            log.error("获取token错误，错误信息：【" + e.getMessage() + "】", e);
            jsonObject.put("error-message", "获取token错误，错误信息：【" + e.getMessage() + "】");
            jsonObject.put("error-body", "获取token错误，错误信息：【" + e.getMessage() + "】");
            return jsonObject;
        } catch (Exception e) {
            log.error("获取token错误，错误信息：【" + e.getMessage() + "】", e);
            jsonObject.put("error-message", "获取token错误，错误信息：【" + e.getMessage() + "】");
            return jsonObject;
        }
    }

    /**
     * 根据有效时间缓存token
     */
    private FwjhEntity getTokenFromRedis(String appId, String appKey, String rtime, String sign, String expiresIn) {
        FwjhEntity fwjhEntity = (FwjhEntity) redisUtil.get("fwjh:" + appId + "@" + appKey);
        if (null != fwjhEntity) {
            return fwjhEntity;
        }
        FwjhEntity entity = new FwjhEntity();
        entity.setAppId(appId);
        entity.setAppKey(appKey);
        entity.setRtime(rtime);
        entity.setSign(sign);
        redisUtil.set("fwjh:" + appId + "@" + appKey, entity, Long.valueOf(expiresIn), TimeUnit.SECONDS);
        return entity;
    }



}

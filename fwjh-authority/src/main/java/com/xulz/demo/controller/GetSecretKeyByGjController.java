package com.xulz.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.demo.commons.GetSecretKeyByFwjh;
import com.xulz.demo.commons.SymmetricEncoderByFwjh;
import com.xulz.demo.exception.CustomException;
import com.xulz.demo.exception.CustomResponseErrorHandler;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO 获取服务聚合平台token
 * @Author xulz
 * @Date 2019/7/2 11:54
 */

@Api("服务聚合平台接口-获取签名密钥")
@RestController
@Log4j2
public class GetSecretKeyByGjController {

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "获取访问鉴权")
    @GetMapping("getAuthority")
    public JSONObject getAuthority(
            @ApiParam(value = "url", required = true, defaultValue = "http://10.50.108.42/proxy/auth/token") @RequestParam String requestUrl,
            @ApiParam(value = "appId", required = true) @RequestParam(value = "") String appId,
            @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {
        JSONObject jsonObject = new JSONObject();
        try {
            String gateway_rtime = String.valueOf(System.currentTimeMillis());
            String gateway_sig = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(mediaType);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

            // Authorization:Basic NWYyNWI2ZjZmNTdiNDc0OGE3Y2RlZTM4ZDk3MGJlYzM6MTIzNDU2
            headers.add("gateway_appid", appId);// header参数，应用id
            headers.add("gateway_sig", gateway_sig);// header参数，签名信息
            headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)
            // 旧地址
            // http://10.50.108.42/proxy/oauth/token?scope=webapp&grant_type=client_credentials
            // headers.add("Authorization", "Basic" + " " + appKey);

            Map<String, String> map = new HashMap<>();

            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(map, headers);
            // 错误处理
            HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity, String.class);
            String result = response.getBody();
            if (StringUtils.isNotBlank(result)) {
                JSONObject objects = JSONObject.parseObject(result);
                String code = objects.getString("code");
                String body = objects.getString("body");
                // 执行结果代码（成功： 0 ， 失败：详见错误码列表）
                if (StringUtils.isNoneBlank(code, body) && "0".equals(code)) {
                    JSONObject bodyObj = JSONObject.parseObject(body);
                    // 1.获取access_token
                    String accessToken = bodyObj.getString("access_token");
                    // 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
                    String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, accessToken);
                    // 3.获取调用服务realSign
                    String realSign = GetSecretKeyByFwjh.gatewaySignEncode(appId, secretKey, gateway_rtime);
                    // 封装调用服务需要的参数
                    jsonObject.put("gateway_appid", appId);
                    jsonObject.put("gateway_sig", realSign);
                    jsonObject.put("gateway_rtime", gateway_rtime);
                    return jsonObject;
                }
                jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
                return jsonObject;
            }
            jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
            return jsonObject;
        } catch (CustomException e) {
            log.error("获取token错误，错误信息：【"+ e.getMessage()+"】",e);
            jsonObject.put("error-message", "获取token错误，错误信息：【"+ e.getMessage()+"】");
            jsonObject.put("error-body", "获取token错误，错误信息：【"+ e.getMessage()+"】");
            return jsonObject;
        } catch (Exception e) {
            log.error("获取token错误，错误信息：【"+ e.getMessage()+"】",e);
            jsonObject.put("error-message", "获取token错误，错误信息：【"+ e.getMessage()+"】");
            return jsonObject;
        }
    }

}

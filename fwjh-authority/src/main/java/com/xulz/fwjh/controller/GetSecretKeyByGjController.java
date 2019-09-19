package com.xulz.fwjh.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.fwjh.commons.GetSecretKeyByFwjh;
import com.xulz.fwjh.commons.ServiceInvocation;
import com.xulz.fwjh.commons.SymmetricEncoderByFwjh;
import com.xulz.fwjh.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    @Value("${request.token.url}")
    private String tokenUrl;
    @Value("${request.token.appId}")
    private String appId;
    @Value("${request.token.appKey}")
    private String appKey;


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
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            // Authorization:Basic NWYyNWI2ZjZmNTdiNDc0OGE3Y2RlZTM4ZDk3MGJlYzM6MTIzNDU2
            headers.add("gateway_appid", appId);// header参数，应用id
            headers.add("gateway_sig", gateway_sig);// header参数，签名信息
            headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)
            // 旧地址
            // http://10.50.108.42/proxy/oauth/token?scope=webapp&grant_type=client_credentials
            // headers.add("Authorization", "Basic" + " " + appKey);
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
                log.info("服务聚合body==================>" + body);
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

    @ApiOperation(value = "通过httpRequest获取token")
    @GetMapping("getAuthorityByHttp")
    public JSONObject getAuthorityByHttp() {
        // 返回结果
        JSONObject resultObj = new JSONObject();
        String currTime = String.valueOf(System.currentTimeMillis());
        // 1.获取access_token。
        try {
            String sign = ServiceInvocation.gatewaySignEncode(appId, appKey, currTime);
            JSONObject jsonObject = ServiceInvocation.httpRequest(tokenUrl, "POST", appId, currTime, sign);
            JSONObject jsonObjectBody = JSONObject.parseObject(jsonObject.getString("body"));
            if (jsonObjectBody != null) {
                String accessToken = jsonObjectBody.getString("access_token");
                String expiresIn = jsonObjectBody.getString("expires_in");
                log.info("服务聚合access_token==================>" + accessToken);
                log.info("服务聚合默认token有效时间==================>" + expiresIn);
                // 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
                String secretKey = ServiceInvocation.AESDncode(appKey, accessToken);
                // 3.调用服务，获取服务的json数据。
                String realSign = ServiceInvocation.gatewaySignEncode(appId, secretKey, currTime);
                // 封装调用服务需要的参数
                resultObj.put("gateway_appid", appId);
                resultObj.put("gateway_sig", realSign);
                resultObj.put("gateway_rtime", currTime);
            } else {
                log.info("获取token错误，请查看响应数据中的错误码和错误信息。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取token错误，请查看响应数据中的错误码和错误信息。", e);
            resultObj.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
            return resultObj;
        }
        return resultObj;
    }


    @ApiOperation(value = "通过RestTemplate获取token(GET)，并处理异常")
    @GetMapping("getToken")
    public JSONObject getToken(
            @ApiParam(value = "url") @RequestParam String url,
            @ApiParam(value = "username") @RequestParam String username,
            @ApiParam(value = "password") @RequestParam String password,
            @ApiParam(value = "grant_type") @RequestParam String grant_type,
            @ApiParam(value = "scope") @RequestParam String scope,
            @ApiParam(value = "client_id") @RequestParam String client_id,
            @ApiParam(value = "client_secret") @RequestParam String client_secret) {

        JSONObject body = null;
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", grant_type);
        map.add("scope", scope);
        map.add("client_id", client_id);
        map.add("client_secret", client_secret);

        // Spring的UriComponentsBuilder类。手动连接字符串更清洁，它会为您处理URL编码
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(map);

        HttpEntity httpEntity = new HttpEntity(headers);
        try {
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, JSONObject.class);
            body = responseEntity.getBody();
        } catch (CustomException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            log.error(e.getBody(), e);
        }
        return body;
    }

    @ApiOperation(value = "通过RestTemplate获取数据(POST)，并自定义处理异常")
    @PostMapping("getInfoByPost")
    public JSONObject getInfoByPost(@RequestParam String url, @RequestParam String id) {

        JSONObject body = null;
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", id);

        // Spring的UriComponentsBuilder类。手动连接字符串更清洁，它会为您处理URL编码
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(map);

        HttpEntity httpEntity = new HttpEntity(headers);
        try {
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, httpEntity, JSONObject.class);
            body = responseEntity.getBody();
        } catch (CustomException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            log.error(e.getBody(), e);
        }
        return body;
    }

    @ApiOperation(value = "HmacSHA256 算法解密")
    @GetMapping("decode")
    public String decode() {
        String result = null;
        try {
            String gateway_rtime = String.valueOf(System.currentTimeMillis());
            String gateway_sig = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(mediaType);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            // Authorization:Basic NWYyNWI2ZjZmNTdiNDc0OGE3Y2RlZTM4ZDk3MGJlYzM6MTIzNDU2
            headers.add("gateway_appid", appId);// header参数，应用id
            headers.add("gateway_sig", gateway_sig);// header参数，签名信息
            headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            // Spring的UriComponentsBuilder类。手动连接字符串更清洁，它会为您处理URL编码
            String requestUrl = "http://localhost:8800/auth/check";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUrl).queryParams(map);
            HttpEntity<String> response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, httpEntity, String.class);
            result = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

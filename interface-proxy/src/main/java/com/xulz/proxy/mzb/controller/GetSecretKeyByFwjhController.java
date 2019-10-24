package com.xulz.proxy.mzb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xulz.proxy.commons.RedisUtils;
import com.xulz.proxy.commons.SymmetricEncoderByFwjh;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulz
 * @Description: 获取甘肃省服务聚合平台鉴权
 * @date 2018/11/1717:32
 */

@Api("服务聚合平台接口-获取签名密钥")
@RestController
public class GetSecretKeyByFwjhController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取签名信息gatewaySign
     *
     * @param appId
     * @param appKey
     * @return
     */
    @RequestMapping(value = "/gatewaySignEncode", method = RequestMethod.POST)
    @ApiOperation(value = "获取签名信息gatewaySign")
    public JSONObject gatewaySignEncode(
            @ApiParam(value = "appId", required = true) @RequestParam(value = "") String appId,
            @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {

        String rtime = String.valueOf(System.currentTimeMillis());
        JSONObject jsonObject = new JSONObject();
        try {
            String gatewaySign = com.xulz.webservice.commons.GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, rtime);
            jsonObject.put("gateway_sig", gatewaySign);
//			jsonObject.put("gateway_rtime", rtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * http请求过程(这里为获取token)
     *
     * @param requestUrl       请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）或者是服务调用的url
     * @param requestMethod    获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
     * @param appIdorSecretKey 获取token时该参数为appId；抵用服务时该参数为SecretKey
     * @param currTime         该参数为当前时间
     * @param sign             该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
     * @return
     */
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ApiOperation(value = "获取访问token")
    public Object getToken(
            @ApiParam(value = "url", required = true, defaultValue = "http://10.50.108.42/proxy/auth/token") @RequestParam(value = "") String requestUrl,
            @ApiParam(value = "appId", required = true) @RequestParam(value = "") String appId,
            @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {

        String gateway_rtime = String.valueOf(System.currentTimeMillis());
        String gateway_sig = "";
        try {
            gateway_sig = com.xulz.webservice.commons.GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 返回结果
        String result = "";
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

            headers.add("gateway_appid", appId);// header参数，应用id
            headers.add("gateway_sig", gateway_sig);// header参数，签名信息
            headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)

            Map<String, String> map = new HashMap<>();
            HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);

            HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, formEntity, String.class);
            result = response.getBody();
            if (StringUtils.isNotBlank(result)) {

                JSONObject objects = JSONObject.parseObject(result);
                return objects;
            } else {
                return "获取token错误，请查看响应数据中的错误码和错误信息";
            }
            // 按照类型转换
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 使用授权码（appKey）对返回数据中的body的access_token进行解密（ASE 算法），最终获得真正秘钥的过程。
     *
     * @param appKey
     * @param access_token
     * @return
     */
    @RequestMapping(value = "/getSecretKeyByFwjh", method = RequestMethod.POST)
    @ApiOperation(value = "获取签名密钥secretKey")
    public JSONObject getSecretKeyByFwjh(
            @ApiParam(value = "appKey(授权码)", required = true) @RequestParam(value = "") String appKey,
            @ApiParam(value = "access_token(访问令牌)", required = true) @RequestParam(value = "") String access_token) {

        JSONObject jsonObject = new JSONObject();
        try {
            String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, access_token);
            jsonObject.put("secretKey", secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 使用（appKey）对返回数据中的body的refresh_token进行解密（ASE 算法），最终获得真正秘钥的过程。
     *
     * @param appKey
     * @param refresh_token
     * @return
     */
    @RequestMapping(value = "/getRefreshKeyByFwjh", method = RequestMethod.POST)
    @ApiOperation(value = "获取刷新密钥refreshKey")
    public JSONObject getRefreshKeyByFwjh(
            @ApiParam(value = "appKey(授权码)", required = true) @RequestParam(value = "") String appKey,
            @ApiParam(value = "refresh_token(刷新令牌)", required = true) @RequestParam(value = "") String refresh_token) {

        JSONObject jsonObject = new JSONObject();
        try {

            String refreshKey = SymmetricEncoderByFwjh.AESDncode(appKey, refresh_token);
            jsonObject.put("refreshKey", refreshKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getAuth", method = RequestMethod.POST)
    @ApiOperation(value = "获取访问服务鉴权")
    public Object getAuth(
            @ApiParam(value = "获取token的url", required = true, defaultValue = "http://10.50.108.42/proxy/auth/token") @RequestParam(value = "") String requestUrl,
            @ApiParam(value = "appId", required = true) @RequestParam(value = "") String appId,
            @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {

        // 武威地址
        // http://29.8.101.19:5000/auth/token
        // 内网地址
        // http://10.50.108.42/proxy/auth/token
        String gateway_rtime = String.valueOf(System.currentTimeMillis());
        String gateway_sig = "";
        try {
            gateway_sig = com.xulz.webservice.commons.GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // 返回结果
        String result = "";
        try {
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

            HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);
            HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, formEntity, String.class);
            result = response.getBody();
            if (StringUtils.isNotBlank(result)) {
                JSONObject objects = JSONObject.parseObject(result);

                String code = objects.getString("code");
                String body = objects.getString("body");

                if (StringUtils.isNoneBlank(code, body) && "0".equals(code)) {
                    JSONObject bodyObj = JSONObject.parseObject(body);
                    // 1.获取access_token
                    String accessToken = bodyObj.getString("access_token");
                    // 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
                    String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, accessToken);
                    // 3.获取调用服务realSign
                    String realSign = com.xulz.webservice.commons.GetSecretKeyByFwjh.gatewaySignEncode(appId, secretKey, gateway_rtime);
                    // 封装调用服务需要的参数
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("gateway_appid", appId);
                    jsonObject.put("gateway_sig", realSign);
                    jsonObject.put("gateway_rtime", gateway_rtime);
                    return jsonObject;
                }
                return "获取token错误，请查看响应数据中的错误码和错误信息";
            } else {
                return "获取token错误，请查看响应数据中的错误码和错误信息";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * http请求过程(这里为调用服务)
     *
     * @param requestUrl    请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）或者是服务调用的url
     * @param requestMethod 获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
     * @param gateway_appid 获取token时该参数为appId；抵用服务时该参数为SecretKey
     * @param gateway_rtime 该参数为当前时间
     * @param gateway_sig   该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
     * @return
     */
    @RequestMapping(value = "/getInfoByFwjh", method = RequestMethod.POST)
    @ApiOperation(value = "调用服务接口")
    public Object getInfoByFwjh(
            @ApiParam(value = "服务调用的url", required = true, defaultValue = "http://10.50.108.42/proxy") @RequestParam(value = "") String requestUrl,
            @ApiParam(value = "服务调用的请求方法（POST或GET）", required = true) @RequestParam(value = "") String requestMethod,
            @ApiParam(value = "参数json类型", required = true) @RequestParam(value = "") String jsonStr,
            @ApiParam(value = "gateway_rtime", required = true) @RequestParam(value = "") String gateway_rtime,
            @ApiParam(value = "gateway_appid", required = true) @RequestParam(value = "") String gateway_appid,
            @ApiParam(value = "gateway_sig", required = true) @RequestParam(value = "") String gateway_sig) {
        //请求方式处理
        HttpMethod httpMethod = null;
        if (requestMethod.trim().equalsIgnoreCase("post")) {
            httpMethod = HttpMethod.POST;
        } else if (requestMethod.trim().equalsIgnoreCase("get")) {
            httpMethod = HttpMethod.GET;
        } else {
            return "请输入请求方式（GET 或 POST）";
        }
        /*参数校验开始*/
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(jsonStr);
        } catch (Exception e) {
            return "请输入json格式数据";
        }
        if (null == jsonObject) {
            return "参数校验失败";
        }
        /*参数校验结束*/

        // 返回结果
        String result = "";
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(mediaType);
//			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

            headers.add("gateway_appid", gateway_appid);// header参数，应用id
            headers.add("gateway_sig", gateway_sig);// header参数，签名信息
            headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)
            /** 参数 **/
            // RestTemplate在postForObject时，不可使用HashMap。而应该是MultiValueMap
//			MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
            //{"xmbm":"1101071703080101"}
//			params.add("userId", "10001");
//			params.add("xmbm","1101071703080101");

            // RestTemplate在exchange时，不可使用MultiValueMap
//			Map<String, Object> params = new HashMap<>();
//			params.put("xmbm","1101071703080101");
            Map<String, Object> params = (Map<String, Object>) jsonObject;

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);

            HttpEntity<String> response = restTemplate.exchange(requestUrl, httpMethod, requestEntity, String.class);
//			HttpEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:8890/getUser3", requestEntity ,String.class);
//			HttpEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:8890/getUser3", params ,String.class);
            result = response.getBody();
            // 按照类型转换
        } catch (Exception e) {
            e.printStackTrace();
            return "请求异常";
        }

        String firstChart = result.trim().substring(0, 1);
        if ("{".equals(firstChart)) {
            JSONObject objects = JSONObject.parseObject(result);
            return objects;
        } else if ("[".equals(firstChart)) {
            JSONArray array = JSONArray.parseArray(result);
            return array;
        } else {
            return "格式转换错误";
        }
    }

    /**
     * http请求过程(这里为调用服务)
     *
     * @param requestUrl    请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）或者是服务调用的url
     * @param requestMethod 获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
     * @return
     */
//	@RequestMapping(value = "/getInfoByFwjh1", method = RequestMethod.POST)
    @ApiOperation(value = "调用服务接口")
    public Object getInfoByFwjh1(
            @ApiParam(value = "服务调用的url", required = true, defaultValue = "http://10.50.108.42/proxy") @RequestParam(value = "") String requestUrl,
            @ApiParam(value = "服务调用的请求方法（POST或GET）", required = true) @RequestParam(value = "") String requestMethod,
            @ApiParam(value = "gateway_rtime", required = true) @RequestParam(value = "") String gateway_rtime,
            @ApiParam(value = "gateway_appid", required = true) @RequestParam(value = "") String gateway_appid,
            @ApiParam(value = "gateway_sig", required = true) @RequestParam(value = "") String gateway_sig) {

        JSONObject serviceJsonData = null;
        try {
            serviceJsonData = com.xulz.proxy.commons.HttpUtils.httpRequest(requestUrl, requestMethod, gateway_appid, gateway_rtime,
                    gateway_sig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceJsonData;

    }

}

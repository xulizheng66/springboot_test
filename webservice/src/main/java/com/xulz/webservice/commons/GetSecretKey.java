package com.xulz.webservice.commons;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulz
 * @Description: 根据密钥（appsecret）对 rid，sid 和 rtime 进行 hmacsha256 计算并进行 base64 转码获得 sign 值
 * @date 2018/11/1715:28
 */
public class GetSecretKey {

    static String url = "http://59.255.104.184:8181/sysapi/auth/refreshappsecret";
    static String rtime = String.valueOf(System.currentTimeMillis());
    static String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-228";
    static String sid = "s_1200000900000_2109";
    static String appKey = "52885d4b5f0faf1699e23efd7f480728";

    /**
     * 根据密钥（appsecret）对 rid，sid 和 rtime 进行 hmacsha256 计算并进行 base64 转码获得 sign 值。
     *
     * @param sid    服务编码
     * @param rid    请求者身份标识
     * @param rtime  请求时间戳
     * @param appKey 服务授权码
     * @return
     */
    public static String getSign(String sid, String rid, String rtime, String appKey) {


        String sign = null;
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appKey.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            System.out.println("rtime--------------------------------"+rtime);

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes),
                    "UTF-8");
            System.out.println("OUTPUT: " + sign);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 根据 rid ,sid , rtime , sign 加密后的密钥（secret）和 密钥的有效期（secretEndTime）
     *
     * @param url
     * @param rid
     * @param sid
     * @param rtime
     * @param sign
     * @return
     */
    public static String getSecretKey(String url, String rid, String sid, String rtime, String sign) {
        RestTemplate restTemplate = new RestTemplate();
        //参数
        Map<String, String> map = new HashMap();
        map.put("gjgxjhpt_rid", rid);
        map.put("gjgxjhpt_sid", sid);
        map.put("gjgxjhpt_rtime", rtime);
        map.put("gjgxjhpt_sign", sign);

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = restTemplate.exchange(url,
                HttpMethod.POST, formEntity, String.class);

        String secretKey = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(secretKey);
        String code = jsonObject.getString("code");// "0"表示成功
        String data = jsonObject.getString("data");

        if ("0".equals(code)) {
            JSONObject jsonObj = JSONObject.parseObject(data);
            String secret = jsonObj.getString("secret");
            String secretEndTime = jsonObj.getString("secretEndTime");
            System.out.println("-----------------" + rtime);

            return secret;
        } else {
            return "系统异常，获取签名失败";
        }
    }

    /**
     * 根据自己的 APPKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥secretKey。
     *
     * @param appkey
     * @param secretKey
     * @return
     */
    public static String getRealSecretKey(String appkey, String secretKey) {
        String realSecretKey = SymmetricEncoder.AESDncode(appkey, secretKey);
        System.out.println("+++++++++++++++++++++"+realSecretKey);
        return realSecretKey;
    }

}

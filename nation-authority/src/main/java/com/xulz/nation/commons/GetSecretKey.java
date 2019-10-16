package com.xulz.nation.commons;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
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
@Log4j2
public class GetSecretKey {

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
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (RestClientException e) {
            log.error("接口调用异常[{}]", e.getMessage());
            return null;
        }
        String secretKey = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(secretKey);
        // "0"表示成功
        String code = jsonObject.getString("code");
        String data = jsonObject.getString("data");
        if ("0".equals(code)) {
            JSONObject jsonObj = JSONObject.parseObject(data);
            String secret = jsonObj.getString("secret");
            //密钥有效期
            String secretEndTime = jsonObj.getString("secretEndTime");
            log.info("第一次secret：---->" + secret);
            log.info("生成密钥时间rtime：---->" + rtime);
            return secret;
        } else if ("-1".equals(code) || "E-106".equals(code)) {
            //{"code":"-1","data":"","message":"接口调用失败"}
            //请求不存在于数据库授权列表中
            return "notExits";
        } else {
            //系统异常
            return null;
        }

    }

    /**
     * 根据自己的 APPKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥secretKey。
     *
     * @param appkey
     * @param secretKey
     * @return
     */
    public static String getRealSecretKey(String rtime, String rid, String sid, String appkey, String secretKey) {

        System.out.println("rtime====>" + rtime);
        System.out.println("rid====>" + rid);
        System.out.println("sid====>" + sid);
        System.out.println("appkey====>" + appkey);
        System.out.println("secretKey====>" + secretKey);
        String appsecret = SymmetricEncoder.AESDncode(appkey, secretKey);

        String realSecretKey = "";
        try {

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString
                    .getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes),
                    "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrttime:"
                    + rtime + "\nsign:" + sign);

            realSecretKey = sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sign:+++++++++++++++++++++" + realSecretKey);
        return realSecretKey;
    }

}

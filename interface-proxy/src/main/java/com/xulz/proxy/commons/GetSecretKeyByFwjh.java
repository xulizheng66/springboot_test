package com.xulz.webservice.commons;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class GetSecretKeyByFwjh {

    /**
     * 根据 密 钥 （appKey或secretKey或refreshKey） 对 gateway_appid和 gateway_rtime 进行 hmacsha256 计算并进行 base64 转码获 得 gateway_sig 值的过程。
     * <p>
     * 密钥生成【核心网关】
     *
     * @param appKey 授权码
     * @param appId  请求者标识
     * @param rtime  请求时间戳
     * @return 加密后的sign
     */
    public static String gatewaySignEncode(String appId, String appKey, String rtime) throws Exception {
        String inputString = appId + rtime;
        return encode(appKey, inputString);
    }

    private static String encode(String appKey, String inputStr) throws Exception {
        String sign;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = appKey.getBytes("UTF-8");
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
        byte[] hmacSha256Bytes = hmacSha256.doFinal(inputStr.getBytes("UTF-8"));
        sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
        return sign;
    }


}

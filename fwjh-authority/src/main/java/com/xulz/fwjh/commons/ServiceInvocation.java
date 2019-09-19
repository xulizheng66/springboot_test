package com.xulz.fwjh.commons;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Description: TODO 服务聚合平台获取token
 * @Author xulz
 * @Date 2019/7/5 10:20
 */
public class ServiceInvocation {

    /**
     * 密钥生成【核心网关】
     *
     * @param appKey 授权码
     * @param appId  请求者标识
     * @param rtime  请求时间戳
     * @return 加密后的sign，即head请求参数的gateway_sig
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

    /**
     * http请求过程
     *
     * @param requestUrl       请求的url，在服务调用过程中url为获取token的url（格式为ip:port/proxy/auth/token）或者是服务调用的url
     * @param requestMethod    获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
     * @param appIdorSecretKey 获取token时该参数为appId；抵用服务时该参数为SecretKey
     * @param currTime         该参数为当前时间
     * @param sign             该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
     * @return
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String appIdorSecretKey,
                                         String currTime, String sign) throws Exception {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod(requestMethod);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestProperty("gateway_appid", appIdorSecretKey);
            httpUrlConnection.setRequestProperty("gateway_rtime", currTime);
            httpUrlConnection.setRequestProperty("gateway_sig", sign);
            httpUrlConnection.connect();
            inputStream = httpUrlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferReader.readLine()) != null) {
                buffer.append(str);
            }
            String buffer2str = buffer.toString();
            System.out.println("响应数据：" + buffer2str);
            jsonObject = JSONObject.parseObject(buffer2str);
            bufferReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConnection.disconnect();
        } catch (ConnectException ce) {
            System.out.println("Server connect time out!");
            throw new ConnectException();
            // ce.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("ioexception request error");
            throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("http request error!");
            throw new Exception();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 解密 解密过程： 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器
     * 5.将加密后的字符串反纺成byte[]数组 6.将加密内容解密
     */
    public static String AESDncode(String encodeRules, String content) {
        // 初始化向量,必须16位
        String ivStr = "AESCBCPKCS5Paddi";
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 新增下面两行，处理 Linux 操作系统下随机数生成不一致的问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            //// 指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivStr.getBytes("UTF-8")));
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        // 如果有错就返加null
        return null;
    }

    public static void main(String[] args) throws Exception {
        // 参数设定
        String appId = "854f38091dad47d4ae472b40c4e7bb09";
        String appKey = "ODU0ZjM4MDkxZGFkNDdkNGFlNDcyYjQwYzRlN2JiMDk6MTIzNDU2";
        String currTime = System.currentTimeMillis() + "";
        // 生成access_token秘钥的url
        String generateTokenUrl = "http://10.18.100.5:5000/auth/token";
        // 服务调用的url
        String invokingServiceUrl = "http://10.18.100.5:5000/api/www.baidu.com";
        // 服务调用的请求方法
        String invokingServiceRequestMethod = "GET";
        // 1.获取access_token。
        String sign = gatewaySignEncode(appId, appKey, currTime);
        JSONObject jsonObject = httpRequest(generateTokenUrl, "POST", appId, currTime, sign);
        JSONObject jsonObjectBody = JSONObject.parseObject(jsonObject.getString("body"));
        if (jsonObjectBody != null) {
            String accessToken = jsonObjectBody.getString("access_token");
            // 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
            String secretKey = AESDncode(appKey, accessToken);
            // 3.调用服务，获取服务的json数据。
            String sign1 = gatewaySignEncode(appId, secretKey, currTime);
            JSONObject serviceJsonData = httpRequest(invokingServiceUrl, invokingServiceRequestMethod, appId, currTime, sign1);
            String codeValue = serviceJsonData.getString("code");
            if (codeValue.equals("200")) {
                System.out.println("服务数据结果:" + serviceJsonData.toString());
            } else {
                System.out.println("获取服务失败，请查看响应数据中的错误码和错误信息。");
            }
        } else {
            System.out.println("获取token错误，请查看响应数据中的错误码和错误信息。");
        }

    }

}

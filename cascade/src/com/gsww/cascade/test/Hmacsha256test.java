package com.gsww.cascade.test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Hmacsha256test {
    public static void main(String args[]) {

        String sid = "s_sys_service001";
        String rid = "428f29b5-4ba7-4631-b5a7-c6ce636dfefe";
        String rtime = "" + System.currentTimeMillis();
        String appsecret = "e82336fa65b2d4e656fad6cfd5cae04c";

        String result = null;

        try {

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            result = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("OUTPUT: " + result);

        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

}

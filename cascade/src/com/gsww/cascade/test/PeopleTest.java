package com.gsww.cascade.test;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.gsww.cascade.utils.HttpUtils;
import com.gsww.cascade.utils.TimeHelper;
import com.gsww.cascade.utils.WebClient;

public class PeopleTest {
    public static void main(String[] args) {
//		refreshappsecret();
        testBWWebservice();
    }

    @SuppressWarnings("finally")
    public static String refreshappsecret() {
        String resultStr = "";
        // 公安部基准核查
        String url = "http://59.255.105.32:8181/sysapi/auth/refreshappsecret";
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-228";
        String sid = "s_1200000900000_2108";
        String appsecret = "d6183543a33b4e73848eb86cc701be36";
        String sign = "";

        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length,
                    "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString
                    .getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("OUTPUT: " + sign);

            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:"
                    + rtime + "\nsign:" + sign);
            Map<String, String> map = new HashMap();
            map.put("gjgxjhpt_rid", rid);
            map.put("gjgxjhpt_sid", sid);
            map.put("gjgxjhpt_rtime", rtime);
            map.put("gjgxjhpt_sign", sign);
            resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign,
                    map);
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultStr;
        }

    }


    @SuppressWarnings("finally")
    public static void testBWWebservice() {
        String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-224";
//		String sid = "s_1200000900000_2108";
        String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-214";
        String sid = "s_1200000900000_2108";
        try {
            SymmetricEncoder se = new SymmetricEncoder();
            String appsecret = se.AESDncode("d73eb5464bdf8c67de59ecf160a1f7ca", "GneUTO9lYEHCWVicmcdUMsw/9mC2mluOZxB3feCV8mFew5yDrHBHPykcy2mUfbF/");

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length,
                    "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString
                    .getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes),
                    "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrttime:"
                    + rtime + "\nsign:" + sign);

//			System.out.println("date="
//					+ TimeHelper.getCurrentTime().replaceAll("-", "")
//							.replaceAll(" ", "").replaceAll(":", ""));

            String url = "http://59.255.105.32:8181/wsproxy";
            Map<String, String> map = new HashMap<String, String>();
//			map.put("number1", "1");
//			map.put("number2", "2");
            //调用接口
            String resultStr = WebClient.getSoapXML(url,
                    "http://ws.apache.org/axis2", "sum", map, rid, sid, rtime,
                    sign);

            resultStr = resultStr.replaceAll("&gt;", ">");
            resultStr = resultStr.replaceAll("&lt;", "<");
            System.out.println("resultStr=============" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.gsww.cascade.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.gsww.cascade.utils.HttpUtils;
import com.gsww.cascade.utils.JSONUtil;
import com.gsww.cascade.utils.WebClient;

public class InterfaceTestQinghai {

    public static void main(String[] args) {
//		testList();
//		testDetail();
        registerInterfaceRest();
//		registerInterfaceWebservice();
//		refreshappsecret();
//		testBWRest();
//		testBJRest();
//		testBWWebservice();
        testBJWebservice();

    }

    public static void testList() {
        String cnName = "1";
        String cataId = "001";
        String groupIds = "";
        String sortBy = "";
        String sortType = "";
        String pageNo = "1";
        String pageSize = "10";
        String url = "http://111.200.200.54:11004/sysapi/service/getlist";
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_sys_service001";
        String rtime = String.valueOf(System.currentTimeMillis());
        String appsecret = "5010ff49c3aea83b19c3b31c32f8373f";
        String sign = null;

        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);
            Map<String, String> map = new HashMap();
            String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, map);
            System.out.println("resultStr=================" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testDetail() {
        String url = "http://111.200.200.54:11004/sysapi/service/getdetail";
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_sys_service002";
        String rtime = String.valueOf(System.currentTimeMillis());
        String appsecret = "5010ff49c3aea83b19c3b31c32f8373f";
        String sign = null;

        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);
            Map<String, String> map = new HashMap();
            map.put("code", "s_1200000900000_2000");
            String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, map);
            System.out.println("resultStr=================" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("finally")
    public static String registerInterfaceRest() {
        String url = "http://111.200.200.54:11004/sysapi/service/register";
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_sys_service003";
        String appsecret = "5010ff49c3aea83b19c3b31c32f8373f";
        String sign = null;
        String resultStr = "";
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");

            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);

            Map<String, Object> map = new HashMap();
            map.put("cnName", "青海_注册Rest服务接口");
            map.put("enName", "qinghai_resttest");
            map.put("inType", "154");
            map.put("idSel", "1100,11000002");
            map.put("abst", "qinghaitest");
            map.put("serviceDesc", "青海rest接口测试");
            map.put("cataId", "151038276112977747 9");
            map.put("cata_title", "工程建设招投标交易公告信息");
            map.put("groupids", "217001");
            map.put("registerId", "qinghai");
            map.put("register", "青海省");
            map.put("deptId", "3017cba2-1eb4-4dce-b123-d17ffc71db81");
            map.put("dept", "青海省");
            map.put("serviceDeptId", "3017cba2-1eb4-4dce-b123-d17ffc71db81");
            map.put("serviceDeptName", "青海省");
            map.put("reqRate", "1000次/小时");
            map.put("reqTimeout", "6");
            map.put("isPublic", "322");
            map.put("url", "http://168.1.15.195:8989/helloDemo/rest/Greeting");
            map.put("serviceType", "11");
            List list = new ArrayList();
            Map m = new HashMap();
            m.put("methodName", "reeting");
            m.put("methodDes", "rest test");
            m.put("methodType", "POST");
            m.put("soapAction", "");
            m.put("responseType", "312");
            m.put("outputSuccess", "{\"code\":0,\"data\":\"2017-12-13 14:57:04\"}");
            m.put("outputFailure", "{\"code\":-1,\"data\":\"failure\"}");

            List paraList = new ArrayList();
            Map m1 = new HashMap();
            m1.put("isRequired", true);
            m1.put("defaultValue", "");
            m1.put("name", "username");
            m1.put("position", "303");
            m1.put("describe", "username");
            m1.put("type", "306");

            paraList.add(m1);
            m.put("inputParams", paraList);

            list.add(m);
            map.put("methods", list);
            map.put("callExample", "");
            map.put("department", "青海省共享交换平台");
            map.put("departmentPerson", "范建飞");
            map.put("departmentTelephone", "18919060219");
            map.put("departmentAddress", "青海省");
            System.out.println("json=========" + JSONUtil.writeMapJSON(map));
            resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(map));
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultStr;
        }
    }


    @SuppressWarnings("finally")
    public static String registerInterfaceWebservice() {
        String url = "http://59.255.104.79:8181/sysapi/service/register";
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_sys_service003";
        String appsecret = "5010ff49c3aea83b19c3b31c32f8373f";
        String sign = null;
        String resultStr = "";
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");

            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);

            Map<String, Object> map = new HashMap();
            map.put("cnName", "青海_测试Webservice服务接口1");
            map.put("enName", "qinghai_webservice_test1");
            map.put("inType", "10");
            //map.put("idSel", "1100,11000002");
            map.put("abst", "qinghaitest");
            map.put("serviceDesc", "青海Webservice接口测试");
            map.put("cataId", "1510382761129777479");
            map.put("cata_title", "工程建设招投标交易公告信息");
            map.put("groupids", "217001");
            map.put("registerId", "qinghai");
            map.put("register", "青海省");
            map.put("deptId", "3017cba2-1eb4-4dce-b123-d17ffc71db81");
            map.put("dept", "青海省");
            map.put("serviceDeptId", "3017cba2-1eb4-4dce-b123-d17ffc71db81");
            map.put("serviceDeptName", "青海省");
            map.put("reqRate", "1000次/小时");
            map.put("reqTimeout", "6");
            map.put("isPublic", "322");
            map.put("url", "http://168.1.15.196:8989/helloDemo/webservice/Sum?wsdl");
            map.put("serviceType", "11");
            List list = new ArrayList();
            Map m = new HashMap();
            m.put("methodName", "sum");
            m.put("methodDes", "求和");
            m.put("methodType", "POST");
            m.put("soapAction", "");
            m.put("responseType", "312");
            m.put("outputSuccess", "");
            m.put("outputFailure", "");

            List paraList = new ArrayList();
            Map m1 = new HashMap();
            m1.put("isRequired", true);
            m1.put("defaultValue", "");
            m1.put("name", "number1");
            m1.put("position", "303");
            m1.put("describe", "数字1");
            m1.put("type", "306");

            Map m2 = new HashMap();
            m2.put("isRequired", true);
            m2.put("defaultValue", "");
            m2.put("name", "number2");
            m2.put("position", "303");
            m2.put("describe", "数字2");
            m2.put("type", "306");

            paraList.add(m1);
            paraList.add(m2);
            m.put("inputParams", paraList);

            list.add(m);
            map.put("methods", list);
            map.put("callExample", "");
            map.put("department", "青海省共享交换平台");
            map.put("departmentPerson", "范建飞");
            map.put("departmentTelephone", "18919060219");
            map.put("departmentAddress", "青海省");
            System.out.println("json=========" + JSONUtil.writeMapJSON(map));
            resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(map));
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultStr;
        }
    }

    @SuppressWarnings("finally")
    public static String refreshappsecret() {
        String resultStr = "";
        //公安部_公安部REST
        String url = "http://111.200.200.54:11004/sysapi/auth/refreshappsecret";
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_1200000900000_2001";
        String appsecret = "996d5136447d3058dca83e14e6860213";
        String sign = "";

        //公安部_公安部REST
//		String url = "http://111.200.200.54:11004/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
//		String sid = "s_1200000900000_2000";
//		String appsecret = "8eb6e174b7de2b6a00536eef00c8d633";
//		String sign = "";

        //天津REST
//		String url = "http://111.200.200.54:11004/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
//		String sid = "s_2712000000000_2007";
//		String appsecret = "4b914084a5d33668a7634a0f9a249e0f";
//		String sign = "";

        //天津ws
//		String url = "http://111.200.200.54:11004/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
//		String sid = "s_2712000000000_2010";
//		String appsecret = "7515b938951eee1b6c7ed12955f8c702";
//		String sign = "";

        //北京市_北京_REST测试服务接口
//		String url = "http://111.200.200.54:11007/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "x2019";
//		String sid = "s_2711000000000_2021";
//		String appsecret = "3bf3b3749c2b19222c21359d123a6c41";
//		String sign = "";
        //部委ws
//		String url = "http://111.200.200.54:11007/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "x2019";
//		String sid = "s_2711000000000_2021";
//		String appsecret = "3bf3b3749c2b19222c21359d123a6c41";
//		String sign = "";

        //北京ws接口测试
//		String url = "http://111.200.200.54:11007/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "x2019";
//		String sid = "s_2711000000000_2013";
//		String appsecret = "1782a8c072d2bee94a1b39f5420ee4d2";
//		String sign = "";

        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);

            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("OUTPUT: " + sign);

            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);
            Map<String, String> map = new HashMap();
            map.put("rid", rid);
            map.put("sid", sid);
            map.put("rtime", rtime);
            map.put("sign", sign);
            resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, map);
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultStr;
        }

    }

    /**
     * 部委rest接口服务调用测试
     *
     * @return
     */
    @SuppressWarnings("finally")
    public static void testBWRest() {
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_1200000900000_2001";
        try {
            SymmetricEncoder se = new SymmetricEncoder();
            String appsecret = se.AESDncode("996d5136447d3058dca83e14e6860213", "UdUiC+EPeWpp2P+CbW1dBhhy0tUFExdbvOecZYFK3B6dJHAJe8fxL1F1/YlXXMuv");
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);
            String url = "http://111.200.200.54:11004/httpproxy";
            Map reqMap = new HashMap();
            String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, reqMap);
//			String resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(reqMap));
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 北京rest接口服务调用测试
     *
     * @return
     */
    @SuppressWarnings("finally")
    public static void testBJRest() {
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_2712000000000_2007";
        try {
            SymmetricEncoder se = new SymmetricEncoder();
            String appsecret = se.AESDncode("4b914084a5d33668a7634a0f9a249e0f", "1jN4aL+VOnkEhL1T7tELiZnKU2hrqTO8PUdCjnOj2PLvD7+rEChRRxRrbymNFp3q");
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrtTime:" + rtime + "\nsign:" + sign);
            String url = "http://111.200.200.54:11003/httpproxy";
            Map reqMap = new HashMap();
            reqMap.put("name", "qinghai");
//			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, reqMap);
            String resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(reqMap));
            System.out.println("resultStr========" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("finally")
    public static void testBWWebservice() {
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_1200000900000_2000";
        try {
            SymmetricEncoder se = new SymmetricEncoder();
            String appsecret = se.AESDncode("8eb6e174b7de2b6a00536eef00c8d633", "vTfhlCte1SEjm2YOFgamSCaZXBfwOz+Mk1vJ+zRBtvxOnOX7SZymwYuRSgnwqrlA");
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrttime:" + rtime + "\nsign:" + sign);
            String url = "http://111.200.200.54:11004/wsproxy";
            Map<String, String> map = new HashMap<String, String>();
            map.put("number1", "1");
            map.put("number2", "2");
            String resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map, rid, sid, rtime, sign);
            System.out.println("resultStr=============" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("finally")
    public static void testBJWebservice() {
        String rtime = String.valueOf(System.currentTimeMillis());
        String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
        String sid = "s_2712000000000_2010";
        try {
            SymmetricEncoder se = new SymmetricEncoder();
            String appsecret = se.AESDncode("7515b938951eee1b6c7ed12955f8c702", "Ap+HFxekEoCixza7ibhtn6SoLUP1PK2h1AEKE0kcXSRtuU0uT3v0J0tCGV2ft8yu");
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appsecret.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String inputString = sid + rid + rtime;
            System.out.println("INPUT: " + inputString);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
            String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            System.out.println("rid:" + rid + "\nsid:" + sid + "\nrttime:" + rtime + "\nsign:" + sign);
            String url = "http://111.200.200.54:11004/wsproxy";
            Map<String, String> map = new HashMap<String, String>();
            map.put("number1", "1");
            map.put("number2", "2");
            String resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map, rid, sid, rtime, sign);
            System.out.println("resultStr=============" + resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

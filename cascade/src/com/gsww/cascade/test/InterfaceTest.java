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

public class InterfaceTest {

	public static void main(String[] args) {
//		testList();
//		testDetail();
//		registerInterfaceRest();
//		registerInterfaceWebservice();
//		refreshappsecret();
//		testBWRest();
//		testBJRest();
		testBWWebservice();
//		testBJWebservice();
		
	}

	public static void testList() {
		String cnName = "1";
		String cataId = "001";
		String groupIds = "";
		String sortBy = "";
		String sortType = "";
		String pageNo = "1";
		String pageSize = "10";
		String url = "http://111.200.200.54:11007/sysapi/service/getlist";
		String rid = "428f29b5-4ba7-4631-b5a7-c6ce636dfefe";
		String sid = "s_sys_service001";
		String rtime = String.valueOf(System.currentTimeMillis());
		String appsecret = "e82336fa65b2d4e656fad6cfd5cae04c";
		String sign = null;

		try {
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);

			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			Map<String, String> map = new HashMap();
			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, map);
			System.out.println("resultStr=================" + resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testDetail() {
		String url = "http://111.200.200.54:11007/sysapi/service/getdetail";
		String rid = "428f29b5-4ba7-4631-b5a7-c6ce636dfefe";
		String sid = "s_sys_service002";
		String rtime = String.valueOf(System.currentTimeMillis());
		String appsecret = "e82336fa65b2d4e656fad6cfd5cae04c";
		String sign = null;

		try {
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);

			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			Map<String, String> map = new HashMap();
			map.put("code", "s_1200000900000_2001");
			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, map);
			System.out.println("resultStr=================" + resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public static String registerInterfaceRest() {
		String url = "http://59.255.104.79:8181/sysapi/service/register";
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_sys_service003";
		String appsecret = "c1b7dc0f41976fe1989e63fc46de336c";
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
			
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			
			Map<String, Object> map = new HashMap();
			map.put("cnName", "甘肃_测试Rest服务接口");
			map.put("enName", "gansu_resttest");
			map.put("inType", "154");
//			map.put("idSel", "1100,11000002");
			map.put("abst", "gansutest");
			map.put("serviceDesc", "甘肃rest接口测试");
			map.put("cataId", "1527233663092703276");
			map.put("cata_title", "全流程测试有条件共享目录1");
			map.put("groupids", "307011024701");
			map.put("registerId","gansu");
			map.put("register","甘肃");
			map.put("deptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("dept","甘肃省");
			map.put("serviceDeptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("serviceDeptName","甘肃省");
			map.put("isPublic","322");
			map.put("url","http://59.219.205.145/helloDemo/rest/Greeting");
			map.put("serviceType","11");
			List list = new ArrayList();
			Map m = new HashMap();
			m.put("methodName", "sum");
			m.put("methodDes", "sum test");
			m.put("methodType", "POST");
			m.put("soapAction", "");
			m.put("responseType","312");
			m.put("outputSuccess","");
			m.put("outputFailure","");
			
			List paraList = new ArrayList();
			Map m1 = new HashMap();
			m1.put("isRequired",true);
			m1.put("defaultValue","");
			m1.put("name","username");
			m1.put("position","303");
			m1.put("describe","username");
			m1.put("type","306");
			
			paraList.add(m1);
			m.put("inputParams",paraList);
			
			list.add(m);
			map.put("methods", list);
			map.put("callExample","");
			map.put("department","甘肃省共享交换平台");
			map.put("departmentPerson","范建飞");
			map.put("departmentTelephone","18919060219");
			map.put("departmentAddress","甘肃省");
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
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_sys_service003";
		String appsecret = "c1b7dc0f41976fe1989e63fc46de336c";
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
			
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			
			Map<String, Object> map = new HashMap();
			map.put("cnName", "甘肃_测试Webservice服务接口1");
			map.put("enName", "gansu_webservice_test1");
			map.put("inType", "10");
			map.put("idSel", "1100,11000002");
			map.put("abst", "gansutest");
			map.put("serviceDesc", "甘肃Webservice接口测试");
			map.put("cataId", "1527234029218254223");
			map.put("cata_title", "全流程测试有条件共享目录2");
			map.put("groupids", "307011024701");
			map.put("registerId","gansu");
			map.put("register","甘肃");
			map.put("deptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("dept","甘肃省");
			map.put("serviceDeptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("serviceDeptName","甘肃省");
			map.put("isPublic","322");
			map.put("url","http://59.219.205.145/helloDemo/webservice/Sum?wsdl");
			map.put("serviceType","11");
			List list = new ArrayList();
			Map m = new HashMap();
			m.put("methodName", "sum");
			m.put("methodDes", "求和");
			m.put("methodType", "POST");
			m.put("soapAction", "");
			m.put("responseType","312");
			m.put("outputSuccess","");
			m.put("outputFailure","");
			
			List paraList = new ArrayList();
			Map m1 = new HashMap();
			m1.put("isRequired",true);
			m1.put("defaultValue","");
			m1.put("name","number1");
			m1.put("position","303");
			m1.put("describe","数字1");
			m1.put("type","306");
			
			Map m2 = new HashMap();
			m2.put("isRequired",true);
			m2.put("defaultValue","");
			m2.put("name","number2");
			m2.put("position","303");
			m2.put("describe","数字2");
			m2.put("type","306");
			
			paraList.add(m1);
			paraList.add(m2);
			m.put("inputParams",paraList);
			
			list.add(m);
			map.put("methods", list);
			map.put("callExample","");
			map.put("department","甘肃省共享交换平台");
			map.put("departmentPerson","范建飞");
			map.put("departmentTelephone","18919060219");
			map.put("departmentAddress","甘肃省");
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
		//公安部_REST-Hello
//		String url = "http://111.200.200.54:11007/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "x2019";
//		String sid = "s_1200000900000_2001";
//		String appsecret = "37dc05b566a512ece76dd6213f67f331";
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
		String url = "http://59.255.104.79:8181/sysapi/auth/refreshappsecret";
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "3599e118-8fe7-46a6-9145-6604d3e16f4a";
		String sid = "s_X01500000000000000_2199";
		String appsecret = "eee1670cc48ed3893e2791f9b6de6a18";
		String sign = "";

		try {
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);

			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("OUTPUT: " + sign);
			
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			Map<String, String> map = new HashMap();
			map.put("gjgxjhpt_rid", rid);
			map.put("gjgxjhpt_sid", sid);
			map.put("gjgxjhpt_rtime", rtime);
			map.put("gjgxjhpt_sign", sign);
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
	 * @return
	 */
	@SuppressWarnings("finally")
	public static void testBWRest() {
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "x2019";
		String sid = "s_1200000900000_2001";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("37dc05b566a512ece76dd6213f67f331", "yQftZ1NT3L6loRbwRxrtkRyhbf1DCtou4SseHKqRnCddFkLbayXYRu6VWp2v9SQf");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			String url = "http://111.200.200.54:11003/httpproxy";
			Map reqMap = new HashMap();
			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, reqMap);
//			String resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(reqMap));
			System.out.println("resultStr========" + resultStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 北京rest接口服务调用测试
	 * @return
	 */
	@SuppressWarnings("finally")
	public static void testBJRest() {
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "3599e118-8fe7-46a6-9145-6604d3e16f4a";
		String sid = "s_X01500000000000000_2199";
		//String appsecret = "71b1ae6ef776b7295f7613029cf86a05";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("eee1670cc48ed3893e2791f9b6de6a18", "TZmRhoAEjxdUKCVDLJ1l1f3j4iyEHf/Nz4thI2hZFd5cHNeuHdWIlOLrZgiwJxI7");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			String url = "http://59.255.104.79:8181/httpproxy";
//			String url = "http://59.207.107.133:8080/helloDemo/rest/Greeting";
//			String url = "http://127.0.0.1:8081/camel/restTest";
//			String url = "http://59.207.107.140:8080/trans/camel/rest_Greeting";
			Map reqMap = new HashMap();
			reqMap.put("userName", "张三");
//			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, reqMap);
			String resultStr = HttpUtils.getResPostJson(url, rid, sid, rtime, sign, JSONUtil.writeMapJSON(reqMap));
			System.out.println("resultStr========" + resultStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public static void testBWWebservice() {
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "x2019";
		String sid = "s_1200000900000_2003";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("02c8d9e3d3bacc128cf4b2159e718686", "qVhJhda0uvyqfplV2bTEksq/3hmP9n2FRynVc/qoHijLGAqovgSHcvc5VkY296AH");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrttime:" + rtime+"\nsign:" + sign);
			String url = "http://111.200.200.54:11003/wsproxy";
			Map<String,String> map = new HashMap<String,String>();
			map.put("number1","1");
			map.put("number2","2");
			String resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map,rid,sid,rtime,sign);
			System.out.println("resultStr=============" + resultStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("finally")
	public static void testBJWebservice() {
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "428f29b5-4ba7-4631-b5a7-c6ce636dfefe";
		String sid = "s_X04100000000000000_2143";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("b5271d305d4d1c7c6431a657231d29b7", "c41I4B+y1wQqphabqGcSGdpHPIhtGvHlgvMCdKPQvQWEMwthAxxCifqwLONXRkIe");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrttime:" + rtime+"\nsign:" + sign);
			String url = "http://59.255.104.79:8181/wsproxy";
			Map<String,String> map = new HashMap<String,String>();
			map.put("number1","1");
			map.put("number2","2");
			String resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map,rid,sid,rtime,sign);
			System.out.println("resultStr=============" + resultStr);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

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

public class InterfaceTestGansu {

	public static void main(String[] args) {
//		testList();
//		testDetail();
//		registerInterfaceRest();
//		registerInterfaceWebservice();
//		refreshappsecret();
//		testBWRest();
//		testBJRest();
//		testBWWebservice();
//		testBJWebservice();
		
//		testCatalog();
//		testxzCatalog();
		
		testCatalog();
		
	}
	
	public static void testCatalog(){
		System.out.println("================================甘肃参数");
		String url = "http://59.255.104.79:8181/sysapi";
//		url="http://59.255.104.79:8181/sysapi/service/register";
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
//		String sid = "s_sys_supply002"; //资源申请
//		String sid = "s_sys_service003"; //服务接口资源上报
//		String sid = "s_sys_cloud001"; //库表资源上报
//		String sid = "s_sys_supply001"; //机构上报
//		String sid = "s_sys_supply009";  //应用系统注册
//		String sid = "s_sys_cloud003"; //资源查询
//		String sid="s_sys_problemback001";//目录纠错
		String sid="s_sys_supply003";//
		String rtime = String.valueOf(System.currentTimeMillis());
		String appsecret = "c1b7dc0f41976fe1989e63fc46de336c";
		try{
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
	
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
		}catch(Exception e) {
			
		}
		
	}
	
	public static void testxzCatalog(){
		System.out.println("================================西藏参数");
		String url = "http://59.255.104.79:8181/sysapi";
		String rid = "7aef22a34-7601-4bd2-8470-70151cb46785";
//		String sid = "s_sys_supply002"; //资源申请
//		String sid = "s_sys_service003"; //服务接口资源上报
//		String sid = "s_sys_cloud001"; //库表资源上报
//		String sid = "s_sys_supply001"; //机构上报
//		String sid = "s_sys_supply009";  //应用系统注册
//		String sid = "s_sys_cloud003"; //资源查询
//		String sid="s_sys_problemback001";//目录纠错
		String sid="s_sys_catalog001";//目录分类注册
		String rtime = String.valueOf(System.currentTimeMillis());
		String appsecret = "6bbda514d18b14b8fa6d3b7b7416a83c";
		try{
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
	
			String inputString = sid + rid + rtime;
//			System.out.println("INPUT: " + inputString);
			
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
		}catch(Exception e) {
			
		}
		
	}


	public static void testList() {
		String cnName = "1";
		String cataId = "001";
		String groupIds = "";
		String sortBy = "";
		String sortType = "";
		String pageNo = "1";
		String pageSize = "10";
		String url = "http://59.255.104.79:8181/sysapi/service/getlist";
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
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			Map<String, String> map = new HashMap();
			String resultStr = HttpUtils.getResGetJson(url, rid, sid, rtime, sign, map);
			System.out.println("resultStr=================" + resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testDetail() {
		String url = "http://59.255.104.79:8181/sysapi/service/getdetail";
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
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			Map<String, String> map = new HashMap();
			map.put("code", "s_X03200000000000000_2244");
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
			map.put("cnName", "甘肃_Rest服务接口");
			map.put("enName", "gansu_resttest");
			map.put("inType", "154");
//			map.put("idSel", "1100,11000002");
			map.put("abst", "gansutest");
			map.put("reqRate", "1000次/小时");
			map.put("reqTimeout", "60");
			map.put("serviceDesc", "甘肃rest接口测试");
			map.put("cataId", "1527233663092703276");
			map.put("cata_title", "全流程测试有条件共享目录1");
			map.put("groupids", "307011024701");
			map.put("registerId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("register","甘肃省");
			map.put("deptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("dept","甘肃省");
			map.put("serviceDeptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("serviceDeptName","甘肃省");
			map.put("isPublic","322");
			map.put("url","http://59.219.205.145/helloDemo/rest/Greeting");
			map.put("serviceType","http");
			List list = new ArrayList();
			Map m = new HashMap();
			m.put("methodName", "reeting");
			m.put("methodDes", "rest test");
			m.put("methodType", "POST");
			m.put("soapAction", "test");
			m.put("responseType","312");
			m.put("outputSuccess","{\"code\":0,\"data\":\"2017-12-13 14:57:04\"}");
			m.put("outputFailure","{\"code\":-1,\"data\":\"failure\"}");
			
			List paraList = new ArrayList();
			Map m1 = new HashMap();
			m1.put("isRequired",true);
			m1.put("defaultValue","zhangsan");
			m1.put("name","username");
			m1.put("position","303");
			m1.put("describe","username");
			m1.put("type","306");
			
			paraList.add(m1);
			m.put("inputParams",paraList);
			
			list.add(m);
			map.put("methods", list);
			map.put("callExample","调用示例");
			Map m2 = new HashMap();
			m2.put("codeNum", "E01");
			m2.put("codeName", "调用失败");
			m2.put("codeNum", "权限认证失败");
			List list2 = new ArrayList();
			list2.add(m2);
			map.put("errorCodes",list2);
			map.put("department","甘肃省共享交换平台");
			map.put("departmentPerson","范建飞");
			map.put("departmentTelephone","18919060219");
//			map.put("departmentAddress","甘肃省");
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
			//map.put("idSel", "1100,11000002");
			map.put("abst", "gansutest");
			map.put("serviceDesc", "甘肃Webservice接口测试");
			map.put("cataId", "1510382761129777479");
			map.put("cata_title", "工程建设招投标交易公告信息");
			map.put("groupids", "217001");
			map.put("registerId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("register","甘肃省");
			map.put("deptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("dept","甘肃省");
			map.put("serviceDeptId","7acbccd9-ff09-4d3a-a485-4ac24f94cc5d");
			map.put("serviceDeptName","甘肃省");
			map.put("reqRate", "1000次/小时");
			map.put("reqTimeout", "6");
			map.put("isPublic","322");
			map.put("url","http://168.1.15.196:8989/helloDemo/webservice/Sum?wsdl");
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
		//rest1
//		String url = "http://59.255.104.79:8181/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
//		String sid = "s_1200019900000_2024";
//		String appsecret = "294c30aa4c03c8f51dfdb9f6fb99d845";
//		String sign = "";
		
		//rest2
//		String url = "http://59.255.104.79:8181/sysapi/auth/refreshappsecret";
//		String rtime = String.valueOf(System.currentTimeMillis());
//		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
//		String sid = "s_X1100000011121212X_2302";
//		String appsecret = "38f1931f43985a653e47e2ae2c5d4477";
//		String sign = "";
		
		String url = "http://59.255.104.79:8181/sysapi/auth/refreshappsecret";
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_1200019900000_2022";
		String appsecret = "b9f545a586fc3ae37de85cee580421d8";
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
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_1200019900000_2024";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("294c30aa4c03c8f51dfdb9f6fb99d845", "c+ky8UNH4iJaLl1HPaRERxYcAm+UwM2nVrTkwdnqDJGP4PlZb9YapUZxgFi5/gaw");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			String url = "http://59.255.104.79:8181/httpproxy";
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
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_X1100000011121212X_2302";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("38f1931f43985a653e47e2ae2c5d4477", "7K6y4IRB5wvVUlGJ4/VEJ+yLNDQbRckDcgOe5n9+M85sjrsvT4lngATjxVFxEjzm");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrtTime:" + rtime+"\nsign:" + sign);
			String url = "http://59.255.104.79:8181/httpproxy";
			Map reqMap = new HashMap();
			reqMap.put("name","gansu");
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
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d";
		String sid = "s_1200019900000_2022";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("b9f545a586fc3ae37de85cee580421d8", "Xi2Z5RIGJEo8gTGYIn95INl9RKZrNESPfUcj2YO2xEydVJYIXQMX3xP1J/CI2nkZ");
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
	
	
	@SuppressWarnings("finally")
	public static void testBJWebservice() {
		String rtime = String.valueOf(System.currentTimeMillis());
		String rid = "3017cba2-1eb4-4dce-b123-d17ffc71db81";
		String sid = "s_2712000000000_2010";
		try{
			SymmetricEncoder se = new SymmetricEncoder();
			String appsecret = se.AESDncode("7515b938951eee1b6c7ed12955f8c702", "Ap+HFxekEoCixza7ibhtn6SoLUP1PK2h1AEKE0kcXSRtuU0uT3v0J0tCGV2ft8yu");
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = appsecret.getBytes("UTF-8");
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
			String inputString = sid + rid + rtime;
			System.out.println("INPUT: " + inputString);
			byte[] hmacSha256Bytes = hmacSha256.doFinal(inputString.getBytes("UTF-8"));
			String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
			System.out.println("rid:"+ rid+"\nsid:" + sid+"\nrttime:" + rtime+"\nsign:" + sign);
			String url = "http://111.200.200.54:11004/wsproxy";
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

package com.xulz.webservice.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import com.xulz.webservice.commons.Constants;
import com.xulz.webservice.commons.SendSoapXml;
import com.xulz.webservice.entity.People;

import aj.org.objectweb.asm.ConstantDynamic;

/**
 * 测试web客户端调用类
 * 
 * @author xiexq
 *
 */
public class WebClient {

	public static String getSoapXML(String wsdlLocation, String namespace, String methodName,
			Map<String, String> patameterMap, String rid, String sid, String rtime, String sign, String type ,String whiteListIp,
			People peopleParams) throws Exception {

		URL url = new URL(wsdlLocation);
		URLConnection conn = url.openConnection();
		HttpURLConnection con = (HttpURLConnection) conn;
		
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("gjgxjhpt_rid", rid);
		conn.setRequestProperty("gjgxjhpt_sid", sid);
		conn.setRequestProperty("gjgxjhpt_rtime", rtime);
		conn.setRequestProperty("gjgxjhpt_sign", sign);
//		conn.setRequestProperty("X-Forwarded-For", "59.219.195.114");
//		conn.setRequestProperty("X-Forwarded-For", "59.219.252.226");
//		conn.setRequestProperty("X-Forwarded-For", "59.219.252.226");
//		conn.setRequestProperty("X-Forwarded-For", "59.219.205.162");
		con.setDoInput(true); // 是否有入参
		con.setDoOutput(true); // 是否有出参
		con.setRequestMethod("POST"); // 设置请求方式
		con.setUseCaches(false);//不使用缓存
		con.setRequestProperty("X-Forwarded-For", whiteListIp);
		con.setRequestProperty("content-type", "text/xml;charset=UTF-8");

//		String soap = getXml(namespace,methodName,patameterMap,rid,sid,rtime,sign);
		String soap = getPeopleXml(namespace, methodName, patameterMap, rid, sid, rtime, sign, peopleParams, type);
		System.out.println("soap:" + soap);

		OutputStream out = con.getOutputStream();
		out.write(soap.getBytes());
		out.close();

		int code = con.getResponseCode();
		System.out.println("rescode=========" + code + "");
		String result = "";
		if (code == 200) {// 服务端返回正常
			InputStream is = con.getInputStream();
			byte[] b = new byte[1024];
			StringBuffer sb = new StringBuffer();
			int len = 0;
			while ((len = is.read(b)) != -1) {
				String str = new String(b, 0, len, "UTF-8");
				sb.append(str);
			}
			result = sb.toString();

			is.close();
		}
		con.disconnect();

		System.out.println("result======="+result);
		return result;
	}

	public static String getXml(String namespace, String methodName, Map<String, String> patameterMap, String rid,
			String sid, String rtime, String sign) {

		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.tongtech.com/\"> ");
		soapRequestData.append("<soapenv:Header>");
		soapRequestData.append("<tongtechheader>");
		soapRequestData.append("<rid>" + rid + "</rid>");
		soapRequestData.append("<sid>" + sid + "</sid>");
		soapRequestData.append("<rtime>" + rtime + "</rtime>");
		soapRequestData.append("<sign>" + sign + "</sign>");
		soapRequestData.append("</tongtechheader>");
		soapRequestData.append("</soapenv:Header>");
		soapRequestData.append(" <soapenv:Body>");
		soapRequestData.append("<web:sum>");
		// 获取键集
		Set<String> nameSet = patameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + patameterMap.get(name) + "</" + name + ">");// 获取对应的值集并追加
		}
		soapRequestData.append("</web:sum>");
		soapRequestData.append("</soapenv:Body>");
		soapRequestData.append("</soapenv:Envelope>");
		String soap = soapRequestData.toString();
		System.out.println("soap======" + soap);
		return soap;
	}

	public static String getPeopleXml(String namespace, String methodName, Map<String, String> patameterMap, String rid,
			String sid, String rtime, String sign,People people,String type) {

		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		soapRequestData.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.com/\"> ");
		soapRequestData.append("<soapenv:Header>");
		soapRequestData.append("<tongtechheader>");
		soapRequestData.append("<gjgxjhpt_rid>" + rid + "</gjgxjhpt_rid>");
		soapRequestData.append("<gjgxjhpt_sid>" + sid + "</gjgxjhpt_sid>");
		soapRequestData.append("<gjgxjhpt_rtime>" + rtime + "</gjgxjhpt_rtime>");
		soapRequestData.append("<gjgxjhpt_sign>" + sign + "</gjgxjhpt_sign>");
		soapRequestData.append("</tongtechheader>");
		soapRequestData.append("</soapenv:Header>");
		soapRequestData.append("<soapenv:Body>");
		// 基准信息查询  标签 web:GK_JZCX_RKJCXXCX
		String start = "";
		String end = "";
		if(Constants.RK_JBXX.equals(type)) {
			start = "<web:GK_JZCX_RKJCXXCX>";
			end = "</web:GK_JZCX_RKJCXXCX>";
		}else if(Constants.RK_SFHC.equals(type)) {
			start = "<web:GK_GXFW_SFHC>";
			end = "</web:GK_GXFW_SFHC>";
		}
		soapRequestData.append(start);
		soapRequestData.append("<web:in0>");
		
		SendSoapXml sx = new SendSoapXml();
		String aa = sx.getInfo(people);
		System.out.println("aa:" + aa);
		soapRequestData.append(aa);
		
		soapRequestData.append("</web:in0>");
		soapRequestData.append("<web:in1>gxzxjkcs</web:in1>");
		soapRequestData.append("<web:in2>cLv6jMAaevpC3jFb7/84Ew==</web:in2>");
		soapRequestData.append(end);

		soapRequestData.append("</soapenv:Body>");
		soapRequestData.append("</soapenv:Envelope>");
		String soap = soapRequestData.toString();
		System.out.println("soap======" + soap);
		return soap;
	}

}

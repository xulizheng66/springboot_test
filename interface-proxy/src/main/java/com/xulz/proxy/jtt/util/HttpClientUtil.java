package com.xulz.proxy.jtt.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpClientUtil
 * 
 * @description
 * @createTime 创建时间：2015-7-8 上午11:35:50
 * @author WANGTING
 * @version 1.0
 */
public class HttpClientUtil {


	

	private static String proxy = "other"; // open:开启代理 other：不开启
	private static String proxy_host = "10.169.17.9";// 10.169.17.9 // 10.144.242.70
	private static int proxy_port = 9999;// 9999 808

	/**
	 * GET 请求
	 * 
	 * @param url
	 *            请求地址:例如http://www.baidu.com
	 * @return 请求返回值
	 */
	public static String get(String url,Map<String,Object> params) {

		// 创建请求HttpClient客户
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();

		HttpGet httpGet = null;
		CloseableHttpResponse httpResponse = null;
		HttpEntity entity = null;

		try {
			if(!params.isEmpty()&&params.size()>0){
				String param = "";
				Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
				while(entries.hasNext()){
					Entry<String, Object> entry = entries.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					param+= key+ "=" +value+"&";
				}
				httpGet = new HttpGet(url+"?"+param.substring(0,param.length()-1));
			}else{
				httpGet = new HttpGet(url);
			}
			
			if("open".equals(proxy)){
			    // 依次是代理地址，代理端口号，协议类型
				System.out.print("HttpClientUtil  httpGet 设置代理:"+proxy_host+":"+proxy_port);
			 	HttpHost proxy = new HttpHost(proxy_host, proxy_port, "http");
			    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			    httpGet.setConfig(config);
			}
			httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			httpGet.setHeader("Accept-Encoding","gzip, deflate");
			httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
			httpGet.setHeader("Cache-Control","max-age=0");
			httpGet.setHeader("Connection","keep-alive");
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
			
			httpResponse = httpClient.execute(httpGet);

			// 响应状态
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				entity = httpResponse.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			}
			else {
				System.out.print("url==" + url + "&服务器响应状态码：" + httpResponse.getStatusLine().getStatusCode());
			}

			httpResponse.close();
		}
		catch (Exception ex) {
			System.out.print("url =" + url+"ex:" +ex);
		}
		finally {
			try {
				// 关闭流并释放资源
				httpClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			entity = null;
			httpResponse = null;
			httpGet = null;
		}

		return "";
	}

	/**
	 * POST 请求
	 * 
	 * @param url
	 *            请求地址:例如http://www.baidu.com
	 * @param params
	 *            请求参数
	 * @return 请求返回值
	 */
	public static String post(String url, Map<String, Object> params) {

		// 请求参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}

		// 创建请求HttpClient客户
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();

		HttpPost httpPost = null;
		CloseableHttpResponse httpResponse = null;
		HttpEntity entity = null;
		try {
			httpPost = new HttpPost(url);
			if("open".equals(proxy)){
			    // 依次是代理地址，代理端口号，协议类型
				System.out.print("HttpClientUtil  httpPost 设置代理:"+proxy_host+":"+proxy_port);
			 	HttpHost proxy = new HttpHost(proxy_host, proxy_port, "http");
			    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			    httpPost.setConfig(config);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));

			// 执行post请求
			httpResponse = httpClient.execute(httpPost);

			// 响应状态
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				entity = httpResponse.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			}
			else {
				System.out.print("url==" + url + "&服务器响应状态码：" + httpResponse.getStatusLine().getStatusCode());
			}

			httpResponse.close();
		}
		catch (Exception ex) {
			System.out.print("Catch Exception: url =" + url+"ex:" +ex);
		}
		finally {
			try {
				// 关闭流并释放资源
				httpClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			entity = null;
			formparams = null;
			httpResponse = null;
			httpPost = null;
		}

		return "";
	}

	/**
	 * 执行请求
	 * @param url
	 * @param postData
	 * @return String
	 */
	public static String doPost(String url, String postData) {
		final String CONTENT_TYPE_TEXT_JSON = "text/json";
		String result = null;
		HttpPost post = null;
		try {
			HttpClient client = new DefaultHttpClient();

			post = new HttpPost(url);

			post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
			post.setHeader("connection", "Keep-Alive");
			post.setHeader("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			post.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			post.setHeader("Accept-Encoding","gzip, deflate");
			post.setHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
			post.setHeader("Cache-Control","max-age=0");
			post.setHeader("Connection","keep-alive");
			post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");


			StringEntity entity = new StringEntity(postData, "UTF-8");
			entity.setContentType(CONTENT_TYPE_TEXT_JSON);
			post.setEntity(entity);

			HttpResponse response = client.execute(post);

			int rspCode = response.getStatusLine().getStatusCode();
			System.out.println("rspCode:" + rspCode);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(post != null) {
				post.releaseConnection();
			}
		}
		return null;
	}
}

package com.xulz.webservice.commons;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils implements java.io.Serializable {
	private static final long serialVersionUID = -176092625883595547L;
	private static final String CHARSET = HTTP.UTF_8;
	private static DefaultHttpClient myHttpClient;
	private static final int OK = 200; // OK: Success!
	private static int timeoutConnection = 10000;// ReadProperties.getPropertyByInt("client.timeout");
													// //连接超时时间
	private static int timeoutSocket = 20000;// ReadProperties.getPropertyByInt("client.socketout");
												// ; //读取数据超时时间

	public HttpUtils() {
	}

	public static synchronized DefaultHttpClient getHttpClient() {
		if (myHttpClient == null) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
					+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 1000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, 2000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 4000);

			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			myHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return myHttpClient;
	}

	/**
	 * 处理http post请求
	 * 
	 */
	public static String getResPostJson(String url, String rid, String sid, String rtTime, String sign, String reqStr)
			throws Exception {
		String strResult = null;
		DefaultHttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("gjgxjhpt_rid", rid);
		post.setHeader("gjgxjhpt_sid", sid);
		post.setHeader("gjgxjhpt_rtime", rtTime);
		post.setHeader("gjgxjhpt_sign", sign);
		post.setHeader("content-type", "application/json");
		try {
			StringEntity se = new StringEntity(reqStr, HTTP.UTF_8);
			post.setEntity(se);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				return strResult;
			} else {
				throw new Exception("亲,网络不给力！");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new Exception("亲,网络不给力！");
		} catch (java.net.SocketTimeoutException e1) {
			throw new Exception("亲,网络不给力！");

		} catch (IOException e2) {
			e2.printStackTrace();
			throw new Exception("亲,网络不给力！");
		}
	}

	/**
	 * 处理http post请求
	 * 
	 */
	public static String getResPostJson(String url, String rid, String sid, String rtime, String sign,
			Map<String, String> reqMap) throws Exception {
		String strResult = null;
		DefaultHttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("content-type", "application/json");
		// post.setHeader("rid", rid);
		// post.setHeader("sid", sid);
		// post.setHeader("rtime", rtime);
		// post.setHeader("sign", sign);
		try {
			String reqStr = JSONUtil.writeMapJSON(reqMap);
			System.out.println("reqStr=======" + reqStr);
			StringEntity se = new StringEntity(reqStr, HTTP.UTF_8);
			post.setEntity(se);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == OK) {
				strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				return strResult;
			} else {
				throw new Exception("亲,网络不给力！");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new Exception("亲,网络不给力！");
		} catch (java.net.SocketTimeoutException e1) {
			throw new Exception("亲,网络不给力！");

		} catch (IOException e2) {
			e2.printStackTrace();
			throw new Exception("亲,网络不给力！");
		}
	}

	/**
	 * TODO(http get)
	 * 
	 * @param url
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static String getResGetJson(String url, String rid, String sid, String rtTime, String sign,
			Map<String, String> reqMap) throws Exception {
		String strResult = null;
		DefaultHttpClient httpClient = getHttpClient();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (String name : reqMap.keySet()) {
			params.add(new BasicNameValuePair(name, reqMap.get(name)));
		}
		// 对参数编码
		String param = URLEncodedUtils.format(params, HTTP.UTF_8);
		// get方法提交
		HttpGet get = new HttpGet(url + "?" + param);
		System.out.println("url:" + url + "?" + param);
		get.setHeader("rid", rid);
		get.setHeader("sid", sid);
		get.setHeader("rtime", rtTime);
		get.setHeader("sign", sign);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == OK) {

				strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				return strResult;
			} else {
				throw new Exception("亲,网络不给力！");

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new Exception("亲,网络不给力！");
		} catch (java.net.SocketTimeoutException e1) {
			throw new Exception("亲,网络不给力！");

		} catch (IOException e2) {
			e2.printStackTrace();
			throw new Exception("亲,网络不给力！");
		}
	}

	/**
	 * 读取远程资源
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream loadResource(String url) throws Exception {
		try {
			URL m;
			URLConnection c;
			InputStream i = null;
			m = new URL(url);
			c = m.openConnection();
			c.setConnectTimeout(timeoutConnection);
			c.setReadTimeout(timeoutSocket);
			i = c.getInputStream();
			return i;
		} catch (IOException e) {
			e.printStackTrace();
			// throw new Exception("网络连接错误，请检查您的网络！",e);
			throw new Exception("亲,网络不给力！");
		}
	}

}

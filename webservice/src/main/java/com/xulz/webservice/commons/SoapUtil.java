package com.xulz.webservice.commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class SoapUtil {
	private static final Logger log = LoggerFactory.getLogger(SoapUtil.class);
	public static final String SOAP_BODY_KEY = "Body";
	public static final String SOAP_SOAPAction_KEY = "SOAPAction";
	public Map<String, Object> map = new HashMap(1);

	public <T> ResponseEntity<T> exchange(String postUrl, String soapXml) {
		String soapAction = null;
		Map map = null;
		try {
			map = parse(soapXml);
		} catch (DocumentException e) {
			log.info("����soapxml[" + soapXml + "]����" + e.getMessage());
		}
		if ((map != null) && (!map.isEmpty())) {
			soapAction = map.get("SOAPAction") == null ? "" : map.get("SOAPAction").toString();
		}
		return exchange(postUrl, soapXml, soapAction);
	}

	public <T> ResponseEntity<T> exchange(String postUrl, String soapXml, String soapAction) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectionRequestTimeout(10000)
				.setConnectTimeout(10000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction == null ? "" : soapAction);

			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);

			response = closeableHttpClient.execute(httpPost);
			httpEntity = response.getEntity();

			Header[] headers = response.getAllHeaders();
			MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap();
			for (Header header : headers) {
				stringMultiValueMap.add(header.getName(), header.getValue());
			}
			if (httpEntity != null) {
				String retStr = EntityUtils.toString(httpEntity, "UTF-8");
				return new ResponseEntity(retStr, stringMultiValueMap,
						HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
			}
			response.close();

			closeableHttpClient.close();

			return ResponseEntity.noContent().build();
		} catch (Exception localException) {

		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException localIOException9) {
				}
			}
			if (closeableHttpClient != null) {
				try {
					closeableHttpClient.close();
				} catch (IOException localIOException10) {
				}
			}
			if (httpEntity != null) {
				try {
					EntityUtils.consume(httpEntity);
				} catch (IOException localIOException11) {
				}
			}
		}
		return ResponseEntity.noContent().build();
	}

	public String doPostSoap1_1(String postUrl, String soapXml) {
		String soapAction = null;
		Map map = null;
		try {
			map = parse(soapXml);
		} catch (DocumentException e) {
			log.info("����soapxml[" + soapXml + "]����" + e.getMessage());
		}
		if ((map != null) && (!map.isEmpty())) {
			soapAction = map.get("SOAPAction") == null ? "" : map.get("SOAPAction").toString();
		}
		return doPostSoap1_2(postUrl, soapXml, soapAction);
	}

	public String doPostSoap1_2(String postUrl, String soapXml, String soapAction) {
		String retStr = "";

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectionRequestTimeout(10000)
				.setConnectTimeout(10000).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction == null ? "" : soapAction);

			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);

			response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
			}
			response.close();

			closeableHttpClient.close();

			return retStr;
		} catch (Exception localException) {
			
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException localIOException4) {
				}
			}
			if (closeableHttpClient != null) {
				try {
					closeableHttpClient.close();
				} catch (IOException localIOException5) {
				}
			}
		}
		return retStr;
	}

	public Map parse(String soap) throws DocumentException {
		Document doc = DocumentHelper.parseText(soap);
		Element root = doc.getRootElement();
		getCode(root);
		return this.map;
	}

	public void getCode(Element root) {
		if (root.elements() != null) {
			List<Element> list = root.elements();
			for (Element e : list) {
				if (e.getQName().getName().toUpperCase().contains("Body".toUpperCase())) {
					getSoapAction(e);
					break;
				}
				if (e.elements().size() > 0) {
					getCode(e);
				}
			}
		}
	}

	public void getSoapAction(Element root) {
		StringBuilder url = new StringBuilder("");
		if (root.elements() != null) {
			List<Element> list = root.elements();
			url.append(((Element) list.get(0)).getQName().getNamespaceURI())
					.append(((Element) list.get(0)).getQName().getName());
		}
		this.map.put("SOAPAction", url.toString());
	}
}

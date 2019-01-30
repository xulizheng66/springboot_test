package com.xulz.webservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.commons.GetSecretKeyByFwjh;
import com.xulz.webservice.commons.SymmetricEncoderByFwjh;
import com.xulz.webservice.commons.XmlUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("服务聚合平台接口调用")
@RestController
public class InterfaceByFwjhController {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/getTokenByFwjh", method = RequestMethod.POST)
	public JSONObject getTokenByFwjh(
			@RequestParam(value = "") String requestUrl,
			@RequestParam(value = "") String appId,
			@RequestParam(value = "") String appKey) {

		String gateway_rtime = String.valueOf(System.currentTimeMillis());
		String gateway_sig = "";
		try {			
			gateway_sig = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 返回结果
		String result = "";
		try {
			// 设置请求头
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
			headers.setContentType(mediaType);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());

			// Authorization:Basic NWYyNWI2ZjZmNTdiNDc0OGE3Y2RlZTM4ZDk3MGJlYzM6MTIzNDU2
			headers.add("gateway_appid", appId);// header参数，应用id
			headers.add("gateway_sig", gateway_sig);// header参数，签名信息
			headers.add("gateway_rtime", gateway_rtime);// header参数，本次请求时间戳(毫秒值)
			// 旧地址
			// http://10.50.108.42/proxy/oauth/token?scope=webapp&grant_type=client_credentials
			// headers.add("Authorization", "Basic" + " " + appKey);

			Map<String, String> map = new HashMap<>();

			HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);
			HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, formEntity, String.class);
			result = response.getBody();
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(result)) {
				JSONObject objects = JSONObject.parseObject(result);

				String code = objects.getString("code");
				String body = objects.getString("body");

				if (StringUtils.isNoneBlank(code, body) && "0".equals(code)) {
					JSONObject bodyObj = JSONObject.parseObject(body);
					// 1.获取access_token
					String accessToken = bodyObj.getString("access_token");
					// 2.在获得服务调用秘钥access_token后，根据自己的appKey使用AES解密算法对返回值进行解密，最终获得真正秘钥的过程。
					String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, accessToken);
					// 3.获取调用服务realSign
					String realSign = GetSecretKeyByFwjh.gatewaySignEncode(appId, secretKey, gateway_rtime);
					// 封装调用服务需要的参数
					jsonObject.put("gateway_appid", appId);
					jsonObject.put("gateway_sig", realSign);
					jsonObject.put("gateway_rtime", gateway_rtime);
					return jsonObject;
				}
				jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
				return jsonObject;
			} else {
				jsonObject.put("message", "获取token错误，请查看响应数据中的错误码和错误信息");
				return jsonObject;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/getTokenByFwjh1", method = RequestMethod.POST)
	public JSONObject getTokenByFwjh1(
			@RequestParam(value = "") String requestUrl,
			@RequestParam(value = "") String appId,
			@RequestParam(value = "") String appKey) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("gateway_sig", "roCVAY9TZRbiUfiUFuhjeWGJuQPHJac5WLjChaV2E0I=");
		jsonObject.put("gateway_rtime", "1545192104443");
		jsonObject.put("gateway_appid", "fa24db9af2124593b8ffe0dc45d023af");
		return jsonObject;
	}
	

	@RequestMapping(value = "/getInfoByPeople1", method = RequestMethod.POST, produces = { "text/xml;charset=UTF-8" })
	@ApiOperation(value = "人口库-WEBSERVICE接口调用")
	public Object getInfoByPeople1(
			final @ApiParam(value = "url(接口地址)", required = true, defaultValue = "http://10.50.108.42/proxy/api/gj_gab_rkk_jzxxcx_ggzyjyj") @RequestParam(value = "") String url,
			final @ApiParam(value = "xmlParams(请求参数)", required = true) @RequestParam(value = "") String xmlParams,
			final @ApiParam(value = "whiteListIp(白名单ip)", required = true) @RequestParam(value = "") String whiteListIp) {

		String result = "";
		try {
			URL url2 = new URL(url);
			URLConnection conn = url2.openConnection();
			HttpURLConnection con = (HttpURLConnection) conn;

			con.setDoInput(true); // 是否有入参
			con.setDoOutput(true); // 是否有出参
			con.setRequestMethod("POST"); // 设置请求方式
			con.setUseCaches(false);// 不使用缓存
			con.setRequestProperty("gateway_sig", "Skv8F062q4PKsqJfW/rw313Bg2Can889Sp17W/7d49s=");
			con.setRequestProperty("gateway_rtime", "1546415519909");
			con.setRequestProperty("gateway_appid", "1f123cacefe946ea8f30eb056d0394fe");
			con.setRequestProperty("X-Forwarded-For", whiteListIp);
			con.setRequestProperty("content-type", "text/xml;charset=UTF-8");

			OutputStream out = con.getOutputStream();
			out.write(xmlParams.getBytes());
			out.close();

			int code = con.getResponseCode();
			System.out.println("rescode=========" + code + "");
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
			System.out.println("result=======" + result);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/getInfoByPeople", method = RequestMethod.POST)
	@ApiOperation(value = "人口库-接口调用")
	public JSONObject getInfoByPeople(
			final @RequestParam(value = "") String gateway_appid,
			final @RequestParam(value = "") String gateway_rtime,
			final @RequestParam(value = "") String gateway_sig,
			final @RequestParam(value = "") String url,
			final @RequestParam(value = "") String xmlParams,
			final @RequestParam(value = "") String whiteListIp) {

		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.set("gateway_appid", gateway_appid);
		headers.set("gateway_rtime", gateway_rtime);
		headers.set("gateway_sig", gateway_sig);
		headers.set("X-Forwarded-For", whiteListIp);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		// 参数
//		Map<String, Object> params = new HashMap<>();
		HttpEntity<String> requestEntity = new HttpEntity<>(xmlParams, headers);
		
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		String result = response.getBody();
		result = result.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
		
		int beginIndex = result.indexOf("<ns1:out>");
		int endIndex = result.indexOf("</ns1:out>");
		result = result.substring(beginIndex + 9, endIndex);
		String xml2json = XmlUtil.xml2JSON(result);
		JSONObject json = JSONObject.parseObject(xml2json);
		return json;
	}
	
	@RequestMapping(value = "/getInfoByPeople1", method = RequestMethod.POST)
	@ApiOperation(value = "人口库-接口调用")
	public JSONObject getInfoByPeople1(
			final @RequestParam(value = "") String gateway_appid,
			final @RequestParam(value = "") String gateway_rtime,
			final @RequestParam(value = "") String gateway_sig,
			final @RequestParam(value = "") String url,
			final @RequestParam(value = "") String xmlParams,
			final @RequestParam(value = "") String whiteListIp) {
		
		String result = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><ns1:GK_GXFW_SFHCResponse xmlns:ns1=\"http://webservice.com\"><ns1:out>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\r\n" + 
				"&lt;PACKAGE&gt;&lt;PACKAGEHEAD&gt;&lt;FFQQBH&gt;4660c10a-6aef-4a75-8b19-90b3b1b34e2f&lt;/FFQQBH&gt;&lt;FSSJ&gt;20181129104501&lt;/FSSJ&gt;&lt;FHDM&gt;10&lt;/FHDM&gt;&lt;FHMS&gt;接口调用成功&lt;/FHMS&gt;&lt;/PACKAGEHEAD&gt;&lt;DATA&gt;&lt;RECORD no=\"1\" code=\"\" msg=\"\"&gt;&lt;GMSFHM&gt;622823199510163616&lt;/GMSFHM&gt;&lt;GMSFHM_PPDDM&gt;1&lt;/GMSFHM_PPDDM&gt;&lt;SWBS&gt;0&lt;/SWBS&gt;&lt;XM&gt;章彦军&lt;/XM&gt;&lt;XM_PPDDM&gt;1&lt;/XM_PPDDM&gt;&lt;/RECORD&gt;&lt;/DATA&gt;&lt;/PACKAGE&gt;</ns1:out></ns1:GK_GXFW_SFHCResponse></soap:Body></soap:Envelope>";
		result = result.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
		int beginIndex = result.indexOf("<ns1:out>");
		int endIndex = result.indexOf("</ns1:out>");
		result = result.substring(beginIndex + 9, endIndex);
		String xml2json = XmlUtil.xml2JSON(result);
		JSONObject json = JSONObject.parseObject(xml2json);
		return json;
		
	}
	
	@RequestMapping(value = "/getInfoByGsxxcx", method = RequestMethod.POST)
	@ApiOperation(value = "工商-接口调用")
	public JSONObject getInfoByGsxxcx(
			final @RequestParam(value = "") String gateway_appid,
			final @RequestParam(value = "") String gateway_rtime,
			final @RequestParam(value = "") String gateway_sig,
			@RequestParam(value = "") String url,
			@RequestParam(value = "") String jsonParams) {

		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gateway_appid", gateway_appid);
		headers.set("gateway_rtime", gateway_rtime);
		headers.set("gateway_sig", gateway_sig);
		headers.set("Authorization", "Basic Z3hzZDAxOjY2NjY2Ng==");
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		// 参数
		JSONObject parseObject = JSONObject.parseObject(jsonParams);
		Map<String, Object> params = (Map<String, Object>)parseObject;
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
		
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		String result = response.getBody();
		JSONArray parseArray = JSONArray.parseArray(result);
		
		JSONObject json  = (JSONObject)parseArray.get(0);
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value = "/getInfoByGsxxyz", method = RequestMethod.POST)
	@ApiOperation(value = "工商-接口调用")
	public JSONObject getInfoByGsxxyz(
			final @RequestParam(value = "") String gateway_appid,
			final @RequestParam(value = "") String gateway_rtime,
			final @RequestParam(value = "") String gateway_sig,
			@RequestParam(value = "") String url,
			@RequestParam(value = "") String jsonParams) {
		
		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gateway_appid", gateway_appid);
		headers.set("gateway_rtime", gateway_rtime);
		headers.set("gateway_sig", gateway_sig);
		headers.set("Authorization", "Basic Z3hzZDAxOjY2NjY2Ng==");
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		
		// 参数
		JSONObject parseObject = JSONObject.parseObject(jsonParams);
		Map<String, Object> params = (Map<String, Object>)parseObject;
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);
		
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		String result = response.getBody();
		JSONObject json = JSONObject.parseObject(result);
		
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getInfoByGs(HttpServletRequest request) {
		
		request.setAttribute("", "");
		ModelAndView mv = new ModelAndView();
		mv.addObject("list","aaa");
		mv.setViewName("index");
		return mv;
	}

		

}

package com.xulz.webservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.Map;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.commons.GetSecretKeyByFwjh;
import com.xulz.webservice.commons.RedisUtils;
import com.xulz.webservice.commons.SymmetricEncoderByFwjh;


/**
 * @author xulz
 * @Description: 国家接口获取密钥
 * @date 2018/11/1717:32
 */

@Api("聚合服务平台接口-获取签名密钥")
@RestController
public class GetSecretKeyByFwjhController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 获取签名信息gatewaySign
	 * @param appId
	 * @param appKey
	 * @return
	 */
	@RequestMapping(value = "/gatewaySignEncode", method = RequestMethod.POST)
	@ApiOperation(value = "获取签名信息gatewaySign")
	public JSONObject gatewaySignEncode(
			@ApiParam(value = "appId", required = true) @RequestParam(value = "") String appId,
			@ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {

		String rtime = String.valueOf(System.currentTimeMillis());
		JSONObject jsonObject = new JSONObject();
		try {
			String gatewaySign = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, rtime);
			jsonObject.put("gateway_Sig", gatewaySign);
//			jsonObject.put("gateway_rtime", rtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * http请求过程(这里为获取token)
	 * @param requestUrl 请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）或者是服务调用的url
	 * @param requestMethod 获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
	 * @param appIdorSecretKey 获取token时该参数为appId；抵用服务时该参数为SecretKey
	 * @param currTime 该参数为当前时间
	 * @param sign 该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
	 * @return
	 */
	@RequestMapping(value = "/getToken", method = RequestMethod.POST)
	@ApiOperation(value = "获取访问token")
	public Object getToken(
			@ApiParam(value = "url", required = true, defaultValue = "http://10.18.100.5:5000/auth/token") @RequestParam(value = "") String requestUrl,
			@ApiParam(value = "gateway_appid", required = true) @RequestParam(value = "") String gateway_appid,
			@ApiParam(value = "gateway_sig", required = true) @RequestParam(value = "") String gateway_sig) {
		
		String gateway_rtime = String.valueOf(System.currentTimeMillis());
		//返回结果
		String result = "";
		try {
			// 设置请求头
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
			headers.setContentType(mediaType);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			
			headers.add("gateway_appid", gateway_appid);//header参数，应用id
			headers.add("gateway_sig", gateway_sig);//header参数，签名信息
			headers.add("gateway_rtime", gateway_rtime);//header参数，本次请求时间戳(毫秒值)
			
			Map<String,String> map = new HashMap<>();
			HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);

			HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, formEntity, String.class);
			result = response.getBody();
			if (StringUtils.isNotBlank(result)) {
				String firstChart = result.trim().substring(0, 1);
				if ("{".equals(firstChart)) {
					JSONObject objects = JSONObject.parseObject(result);
					return objects;
				} else if ("[".equals(firstChart)) {
					JSONArray array = JSONArray.parseArray(result);
					return array;
				} else {
					return "格式转换错误";
				}
			} else {
				return "获取token错误，请查看响应数据中的错误码和错误信息";
			}
			// 按照类型转换
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 使用授权码（appKey）对返回数据中的body的access_token进行解密（ASE 算法）
	 * @param appKey
	 * @param access_token
	 * @return
	 */
	@RequestMapping(value = "/getSecretKeyByFwjh", method = RequestMethod.POST)
	@ApiOperation(value = "获取签名密钥secretKey")
	public JSONObject getSecretKeyByFwjh(
			@ApiParam(value = "appKey(授权码)", required = true) @RequestParam(value = "") String appKey,
			@ApiParam(value = "access_token(访问令牌)", required = true) @RequestParam(value = "") String access_token) {

		JSONObject jsonObject = new JSONObject();
		try {
			String secretKey = SymmetricEncoderByFwjh.AESDncode(appKey, access_token);
			jsonObject.put("secretKey", secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 使用（appKey）对返回数据中的body的refresh_token进行解密（ASE 算法）
	 * @param appKey
	 * @param access_token
	 * @return
	 */
	@RequestMapping(value = "/getRefreshKeyByFwjh", method = RequestMethod.POST)
	@ApiOperation(value = "刷新密钥refreshKey")
	public JSONObject getRefreshKeyByFwjh(
			@ApiParam(value = "appKey(授权码)", required = true) @RequestParam(value = "") String appKey,
			@ApiParam(value = "refresh_token(刷新令牌)", required = true) @RequestParam(value = "") String refresh_token) {
		
		JSONObject jsonObject = new JSONObject();
		try {

			String refreshKey = SymmetricEncoderByFwjh.AESDncode(appKey, refresh_token);
			jsonObject.put("refreshKey", refreshKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * http请求过程(这里为调用服务)
	 * @param requestUrl 请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）或者是服务调用的url
	 * @param requestMethod 获取token时，请求方法为POST；调用服务是请求方法依据服务注册时定义。
	 * @param appIdorSecretKey 获取token时该参数为appId；抵用服务时该参数为SecretKey
	 * @param currTime 该参数为当前时间
	 * @param sign 该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
	 * @return
	 */
	@RequestMapping(value = "/getInfoByFwjh", method = RequestMethod.POST)
	@ApiOperation(value = "调用服务接口")
	public Object getInfoByFwjh(
			@ApiParam(value = "服务调用的url", required = true) @RequestParam(value = "") String requestUrl,
			@ApiParam(value = "服务调用的请求方法（POST或GET）", required = true) @RequestParam(value = "") String requestMethod,
			@ApiParam(value = "secretKey", required = true) @RequestParam(value = "") String secretKey,
			@ApiParam(value = "gateway_appid", required = true) @RequestParam(value = "") String gateway_appid,
			@ApiParam(value = "gateway_sig", required = true) @RequestParam(value = "") String gateway_sig) {
		
		String gateway_rtime = String.valueOf(System.currentTimeMillis());
		//返回结果
		String result = "";
		try {
			
			String sign = GetSecretKeyByFwjh.gatewaySignEncode(gateway_appid, secretKey, gateway_rtime);
			
			// 设置请求头
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
			headers.setContentType(mediaType);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			
			headers.add("gateway_appid", gateway_appid);//header参数，应用id
			headers.add("gateway_sig", sign);//header参数，签名信息
			headers.add("gateway_rtime", gateway_rtime);//header参数，本次请求时间戳(毫秒值)
			
			Map<String,String> map = new HashMap<>();
			HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);

			HttpEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, formEntity, String.class);
			result = response.getBody();
			// 按照类型转换
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String firstChart = result.trim().substring(0, 1);
		if ("{".equals(firstChart)) {
			JSONObject objects = JSONObject.parseObject(result);
			return objects;
		} else if ("[".equals(firstChart)) {
			JSONArray array = JSONArray.parseArray(result);
			return array;
		} else {
			return "格式转换错误";
		}
		
	}
	
}

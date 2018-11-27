package com.xulz.webservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inspur.openservice.api.OpenServiceClient;
import com.inspur.openservice.api.RequestParams;
import com.inspur.openservice.api.model.ClientException;
import com.xulz.webservice.commons.Constants;
import com.xulz.webservice.commons.ResponseFormat;
import com.xulz.webservice.entity.HeaderParams;
import com.xulz.webservice.entity.MzbParams;
import com.xulz.webservice.entity.People;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.spring.web.json.Json;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-14 16:36
 **/

@Api("国家服务接口-REST接口调用")
@RestController
public class RestfullController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/getRestInfoByZJB", method = RequestMethod.POST)
	@ApiOperation(value = "住建部-REST接口调用")
	public Object getRestInfoByZJB(
			@ApiParam(value = "服务url", required = true, defaultValue = "http://59.255.104.184:8181/httpproxy") @RequestParam(value = "") String serverUrl,
			@ApiParam(value = "参数(json字符串)", required = true) @RequestParam(value = "") String params,
			@ApiParam(value = "headersByJson", required = true) @RequestBody HeaderParams headersByJson) {
		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(mediaType);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		String rid = headersByJson.getRid();
		String sid = headersByJson.getSid();
		String rtime = headersByJson.getRtime();
		String sign = headersByJson.getSign();

		if (StringUtils.isNoneBlank(rid, sid, rtime, sign)) {

			headers.add("gjgxjhpt_rid", rid);
			headers.add("gjgxjhpt_sid", sid);
			headers.add("gjgxjhpt_rtime", rtime);
			headers.add("gjgxjhpt_sign", sign);

//			HttpEntity<String> formEntity = new HttpEntity<>("", headers);
//			result = restTemplate.postForObject(serverUrl, formEntity, String.class);
//			result = result == null ? "" : result;
			
			// post调用并传递参数扩展方法
//			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//			map.add("enterprisename", "深圳市慧谷通信技术有限公司");
//			map.add("apikey", "4028c681606ca20f01606d0c7e030003");
//			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, headers);
			
			Map<String,String> map = JSONObject.parseObject(params,Map.class);
			HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);

			HttpEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, formEntity, String.class);
			String result = response.getBody();
			System.out.println("+++++++++++++++++" + result);

			// 按照类型转换
			String firstChart = result.trim().substring(0, 1);
			if ("{".equals(firstChart)) {
				JSONObject objects = JSONObject.parseObject(result);
				return ResponseFormat.retParam(200, objects);
			} else if ("[".equals(firstChart)) {
				JSONArray array = JSONArray.parseArray(result);
				return ResponseFormat.retParam(200, array);
			}
		}
		return "请求头参数不能为空";
	}

	@RequestMapping(value = "/getRestInfoByMZB", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用")
	public Object getRestInfoByMZB(@ApiParam(value = "rid", required = true) @RequestParam(value = "") String rid,
			@ApiParam(value = "sid", required = true) @RequestParam(value = "") String sid,
			@ApiParam(value = "sign(第二次获取到的sign)", required = true) @RequestParam(value = "") String sign,
			@ApiParam(value = "rtime(第二次获取到的rtime)", required = true) @RequestParam(value = "") String rtime,
			@ApiParam(value = "type(单人：dr 双人：sr 社会团体法人：fr)", required = true) @RequestParam(value = "") String type,
			final @ApiParam(value = "查询参数对象", required = true) @RequestBody MzbParams mzbParams) {

		String jsonStr = "";
		// sdk
		OpenServiceClient client = new OpenServiceClient();
		RequestParams openServiceParam = new RequestParams();
		// 默认参数
		openServiceParam.setContext("");
		openServiceParam.setVersion("");
		openServiceParam.setMethod("get");
		openServiceParam.addParam("clientName", "中心");

		// 中心认证参数
		openServiceParam.addParam("gjgxjhpt_rid", rid);
		openServiceParam.addParam("gjgxjhpt_sid", sid);
		openServiceParam.addParam("gjgxjhpt_sign", sign);
		openServiceParam.addParam("gjgxjhpt_rtime", rtime);

		String name_man = mzbParams.getName_man();
		String cert_num_man = mzbParams.getCert_num_man();
		String name_woman = mzbParams.getName_woman();
		String cert_num_woman = mzbParams.getCert_num_woman();
		String org_name = mzbParams.getOrg_name();
		String usc_code = mzbParams.getUsc_code();
		// 业务逻辑处理
		if (Constants.MZB_DR.equals(type)) {
			// 单人
			openServiceParam.addParam("name_man", name_man); // 姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 身份证号
		} else if (Constants.MZB_SR.equals(type)) {
			// 双人
			openServiceParam.addParam("name_man", name_man);// 男方姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 男方身份证号
			openServiceParam.addParam("name_woman", name_woman);// 女方姓名
			openServiceParam.addParam("cert_num_woman", cert_num_woman);// 女方身份证号
		} else if (Constants.MZB_FR.equals(type)) {
			// 社会团体法人登记证书查询
			openServiceParam.addParam("org_name", org_name);// 社会组织名称
			openServiceParam.addParam("usc_code", usc_code); // 统一信用代码
		}

		// 分页参数
		openServiceParam.addParam("start", "0");// 起始页
		openServiceParam.addParam("limit", "1");// 页面大小

		try {
			jsonStr = client.sendRequest(openServiceParam);
		} catch (ClientException e) {
			e.printStackTrace();
			return "参数异常";
		}
		System.out.println("result" + jsonStr);

		return JSONObject.parseObject(jsonStr);
	}
}

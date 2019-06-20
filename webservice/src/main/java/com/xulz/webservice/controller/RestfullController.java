package com.xulz.webservice.controller;

import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.xulz.webservice.commons.GetSecretKey;
import com.xulz.webservice.commons.RedisUtils;
import com.xulz.webservice.commons.TimeHelper;
import com.xulz.webservice.entity.HeaderParams;
import com.xulz.webservice.entity.MzbParams;
import com.xulz.webservice.entity.NationInterface;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@Autowired
	private RedisUtils redisUtils;
	//获取国家鉴权地址
	@Value("${com.mzb.authUrl}")
    private String authUrl;
	// #社会团体法人（兰州新区）
	@Value("${com.mzb.fr_lzxq.rid}")
	private String fr_lzxq_rid;
	@Value("${com.mzb.fr_lzxq.sid}")
	private String fr_lzxq_sid;
	@Value("${com.mzb.fr_lzxq.appKey}")
	private String fr_lzxq_appKey;
	//社会团体法人（武威）
	@Value("${com.mzb.fr_ww.rid}")
	private String fr_ww_rid;
	@Value("${com.mzb.fr_ww.sid}")
	private String fr_ww_sid;
	@Value("${com.mzb.fr_ww.appKey}")
	private String fr_ww_appKey;
	//单人（甘肃省人民政府)
	@Value("${com.mzb.dr_srmzf.rid}")
	private String dr_srmzf_rid;
	@Value("${com.mzb.dr_srmzf.sid}")
	private String dr_srmzf_sid;
	@Value("${com.mzb.dr_srmzf.appKey}")
	private String dr_srmzf_appKey;
	//双人（甘肃省人民政府）
	@Value("${com.mzb.sr_srmzf.rid}")
	private String sr_srmzf_rid;
	@Value("${com.mzb.sr_srmzf.sid}")
	private String sr_srmzf_sid;
	@Value("${com.mzb.sr_srmzf.appKey}")
	private String sr_srmzf_appKey;
	//双人（甘肃身公安厅）
	@Value("${com.mzb.sr_sgat.rid}")
	private String sr_sgat_rid;
	@Value("${com.mzb.sr_sgat.sid}")
	private String sr_sgat_sid;
	@Value("${com.mzb.sr_sgat.appKey}")
	private String sr_sgat_appKey;
	//双人（甘肃省人社厅）
	@Value("${com.mzb.db_srst.rid}")
	private String db_srst_rid;
	@Value("${com.mzb.db_srst.sid}")
	private String db_srst_sid;
	@Value("${com.mzb.db_srst.appKey}")
	private String db_srst_appKey;
	//殡葬服务火化信息(兰州市)
	@Value("${com.mzb.bzfw_lzs.rid}")
	private String bzfw_lzs_rid;
	@Value("${com.mzb.bzfw_lzs.sid}")
	private String bzfw_lzs_sid;
	@Value("${com.mzb.bzfw_lzs.appKey}")
	private String bzfw_lzs_appKey;
	
	@RequestMapping(value = "/getRestInfoByZJB", method = RequestMethod.POST)
	@ApiOperation(value = "住建部-REST接口调用")
	public Object getRestInfoByZJB(
			@ApiParam(value = "服务url", required = true, defaultValue = "http://59.255.105.32:8181/httpproxy") @RequestParam(value = "") String serverUrl,
			@ApiParam(value = "参数(json格式)", required = true) @RequestParam(value = "") String params,
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
			
			@SuppressWarnings("unchecked")
			Map<String,String> map = JSONObject.parseObject(params,Map.class);
			HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(map, headers);

			HttpEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, formEntity, String.class);
			String result = response.getBody();
			System.out.println("+++++++++++++++++" + result);

			// 按照类型转换
			String firstChart = result.trim().substring(0, 1);
			if ("{".equals(firstChart)) {
				JSONObject objects = JSONObject.parseObject(result);
				return objects;
			} else if ("[".equals(firstChart)) {
				JSONArray array = JSONArray.parseArray(result);
				return array;
			} else {
				return result;
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
		if (Constants.MZB_DR.equalsIgnoreCase(type)) {
			// 单人
			openServiceParam.addParam("name_man", name_man); // 姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 身份证号
		} else if (Constants.MZB_SR.equalsIgnoreCase(type)) {
			// 双人
			openServiceParam.addParam("name_man", name_man);// 男方姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 男方身份证号
			openServiceParam.addParam("name_woman", name_woman);// 女方姓名
			openServiceParam.addParam("cert_num_woman", cert_num_woman);// 女方身份证号
		} else if (Constants.MZB_FR.equalsIgnoreCase(type)) {
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
	
	
	
	@RequestMapping(value = "/getRestInfoByMzbWithHeaders", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(带请求头)")
	public Object getRestInfoByMzbWithHeaders() {
		
		String rid = "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-257";
		String sid = "s_12100000000018032A_3942";
		String sign = "VxLjL++GE4N+08Gvl2+zUOW9Frq9OIrzhcXR/zKSC08=";
		String rtime = "1545310704021";
		String params = "{\"type\":\"fr\",\"param\":{\"a\":\"a\",\"b\":\"b\"}}";
		
		JSONObject paramsOjb = JSONObject.parseObject(params);
		String type = String.valueOf(paramsOjb.getString("type"));
		MzbParams mzbParams = new MzbParams();
		mzbParams.setCert_num_man("11");
		mzbParams.setCert_num_woman("11");
		mzbParams.setName_man("11");
		mzbParams.setName_woman("11");
		mzbParams.setOrg_name("11");
		mzbParams.setUsc_code("11");
		
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
		if (Constants.MZB_DR.equalsIgnoreCase(type)) {
			// 单人
			openServiceParam.addParam("name_man", name_man); // 姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 身份证号
		} else if (Constants.MZB_SR.equalsIgnoreCase(type)) {
			// 双人
			openServiceParam.addParam("name_man", name_man);// 男方姓名
			openServiceParam.addParam("cert_num_man", cert_num_man);// 男方身份证号
			openServiceParam.addParam("name_woman", name_woman);// 女方姓名
			openServiceParam.addParam("cert_num_woman", cert_num_woman);// 女方身份证号
		} else if (Constants.MZB_FR.equalsIgnoreCase(type)) {
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
	
	/**
	 * getSecretKeyByNation
	 * @param url
	 * @param rid
	 * @param sid
	 * @param appkey
	 * @return
	 */
	public JSONObject getSecretKeyByNation(
			@ApiParam(value = "url（获取签名地址）", required = true, defaultValue = "http://59.255.105.32:8181/sysapi/auth/refreshappsecret") @RequestParam(value = "") String url,
			@ApiParam(value = "rid（请求者身份标识）", required = true) @RequestParam(value = "") String rid,
			@ApiParam(value = "sid（服务编码）", required = true) @RequestParam(value = "") String sid,
			@ApiParam(value = "appkey（服务授权码）", required = true) @RequestParam(value = "") String appkey) {

		JSONObject jsonObject = new JSONObject();
		String rtime = String.valueOf(System.currentTimeMillis());
		// 首先去redis 中去取，存在则直接返回，不存在再调国家接口
		boolean exists = redisUtils.exists(rid + "@" + sid + "@" + appkey);
		if (exists) {
			NationInterface info = (NationInterface) redisUtils.get(rid + "@" + sid + "@" + appkey);
			jsonObject.put("sign", info.getRealSecretKey());
			// redis中获取到的rtime 和 sign

			System.out.println("sign>>>>>>>>>>>>" + info.getRealSecretKey());
			return jsonObject;
		} else {
			// 国家接口获取到的secretKey当天有效
			String sign = GetSecretKey.getSign(sid, rid, rtime, appkey);
			if (StringUtils.isNotBlank(sign) && !"-1".equals(sign)) {
				String secretKey = GetSecretKey.getSecretKey(url, rid, sid, rtime, sign);
				if (StringUtils.isNotBlank(secretKey)) {
					
					// 使用redis缓存 jsonObject,并设置有效期
					long current = System.currentTimeMillis();// 当前时间毫秒数
					long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24)
							- TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
					long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
					long period = twelve - current;// 时间间隔
					// 保存到 nationInterface 对象中
					NationInterface nationInterface = new NationInterface();
					nationInterface.setRtime(rtime);
					nationInterface.setRealSecretKey(secretKey);
					nationInterface.setAppkey(appkey);
					nationInterface.setSid(sid);
					nationInterface.setRid(rid);
					// 设置规则 key 为 rid@sid@appKey  sign
					// 当天有效
					boolean isOk = redisUtils.set(rid + "@" + sid + "@" + appkey, nationInterface, period, TimeUnit.MILLISECONDS);
					boolean isOk1 = redisUtils.set(sign, nationInterface, period, TimeUnit.MILLISECONDS);
					System.out.println(isOk);
					//存redis成功
					if (isOk && isOk1) {
						jsonObject.put("sign", secretKey);
						return jsonObject;
					}else {
						jsonObject.put("message", "sign存入redis失败");
						return jsonObject;
					}
				} else if(StringUtils.isNotBlank(sign) && "-1".equals(sign)){
					//{"code":"-1","data":"","message":"接口调用失败"}
					jsonObject.put("message", "请求不存在于数据库授权列表中");
					return jsonObject;
				} else {
					jsonObject.put("message", "系统异常");
					return jsonObject;
				}
			}
			jsonObject.put("message", "系统异常，hmacsha256 计算失败");
			return jsonObject;
		}
	}
	
	/**
	 * 获取真正的密钥和时间戳
	 * @param rid
	 * @param sid
	 * @param appKey
	 * @return
	 */
	public JSONObject getRealSignAndRtime(
			final @ApiParam(value = "rid", required = true) @RequestParam(value = "") String rid,
			final @ApiParam(value = "sid", required = true) @RequestParam(value = "") String sid,
			final @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {
		
		JSONObject jsonObject = new JSONObject();
		String rtime = String.valueOf(System.currentTimeMillis());
		boolean exists = redisUtils.exists(rid + "@" + sid + "@" + appKey);
		
		if (exists) {
			NationInterface info = (NationInterface) redisUtils.get(rid + "@" + sid + "@" + appKey);
			//获取真正的sign
			String realSecretKey = GetSecretKey.getRealSecretKey(rtime,rid,sid,appKey,info.getRealSecretKey());
			
			jsonObject.put("sign", realSecretKey);
			jsonObject.put("rtime", rtime);
		}else {
			jsonObject.put("message", "sign不存在，请先获取sign值");
		}
		return jsonObject;
	}
	
	/**
	 * 注解的方法必须是无输入参数并返回空类型void的。
	 * spring 定时任务
	 */
	/*@Scheduled(fixedRate = 60 * 1000 * 30)
	public void setRealSignAndRtime() {
		redisUtils.set(sr_sgat_rid + sr_srmzf_sid + sr_sgat_appKey, "");
		System.out.println(TimeHelper.getCurrentTime());
		
	}*/
	
	
	/*@GetMapping(value="/test")
	public void test() {
		
		JSONObject json =  new JSONObject();
		json.put("11", "11");
		json.put("22", "22");
		redisUtils.set("json", json);
		
		JSONObject object = (JSONObject)redisUtils.get("json");
		
		String result = object.getString("11");
		System.out.println(result);
		
	}*/
	
	
	
	/**
	 * 民政部-REST接口调用(公共方法)
	 * @param rid 请求者标识
	 * @param sid 接口编码
	 * @param appKey 发送方签名(appKey)
	 * @param params 接口请求参数Json字符串
	 * @param type 接口类型
	 * @return
	 */
	public JSONObject getRestInfoByMzbWithPublic(String rid,String sid,String appKey,MzbParams params,String type) {
		
		String url = authUrl;
		// 获取国家鉴权 (当日有效)
		JSONObject secretKeyByNation = getSecretKeyByNation(url, rid, sid, appKey);
		// 返回json对象后，message 为错误信息   sign 为正确信息
		String error = secretKeyByNation.getString("message");
		String firstSign = secretKeyByNation.getString("sign");
		
		//返回结果
		String jsonStr = "";
		JSONObject resultJson = new JSONObject();
		
		if (null != secretKeyByNation && StringUtils.isNotBlank(error)) {
			resultJson.put("message", "国家鉴权获取失败");
			return resultJson;
		} else if (StringUtils.isNotBlank(firstSign)){
			//真正的密钥有效期30分钟
			//定时器每30分钟 获取 真正的密钥并保存到redis  firstSign@rid@sid@appkey
			JSONObject realSignAndRtime = null;
			if (redisUtils.exists(firstSign + "@" + rid + "@" + sid + "@" + appKey)) {
				realSignAndRtime = (JSONObject)redisUtils.get(firstSign + "@" + rid + "@" + sid + "@" + appKey);
			} else {
				realSignAndRtime = getRealSignAndRtime(rid, sid, appKey);
				redisUtils.set(firstSign + "@" + rid + "@" + sid + "@" + appKey,realSignAndRtime,30L,TimeUnit.MINUTES);
			}
			
			String error1 = realSignAndRtime.getString("message");
			
			if (StringUtils.isNoneBlank(error1)) {
				resultJson.put("message", "请先获取国家鉴权");
				return resultJson;
			}else {
				//业务处理开始
				String sign = realSignAndRtime.getString("sign");
				String rtime = realSignAndRtime.getString("rtime");
				//{"name_man":"xxx","cert_num_man":"xxx","name_woman":"xxx","cert_num_woman":"xxx","org_name":"xxx","usc_code":"xxx"}
				//调用接口类型 单人：dr  双人：sr  法人：fr 
				//String type = String.valueOf(paramsOjb.getString("type"));
				
				String name_man = params.getName_man();;
				String cert_num_man = params.getCert_num_man();
				String name_woman = params.getName_woman();
				String cert_num_woman = params.getCert_num_woman();
				String org_name = params.getOrg_name();
				String usc_code = params.getUsc_code();
				String id_card = params.getId_card();
				String name = params.getName();
			 		
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
				
				// 业务逻辑处理
				if (Constants.MZB_DR.equalsIgnoreCase(type)) {
					// 单人
					openServiceParam.addParam("name_man", name_man); // 姓名
					openServiceParam.addParam("cert_num_man", cert_num_man);// 身份证号
				} else if (Constants.MZB_SR.equalsIgnoreCase(type)) {
					// 双人
					openServiceParam.addParam("name_man", name_man);// 男方姓名
					openServiceParam.addParam("cert_num_man", cert_num_man);// 男方身份证号
					openServiceParam.addParam("name_woman", name_woman);// 女方姓名
					openServiceParam.addParam("cert_num_woman", cert_num_woman);// 女方身份证号
				} else if (Constants.MZB_FR.equalsIgnoreCase(type)) {
					// 社会团体法人登记证书查询
					openServiceParam.addParam("org_name", org_name);// 社会组织名称
					openServiceParam.addParam("usc_code", usc_code); // 统一信用代码
				} else if (Constants.MZB_DB.equalsIgnoreCase(type)) {
					//低保对象信息
					openServiceParam.addParam("id_card",id_card);// 身份证号
					openServiceParam.addParam("name",name); //姓名
				} else if (Constants.MZB_BZFW.equalsIgnoreCase(type)) {
					//殡葬服务火化信息
					openServiceParam.addParam("id_card",id_card);// 身份证号
					openServiceParam.addParam("name",name); //逝者姓名
				} 
				
				// 分页参数
				openServiceParam.addParam("start", "0");// 起始页
				openServiceParam.addParam("limit", "1");// 页面大小
				
				try {
					jsonStr = client.sendRequest(openServiceParam);
				} catch (ClientException e) {
					e.printStackTrace();
					resultJson.put("message", "参数异常");
					return resultJson;
				}
				System.out.println("result" + jsonStr);
				return JSONObject.parseObject(jsonStr);
			}
			
		}
		return resultJson;
	}
	
	@RequestMapping(value = "/getRestInfoByMzbWithFrLzxq", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(社会团体法人-兰州新区)")
	public JSONObject getRestInfoByMzbWithFrLzxq(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_FR;
		//参数判断
		if (!StringUtils.isNoneBlank(mzbParams.getOrg_name(),mzbParams.getUsc_code())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(fr_lzxq_rid, fr_lzxq_sid, fr_lzxq_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestInfoByMzbWithFrWw", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(社会团体法人-武威)")
	public JSONObject getRestInfoByMzbWithFrWw(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_FR;
		if (!StringUtils.isNoneBlank(mzbParams.getOrg_name(),mzbParams.getUsc_code())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(fr_ww_rid, fr_ww_sid, fr_ww_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestInfoByMzbWithDrRmzf", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(单人（甘肃省人民政府))")
	public JSONObject getRestInfoByMzbWithDrRmzf(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_DR;
		if (!StringUtils.isNoneBlank(mzbParams.getName_man(),mzbParams.getCert_num_man())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(dr_srmzf_rid, dr_srmzf_sid, dr_srmzf_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestInfoByMzbWithSrRmzf", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(双人（甘肃省人民政府）)")
	public JSONObject getRestInfoByMzbWithSrRmzf(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_SR;
		if (!StringUtils.isNoneBlank(mzbParams.getName_man(),mzbParams.getCert_num_man(),mzbParams.getName_woman(),mzbParams.getCert_num_woman())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(sr_srmzf_rid, sr_srmzf_sid, sr_srmzf_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestInfoByMzbWithSrGat", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(双人（甘肃身公安厅）)")
	public JSONObject getRestInfoByMzbWithSrGat(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_SR;
		if (!StringUtils.isNoneBlank(mzbParams.getName_man(),mzbParams.getCert_num_man(),mzbParams.getName_woman(),mzbParams.getCert_num_woman())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(sr_sgat_rid, sr_sgat_sid, sr_sgat_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestDbInfoByMzbWithSrst", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(低保信息查询-省人社厅)")
	public JSONObject getRestDbInfoByMzbWithSrst(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_DB;
		if (!StringUtils.isNoneBlank(mzbParams.getId_card(),mzbParams.getName())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(db_srst_rid, db_srst_sid, db_srst_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	@RequestMapping(value = "/getRestBzfwhhxxInfoByMzbWithLzs", method = RequestMethod.POST)
	@ApiOperation(value = "民政部-REST接口调用(殡葬服务火化信息查询-兰州市)")
	public JSONObject getRestBzfwhhxxInfoByMzbWithLzs(@RequestBody MzbParams mzbParams) {
		JSONObject result = new JSONObject();
		String type = Constants.MZB_BZFW;
		if (!StringUtils.isNoneBlank(mzbParams.getId_card(),mzbParams.getName())) {
			result.put("message", "参数错误，请核实");
			return result;
		}
		JSONObject restInfo = getRestInfoByMzbWithPublic(bzfw_lzs_rid, bzfw_lzs_sid, bzfw_lzs_appKey, mzbParams, type);
		if (null != restInfo && StringUtils.isNotBlank(restInfo.getString("message"))) {
			result.put("message", "系统异常");
			return result;
		} else {
			return restInfo;
		}
	}
	
	
}

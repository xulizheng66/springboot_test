package com.xulz.webservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.client.WebClient;
import com.xulz.webservice.commons.GetSecretKey;
import com.xulz.webservice.commons.Md5Util;
import com.xulz.webservice.commons.RedisUtils;
import com.xulz.webservice.commons.SymmetricEncoder;
import com.xulz.webservice.entity.NationInterface;
import com.xulz.webservice.entity.People;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author xulz
 * @Description: ${todo}
 * @date 2018/11/1618:37
 */

@Api("国家服务接口-WEBSERVICE接口调用")
@RestController
public class WebServiceController {

//	@Autowired
//	private RestTemplate restTemplate;
	@Autowired
	private RedisUtils redisUtils;
	
	@RequestMapping(value = "/getWebServiceInfoByPeople", method = RequestMethod.POST, produces = { "application/xml;charset=UTF-8" })
	@ApiOperation(value = "人口库-WEBSERVICE接口调用")
	public Object getWebServiceInfoByPeople(
			final @ApiParam(value = "url", required = true, defaultValue = "http://59.255.104.184:8181/wsproxy") @RequestParam(value = "") String url,
			final @ApiParam(value = "查询参数对象", required = true) @RequestBody People peopleParams,
			final @ApiParam(value = "查询类型type(基本信息：jbxx,身份核查：sfhc)", required = true) @RequestParam(value = "") String type,
			final @ApiParam(value = "whiteListIp", required = true) @RequestParam(value = "") String whiteListIp,
			@ApiParam(value = "rid", required = true) @RequestParam(value = "") String rid,
			@ApiParam(value = "sid", required = true) @RequestParam(value = "") String sid,
			@ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey,
			final @ApiParam(value = "sign", required = true) @RequestParam(value = "") String sign) {

		String rtime = String.valueOf(System.currentTimeMillis());
		
//		boolean exists = redisUtils.exists(sign.trim());
//		NationInterface info = null;
//		if (exists) {
//			info = (NationInterface)redisUtils.get(sign);
//			rid = info.getRid();
//			sid = info.getSid();
//			appKey = info.getAppkey();
//		}
		
		Map<String, String> map = new HashMap<String, String>();
//		map.put("number1", "1");
//		map.put("number2", "2");
		//获取真正的sign
		String realSecretKey = GetSecretKey.getRealSecretKey(rtime,rid,sid,appKey,sign);
		// 调用接口
		String resultStr = null;
		try {
			resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map, rid, sid, rtime, realSecretKey,type, whiteListIp, peopleParams);

			resultStr = resultStr.replaceAll("&gt;", ">");
			resultStr = resultStr.replaceAll("&lt;", "<");
			System.out.println(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// 调用接口2
		/*String methodName = "";
		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(url);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("queryIACEntInfo", identity, security, entname, regno, entstatus, ver);

			System.out.println("--------------------" + objects[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return resultStr;
	}

	@RequestMapping(value = "/getWebServiceInfoByFGW", method = RequestMethod.POST)
	@ApiOperation(value = "发展改革委--WEBSERVICE接口调用")
	public Object getWebServiceInfoByFGW(
			@ApiParam(value = "url", required = true) @RequestParam(value = "") String serverUrl,
			@ApiParam(value = "参数", required = true) @RequestParam(value = "") String jsonStr) {

//        String url = "http://59.255.188.8/services/iacinfo?wsdl";

		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		String identity = jsonObj.getString("identity");// 身份识别码 rid
		String realSecretKey = jsonObj.getString("realSecretKey");// 密钥

//        String security = jsonObj.getString("security");//entname+regno+秘钥 进行MD5运算

		String entname = jsonObj.getString("entname");// 所需要查询的企业名称
		String regno = jsonObj.getString("regno");// 所需要查询的工商注册号
		String entstatus = jsonObj.getString("entstatus");
		String ver = jsonObj.getString("ver");// 传0

		String security = Md5Util.toMD5(entname + regno + realSecretKey);// entname+regno+秘钥 进行MD5运算
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(serverUrl);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("queryIACEntInfo", identity, security, entname, regno, entstatus, ver);

			System.out.println("--------------------" + objects[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects[0];
	}
	
	
	@RequestMapping(value = "/queryIACEntInfo", method = RequestMethod.POST)
	@ApiOperation(value = "事业单位信息查询接口")
	public Object queryIACEntInfo(
			@ApiParam(value = "服务url", required = true) @RequestParam(value = "") String serverUrl,
			@ApiParam(value = "参数", required = true) @RequestParam(value = "") String jsonStr) {

//        String url = "http://59.255.188.8/services/iacinfo?wsdl";

		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		String identity = jsonObj.getString("identity");// 身份识别码 rid
		String realSecretKey = jsonObj.getString("realSecretKey");// 密钥

//        String security = jsonObj.getString("security");//entname+regno+秘钥 进行MD5运算

		String entname = jsonObj.getString("entname");// 所需要查询的企业名称
		String regno = jsonObj.getString("regno");// 所需要查询的工商注册号
		String entstatus = jsonObj.getString("entstatus");
		String ver = jsonObj.getString("ver");// 传0

		String security = Md5Util.toMD5(entname + regno + realSecretKey);// entname+regno+秘钥 进行MD5运算
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(serverUrl);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("queryIACEntInfo", identity, security, entname, regno, entstatus, ver);

			System.out.println("--------------------" + objects[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects[0];
	}

}

package com.xulz.webservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.client.WebClient;
import com.xulz.webservice.commons.GetSecretKey;
import com.xulz.webservice.commons.Md5Util;
import com.xulz.webservice.commons.RedisUtils;
import com.xulz.webservice.commons.UnescapeUtil;
import com.xulz.webservice.entity.People;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulz
 * @Description: ${todo}
 * @date 2018/11/1618:37
 */

@Api("国家服务接口-WEBSERVICE接口调用")
@RestController
public class WebServiceController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/getWebServiceInfoByPeople", method = RequestMethod.POST, produces = {
            "application/xml;charset=UTF-8"})
    @ApiOperation(value = "人口库-WEBSERVICE接口调用")
    public Object getWebServiceInfoByPeople(
            final @ApiParam(value = "url", required = true, defaultValue = "http://59.255.105.32:8181/wsproxy") @RequestParam(value = "") String url,
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
        // 获取真正的sign
        String realSecretKey = GetSecretKey.getRealSecretKey(rtime, rid, sid, appKey, sign);
        // 调用接口
        String resultStr = null;
        try {
            resultStr = WebClient.getSoapXML(url, "http://ws.apache.org/axis2", "sum", map, rid, sid, rtime,
                    realSecretKey, type, whiteListIp, peopleParams);

            resultStr = resultStr.replaceAll("&gt;", ">");
            resultStr = resultStr.replaceAll("&lt;", "<");
            System.out.println(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 调用接口2
        /*
         * String methodName = ""; JSONObject jsonObj = JSONObject.parseObject(jsonStr);
         *
         * // 创建动态客户端 JaxWsDynamicClientFactory dcf =
         * JaxWsDynamicClientFactory.newInstance(); Client client =
         * dcf.createClient(url); // 需要密码的情况需要加上用户名和密码 //
         * client.getOutInterceptors().add(new
         * ClientLoginInterceptor(USER_NAME,PASS_WORD)); Object[] objects = new
         * Object[0]; try { // invoke("方法名",参数1,参数2,参数3....); objects =
         * client.invoke("queryIACEntInfo", identity, security, entname, regno,
         * entstatus, ver);
         *
         * System.out.println("--------------------" + objects[0]); } catch (Exception
         * e) { e.printStackTrace(); }
         */
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
//		 client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
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

    /**
     * 调用webservice.asmx接口
     */
    public static void main(String[] args) {
        String method = "GetCompletionRecordDataSet";

        // 获取webservice接口地址
        String url = "http://59.219.194.194/GSST.SharePort.WebService/CompletionRecordService.asmx";
        // 获取域名地址，server定义的 http://tempuri.org/ 默认命名空间
        String soapaction = "http://tempuri.org/";

        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            // 设置地址
            call.setTargetEndpointAddress(url);
            // 启用soap
            call.setUseSOAPAction(true);
            // 设置soap访问地址（缺少会报错）
            call.setSOAPActionURI(soapaction + method);
            // 设置要调用的方法
            call.setOperationName(new QName(soapaction, method));

            // 设置参数名称，具体参照提示页面
            call.addParameter(new QName(soapaction, "strPassword"), XMLType.SOAP_STRING, ParameterMode.IN);
            call.addParameter(new QName(soapaction, "buildUnit"), XMLType.SOAP_STRING, ParameterMode.IN);
            call.addParameter(new QName(soapaction, "projectName"), XMLType.SOAP_STRING, ParameterMode.IN);
            // 要返回的数据类型，这里很重要，这样设置可以返回xml数据，便于解析
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);

            // 调用方法并传递参数
            Object[] params = new Object[]{"56@DE@pd", "合作市卫生和计划生育局", "勒秀乡中心卫生院业务用房及职工在周转房建设项目"};

            // 返回结果
//			String invoke = (String)call.invoke(params);

            Schema schema = (Schema) call.invoke(params); // 调用并返回内容
            MessageElement[] msgele = schema.get_any();// 取到返回的xml元素数组
            MessageElement messageElement = msgele[0];
            String result = "";
            result = messageElement.toString().replaceAll("&gt;", ">");
            result = result.replaceAll("&lt;", "<").replaceAll("&quot;", "");
            // &#x编码转换成汉字
            result = UnescapeUtil.unescape(result);
            System.out.println("result is:::" + result);

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

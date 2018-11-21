package com.xulz.webservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.commons.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @RequestMapping(value = "/getWebServiceInfo", method = RequestMethod.GET)
    @ApiOperation(value = "WEBSERVICE接口调用")
    public Object getWebServiceInfo(
            @ApiParam(value = "服务url", required = true) @RequestParam(value = "") String serverUrl,
            @ApiParam(value = "参数", required = true) @RequestParam(value = "") String jsonStr,
            @ApiParam(value = "方法名", required = true) @RequestParam(value = "") String functionName) {

        String url = "http://59.255.42.231:8088/Tlw_CkFgw_WebService.asmx?wsdl";
        //创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serverUrl);
        Object[] objects = new Object[0];
        try {
            JSONObject.parseObject(jsonStr);
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke(functionName, 10001L);

            System.out.println("--------------------" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects[0];
    }

    @RequestMapping(value = "/queryIACEntInfo", method = RequestMethod.GET)
    @ApiOperation(value = "事业单位信息查询接口")
    public Object queryIACEntInfo(
            @ApiParam(value = "服务url", required = true) @RequestParam(value = "") String serverUrl,
            @ApiParam(value = "参数", required = true) @RequestParam(value = "") String jsonStr) {

//        String url = "http://59.255.188.8/services/iacinfo?wsdl";

        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        String identity = jsonObj.getString("identity");//身份识别码 rid
        String realSecretKey = jsonObj.getString("realSecretKey");//密钥

//        String security = jsonObj.getString("security");//entname+regno+秘钥 进行MD5运算

        String entname = jsonObj.getString("entname");//所需要查询的企业名称
        String regno = jsonObj.getString("regno");//所需要查询的工商注册号
        String entstatus = jsonObj.getString("entstatus");
        String ver = jsonObj.getString("ver");//传0

        String security = Md5Util.toMD5(entname + regno + realSecretKey);//entname+regno+秘钥 进行MD5运算
        //创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serverUrl);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
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

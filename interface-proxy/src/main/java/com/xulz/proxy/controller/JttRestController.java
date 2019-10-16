package com.xulz.proxy.controller;

import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.bean.RestResult;
import com.jiuqi.bi.rest.exception.RestException;
import com.xulz.proxy.client.JttRestClient;
import com.xulz.proxy.entity.JttParams;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 交通厅接口调用
 * @Author xulz
 * @Date 2019/8/5 17:32
 */
@RestController
@Log4j2
@RequestMapping("jtt")
public class JttRestController {
    /**
     * 请求url
     */
    @Value("${com.jtt.restClientUrl}")
    private String restClientUrl;
    /**
     * 请求路径
     */
    @Value("${com.jtt.requestPath}")
    private String requestPath;


    //http://59.219.195.5:6080/DATACENTERPro/api/dataShare/getZWResource
    @PostMapping("getZWResource")
    public RestResult getZWResource(@RequestBody JttParams jttParams) throws RestException {
        // 参数校验
        RestResult result = new RestResult();
        if (jttParams.getToken() == null) {
            result.setStatusCode(400);
            result.setData("token不能为空".getBytes());
            return result;
        }
        jttParams.setRestClienturl(restClientUrl);
        jttParams.setRequestPath(requestPath);
        RestClient restClient = JttRestClient.getRestClient(jttParams);
        log.info("交通厅接口--->登录成功");
        RestResult result2 = null;
        if (restClient != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            // 共享管理系统中的token
            params.put("token", jttParams.getToken());
            // 以post方式访问REST服务器资源
            // dataShareChange/getShareInfoByToken路径保持不变
            // params要满足共享测试里面条件要求
            result2 = restClient.post(jttParams.getRequestPath(), params);
            log.info("[result]:{}", result2.getDataAsString());
        }
        return result2;
    }


    public static void main(String[] args) throws RestException {
        JttParams jttParams = new JttParams();
        RestClient restClient = JttRestClient.getRestClient(jttParams);
        System.out.println("登录成功");
        if (restClient != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            //共享管理系统中的token
            params.put("token", "");
            //以post方式访问REST服务器资源
            // dataShareChange/getShareInfoByToken路径保持不变
            // params要满足共享测试里面条件要求
            RestResult result2 = restClient.post("/dataShare/getZWResource", params);
            System.out.println(result2.getDataAsString());
        }
    }


}



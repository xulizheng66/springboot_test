package com.xulz.proxy.jtt.controller;

import com.alibaba.fastjson.JSONObject;
import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.bean.RestResult;
import com.jiuqi.bi.rest.exception.RestException;
import com.xulz.proxy.client.JttRestClient;
import com.xulz.proxy.commons.Constants;
import com.xulz.proxy.entity.JttParams;
import com.xulz.proxy.jtt.DTO.Params;
import com.xulz.proxy.jtt.VO.ResultVO;
import com.xulz.proxy.jtt.enums.ResultEnum;
import com.xulz.proxy.jtt.util.HttpClientUtil;
import com.xulz.proxy.jtt.util.ResultVOUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
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

    @Autowired
    private RestTemplate restTemplate;

    //http://59.219.195.5:6080/DATACENTERPro/api/dataShare/getZWResource
    @PostMapping("getZWResource")
    public RestResult getZWResource(@Valid @RequestBody JttParams jttParams,
                                    BindingResult bindingResult) {
        // 参数校验
        RestResult result = new RestResult();
        if (bindingResult.hasErrors()) {
            log.error("请求参数不正确, jttParams={}", jttParams);
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            result.setStatusCode(ResultEnum.INPUT_PARAMS_ERROR.getCode());
            result.setStatusText(defaultMessage);
            return result;
        }

        jttParams.setRestClienturl(restClientUrl);
        jttParams.setRequestPath(requestPath);
        try {
            RestClient restClient = JttRestClient.getRestClient(jttParams);
            Map<String, Object> params = new HashMap<String, Object>();
            // 共享管理系统中的token
            params.put("token", jttParams.getToken());
            // 以post方式访问REST服务器资源
            // dataShareChange/getShareInfoByToken路径保持不变
            // params要满足共享测试里面条件要求
            params.put("pageNo", jttParams.getPageNo());
            params.put("pageSize", jttParams.getPageSize());
            log.info("交通厅------>调用接口开始...");
            result = restClient.post(jttParams.getRequestPath(), params);
            log.info("调用成功，返回结果[result]:{}", result.getDataAsString());
            return result;
        } catch (RestException e) {
            log.error("交通厅接口调用异常：【{}】", e.getMessage());
            result.setStatusCode(400);
            result.setStatusText(e.getMessage());
        }
        return result;
    }


    @PostMapping("test")
    public void test() {
        log.info("交通厅接口调用开始...");
        try {
            JSONObject params = new JSONObject();
            //一个token对应一个接口
            params.put("token", Constants.JTT.SJTSYT_WLYYCZQCJYXKZXX);
            //固定值不用修改
            params.put("queryType", Constants.JTT.QUERYTYPE);
            //政务专用接口 固定值不用修改
            params.put("source", Constants.JTT.SOURCE);

            //pageNo和pageSize为可选参数  pageNo默认1，pageSize默认为1000
            params.put("pageNo", 1);
            params.put("pageSize", 10);

            //分配好的用户
            params.put("user", Constants.JTT.USER);
            params.put("pwd", Constants.JTT.PWD);
            log.info("[入参]：{}", params);

            //IP 可以修改成映射好的IP
            String data = HttpClientUtil.doPost(Constants.JTT.URL, params.toJSONString());
            log.info("[结果]:{}", data);
        } catch (Exception e) {
            log.error("[调用异常]{}", e.getMessage());
        }

    }


    @PostMapping("query")
    public ResultVO query(@Valid @RequestBody Params inputParams, BindingResult bindingResult) {

        // 参数校验
        if (bindingResult.hasErrors()) {
            log.error("请求参数不正确, inputParams={}", inputParams);
            String message = bindingResult.getFieldError().getDefaultMessage();
            return ResultVOUtil.error(ResultEnum.INPUT_PARAMS_ERROR.getCode(), message);
        }
        log.info("交通厅接口调用开始...");
        Map<String, Object> params = new HashMap<String, Object>(10);
        //一个token对应一个接口
        params.put("token", inputParams.getToken());
        //固定值不用修改
        params.put("queryType", Constants.JTT.QUERYTYPE);
        //政务专用接口 固定值不用修改
        params.put("source", Constants.JTT.SOURCE);

        //pageNo和pageSize为可选参数  pageNo默认1，pageSize默认为1000
        params.put("pageNo", inputParams.getPageNo());
        params.put("pageSize", inputParams.getPageSize());

        //分配好的用户
        params.put("user", Constants.JTT.USER);
        params.put("pwd", Constants.JTT.PWD);
        log.info("[入参]：{}", params);

        log.info("resttemplate调用开始...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity = new HttpEntity(params, headers);
        //IP 可以修改成映射好的IP
        ResponseEntity<String> result = null;
        try {
            result = restTemplate.exchange(Constants.JTT.URL, HttpMethod.POST, httpEntity, String.class);
            log.info("返回结果：{}", result.getBody());
            log.info("resttemplate结束...");
            if (result.hasBody()) {
                JSONObject jsonResult = JSONObject.parseObject(result.getBody());
                return ResultVOUtil.success(jsonResult);
            }
            return ResultVOUtil.error(ResultEnum.CALL_REMOTE_ERROR);
        } catch (RestClientException e) {
            log.error("[接口调用异常],异常信息：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.CALL_REMOTE_ERROR.getCode(), ResultEnum.CALL_REMOTE_ERROR.getMsg()
                    + "，异常信息：" + e.getMessage());
        }
    }


    @PostMapping("queryInfo")
    public JSONObject queryInfo(@Valid @RequestBody Params inputParams, BindingResult bindingResult) {
        log.info("交通厅接口调用开始...");
        JSONObject jsonResult = new JSONObject();
        // 参数校验
        if (bindingResult.hasErrors()) {
            log.error("请求参数不正确, inputParams={}", inputParams);
            String message = bindingResult.getFieldError().getDefaultMessage();
            jsonResult.put("code", ResultEnum.INPUT_PARAMS_ERROR.getCode());
            jsonResult.put("msg", message);
            return jsonResult;
        }
        Map<String, Object> params = new HashMap<String, Object>(10);
        //一个token对应一个接口
        params.put("token", inputParams.getToken());
        //固定值不用修改
        params.put("queryType", Constants.JTT.QUERYTYPE);
        //政务专用接口 固定值不用修改
        params.put("source", Constants.JTT.SOURCE);

        //pageNo和pageSize为可选参数  pageNo默认1，pageSize默认为1000
        params.put("pageNo", inputParams.getPageNo());
        params.put("pageSize", inputParams.getPageSize());

        // 分配好的用户
        params.put("user", Constants.JTT.USER);
        params.put("pwd", Constants.JTT.PWD);
        // 根据token增加参数
        if (inputParams.getToken().equals(Constants.JTT.SJTSYT_LYKY)
                && StringUtils.isNotBlank(inputParams.getTimekey())) {
            // 旅游客运车辆动态信息
            params.put("timekey", inputParams.getTimekey());
        }
        log.info("[入参]：{}", params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity httpEntity = new HttpEntity(params, headers);
        //IP 可以修改成映射好的IP
        ResponseEntity<String> result = null;
        try {
            result = restTemplate.exchange(Constants.JTT.URL, HttpMethod.POST, httpEntity, String.class);
            log.info("返回结果：{}", result.getBody());
            log.info("交通厅接口调用结束...");
            if (result.hasBody()) {
                jsonResult = JSONObject.parseObject(result.getBody());
                return jsonResult;
            }
            jsonResult.put("code", ResultEnum.CALL_REMOTE_ERROR.getCode());
            jsonResult.put("msg", ResultEnum.CALL_REMOTE_ERROR.getMsg());
            return jsonResult;
        } catch (RestClientException e) {
            log.error("[接口调用异常],异常信息：{}", e.getMessage());
            jsonResult.put("code", ResultEnum.CALL_REMOTE_ERROR.getCode());
            jsonResult.put("msg", ResultEnum.CALL_REMOTE_ERROR.getMsg() + "，异常信息：" + e.getMessage());
            return jsonResult;
        }
    }

    @PostMapping("queryInfo1")
    public Params queryInfo1(@Valid @RequestBody Params params) {
        System.out.println("params = " + params.toString());
        return params;
    }


}



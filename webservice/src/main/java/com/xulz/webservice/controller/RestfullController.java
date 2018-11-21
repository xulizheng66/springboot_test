package com.xulz.webservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.commons.Constants;
import com.xulz.webservice.commons.ResponseFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @RequestMapping(value = "/getRestInfo", method = RequestMethod.GET)
    @ApiOperation(value = "REST接口调用")
    public Object getRestInfo(
            @ApiParam(value = "服务url+参数", required = true) @RequestParam(value = "") String serverUrl,
            @ApiParam(value = "headersByJson", required = true) @RequestParam(value = "") String headersByJson) {
        String result = null;
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //固定的两个headers

        JSONObject headersJson = JSONObject.parseObject(headersByJson);

        String rid = headersJson.getString("rid");
        String sid = headersJson.getString("sid");
        String rtime = headersJson.getString("rtime");
        String sign = headersJson.getString("sign");

        if (StringUtils.isNoneBlank(rid, sid, rtime, sign)) {

            headers.add("gjgxjhpt_rid", rid);
            headers.add("gjgxjhpt_sid", sid);
            headers.add("gjgxjhpt_rtime", rtime);
            headers.add("gjgxjhpt_sign", sign);

            HttpEntity<String> formEntity = new HttpEntity<>(null, headers);
            result = restTemplate.postForObject(serverUrl, formEntity, String.class);
            result = result == null ? "" : result;
            //post调用并传递参数扩展方法
        /*MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("enterprisename", "深圳市慧谷通信技术有限公司");
        map.add("apikey", "4028c681606ca20f01606d0c7e030003");
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, headers);

        HttpEntity<String> response = restTemplate.exchange(
                serverUrl,
                HttpMethod.POST, formEntity, String.class);
        String body = response.getBody();
        System.out.println("+++++++++++++++++" + body);*/
            //按照类型转换
            String firstChart = result.trim().substring(0, 1);
            if ("{".equals(firstChart)){
                JSONObject objects = JSONObject.parseObject(result);
                return ResponseFormat.retParam(200, objects);
            }else if("[".equals(firstChart)){
                JSONArray array = JSONArray.parseArray(result);
                return ResponseFormat.retParam(200, array);
            }
        }
        return "请求头参数不能为空";
    }
}

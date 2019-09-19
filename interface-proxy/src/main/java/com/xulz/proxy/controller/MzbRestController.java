package com.xulz.proxy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.inspur.openservice.api.model.PostParameter;
import com.xulz.proxy.service.SecretKeyService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.inspur.openservice.api.OpenServiceClient;
import com.inspur.openservice.api.RequestParams;
import com.inspur.openservice.api.model.ClientException;
import com.xulz.proxy.commons.Constants;
import com.xulz.proxy.commons.RedisUtils;
import com.xulz.proxy.entity.MzbParams;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 民政部接口调用
 * @author: xulz
 * @create: 2018-11-14 16:36
 **/

@Api("国家服务接口-民政部REST接口调用")
@RestController
@Log4j2
public class MzbRestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    SecretKeyService secretKeyService;
    //获取国家鉴权地址
    @Value("${com.mzb.authUrl}")
    private String authUrl;
    @Value("${com.mzb.callUrl}")
    private String callUrl;
    // 社会团体法人（兰州新区）
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
    //全国留守儿童和困境儿童信息(扶贫办)
    @Value("${com.mzb.lset_fpb.rid}")
    private String lset_fpb_rid;
    @Value("${com.mzb.lset_fpb.sid}")
    private String lset_fpb_sid;
    @Value("${com.mzb.lset_fpb.appKey}")
    private String lset_fpb_appKey;
    //低保对象(扶贫办)
    @Value("${com.mzb.db_fpb.rid}")
    private String db_fpb_rid;
    @Value("${com.mzb.db_fpb.sid}")
    private String db_fpb_sid;
    @Value("${com.mzb.db_fpb.appKey}")
    private String db_fpb_appKey;

    /**
     * 民政部-REST接口调用(公共方法)
     *
     * @param rid    请求者标识
     * @param sid    接口编码
     * @param appKey 发送方签名(appKey)
     * @param params 接口请求参数Json字符串
     * @param type   接口类型
     * @return
     */
    public JSONObject getRestInfoByMzbWithPublic(String rid, String sid, String appKey, MzbParams params, String type) {

        String url = authUrl;
        // 获取国家鉴权 (当日有效)
        JSONObject secretKeyByNation = secretKeyService.getSecretKeyByNation(url, rid, sid, appKey);
        // 返回json对象后，message 为错误信息   sign 为正确信息
        String error = secretKeyByNation.getString("message");
        String firstSign = secretKeyByNation.getString("sign");

        //返回结果
        String jsonStr = "";
        JSONObject resultJson = new JSONObject();

        if (null != secretKeyByNation && StringUtils.isNotBlank(error)) {
            resultJson.put("message", "国家鉴权获取失败");
            return resultJson;
        } else if (StringUtils.isNotBlank(firstSign)) {
            //真正的密钥有效期30分钟
            //定时器每30分钟 获取 真正的密钥并保存到redis  firstSign@rid@sid@appkey
            log.info("第一次取到的密钥==============>" + firstSign);
            JSONObject realSignAndRtime = null;
            if (redisUtils.exists(firstSign + "@" + rid + "@" + sid + "@" + appKey)) {
                realSignAndRtime = (JSONObject) redisUtils.get(firstSign + "@" + rid + "@" + sid + "@" + appKey);
                log.info("从redis中获取到的第二次密钥 ================>>>" + realSignAndRtime.toJSONString());
            } else {
                log.info("获取第二次密钥开始 ================>>>");
                realSignAndRtime = secretKeyService.getRealSignAndRtime(rid, sid, appKey);
                log.info("获取到的第二次密钥 ================>>>" + realSignAndRtime.toJSONString());
                redisUtils.set(firstSign + "@" + rid + "@" + sid + "@" + appKey, realSignAndRtime, 30L, TimeUnit.MINUTES);
            }

            String error1 = realSignAndRtime.getString("message");

            if (StringUtils.isNotBlank(error1)) {
                resultJson.put("errorMsg", "请先获取国家鉴权");
                return resultJson;
            } else {
                //业务处理开始
                log.info("业务处理开始------------->>>>>");
                String sign = realSignAndRtime.getString("sign");
                String rtime = realSignAndRtime.getString("rtime");
                log.info("第二次密钥：sign===============>" + sign);
                log.info("rtime===============>" + rtime);
                //调用接口类型 单人：dr  双人：sr  法人：fr

                String name_man = params.getName_man();
                String cert_num_man = params.getCert_num_man();
                String name_woman = params.getName_woman();
                String cert_num_woman = params.getCert_num_woman();
                String org_name = params.getOrg_name();
                String usc_code = params.getUsc_code();
                String id_card = params.getId_card();
                String name = params.getName();
                String child_name = params.getChild_name();
                String child_idcard = params.getChild_idcard();

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
                    openServiceParam.addParam("id_card", id_card);// 身份证号
                    openServiceParam.addParam("name", name); //姓名
                } else if (Constants.MZB_BZFW.equalsIgnoreCase(type)) {
                    //殡葬服务火化信息
                    openServiceParam.addParam("id_card", id_card);// 身份证号
                    openServiceParam.addParam("name", name); //逝者姓名
                } else if (Constants.MZB_LSET.equalsIgnoreCase(type)) {
                    //留守儿童和困境儿童信息
                    openServiceParam.addParam("child_name", child_name);// 儿童姓名
                    openServiceParam.addParam("child_idcard", child_idcard); //身份证号
                }

                // 分页参数
                openServiceParam.addParam("start", "0");// 起始页
                openServiceParam.addParam("limit", "1");// 页面大小

                try {
                    jsonStr = this.sendRequest(openServiceParam, client);
                } catch (ClientException e) {
                    e.printStackTrace();
                    log.error("message===================>" + e.getMessage(), e);
                    log.error("error===================>" + e.getError(), e);
                    log.error("ErrorDescription===================>" + e.getErrorDescription(), e);
                    resultJson.put("message", "系统异常【" + e.getMessage() + "】");
                    return resultJson;
                }
                System.out.println("result" + jsonStr);
                return JSONObject.parseObject(jsonStr);
            }

        }
        return resultJson;
    }

    /**
     * 民政部发送请求的方法
     */
    public String sendRequest(RequestParams requestParam, OpenServiceClient client) throws ClientException {
        String context = requestParam.getContext();
        String version = requestParam.getVersion();
        String method = requestParam.getMethod();
        List<PostParameter> postParameters = requestParam.getPostParameters();
        String resultValue = client.sendRequest(context, version, method, postParameters);
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, Object> jasonObject = (Map) mapper.readValue(resultValue, Map.class);
            String status = (String) jasonObject.get("status");
            if ("success".equals(status)) {
                return mapper.writeValueAsString(jasonObject.get("result"));
            } else {
                Map<String, Object> result = (Map) jasonObject.get("result");
                String errormsg = (String) result.get("errormsg");
                throw new RuntimeException(errormsg);
            }
        } catch (JsonParseException var12) {
            throw new RuntimeException(var12.getMessage());
        } catch (JsonMappingException var13) {
            throw new RuntimeException(var13.getMessage());
        } catch (IOException var14) {
            throw new RuntimeException(var14.getMessage());
        }
    }


    @RequestMapping(value = "/getRestInfoByMzbWithFrLzxq", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(社会团体法人-兰州新区)")
    public JSONObject getRestInfoByMzbWithFrLzxq(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_FR;
        //参数判断
        if (!StringUtils.isNoneBlank(mzbParams.getOrg_name(), mzbParams.getUsc_code())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(fr_lzxq_rid, fr_lzxq_sid, fr_lzxq_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestInfoByMzbWithFrWw", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(社会团体法人-武威)")
    public JSONObject getRestInfoByMzbWithFrWw(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_FR;
        if (!StringUtils.isNoneBlank(mzbParams.getOrg_name(), mzbParams.getUsc_code())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(fr_ww_rid, fr_ww_sid, fr_ww_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestInfoByMzbWithDrRmzf", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(单人（甘肃省人民政府))")
    public JSONObject getRestInfoByMzbWithDrRmzf(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_DR;
        if (!StringUtils.isNoneBlank(mzbParams.getName_man(), mzbParams.getCert_num_man())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(dr_srmzf_rid, dr_srmzf_sid, dr_srmzf_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestInfoByMzbWithSrRmzf", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(双人（甘肃省人民政府）)")
    public JSONObject getRestInfoByMzbWithSrRmzf(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_SR;
        if (!StringUtils.isNoneBlank(mzbParams.getName_man(), mzbParams.getCert_num_man(), mzbParams.getName_woman(), mzbParams.getCert_num_woman())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(sr_srmzf_rid, sr_srmzf_sid, sr_srmzf_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestInfoByMzbWithSrGat", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(双人（甘肃身公安厅）)")
    public JSONObject getRestInfoByMzbWithSrGat(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_SR;
        if (!StringUtils.isNoneBlank(mzbParams.getName_man(), mzbParams.getCert_num_man(), mzbParams.getName_woman(), mzbParams.getCert_num_woman())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(sr_sgat_rid, sr_sgat_sid, sr_sgat_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestDbInfoByMzbWithSrst", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(低保信息查询-省人社厅)")
    public JSONObject getRestDbInfoByMzbWithSrst(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_DB;
        if (!StringUtils.isNoneBlank(mzbParams.getId_card(), mzbParams.getName())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(db_srst_rid, db_srst_sid, db_srst_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getRestBzfwhhxxInfoByMzbWithLzs", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(殡葬服务火化信息查询-兰州市)")
    public JSONObject getRestBzfwhhxxInfoByMzbWithLzs(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_BZFW;
        if (!StringUtils.isNoneBlank(mzbParams.getId_card(), mzbParams.getName())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(bzfw_lzs_rid, bzfw_lzs_sid, bzfw_lzs_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getLsethkjetByMzbWithFpb", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(全国留守儿童和和困境儿童信息查询-扶贫办)")
    public JSONObject getLsethkjetByMzbWithFpb(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_LSET;
        if (!StringUtils.isNoneBlank(mzbParams.getChild_idcard(), mzbParams.getChild_name())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(lset_fpb_rid, lset_fpb_sid, lset_fpb_appKey, mzbParams, type);
        return restInfo;
    }

    @RequestMapping(value = "/getDbByMzbWithFpb", method = RequestMethod.POST)
    @ApiOperation(value = "民政部-REST接口调用(低保对象信息查询-扶贫办)")
    public JSONObject getDbByMzbWithFpb(@RequestBody MzbParams mzbParams) {
        JSONObject result = new JSONObject();
        String type = Constants.MZB_DB;
        if (!StringUtils.isNoneBlank(mzbParams.getId_card(), mzbParams.getName())) {
            result.put("message", "参数错误，请核实");
            return result;
        }
        JSONObject restInfo = getRestInfoByMzbWithPublic(db_fpb_rid, db_fpb_sid, db_fpb_appKey, mzbParams, type);
        return restInfo;
    }


    /**
     * 系统参数校验
     * @param params
     * @return
     */
    private JSONObject checkParmas(MzbParams params) {
        JSONObject result = new JSONObject();
        if (StringUtils.isBlank(params.getSid())) {
            result.put("errorMsg", "sid不能为空");
            return result;
        }
        if (StringUtils.isBlank(params.getRid())) {
            result.put("errorMsg", "rid不能为空");
            return result;
        }
        if (StringUtils.isBlank(params.getAppkey())) {
            result.put("errorMsg", "appkey不能为空");
            return result;
        }
        return result;
    }

    @ApiOperation(value = "获取国家鉴权")
    private JSONObject getAuthByGj(MzbParams params) {
        String rid = params.getRid();
        String sid = params.getSid();
        String appKey = params.getAppkey();
        // 获取国家鉴权 (当日有效)
        JSONObject secretKeyByNation = secretKeyService.getSecretKeyByNation(authUrl, params.getRid(), params.getSid(), params.getAppkey());
        // 返回json对象后，message 为错误信息   sign 为正确信息
        String error = secretKeyByNation.getString("message");
        String firstSign = secretKeyByNation.getString("sign");
        //返回结果
        JSONObject resultJson = new JSONObject();

        if (null != secretKeyByNation && StringUtils.isNotBlank(error)) {
            resultJson.put("errorMsg", "国家鉴权获取失败");
            return resultJson;
        } else if (StringUtils.isNotBlank(firstSign)) {
            //真正的密钥有效期30分钟
            //定时器每30分钟 获取 真正的密钥并保存到redis  firstSign@rid@sid@appkey
            if (redisUtils.exists(firstSign + "@" + rid + "@" + sid + "@" + appKey)) {
                resultJson = (JSONObject) redisUtils.get(firstSign + "@" + rid + "@" + sid + "@" + appKey);
            } else {
                resultJson = secretKeyService.getRealSignAndRtime(rid, sid, appKey);
                redisUtils.set(firstSign + "@" + rid + "@" + sid + "@" + appKey, resultJson, 30L, TimeUnit.MINUTES);
            }

            String errorMsg = resultJson.getString("message");
            if (StringUtils.isNotBlank(errorMsg)) {
                resultJson.put("errorMsg", "请先获取国家鉴权");
                return resultJson;
            }
        }
        return resultJson;
    }


    @RequestMapping(value = "/getInfoByMzb", method = RequestMethod.POST)
    @ApiOperation(value = "民政部接口调用(通用接口)")
    public JSONObject getInfoByMzb(@RequestBody MzbParams mzbParams) {
        // 系统参数校验
        JSONObject result = this.checkParmas(mzbParams);
        if (StringUtils.isNotBlank(result.getString("errorMsg"))) {
            return result;
        }
        // 获取国家接口鉴权并保存到redis
        JSONObject authByGj = this.getAuthByGj(mzbParams);
        if (StringUtils.isNotBlank(authByGj.getString("errorMsg"))) {
            return authByGj;
        }

        //业务处理开始
        String sign = authByGj.getString("sign");
        String rtime = authByGj.getString("rtime");
        //{"name_man":"xxx","cert_num_man":"xxx","name_woman":"xxx","cert_num_woman":"xxx","org_name":"xxx","usc_code":"xxx"}

        String rid = mzbParams.getRid();
        String sid = mzbParams.getSid();

        // 入参
        String name_man = mzbParams.getName_man();
        String cert_num_man = mzbParams.getCert_num_man();
        String name_woman = mzbParams.getName_woman();
        String cert_num_woman = mzbParams.getCert_num_woman();
        String org_name = mzbParams.getOrg_name();
        String usc_code = mzbParams.getUsc_code();
        String id_card = mzbParams.getId_card();
        String name = mzbParams.getName();
        String child_name = mzbParams.getChild_name();
        String child_idcard = mzbParams.getChild_idcard();

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

        // 单人
        openServiceParam.addParam("name_man", name_man); // 姓名
        openServiceParam.addParam("cert_num_man", cert_num_man);// 身份证号
        // 双人
        openServiceParam.addParam("name_man", name_man);// 男方姓名
        openServiceParam.addParam("cert_num_man", cert_num_man);// 男方身份证号
        openServiceParam.addParam("name_woman", name_woman);// 女方姓名
        openServiceParam.addParam("cert_num_woman", cert_num_woman);// 女方身份证号
        // 社会团体法人登记证书查询
        openServiceParam.addParam("org_name", org_name);// 社会组织名称
        openServiceParam.addParam("usc_code", usc_code); // 统一信用代码
        //低保对象信息 或 殡葬服务火化信息
        openServiceParam.addParam("id_card", id_card);// 身份证号
        openServiceParam.addParam("name", name); //姓名
        //全国留守儿童和困境儿童信息
        openServiceParam.addParam("child_name", child_name);// 儿童姓名
        openServiceParam.addParam("child_idcard", child_idcard); //身份证号码

        // 分页参数
        openServiceParam.addParam("start", "0");// 起始页
        openServiceParam.addParam("limit", "1");// 页面大小
        // 返回结果
        JSONObject resultJson = new JSONObject();
        try {
            String resultStr = client.sendRequest(openServiceParam);
            resultJson = JSONObject.parseObject(resultStr);
            return resultJson;
        } catch (ClientException e) {
            e.printStackTrace();
            resultJson.put("message", "参数异常");
            return resultJson;
        }
    }


}

package com.xulz.proxy.service;

import com.alibaba.fastjson.JSONObject;
import com.xulz.proxy.commons.GetSecretKey;
import com.xulz.proxy.commons.RedisUtils;
import com.xulz.proxy.entity.NationInterface;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author xulz
 * @Date 2019/7/11 17:28
 */
@Component
@Log4j2
public class SecretKeyService {

    @Autowired
    RedisUtils redisUtils;

    /**
     * getSecretKeyByNation
     *
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
        // 用来保存sign
        JSONObject jsonObject = new JSONObject();
        String rtime = String.valueOf(System.currentTimeMillis());
        // 首先去redis 中去取，存在则直接返回，不存在再调国家接口
        boolean exists = redisUtils.exists(rid + "@" + sid + "@" + appkey);
        if (exists) {
            NationInterface info = (NationInterface) redisUtils.get(rid + "@" + sid + "@" + appkey);
            jsonObject.put("sign", info.getRealSecretKey());
            // redis中获取到的rtime 和 sign
            log.info("sign>>>>>>>>>>>>" + info.getRealSecretKey());
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
                    log.info("国家鉴权保存到redis=================>" + isOk);
                    //存redis成功
                    if (isOk && isOk1) {
                        jsonObject.put("sign", secretKey);
                        return jsonObject;
                    } else {
                        jsonObject.put("message", "sign存入redis失败");
                        return jsonObject;
                    }
                } else if (StringUtils.isNotBlank(sign) && "-1".equals(sign)) {
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
     *
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
            log.info("redisInfo===============================>>>>>" + info.toString());
            String realSecretKey = GetSecretKey.getRealSecretKey(rtime, rid, sid, appKey, info.getRealSecretKey());
            log.info("realSecretKey===============================>>>>>>>" + realSecretKey);
            jsonObject.put("sign", realSecretKey);
            jsonObject.put("rtime", rtime);
        } else {
            jsonObject.put("message", "sign不存在，请先获取sign值");
        }
        return jsonObject;
    }

}

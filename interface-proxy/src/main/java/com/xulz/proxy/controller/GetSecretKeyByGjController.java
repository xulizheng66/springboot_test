package com.xulz.proxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.proxy.commons.GetSecretKey;
import com.xulz.proxy.commons.RedisUtils;
import com.xulz.proxy.entity.NationInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author xulz
 * @Description: 获取国家接口鉴权
 * @date 2018/11/1717:32
 */

@Api("国家服务接口-获取签名密钥")
@RestController
public class GetSecretKeyByGjController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/getSecretKeyByGj", method = RequestMethod.POST)
    @ApiOperation(value = "获取密钥")
    public JSONObject getSecretKeyByGj(
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
                if (StringUtils.isNotBlank(secretKey) && !"-1".equals(secretKey)) {

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
                    } else {
                        jsonObject.put("message", "sign存入redis失败");
                        return jsonObject;
                    }
                } else if (StringUtils.isNotBlank(sign) && "-1".equals(secretKey)) {
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

    @RequestMapping(value = "/getSignAndRtime", method = RequestMethod.POST)
    @ApiOperation(value = "获取真正的密钥和时间戳")
    public Object getSignAndRtime(
            final @ApiParam(value = "rid", required = true) @RequestParam(value = "") String rid,
            final @ApiParam(value = "sid", required = true) @RequestParam(value = "") String sid,
            final @ApiParam(value = "appKey", required = true) @RequestParam(value = "") String appKey) {


        JSONObject jsonObject = new JSONObject();
        String rtime = String.valueOf(System.currentTimeMillis());
        boolean exists = redisUtils.exists(rid + "@" + sid + "@" + appKey);

        if (exists) {
            NationInterface info = (NationInterface) redisUtils.get(rid + "@" + sid + "@" + appKey);
            //获取真正的sign
            String realSecretKey = GetSecretKey.getRealSecretKey(rtime, rid, sid, appKey, info.getRealSecretKey());

            jsonObject.put("sign", realSecretKey);
            jsonObject.put("rtime", rtime);
        } else {
            jsonObject.put("message", "sign不存在，请先获取sign值");
        }
        return jsonObject;
    }

    @GetMapping("get")
    public JSONObject get() {
        JSONObject object = new JSONObject();
        object.put("aaaa", "111111");
        object.put("bbbb", 222222);
        boolean isOK = redisUtils.set("object", object, 10L, TimeUnit.MINUTES);
        if (isOK) {
            JSONObject o = new JSONObject();
            Object object1 = redisUtils.get("object");
            if (object1 instanceof JSONObject) {
                o = (JSONObject) object1;
            }
            return o;
        }
        return null;
    }


}

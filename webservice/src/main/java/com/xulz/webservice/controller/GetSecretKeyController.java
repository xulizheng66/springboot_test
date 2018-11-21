package com.xulz.webservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.xulz.webservice.commons.GetSecretKey;
import com.xulz.webservice.commons.RedisUtils;
import com.xulz.webservice.entity.NationInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.AAShapePipe;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author xulz
 * @Description: 国家接口获取密钥
 * @date 2018/11/1717:32
 */

@Api("国家服务接口-获取签名密钥（每个接口每天获取一次）")
@RestController
public class GetSecretKeyController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/getSecretKey", method = RequestMethod.GET)
    @ApiOperation(value = "获取密钥")
    public JSONObject getSecretKey(
            @ApiParam(value = "url（获取签名地址）", required = true, defaultValue = "http://59.255.104.184:8181/sysapi/auth/refreshappsecret") @RequestParam(value = "") String url,
            @ApiParam(value = "sid（服务编码）", required = true) @RequestParam(value = "") String sid,
            @ApiParam(value = "rid（请求者身份标识）", required = true) @RequestParam(value = "") String rid,
            @ApiParam(value = "appkey（服务授权码）", required = true) @RequestParam(value = "") String appkey) {

        JSONObject jsonObject = new JSONObject();

        String rtime = String.valueOf(System.currentTimeMillis());
        String errorMsg = "系统异常";
        //首先去redis 中去取，存在则直接返回，不存在再调国家接口
        boolean exists = redisUtils.exists(rid + "@" + sid);
        if (exists){
            NationInterface info = (NationInterface) redisUtils.get(rid + "@" + sid);
            jsonObject.put("rtime",info.getRtime());
            jsonObject.put("realSecretKey",info.getRealSecretKey());
            //redis中获取到的rtime 和 realSecretKey

            System.out.println("rtime>>>>>>>>>>>>"+info.getRtime());
            System.out.println("realSecretKey>>>>>>>>>>>>"+info.getRealSecretKey());
            return jsonObject;
        }else{
            //国家接口获取到的secretKey当天有效
            String sign = GetSecretKey.getSign(sid, rid, rtime, appkey);
            if (StringUtils.isNotBlank(sign)) {
                String secretKey = GetSecretKey.getSecretKey(url, rid, sid, rtime, sign);
                if (StringUtils.isNotBlank(secretKey)) {
                    String realSecretKey = GetSecretKey.getRealSecretKey(appkey, secretKey);
                    jsonObject.put("realSecretKey",realSecretKey);
                    jsonObject.put("rtime",rtime);
                    //使用redis缓存 jsonObject,并设置有效期
                    long current = System.currentTimeMillis();//当前时间毫秒数
                    long zero = current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
                    long twelve = zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
                    long period = twelve - zero;//时间间隔
                    //保存到 nationInterface 对象中
                    NationInterface nationInterface = new NationInterface();
                    nationInterface.setRtime(rtime);
                    nationInterface.setRealSecretKey(realSecretKey);
                    nationInterface.setAppkey(appkey);
                    nationInterface.setSid(sid);
                    nationInterface.setRid(rid);
                    //设置规则 key 为 rid@sid
                    boolean isOk = redisUtils.set(rid + "@" + sid, nationInterface, period, TimeUnit.MILLISECONDS);
                    System.out.println(isOk);

                    return jsonObject;
                }
            }
        }

        jsonObject.put("error", errorMsg);
        return jsonObject;
    }

}

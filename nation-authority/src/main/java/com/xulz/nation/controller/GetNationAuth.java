package com.xulz.nation.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.xulz.nation.base.BaseController;
import com.xulz.nation.base.ResultMsg;
import com.xulz.nation.commons.GetSecretKey;
import com.xulz.nation.commons.RedisUtils;
import com.xulz.nation.entity.NationInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 获取国家鉴权
 * @Author: xulz
 * @Date： 2019/9/19 14:22
 * @version: 1.0
 */
@Log4j2
@RestController
@RequestMapping("/nation")
public class GetNationAuth extends BaseController {

    @Autowired
    RedisUtils redisUtils;

    @PostMapping("getAuth")
    @ApiOperation(value = "获取密钥")
    public ResultMsg getNationAuth(
            @ApiParam(value = "url（获取签名地址）", required = true, defaultValue = "http://59.255.105.32:8181/sysapi/auth/refreshappsecret")
            @RequestParam String url,
            @ApiParam(value = "rid（请求者身份标识）", required = true) @RequestParam String rid,
            @ApiParam(value = "sid（服务编码）", required = true) @RequestParam String sid,
            @ApiParam(value = "appkey（服务授权码）", required = true) @RequestParam String appkey) {
        log.info("国家鉴权获取开始...");
        String rtime = String.valueOf(System.currentTimeMillis());
        String sign = GetSecretKey.getSign(sid, rid, rtime, appkey);
        if (StringUtils.isNotBlank(sign)) {
            // 国家接口获取到的secretKey当天有效
            String firstSign = null;
            // 首先去redis 中去取，存在则直接返回，不存在再调国家接口
            boolean exists = redisUtils.exists("nation:" + rid + "@" + sid + "@" + appkey);
            if (exists) {
                NationInterface info = (NationInterface) redisUtils.get("nation:" + rid + "@" + sid + "@" + appkey);
                firstSign = info.getRealSecretKey();
            } else {
                // 调用远程接口，获取firstSign
                firstSign = GetSecretKey.getSecretKey(url, rid, sid, rtime, sign);
            }
            if (StringUtils.isNotBlank(firstSign) && !("notExits".equals(firstSign))) {
                NationInterface nationInterface = getTokenFromRedis(rid, sid, rtime, appkey, firstSign);
                log.info("国家鉴权获取成功...\n" + nationInterface.toString());
                log.info("获取真正的密钥开始...\n");
                JSONObject result = new JSONObject();
                String realSecretKey = GetSecretKey.getRealSecretKey(rtime, rid, sid, appkey, firstSign);
                result.put("rtime", rtime);
                result.put("sign", realSecretKey);
                log.info("获取到的签名和时间" + result.toJSONString());
                return returnSuccess(result);
            } else if (StringUtils.isBlank(firstSign)) {
                log.error("鉴权获取异常，请检查url");
                return returnError("鉴权获取异常，请检查url");
            }
            log.error("请求不存在于数据库授权列表中");
            return returnError("请求不存在于数据库授权列表中");
        }
        log.error("系统异常，hmacsha256 计算失败");
        return returnError("系统异常，hmacsha256 计算失败");
    }

    /**
     * 将当天的密钥保存到redis
     */
    private NationInterface getTokenFromRedis(String rid, String sid, String rtime, String appkey, String secretKey) {
        NationInterface ni = (NationInterface) redisUtils.get("nation:" + rid + "@" + sid + "@" + appkey);
        if (null != ni) {
            return ni;
        }
        // 当前时间
        Date now = DateUtil.date();
        // 今天12点的时间
        Date endOfDay = DateUtil.endOfDay(now);
        // 相差时间 毫秒
        long period = DateUtil.between(now, endOfDay, DateUnit.MS);
        // 存入redis 避免重复获取
        NationInterface nationInterface = new NationInterface();
        nationInterface.setRtime(rtime);
        nationInterface.setRealSecretKey(secretKey);
        nationInterface.setAppkey(appkey);
        nationInterface.setSid(sid);
        nationInterface.setRid(rid);
        redisUtils.set("nation:" + rid + "@" + sid + "@" + appkey, nationInterface, period, TimeUnit.MILLISECONDS);
        return nationInterface;
    }

    /**
     * 将当天的密钥保存到redis
     */
    private NationInterface getRealSign(String rid, String sid, String rtime, String appkey, String secretKey) {

        return null;
    }



/*    @GetMapping("test")
    public Object test() {
        NationInterface nationInterface = new NationInterface();
        nationInterface.setRtime(String.valueOf(DateUtil.date()));
        nationInterface.setRealSecretKey("sign");
        nationInterface.setAppkey("appkey");
        nationInterface.setSid("sid");
        nationInterface.setRid("rid");

        // 当前时间
        Date now = DateUtil.date();
        // 到今天12点的时间
        Date endOfDay = DateUtil.endOfDay(now);
        //  相差时间
        long period = DateUtil.between(now, endOfDay, DateUnit.MS);
        boolean isOk = redisUtils.set("nation:rid@sid@appkey", nationInterface, period, TimeUnit.MILLISECONDS);

        NationInterface o = (NationInterface) redisUtils.get("nation:rid@sid@appkey");
        return o;
    }*/

}

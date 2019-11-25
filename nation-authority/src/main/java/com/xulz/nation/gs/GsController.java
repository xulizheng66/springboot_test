package com.xulz.nation.gs;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.xulz.nation.base.BaseController;
import com.xulz.nation.base.ResultMsg;
import com.xulz.nation.commons.GetSecretKey;
import com.xulz.nation.commons.RedisUtils;
import com.xulz.nation.entity.NationInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: xulz
 * @date: 2019/11/22 11:18
 */
@Api(value = "gs",tags = {"gs"})
@Log4j2
@RestController
@RequestMapping("/gs")
public class GsController extends BaseController {

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
        // 1.调用 HmacSHA256
        String rtime = String.valueOf(System.currentTimeMillis());
        String sign = GetSecretKey.getSign(sid, rid, rtime, appkey);
        if (StringUtils.isNotBlank(sign)) {
            // 2.调用远程接口，获取firstSign
            String firstSign = GetSecretKey.getSecretKey(url, rid, sid, rtime, sign);
            if (StringUtils.isNotBlank(firstSign) && !("notExits".equals(firstSign))) {
                log.info("国家鉴权获取成功...\n");
                log.info("获取真正的密钥开始...\n");
                // 3. 4.
                String realSecretKey = GetSecretKey.getRealSecretKey(rtime, rid, sid, appkey, firstSign);
                // 返回结果
                JSONObject result = new JSONObject();
                result.put("gjgxjhpt_rid", rid);
                result.put("gjgxjhpt_sid", sid);
                result.put("gjgxjhpt_sign", realSecretKey);
                result.put("gjgxjhpt_rtime", rtime);
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

}

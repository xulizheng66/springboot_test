package com.xulz.fwjh.controller;

import com.xulz.fwjh.commons.GetSecretKeyByFwjh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @Author xulz
 * @Date 2019/7/16 17:05
 */
@RestController
@RequestMapping("auth")
public class DecodeSignController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${request.token.url}")
    private String tokenUrl;
    @Value("${request.token.appId}")
    private String appId;
    @Value("${request.token.appKey}")
    private String appKey;

    @PostMapping("/check")
    public String check(@RequestHeader("gateway_appid") String gateway_appid,
                        @RequestHeader("gateway_sig") String gateway_sig,
                        @RequestHeader("gateway_rtime") String gateway_rtime) {

        try {
            String sig = GetSecretKeyByFwjh.gatewaySignEncode(appId, appKey, gateway_rtime);
            if (!gateway_appid.equals(appId)) {
                return "appid有误";
            }
            if (!gateway_sig.equals(sig)) {
                return "sig有误";
            }
            return "检验成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

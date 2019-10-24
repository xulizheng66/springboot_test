package com.xulz.proxy.client;

import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.exception.RestException;
import com.xulz.proxy.commons.Constants;
import com.xulz.proxy.entity.JttParams;
import lombok.extern.log4j.Log4j2;

/**
 * @Description: TODO
 * @Author xulz
 * @Date 2019/8/5 18:00
 */
@Log4j2
public class JttRestClient {

    public static RestClient getRestClient(JttParams jttParams) throws RestException {
        // http://59.219.195.5:6080/DATACENTERPro/api/dataShare/getZWResource
        // 创建一个RestClient对象
//        RestClient restClient = new RestClient("http://59.219.195.5:6080/DATACENTERPro/api");
        RestClient restClient = new RestClient(jttParams.getRestClienturl());
        //应用编码（固定不变）
        String appid = Constants.JTT.APPID;
        //机器码（固定不变）
        String devid = Constants.JTT.DEVID;
        //用户名
        String user = Constants.JTT.USER;
        //密码
        String pwd = Constants.JTT.PWD;
        log.info("restClientUrl:" + jttParams.getRestClienturl());
        log.info("devid:" + devid);
        log.info("user:" + user);
        log.info("pwd:" + pwd);
        log.info("交通厅接口------>登录开始...");
        restClient.login(appid, devid, user, pwd);
        log.info("交通厅接口------>登录成功...");
        return restClient;
    }


}

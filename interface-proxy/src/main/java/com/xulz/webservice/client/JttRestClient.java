package com.xulz.webservice.client;

import com.jiuqi.bi.rest.RestClient;
import com.jiuqi.bi.rest.exception.RestException;
import com.xulz.webservice.entity.JttParams;
import lombok.extern.log4j.Log4j2;

/**
 * @Description: TODO
 * @Author xulz
 * @Date 2019/8/5 18:00
 */
@Log4j2
public class JttRestClient {

    public static RestClient getRestClient(JttParams jttParams) {
        // http://59.219.195.5:6080/DATACENTERPro/api/dataShare/getZWResource
        // 创建一个RestClient对象
//        RestClient restClient = new RestClient("http://59.219.195.5:6080/DATACENTERPro/api");
        RestClient restClient = new RestClient(jttParams.getRestClienturl());
        boolean b = false;
        try {
            //应用编码（固定不变）
            String appid = "{68AD88CF-233E-E3E0-5035-7DF03742600A}";
            //机器码（固定不变）
            String devid = "292E-B46D-62AB-1018";
            //用户名
            String user = "guest";
            //密码
            String pwd = "guest";
            restClient.login(appid, devid, user, pwd);
            return restClient;
        } catch (RestException e1) {
            e1.printStackTrace();
            log.error(e1.getMessage(), e1);
        }
        if (!b) {
            System.out.println("登录失败");
            log.error("登录失败");
        }
        return null;
    }
}

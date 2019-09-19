package com.xulz.webservice.client;

import com.inspur.openservice.api.OpenServiceClient;
import com.inspur.openservice.api.RequestParams;

public class TestDemo {

    public static void main(String[] args) throws Exception {
        OpenServiceClient client = new OpenServiceClient();
        RequestParams openServiceParam = new RequestParams();
        // 默认参数
        openServiceParam.setContext("");
        openServiceParam.setVersion("");
        openServiceParam.setMethod("get");
        openServiceParam.addParam("clientName", "中心");

        // 中心认证参数
        openServiceParam.addParam("gjgxjhpt_rid", "7acbccd9-ff09-4d3a-a485-4ac24f94cc5d@df-177");//
        openServiceParam.addParam("gjgxjhpt_sid", "s_12100000000018032A_3942");//
        openServiceParam.addParam("gjgxjhpt_sign", "2sDewzGX7yf/fieLlLzSlOjUT+03hySsyfPVe2m85eY=");//
        openServiceParam.addParam("gjgxjhpt_rtime", "1542964093085");

        // 业务逻辑参数(单人)
//		openServiceParam.addParam("cert_num_man", "xxx");// 身份证号
//		openServiceParam.addParam("name_man", "xxx"); // 姓名

        // 业务逻辑参数(双人)
//		openServiceParam.addParam("name_man","XXX");//男方姓名
//		openServiceParam.addParam("cert_num_man","XXXXX");//男方身份证号
//		openServiceParam.addParam("cert_num_woman","XXXXX");//女方身份证号
//		openServiceParam.addParam("name_woman","XXX");//女方姓名

        // 业务逻辑参数(社会团体法人登记证书查询)
        openServiceParam.addParam("org_name", "甘肃万维信息技术有限责任公司");// 社会组织名称
        openServiceParam.addParam("usc_code", "91620100710338815H"); // 统一信用代码

        // 分页参数
        openServiceParam.addParam("start", "0");// 起始页
        openServiceParam.addParam("limit", "1");// 页面大小
        String jsonObj = client.sendRequest(openServiceParam);
        System.out.println(jsonObj);
    }
}

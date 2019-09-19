package com.xulz.proxy.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xulz.proxy.entity.People;

public class SendSoapXml {

    public String getInfo(People people) {
        Map<String, String> maps = new HashMap<String, String>();
        List<String> listData = new ArrayList<String>();

        // 参数处理
		/*JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		String FWQQBH = jsonObject.getString("FWQQBH");
		String QQBWDM = jsonObject.getString("QQBWDM");
		String QQBWMC = jsonObject.getString("QQBWMC");
		String SJLYXTDM = jsonObject.getString("SJLYXTDM");
		String SJLYXTMC = jsonObject.getString("SJLYXTMC");
		String JLS = jsonObject.getString("JLS");
		String QQRGMSFHM = jsonObject.getString("QQRGMSFHM");
		String QQRXM = jsonObject.getString("QQRXM");
		String QQRDWDM = jsonObject.getString("QQRDWDM");
		String QQRDWMC = jsonObject.getString("QQRDWMC");
		String QQYWLXDM = jsonObject.getString("QQYWLXDM");
		String QQYWLXMC = jsonObject.getString("QQYWLXMC");
		String QQYWXTDM = jsonObject.getString("QQYWXTDM");
		String QQYWXTMC = jsonObject.getString("QQYWXTMC");
		String FSSJ = TimeHelper.getCurrentCompactTime();
		// 请求人
		String requestName = jsonObject.getString("requestName");
		String requestIdCard = jsonObject.getString("requestIdCard");*/

        if (null != people) {
            String FWQQBH = people.getFWQQBH();
            String QQBWDM = people.getQQBWDM();
            String QQBWMC = people.getQQBWMC();
            String SJLYXTDM = people.getSJLYXTDM();
            String SJLYXTMC = people.getSJLYXTMC();
            String JLS = people.getJLS();
            String QQRGMSFHM = people.getQQRGMSFHM();
            String QQRXM = people.getQQRXM();
            String QQRDWDM = people.getQQRDWDM();
            String QQRDWMC = people.getQQRDWMC();
            String QQYWLXDM = people.getQQYWLXDM();
            String QQYWLXMC = people.getQQYWLXMC();
            String QQYWXTDM = people.getQQYWXTDM();
            String QQYWXTMC = people.getQQYWXTMC();
            String requestName = people.getRequestName();
            String requestIdCard = people.getRequestIdCard();
            String FSSJ = TimeHelper.getCurrentCompactTime();


            // 要查询人的信息
            listData.add(requestName);
            listData.add(requestIdCard);

            /* 公共资源交易局 **/
            maps.put("FWQQBH", FWQQBH);//
            maps.put("QQBWDM", QQBWDM);
            maps.put("QQBWMC", QQBWMC);
            maps.put("SJLYXTDM", SJLYXTDM);
            maps.put("SJLYXTMC", SJLYXTMC);
            maps.put("JLS", JLS);
            maps.put("FSSJ", FSSJ);
            maps.put("QQRGMSFHM", QQRGMSFHM);
            maps.put("QQRXM", QQRXM);
            maps.put("QQRDWDM", QQRDWDM);
            maps.put("QQRDWMC", QQRDWMC);
            maps.put("QQYWLXDM", QQYWLXDM);
            maps.put("QQYWLXMC", QQYWLXMC);
            maps.put("QQYWXTDM", QQYWXTDM);
            maps.put("QQYWXTMC", QQYWXTMC);
        }

        String sapXml = createXml(maps, listData);// 创建xml请求

        /* 公共资源交易局 **/

		/*maps.put("FWQQBH", "4660c10a-6aef-4a75-8b19-90b3b1b34e2f");//
		maps.put("QQBWDM", "GJXXZX");
		maps.put("QQBWMC", "国家信息中心");
		maps.put("SJLYXTDM", "11620000013897179N");
		maps.put("SJLYXTMC", "甘肃省人民政府办公厅");
		maps.put("JLS", "1");
		maps.put("FSSJ", FSSJ);
		maps.put("QQRGMSFHM", "622224198911094032");
		maps.put("QQRXM", "范建飞");
		maps.put("QQRDWDM", "gbdsjmzzjyxkz");
		maps.put("QQRDWMC", "甘肃省新闻出版广电局");
		maps.put("QQYWLXDM", "gbdsjmzzjyxkz");
		maps.put("QQYWLXMC", "广播电视节目制作经营许可证");
		maps.put("QQYWXTDM", "gsgbdsjzwxxzygxpt");
		maps.put("QQYWXTMC", "甘肃广播电视局政务信息资源共享平台");

		String sapXml = createXml(maps, listData);// 创建xml请求
*/
        /********* 政务服务 *********/
//		maps.put("FWQQBH", "baf7c576-5e58-447a-8da0-d92afd6950c0");//
//		maps.put("QQBWDM", "GJXXZX");
//		maps.put("QQBWMC", "国家信息中心");
//		maps.put("SJLYXTDM", "11620000013897179N");
//		maps.put("SJLYXTMC", "甘肃省人民政府办公厅");
//		maps.put("JLS", "1");
//		maps.put("FSSJ",TimeHelper.getCurrentCompactTime());
//		maps.put("QQRGMSFHM", "622224198911094032");
//		maps.put("QQRXM", "范建飞");
//		maps.put("QQRDWDM", "11620000013897179N");
//		maps.put("QQRDWMC", "甘肃省人民政府办公厅");
//		maps.put("QQYWLXDM", "SMRZ");
//		maps.put("QQYWLXMC", "实名认证");
//		maps.put("QQYWXTDM", "GSZWFWWWZQ");
//		maps.put("QQYWXTMC", "甘肃政务服务网统一身份认证系统");
//		String sapXml = createXml(maps,listData);//创建xml请求

        // 发送请求
        return sapXml;
    }

    /**
     * 创建xml请求字符串
     *
     * @return
     */
    public String createXml(Map<String, String> maps, List<String> listData) {
        StringBuffer soapRequestData = new StringBuffer();
        soapRequestData.append("<![CDATA[");
        soapRequestData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        soapRequestData.append("<PACKAGE>");
        soapRequestData.append("<PACKAGEHEAD>");
        soapRequestData.append("<FWQQBH>" + maps.get("FWQQBH") + "</FWQQBH>");
        soapRequestData.append("<QQBWDM>" + maps.get("QQBWDM") + "</QQBWDM>");
        soapRequestData.append("<QQBWMC>" + maps.get("QQBWMC") + "</QQBWMC>");
        soapRequestData.append("<SJLYXTDM>" + maps.get("SJLYXTDM") + "</SJLYXTDM>");
        soapRequestData.append("<SJLYXTMC>" + maps.get("SJLYXTMC") + "</SJLYXTMC>");
        soapRequestData.append("<JLS>" + maps.get("JLS") + "</JLS>");
        soapRequestData.append("<FSSJ>" + maps.get("FSSJ") + "</FSSJ>");
        soapRequestData.append("<QQRGMSFHM>" + maps.get("QQRGMSFHM") + "</QQRGMSFHM>");
        soapRequestData.append("<QQRXM>" + maps.get("QQRXM") + "</QQRXM>");
        soapRequestData.append("<QQRDWDM>" + maps.get("QQRDWDM") + "</QQRDWDM>");
        soapRequestData.append("<QQRDWMC>" + maps.get("QQRDWMC") + "</QQRDWMC>");
        soapRequestData.append("<QQYWLXDM>" + maps.get("QQYWLXDM") + "</QQYWLXDM>");
        soapRequestData.append("<QQYWLXMC>" + maps.get("QQYWLXMC") + "</QQYWLXMC>");
        soapRequestData.append("<QQYWXTDM>" + maps.get("QQYWXTDM") + "</QQYWXTDM>");
        soapRequestData.append("<QQYWXTMC>" + maps.get("QQYWXTMC") + "</QQYWXTMC>");
        soapRequestData.append("</PACKAGEHEAD>");
        soapRequestData.append("<DATA>");

        for (int i = 0, j = 0; i < listData.size(); i = i + 2, j++) {
            soapRequestData.append("<RECORD no=\"" + (j + 1) + "\">");
            soapRequestData.append("<XM>" + listData.get(i) + "</XM>");
            soapRequestData.append("<GMSFHM>" + listData.get(i + 1) + "</GMSFHM>");
            soapRequestData.append("</RECORD>");
        }
        soapRequestData.append("</DATA>");
        soapRequestData.append("</PACKAGE>");
        soapRequestData.append("]]>");
        String soap = soapRequestData.toString();
        return soap;

    }

}

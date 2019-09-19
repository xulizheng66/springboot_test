package com.gsww.cascade.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SendSoapXml {


    public String test1() {
        Map<String, String> maps = new HashMap<String, String>();
        List<String> listData = new ArrayList<String>();
        listData.add("章彦军");
        listData.add("622823199510163616");

        /*公共资源交易局**/
		/*maps.put("FWQQBH", "baf7c576-5e58-447a-8da0-d92afd6950c0");//
//		maps.put("FWQQBH", "s_1200000900000_2108");
		maps.put("QQBWDM", "GJXXZX");
		maps.put("QQBWMC", "国家信息中心");
		maps.put("SJLYXTDM", "11620000013897179N");
		maps.put("SJLYXTMC", "甘肃省人民政府办公厅");
		maps.put("JLS", "1");
		maps.put("FSSJ",TimeHelper.getCurrentCompactTime());
		maps.put("QQRGMSFHM", "622224198911094032");
		maps.put("QQRXM", "范建飞");
		maps.put("QQRDWDM", "12620000224333349J");
		maps.put("QQRDWMC", "甘肃省公共资源交易局");
		maps.put("QQYWLXDM", "hypbzjwtdbsfxx");
		maps.put("QQYWLXMC", "核验评标专家和投标人授权委托代表的身份信息");
		maps.put("QQYWXTDM", "gssggzyjydsjpt");
		maps.put("QQYWXTMC", "甘肃省公共资源交易大数据平台");
		String sapXml = createXml(maps,listData);//创建xml请求
		*/

        /*公共资源交易局**/
        maps.put("FWQQBH", "baf7c576-5e58-447a-8da0-d92afd6950c0");//
//		maps.put("FWQQBH", "s_1200000900000_2108");
        maps.put("QQBWDM", "GJXXZX");
        maps.put("QQBWMC", "国家信息中心");
        maps.put("SJLYXTDM", "11620000013897179N");
        maps.put("SJLYXTMC", "甘肃省人民政府办公厅");
        maps.put("JLS", "1");
        maps.put("FSSJ", TimeHelper.getCurrentCompactTime());
        maps.put("QQRGMSFHM", "622823199510163616");
        maps.put("QQRXM", "章彦军");
        maps.put("QQRDWDM", "116200000139667600");
        maps.put("QQRDWMC", "甘肃省食品药品监督管理局");
        maps.put("QQYWLXDM", "spscxkzscsqbgxksx");
        maps.put("QQYWLXMC", "食品生产许可证首次申请、变更(许可事项)");
        maps.put("QQYWXTDM", "gsfdaxzsp");
        maps.put("QQYWXTMC", "甘肃省食品药品监督管理局行政审批系统");
        String sapXml = createXml(maps, listData);//创建xml请求

        /*********政务服务*********/
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

        //发送请求
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

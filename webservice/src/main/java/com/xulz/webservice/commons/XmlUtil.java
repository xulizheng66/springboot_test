package com.xulz.webservice.commons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import com.alibaba.fastjson.JSONObject;

public class XmlUtil {

	
	/** 
     * 将xml格式的字符串转换成Map对象 
     *  
     * @param xmlStr xml格式的字符串 
     * @return Map对象 
     * @throws Exception 异常 
     */  
    public static Map<String, Object> xmlStrToMap(String xmlStr) throws Exception {  
        if(StringUtils.isEmpty(xmlStr)) {  
            return null;  
        }  
        Map<String, Object> map = new HashMap<String, Object>();  
        //将xml格式的字符串转换成Document对象  
        Document doc = DocumentHelper.parseText(xmlStr);  
        //获取根节点  
        Element root = doc.getRootElement();  
        //获取根节点下的所有元素  
        List children = root.elements();  
        //循环所有子元素  
        if(children != null && children.size() > 0) {  
            for(int i = 0; i < children.size(); i++) {  
                Element child = (Element)children.get(i);  
                map.put(child.getName(), child.getTextTrim());  
            }  
        }  
        return map;  
    }

	
    /**
     * 转换一个xml格式的字符串到json格式
     * @param xmlxml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    public static String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一个迭代方法
     * 
     * @param element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map  iterateElement(Element element) {
        List jiedian = element.elements();
        Element et = null;
        Map obj = new HashMap();
        Object temp;
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.elements().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    temp = obj.get(et.getName());
                    if(temp instanceof List){
                        list = (List)temp;
                        list.add(iterateElement(et));
                    }else if(temp instanceof Map){
                        list.add((HashMap)temp);
                        list.add(iterateElement(et));
                    }else{
                        list.add((String)temp);
                        list.add(iterateElement(et));
                    }
                    obj.put(et.getName(), list);
                }else{
                    obj.put(et.getName(), iterateElement(et));
                }
            } else {
                if (obj.containsKey(et.getName())) {
                    temp = obj.get(et.getName());
                    if(temp instanceof List){
                        list = (List)temp;
                        list.add(et.getTextTrim());
                    }else if(temp instanceof Map){
                        list.add((HashMap)temp);
                        list.add(iterateElement(et));
                    }else{
                        list.add((String)temp);
                        list.add(et.getTextTrim());
                    }
                    obj.put(et.getName(), list);
                }else{
                    obj.put(et.getName(), et.getTextTrim());
                }
            }
        }
        return obj;
    }
}

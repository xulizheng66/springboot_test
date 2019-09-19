package com.xulz.webservice.client;

import com.xulz.webservice.entity.User;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 16:52
 **/
public class Client {

    /*public static void main(String args[]) {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://59.255.188.78/service?wsdl");
        try {
            //getUser 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
            Object[] objects = client.invoke("getUser","10001");
            User user = (User) objects[0];
            System.out.println("++++++++++++++++++++"+user.getUsername());
            System.out.println("--------------------"+objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

//    public static void main(String args[]) {
//
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        org.apache.cxf.endpoint.Client client = dcf.createClient("http://59.255.42.231:8088/Tlw_CkFgw_WebService.asmx?wsdl");
//        try {
//            //getUser 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
//            Object[] objects = client.invoke("getUser","10001");
////            User user = (User) objects[0];
////            System.out.println("++++++++++++++++++++"+user.getUsername());
//            System.out.println("--------------------"+objects[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String args[]) {
//
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        org.apache.cxf.endpoint.Client client = dcf.createClient("http://127.0.0.1:8899/webservice/user?wsdl");
//        try {
//            //getName 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
//            Object[] objects = client.invoke("getName","10001");
//            System.out.println("--------------------"+objects[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String args[]) {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://127.0.0.1:8899/webservice/user?wsdl");
        try {
            //getName 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
            Object[] objects = client.invoke("getName", "10002");
            System.out.println("--------------------" + objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

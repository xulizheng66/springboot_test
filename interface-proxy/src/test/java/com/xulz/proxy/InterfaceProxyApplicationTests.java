package com.xulz.proxy;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterfaceProxyApplicationTests {

	/*@Test
	public void contextLoads() {
		System.out.println("11111111111111111111111111111");
	}*/

    /**
     * 动态调用方式
     */
	/*@Test
	public void getName() {
		// 创建动态客户端
		String wsdlAddress = "http://localhost:8899/webservice/user?wsdl";
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlAddress);
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
		try {
			//getName 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
			Object[] objects = client.invoke("getName",1L);
			System.out.println("--------------------"+objects[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*@Test
	public void getUser() {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:8899/webservice/user?wsdl");
		try {
			//getUser 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
			Object[] objects = client.invoke("getUser",10001L);
			System.out.println("--------------------"+objects[0]);
			System.out.println("--------------------"+objects[0].getClass());
//			User user = (User) objects[0];
//			System.out.println("++++++++++++++++++++"+user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

    /**
     * 代理类工厂的方式
     */
	/*@Test
	public void getName1() {
		try {
			// 接口地址
			String wsdlAddress = "http://localhost:8899/webservice/user?wsdl";
			// 代理工厂
			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
			// 设置代理地址
			jaxWsProxyFactoryBean.setAddress(wsdlAddress);
			// 设置接口类型
			jaxWsProxyFactoryBean.setServiceClass(UserService.class);
			// 创建一个代理接口实现
			UserService cs = (UserService) jaxWsProxyFactoryBean.create();
			// 数据准备
			Long userId = 10001L;
			// 调用代理接口的方法调用并返回结果
			String result = cs.getName(userId);
			System.out.println("返回结果:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*@Test
	public void getUser1() {
		try {
			// 接口地址
			String wsdlAddress = "http://localhost:8899/webservice/user?wsdl";
			// 代理工厂
			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
			// 设置代理地址
			jaxWsProxyFactoryBean.setAddress(wsdlAddress);
			// 设置接口类型
			jaxWsProxyFactoryBean.setServiceClass(UserService.class);
			// 创建一个代理接口实现
			UserService cs = (UserService) jaxWsProxyFactoryBean.create();
			// 数据准备
			Long userId = 10001L;
			// 调用代理接口的方法调用并返回结果
			User user = cs.getUser(userId);
			System.out.println("返回结果:" + user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


	/*@Test
	public void gettest() {
		try {
			// 接口地址
			String wsdlAddress = "http://59.255.42.231:8088/Tlw_CkFgw_WebService.asmx?wsdl";
			// 代理工厂
			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
			// 设置代理地址
			jaxWsProxyFactoryBean.setAddress(wsdlAddress);
			// 设置接口类型
			jaxWsProxyFactoryBean.setServiceClass(UserService.class);
			// 创建一个代理接口实现
			UserService cs = (UserService) jaxWsProxyFactoryBean.create();
			// 数据准备
			Long userId = 10001L;
			// 调用代理接口的方法调用并返回结果
			User user = cs.getUser(userId);
			System.out.println("返回结果:" + user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}

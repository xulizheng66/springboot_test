package com.xulz.webservice.config;

import com.xulz.webservice.service.UserService;
import com.xulz.webservice.service.UserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 15:05
 **/
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private SpringBus springBus;

    @Autowired
    private UserService userService;//要发布的接口

    /**
     * 注册请求接口为servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean disServlet() {
        //方法名不可以为disPatchServlet 否则项目不能启动
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(){
        return new SpringBus();
    }

    /**
     * JAX-WS
     **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
//        EndpointImpl endpoint1 = new EndpointImpl(springBus(), userService);
        endpoint.publish("/user");//发布地址
        return endpoint;
    }

    //多类业务的Endpoints，只要在CxfConfig文件继续添加Endpoint 和相应的WebService实现即可
    /** * Another Webservice of Mine. * @return */
    /*@Bean
    public AnotherService anotherService() {
        return new AnotherServiceImpl();
    }

    @Bean
    public EndpointImpl anotherEndpoint(AnotherService anotherService) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), anotherService);
        endpoint.publish("/another");
        return endpoint;
    }*/

}
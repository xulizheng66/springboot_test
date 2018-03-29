package com.turbur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication //代替上面3个注解
@EnableTransactionManagement  //开启注解事务管理
public class Application extends SpringBootServletInitializer {//配置spring mvc

    //重写configure() 方法
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}

package com.xulz.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xulz
 */
@SpringBootApplication
@EnableSwagger2             //启动swagger注解
@EnableScheduling    //开启定时器
public class InterfaceProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceProxyApplication.class, args);
    }
}

package com.xulz.nation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xulz
 */
@SpringBootApplication
@EnableSwagger2
public class NationAhthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationAhthorityApplication.class, args);
    }

}

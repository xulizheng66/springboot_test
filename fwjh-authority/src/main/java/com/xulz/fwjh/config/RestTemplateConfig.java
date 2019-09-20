package com.xulz.fwjh.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@ConditionalOnClass({RestTemplate.class})
public class RestTemplateConfig {

    @Bean
    @ConditionalOnMissingBean({RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {

        RestTemplate restTemplate = new RestTemplate(createFactory());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        for (HttpMessageConverter<?> item : converterList) {
            if (item instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) item).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
        // 解决中文乱码和返回xml
        /*restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());*/

        return restTemplate;

    }

    @Bean
    public ClientHttpRequestFactory createFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(10000);//单位为ms
        factory.setConnectTimeout(10000);//单位为ms
        return factory;
    }

}
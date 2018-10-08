package com.turbur.controller;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消息消费者
 * @author: xulz
 * @create: 2018-09-18 10:30
 **/
@Component
//通过@RabbitListener注解定义该类对hello队列的监听
@RabbitListener(queues = "hello")
public class Receiver {
    //用@RabbitHandler注解来指定对消息的处理方法
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }


}

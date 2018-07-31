package com.turbur.controller;

import com.turbur.commons.utils.TimeHelper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xulz
 * @Description: springboot 任务定时器
 * @EnableScheduling：标注启动定时任务。
 * @Scheduled(fixedRate = 1000 * 30)  定义某个定时任务。
 * @date 2018/7/3113:53
 */

@Component //把普通pojo实例化到spring容器中
//@Configurable  //spring的一个注解，用来自动注入bean的注解，不需要通过BeanFactory去获取
@EnableScheduling  //标注启动定时任务。
public class ScheduleController {

    /**
     * springboot 任务定时器
     */
    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + TimeHelper.getCurrentTime());
    }

}

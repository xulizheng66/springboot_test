package com.turbur.entity;

import org.springframework.stereotype.Component;

/**
 * @author xulz
 * @Description: ${todo}
 * @date 2018/8/1310:31
 */
@Component
public class Dog {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

package com.turbur;

import com.turbur.commons.utils.RedisUtils;
import com.turbur.entity.Persion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xulz
 * @Description: ${todo}
 * @date 2018/8/1311:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbooHelloworldQuickApplicationTests {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Persion persion;

    /**
     * redis的存取
     */
    @Test
    public void contextLoads() {
        redisUtils.set("aaa","aaa");
        System.out.println(redisUtils.get("aaa"));

    }

    /**
     * 使用yml为bean赋值
     */
    @Test
    public void test() {

        System.out.println("---------------------"+persion.getLastName());
        System.out.println("test");
        System.out.println("test1");
        System.out.println("111111");
    }

}
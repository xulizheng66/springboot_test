package com.turbur;

import com.turbur.controller.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-09-18 10:39
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class rabbitmqTest {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        sender.send();
    }
}

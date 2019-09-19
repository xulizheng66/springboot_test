package com.xulz.webservice.service;

import com.xulz.webservice.entity.User;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 14:17
 **/
@WebService(serviceName = "userService", // 方法名
        targetNamespace = "http://service.webservice.xulz.com/", // 与接口中的命名空间一致,一般是接口的包名倒写
        endpointInterface = "com.xulz.webservice.service.UserService"// 接口地址(包名+接口名)
)

@Component
public class UserServiceImpl implements UserService {

    // 创建线程安全的Map
    private static Map<String, User> userMap = Collections.synchronizedMap(new HashMap<String, User>());

    public UserServiceImpl() {
//        System.out.println("向实体类插入数据");

        User user = new User();
        user.setUserId("10001");
        user.setUsername("xulz1");
        user.setEmail("xulz1@qq.com");
        user.setGmtCreate(new Date());
        userMap.put(user.getUserId(), user);
        user = new User();
        user.setUserId("10002");
        user.setUsername("xulz2");
        user.setEmail("xulz2@qq.com");
        user.setGmtCreate(new Date());
        userMap.put(user.getUserId(), user);
        user = new User();
        user.setUserId("10003");
        user.setUsername("xulz3");
        user.setEmail("xulz3@qq.com");
        user.setGmtCreate(new Date());
        userMap.put(user.getUserId(), user);
    }

    @Override
    public String getName(String userId) {
        return "xulz-" + userId;
    }

    @Override
    public User getUser(String userId) {
        return userMap.get(userId);
    }

    @Override
    public User getUser1(User users) {
        return users;
    }

}

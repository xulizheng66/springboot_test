package com.turbur.controller;

import com.turbur.entity.User;
import com.turbur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userList")
    public List<User> findAll(){

        List<User> users = userService.getAll();

        return users;
    }

    @RequestMapping(value = "/insert")
    public void insert(){
        User user = new User();
        user.setUserId(4);
        user.setUserName("xulizheng");
        userService.insert(user);
    }

    @RequestMapping(value={"/","/index.html"})  //可以配置多个映射路径
    public ModelAndView showUserList(HttpServletRequest request){
        List<User> userList = this.findAll();
        request.setAttribute("userList",userList);
        //第一个参数：逻辑视图名，第二个参数：数据模型名，第三个参数：数据模型对象
        //逻辑视图名在配置文件application.yml 中解析为具体的页面视图
        //数据模型对象将以数据模型名称为参数名放到request属性中。
        return new ModelAndView("index","success","列表显示成功");
    }
}

package com.turbur.controller;

import com.turbur.entity.User;
import com.turbur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    /**
     * 多文件上传
     * @param multiRequest
     * @param request
     * @return
     */
    @PostMapping(value = "imgUpload")
    public ModelAndView imgUpload(MultipartHttpServletRequest multiRequest,HttpServletRequest request){
        Iterator<String> fileNames = multiRequest.getFileNames();//获得所有的文件名
        List<String> urls = new ArrayList<>();
        while (fileNames.hasNext()){
            //迭代，对单个文件进行操作
            String fileName = fileNames.next();
            MultipartFile file = multiRequest.getFile(fileName);//根据文件名获取文件
            // 文件不为空
            if(!file.isEmpty()) {
                // 文件存放路径
                String path = request.getServletContext().getRealPath("/");
                // 文件名称
                String name = String.valueOf(new Date().getTime()+"_"+file.getOriginalFilename());
                File destFile = new File(path,name);
                // 转存文件
                try {
                    file.transferTo(destFile);//把内存中图片写入磁盘
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
                // 访问的url
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + name;
                urls.add(url);
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("urls", urls);
        mv.setViewName("index");
        return mv;
    }

}

package com.turbur.controller;

import com.turbur.commons.utils.RedisUtils;
import com.turbur.entity.User;
import com.turbur.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private  RedisUtils redisUtils;

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
        HttpSession session1 = request.getSession().getSessionContext().getSession("N2FlZDlmODQtYzgwZi00Njk0LWFiODItZTY2M2IwODZkZmRk");
        HttpSession session2 = request.getSession().getSessionContext().getSession("5613110FBD80E14289B67F4D15CF9DE12");

        Object attribute = request.getSession().getAttribute("N2FlZDlmODQtYzgwZi00Njk0LWFiODItZTY2M2IwODZkZmRk");
        System.out.println(session1);
        System.out.println(session2);
        System.out.println(attribute);
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

    public String test(){
        redisUtils.set("aaa","aaa");
        return null;
    }



    /**
     * 虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致
     * main方法(序列化和反序列化)
     * @param args
     */
    public static void main(String[] args) {
        //File.separator  表示路径的分隔符（windows是"\"）
        /*File file = new File(File.separator+"filetest.txt");//默认项目所在的盘的根目录
        try {
            boolean newFile = file.createNewFile();
            if (newFile){
                System.out.println("创建成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*redisUtils.set("aaa","aaa");
        String aaa = (String) redisUtils.get("aaa");
        System.out.println(aaa);*/


        User user = new User();
        user.setUserId(10);
        user.setUserName("Ricky");
        user.setPassword("root");
        System.out.println(user);

        File objectFile = new File("d:"+File.separator+"user.bin");

        //Write Obj to File 将对象写到文件（序列化）
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(objectFile);
            oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
        }

        //Read Obj from File 从文件中读取对象（反序列化）
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(objectFile));
            User newUser = (User) ois.readObject();
            System.out.println(newUser.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
        }


    }


}

package com.xulz.webservice.controller;

import com.xulz.webservice.entity.User;
import com.xulz.webservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 14:50
 **/

@Api("userController相关api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    public RestTemplate restTemplate;
    
    
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ApiOperation(value = "根据用户Id查询对象")
    public User getUser(
    		//userId 10001
            @ApiParam(value = "用户id", required = false) @RequestParam(value = "userId") String userId) {
        return userService.getUser(userId);
    }
    
    
    @RequestMapping(value = "/getUser1", method = RequestMethod.POST)
    @ApiOperation(value = "根据用户Id查询对象")
    public String getUser1( @RequestParam(value = "userId") String userId) {
        return "1111111111";
    }
    
    @RequestMapping(value = "/getUser2", method = RequestMethod.POST)
    @ApiOperation(value = "根据用户Id查询对象")
    public String getUser2() {

        long l = TimeUnit.DAYS.toSeconds(1);
        TimeUnit milliseconds = TimeUnit.MILLISECONDS;

        return "2222222222";
    }
    
    @RequestMapping(value = "/getUser3", method = RequestMethod.POST, headers="sign=abc")
    @ApiOperation(value = "根据用户Id查询对象")
    public String getUser3(@RequestParam(value = "userId") String userId) {
    	return "3333333333";
    }
    
    
    @RequestMapping("getUserByRestTemplate")
    public Object getUserByRestTemplate() {
    	
    	
    	return null;
    } 
    
    
   
}

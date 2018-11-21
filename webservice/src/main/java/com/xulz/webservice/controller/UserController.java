package com.xulz.webservice.controller;

import com.xulz.webservice.entity.User;
import com.xulz.webservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户Id查询对象")
    public User getUser(
            @ApiParam(value = "用户id", required = false) @RequestParam(value = "userId") Long userId) {
        return userService.getUser(userId);
    }

}

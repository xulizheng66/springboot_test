package com.xulz.proxy.service;

import com.xulz.proxy.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 14:16
 **/
@WebService
public interface UserService {
    @WebMethod
    String getName(@WebParam(name = "userId") String userId);

    @WebMethod
    User getUser(@WebParam(name = "userId") String userId);

    @WebMethod
    User getUser1(@WebParam(name = "user") User users);
}

package com.xulz.webservice.service;

import com.xulz.webservice.entity.User;

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
    String getName(@WebParam(name = "userId") Long userId);
    @WebMethod
    User getUser(@WebParam(name = "userId") Long userId);
}

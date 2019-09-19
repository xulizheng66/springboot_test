package com.xulz.webservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-09 14:12
 **/
@Data //logbok注解
public class User implements Serializable {

    private static final long serialVersionUID = -5939599230753662529L;
    private String userId;
    private String username;
    private String email;
    private Date gmtCreate;

}

package com.xulz.nation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xulz
 * @Description: 国家接口密钥和请求时间信息
 * @date 2018/11/2010:56
 */
@Data
public class NationInterface implements Serializable {

    private String rid;
    private String sid;
    private String appkey;
    private String rtime;
    private String realSecretKey;
    
}

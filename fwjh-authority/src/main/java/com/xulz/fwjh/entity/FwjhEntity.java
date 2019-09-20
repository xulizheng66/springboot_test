package com.xulz.fwjh.entity;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: xulz
 * @Date： 2019/9/20 12:00
 * @version: 1.0
 */
@Data
public class FwjhEntity {

    private String appId;
    private String appKey;
    private String rtime;
    /**
     * 获取到的密钥
     */
    private String sign;

}

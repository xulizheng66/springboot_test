package com.xulz.proxy.jtt.VO;

import lombok.Data;

/**
 * @description: 返回结果
 * @author: xulz
 * @date： 2019/10/23 16:30
 * @version: 1.0
 */
@Data
public class ResultVO<T> {

    private Integer code;
    private String msg;
    private T data;

}

package com.xulz.proxy.jtt.enums;

import io.swagger.models.auth.In;
import lombok.Getter;

/**
 * @description: TODO
 * @author: xulz
 * @date： 2019/10/23 17:27
 * @version: 1.0
 */
@Getter
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 失败
     */
    ERROR(-1, "失败"),
    /**
     * 参数错误
     */
    INPUT_PARAMS_ERROR(-2, "参数错误"),
    /**
     * 远程调用异常
     */
    CALL_REMOTE_ERROR(-3, "接口调用异常");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

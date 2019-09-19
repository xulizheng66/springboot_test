package com.xulz.proxy.commons;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Objects;

/**
 * @description: ${description}
 * @author: xulz
 * @create: 2018-11-14 19:51
 **/
@Data
public class GenericResponse {

    /**
     * 程序定义状态码
     */
    private int code;
    /**
     * 必要的提示信息
     */
    private String message;
    /**
     * 业务数据
     */
    private Object datas;

    /**
     * 对业务数据单独处理
     *
     * @return
     */
    @Override
    public String toString() {
        if (Objects.isNull(this.datas)) {
            this.setDatas(new Object());
        }
        return JSON.toJSONString(this);
    }
}

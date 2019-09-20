package com.xulz.nation.base;

import com.xulz.nation.base.ResultStatusCode;
import lombok.Data;

/**
 * @Description: 返回类封装
 * @Author: xulz
 * @Date： 2019/9/19 14:56
 * @version: 1.0
 */
@Data
public class ResultMsg {

    private int resultCode;
    private String resultMsg;
    private Object resultData;
    private Exception exception;

    public ResultMsg(){}

    public ResultMsg(ResultStatusCode resultCode, String resultMsg, Object resultData){
        this.resultCode = resultCode.getResultCode();
        this.resultData = resultData;
        this.resultMsg = resultMsg;
    }

    public ResultMsg(ResultStatusCode resultCode,String resultMsg,Object resultData,Exception e){
        this.resultCode = resultCode.getResultCode();
        this.resultData = resultData;
        this.resultMsg = resultMsg;
        this.exception = e;
    }
}

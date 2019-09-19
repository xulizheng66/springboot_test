package com.xulz.nation.base;

/**
 * @Description: 返回状态枚举类
 * @Author: xulz
 * @Date： 2019/9/19 14:58
 * @version: 1.0
 */

public enum ResultStatusCode {

    /**
     * OK
     */
    OK(0, "OK"),
    /**
     *  ERROR
     */
    ERROR(-1,"ERROR");

    private int resultCode;
    private String resultMsg;


    private ResultStatusCode(int resultCode, String resultMsg)
    {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

}

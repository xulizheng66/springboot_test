package com.xulz.nation.base;

import io.swagger.annotations.ApiOperation;

/**
 * @Description: TODO
 * @Author: xulz
 * @Date： 2019/9/19 15:00
 * @version: 1.0
 */
public class BaseController {

    ResultMsg resultMsg = new ResultMsg();

    @ApiOperation(value = "获取数据成功", notes = "获取数据成功")
    public ResultMsg returnSuccess(Object object) {
        resultMsg.setResultCode(ResultStatusCode.OK.getResultCode());
        resultMsg.setResultData(object);
        resultMsg.setResultMsg("SUCCESS");
        return resultMsg;
    }

    @ApiOperation(value = "获取数据成功", notes = "获取数据成功")
    public ResultMsg returnSuccess(Object object, String msg) {
        resultMsg.setResultCode(ResultStatusCode.OK.getResultCode());
        resultMsg.setResultData(object);
        resultMsg.setResultMsg(msg);
        return resultMsg;
    }

    @ApiOperation(value = "获取数据失败", notes = "获取数据失败")
    public ResultMsg returnError(Object object) {
        resultMsg.setResultCode(ResultStatusCode.ERROR.getResultCode());
        resultMsg.setResultData(object);
        resultMsg.setResultMsg("ERROR");
        return resultMsg;
    }

    @ApiOperation(value = "获取数据失败", notes = "获取数据失败")
    public ResultMsg returnError(Object object, String errorMsg, Exception e) {
        resultMsg.setResultCode(ResultStatusCode.ERROR.getResultCode());
        resultMsg.setResultData(object);
        resultMsg.setResultMsg(errorMsg);
        resultMsg.setException(e);
        return resultMsg;
    }

}

package com.xulz.proxy.jtt.util;

import com.xulz.proxy.jtt.VO.ResultVO;
import com.xulz.proxy.jtt.enums.ResultEnum;

/**
 * @description: TODO
 * @author: xulz
 * @date： 2019/10/23 16:35
 * @version: 1.0
 */
public class ResultVOUtil {

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultEnum.SUCCESS.getMsg());
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultEnum.SUCCESS.getMsg());
        return resultVO;
    }

    public static ResultVO error() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.ERROR.getCode());
        resultVO.setMsg(ResultEnum.ERROR.getMsg());
        return resultVO;
    }

    public static ResultVO error(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.ERROR.getCode());
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}

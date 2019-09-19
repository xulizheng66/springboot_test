package com.xulz.nation.controller;

import com.xulz.nation.base.BaseController;
import com.xulz.nation.base.ResultMsg;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 获取国家鉴权
 * @Author: xulz
 * @Date： 2019/9/19 14:22
 * @version: 1.0
 */
@Log4j2
@RestController
@RequestMapping("/nation")
public class GetNationAuth extends BaseController {


    @GetMapping("getAuth")
    public ResultMsg getNationAuth(){

        return null;
    }


}

package com.xulz.proxy.entity;

import lombok.Data;

/**
 * 民政部参数帮助类
 *
 * @author Administrator
 */
@Data
public class MzbParams {

    // 获取国家鉴权固有参数
    private String rid;
    private String sid;
    private String appkey;

    private String name_man;//男方姓名
    private String cert_num_man;//男方身份证号
    private String name_woman;//女方姓名
    private String cert_num_woman;//女方身份证号
    private String org_name;// 社会组织名称
    private String usc_code;// 统一信用代码
    //低保对象信息 和 殡葬服务火化信息
    private String id_card;//身份证号
    private String name;//姓名
    //全国留守儿童和困境儿童信息
    private String child_name;//儿童姓名
    private String child_idcard;//身份证号码

    /**
     * 接口编码
     */
    private String code;


}

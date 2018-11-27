package com.xulz.webservice.entity;

import lombok.Data;
/**
   *    民政部参数帮助类
 * @author Administrator
 *
 */
@Data
public class MzbParams {

	
	private String name_man;//男方姓名
	private String cert_num_man;//男方身份证号
	private String name_woman;//女方姓名
	private String cert_num_woman;//女方身份证号
	private String org_name;// 社会组织名称
	private String usc_code;// 统一信用代码
	
}

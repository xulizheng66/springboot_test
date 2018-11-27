package com.xulz.webservice.entity;

import lombok.Data;

/**
   *  请求头帮助类
 * @author Administrator
 *
 */

@Data
public class HeaderParams {

	private String rid;
	private String sid;
	private String rtime;
	private String sign;

}

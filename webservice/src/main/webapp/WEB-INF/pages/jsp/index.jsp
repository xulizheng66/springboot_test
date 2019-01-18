<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>接口调用</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" href="/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="/style/css/admin.css" media="all">
	<script src="/style/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/layui/layui.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
		<legend>接口调用测试</legend>
	</fieldset>
	<div class="layui-collapse" lay-filter="test">
	
		<div class="layui-colla-item">
			<h2 class="layui-colla-title">获取服务聚合平台鉴权</h2>
			<div class="layui-colla-content">
				<form id="form1" class="layui-form" action="">
					<div class="layui-form-item">
					    <div class="layui-inline">
					      <label class="layui-form-label">客户端ID</label>
					      <div class="layui-input-inline">
					        <input type="text" name="appId" lay-verify="require" autocomplete="off" class="layui-input">
					      </div>
					    </div>
					    <div class="layui-inline">
					      <label class="layui-form-label">应用公钥</label>
					      <div class="layui-input-inline">
					        <input type="tel" name="appKey" lay-verify="require" autocomplete="off" class="layui-input">
					      </div>
					    </div>
				    </div>
				    <div class="layui-form-item">
					    <div class="layui-input-block">
					      <button id="button1" type="button" class="layui-btn" lay-submit="" lay-filter="demo1" onclick="queryWithAjax('1')">立即提交</button>
					      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
					    </div>
					</div>	
				</form>
			
				<table id="table1" class="layui-table" lay-size="lg" style="display: none">
				  <colgroup>
				    <col width="300">
				    <col>
				  </colgroup>
				  <thead>
				    <tr>
				      <th>字段</th>
				      <th>值</th>
				    </tr> 
				  </thead>
				  <tbody>
				  </tbody>
				</table>
			</div>
		</div>
		
	
		<div class="layui-colla-item">
			<h2 class="layui-colla-title">人口库基准信息查询接口</h2>
			<div class="layui-colla-content">
				
			<form id="form2" class="layui-form" action="">
				<div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_appid</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_appid" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_rtime</label>
				      <div class="layui-input-inline">
				        <input type="text" name="gateway_rtime" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_sig</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_sig" lay-verify="url" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			    <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">接口URL</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="url" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">白名单IP</label>
				      <div class="layui-input-inline">
				        <input type="text" name="whiteListIp" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">请求报文</label>
				      <div class="layui-input-inline">
				        <input type="text" name="xmlParams" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
				
				<div class="layui-form-item">
				    <div class="layui-input-block">
 					  <button id="button2" type="button" class="layui-btn" onclick="queryWithAjax('2')">立即提交</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				</div>
			</form>
			
			<table id="table2" class="layui-table" lay-size="lg" style="display: none">
			  <colgroup>
			    <col width="300">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th>字段</th>
			      <th>值</th>
			    </tr> 
			  </thead>
			  <tbody>
			    <tr>
			      <td>贤心</td>
			      <td>人生就像是一场修行</td>
			    </tr>
			  </tbody>
			</table>
			</div>
		</div>
		<div class="layui-colla-item">
			<h2 class="layui-colla-title">人口库身份核查接口</h2>
			<div class="layui-colla-content">
			<form id="form3" class="layui-form" action="">
				<div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_appid</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_appid" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_rtime</label>
				      <div class="layui-input-inline">
				        <input type="text" name="gateway_rtime" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_sig</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_sig" lay-verify="url" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			    <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">接口URL</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="url" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">白名单IP</label>
				      <div class="layui-input-inline">
				        <input type="text" name="whiteListIp" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">请求报文</label>
				      <div class="layui-input-inline">
				        <input type="text" name="xmlParams" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
				
				<div class="layui-form-item">
				    <div class="layui-input-block">
 					  <button id="button3" type="button" class="layui-btn" onclick="queryWithAjax('3')">立即提交</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				</div>
			</form>
			
			<table id="table3" class="layui-table" lay-size="lg" style="display: none">
			  <colgroup>
			    <col width="300">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th>字段</th>
			      <th>值</th>
			    </tr> 
			  </thead>
			  <tbody>
			    <tr>
			      <td>贤心</td>
			      <td>人生就像是一场修行</td>
			    </tr>
			  </tbody>
			</table>
			
			</div>
		</div>
		<div class="layui-colla-item">
			<h2 class="layui-colla-title">工商总局_企业基本信息查询接口</h2>
			<div class="layui-colla-content">
			<form id="form4" class="layui-form" action="">
				<div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_appid</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_appid" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_rtime</label>
				      <div class="layui-input-inline">
				        <input type="text" name="gateway_rtime" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_sig</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_sig" lay-verify="url" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			    <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">接口URL</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="url" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">请求参数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="jsonParams" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
				
				<div class="layui-form-item">
				    <div class="layui-input-block">
 					  <button id="button4" type="button" class="layui-btn" onclick="queryWithAjax('4')">立即提交</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				</div>
			</form>
			
			<table id="table4" class="layui-table" lay-size="lg" style="display: none">
			  <colgroup>
			    <col width="300">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th>字段</th>
			      <th>值</th>
			    </tr> 
			  </thead>
			  <tbody>
			    <tr>
			      <td>贤心</td>
			      <td>人生就像是一场修行</td>
			    </tr>
			  </tbody>
			</table>
			
			</div>
		</div>
		<div class="layui-colla-item">
			<h2 class="layui-colla-title">工商总局_企业基本信息验证接口</h2>
			<div class="layui-colla-content">
			<form id="form5" class="layui-form" action="">
				<div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_appid</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_appid" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_rtime</label>
				      <div class="layui-input-inline">
				        <input type="text" name="gateway_rtime" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">gateway_sig</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="gateway_sig" lay-verify="url" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			    <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">接口URL</label>
				      <div class="layui-input-inline">
				        <input type="tel" name="url" lay-verify="required|phone" autocomplete="off" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">请求参数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="jsonParams" lay-verify="email" autocomplete="off" class="layui-input">
				      </div>
				    </div>
			    </div>
				
				<div class="layui-form-item">
				    <div class="layui-input-block">
 					  <button id="button5" type="button" class="layui-btn" onclick="queryWithAjax('5')">立即提交</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				</div>
			</form>
			
			<table id="table5" class="layui-table" lay-size="lg" style="display: none">
			  <colgroup>
			    <col width="300">
			    <col width="300">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th>字段</th>
			      <th>值</th>
			      <th>说明</th>
			    </tr> 
			  </thead>
			  <tbody>
			    <tr>
			      <td>贤心</td>
			      <td>人生就像是一场修行</td>
			      <td>人生就像是一场修行</td>
			    </tr>
			  </tbody>
			</table>
			
			</div>
		</div>
	</div>

	<script>
		layui.use(['element', 'layer'], function(){
		  var element = layui.element;
		  var layer = layui.layer;
		  //监听折叠
		  element.on('collapse(test)', function(data){
		    layer.msg('展开状态：'+ data.show);
		  });
		});
		
		//ajax条件查询
	    function queryWithAjax(type) {
	        //设置为同步请求，优先执行
	        //$.ajaxSettings.async = false;
	        //1 获取token 2人口库基准信息  3人口库身份核查  4工商局基准信息  5工商局法人核查
	        if('1' == type){
		        var params = {
		        	//"requestUrl": "http://10.50.108.42/proxy/auth/token",
		        	"requestUrl": "http://29.8.101.19:5000/auth/token",
	        		"appId": $("input[name=appId]").val(),
	                "appKey": $("input[name=appKey]").val()
		        };
		        $.post('getTokenByFwjh', params, function (data) {
		        	console.log(data);
		            $("#table1 tbody tr").remove();
		            if (data.gateway_appid.length > 0){
		            	var html = 
		            	"<tr>"+
	                        "<td>"+ "gateway_appid" +"</td>"+
	                        "<td>"+ data.gateway_appid +"</td>"+
                        "</tr>" +
                        "<tr>" +
	                        "<td>"+ "gateway_rtime" +"</td>"+
	                        "<td>"+ data.gateway_rtime +"</td>"+
                        "</tr>" +
                        "<tr>"+
	                        "<td>"+ "gateway_sig" +"</td>"+
	                        "<td>"+ data.gateway_sig +"</td>"+
                        "</tr>";
                        $("#table1 tbody").append(html);
		            }else{
		                $(".table1 tbody").append(
		                    "<tr><td colspan='3'" +
		                    "style='text-align:center;font:700 14px/25px Microsoft Yahei ;color:red;'>系统查询出错</td></tr>");
		            }
		            $("#table1").show();
		        });
	        }else if('2' == type){
	        	var params = {
			        	"gateway_appid": $("#form2 input[name=gateway_appid]").val(),
			        	"gateway_rtime": $("#form2 input[name=gateway_rtime]").val(),
			        	"gateway_sig": $("#form2 input[name=gateway_sig]").val(),
		        		"url": $("#form2 input[name=url]").val(),
		                "xmlParams": $("#form2 input[name=xmlParams]").val(),
		                "whiteListIp": $("#form2 input[name=whiteListIp]").val()
			        };
	        	$.post('getInfoByPeople', params, function (data) {
		            $("#table2 tbody tr").remove();
		            console.log(data);
		            if (data. GMSFHM > 0){
		            	var html = 
		            	"<tr>"+
	                        "<td>"+ "公民身份号码(GMSFHM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.GMSFHM +"</td>"+
                        "</tr>" +
                        "<tr>" +
	                        "<td>"+ "姓名(XM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.XM +"</td>"+
                        "</tr>" +
                        "<tr>"+
	                        "<td>"+ "性别(XBDM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.XBDM +"</td>"+
                        "</tr>"+
                        "<tr>"+
	                        "<td>"+ "民族(MZDM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.MZDM +"</td>"+
	                    "</tr>"+
	                    "<tr>"+
		                    "<td>"+ "出生日期(CSRQ)" +"</td>"+
		                    "<td>"+ data.PACKAGE.DATA.RECORD.CSRQ +"</td>"+
	                	"</tr>"+
		                "<tr>"+
			                "<td>"+ "出生地-国家（地区）(CSD_GJHDQDM)" +"</td>"+
			                "<td>"+ data.PACKAGE.DATA.RECORD.CSD_GJHDQDM +"</td>"+
			            "</tr>"+
			            "<tr>"+
				            "<td>"+ "出生地-省市县（区）(CSD_SSXQDM)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.CSD_SSXQDM +"</td>"+
		      			"</tr>"+
		      			"<tr>"+
				            "<td>"+ "公民身份号码-匹配度代码(GMSFHM_PPDDM)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.GMSFHM_PPDDM +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "姓名-匹配度代码(XM_PPDDM)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.XM_PPDDM +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "死亡标识(SWBS)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.SWBS +"</td>"+
		  				"</tr>";
                        $("#table2 tbody").append(html);
		            }else{
		                $(".table2 tbody").append(
		                    "<tr><td colspan='3'" +
		                    "style='text-align:center;font:700 14px/25px Microsoft Yahei ;color:red;'>系统查询出错</td></tr>");
		            }
		            $("#table2").show();
		        });
	        }else if('3' == type){
	        	var params = {
			        	"gateway_appid": $("#form3 input[name=gateway_appid]").val(),
			        	"gateway_rtime": $("#form3 input[name=gateway_rtime]").val(),
			        	"gateway_sig": $("#form3 input[name=gateway_sig]").val(),
		        		"url": $("#form3 input[name=url]").val(),
		                "xmlParams": $("#form3 input[name=xmlParams]").val(),
		                "whiteListIp": $("#form3 input[name=whiteListIp]").val()
			        };
	        	$.post('getInfoByPeople', params, function (data) {
		            $("#table3 tbody tr").remove();
		            if (data.PACKAGE.DATA.RECORD.GMSFHM.length > 0){
		            	var html = 
		            	"<tr>"+
	                        "<td>"+ "公民身份号码(GMSFHM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.GMSFHM+"</td>"+
                        "</tr>" +
                        "<tr>" +
	                        "<td>"+ "姓名(XM)" +"</td>"+
	                        "<td>"+ data.PACKAGE.DATA.RECORD.XM +"</td>"+
                        "</tr>" +
		      			"<tr>"+
				            "<td>"+ "公民身份号码-匹配度代码(GMSFHM_PPDDM)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.GMSFHM_PPDDM +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "姓名-匹配度代码(XM_PPDDM)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.XM_PPDDM +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "死亡(注销)标识(SWBS)" +"</td>"+
				            "<td>"+ data.PACKAGE.DATA.RECORD.SWBS +"</td>"+
		  				"</tr>";
                        $("#table3 tbody").append(html);
		            }else{
		                $(".table3 tbody").append(
		                    "<tr><td colspan='3'" +
		                    "style='text-align:center;font:700 14px/25px Microsoft Yahei ;color:red;'>系统查询出错</td></tr>");
		            }
		            $("#table3").show();
		        });
	        }else if('4' == type){
	        	var params = {
			        	"gateway_appid": $("#form4 input[name=gateway_appid]").val(),
			        	"gateway_rtime": $("#form4 input[name=gateway_rtime]").val(),
			        	"gateway_sig": $("#form4 input[name=gateway_sig]").val(),
		        		"url": $("#form4 input[name=url]").val(),
		                //"jsonParams": $("#form4 input[name=jsonParams]").val()
		                "jsonParams": '{"entname": "甘肃万维信息技术有限责任公司","uniscid": "91620100710338815H"}'
			        };
	        	$.post('getInfoByGsxxcx', params, function (data) {
	        		console.log(data);
		            $("#table4 tbody tr").remove();
		            if (data.apprdate > 0){
		            	var html = 
		            	"<tr>"+
	                        "<td>"+ "核准日期(apprdate)" +"</td>"+
	                        "<td>"+ data.apprdate+"</td>"+
                        "</tr>" +
                        "<tr>" +
	                        "<td>"+ "住所(dom)" +"</td>"+
	                        "<td>"+ data.dom +"</td>"+
                        "</tr>" +
		      			"<tr>"+
				            "<td>"+ "企业名称(entname)" +"</td>"+
				            "<td>"+ data.entname +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "企业类型（中文）(enttypeCn)" +"</td>"+
				            "<td>"+ data.enttypeCn +"</td>"+
	      				"</tr>"+
		      			"<tr>"+
				            "<td>"+ "成立日期(estdate)" +"</td>"+
				            "<td>"+ data.estdate +"</td>"+
		  				"</tr>"+
		  				"<tr>"+
				            "<td>"+ "法定代表人(name)" +"</td>"+
				            "<td>"+ data.name +"</td>"+
		  				"</tr>"+
						"<tr>"+
				            "<td>"+ "经营期限自(opfrom)" +"</td>"+
				            "<td>"+ data.opfrom +"</td>"+
						"</tr>"+
						"<tr>"+
				            "<td>"+ "经营范围(opscope)" +"</td>"+
				            "<td>"+ data.opscope +"</td>"+
						"</tr>"+
						"<tr>"+
				            "<td>"+ "经营期限至(opto)" +"</td>"+
				            "<td>"+ data.opto +"</td>"+
						"</tr>"+
						"<tr>"+
				            "<td>"+ "注册资本(regcap)" +"</td>"+
				            "<td>"+ data.regcap +"</td>"+
						"</tr>"+
						"<tr>"+
				            "<td>"+ "注册资本币种（中文）(regcapcurCn)" +"</td>"+
				            "<td>"+ data.regcapcurCn +"</td>"+
						"</tr>"+
						"<tr>"+
			            	"<td>"+ "注册号(regno)" +"</td>"+
				            "<td>"+ data.regno +"</td>"+
						"</tr>"+
						"<tr>"+
				            "<td>"+ "登记机关（中文）(regorgCn)" +"</td>"+
				            "<td>"+ data.regorgCn +"</td>"+
						"</tr>"+
						"<tr>"+
						    "<td>"+ "登记状态（中文）(regstateCn)" +"</td>"+
						    "<td>"+ data.regstateCn +"</td>"+
						"</tr>"+
						"<tr>"+
					        "<td>"+ "节点号(sExtNodenum)" +"</td>"+
					        "<td>"+ data.sExtNodenum +"</td>"+
						"</tr>"+
						"<tr>"+
							"<td>"+ "统一社会信用代码(uniscid)" +"</td>"+
							"<td>"+ data.uniscid +"</td>"+
						"</tr>";
                        $("#table4 tbody").append(html);
		            }else{
		                $(".table4 tbody").append(
		                    "<tr><td colspan='3'" +
		                    "style='text-align:center;font:700 14px/25px Microsoft Yahei ;color:red;'>系统查询出错</td></tr>");
		            }
		            $("#table4").show();
		        });
	        	
	        	
	        }else if('5' == type){
	        	var params = {
			        	"gateway_appid": $("#form5 input[name=gateway_appid]").val(),
			        	"gateway_rtime": $("#form5 input[name=gateway_rtime]").val(),
			        	"gateway_sig": $("#form5 input[name=gateway_sig]").val(),
		        		"url": $("#form5 input[name=url]").val(),
		                //"jsonParams": $("#form4 input[name=jsonParams]").val()
		                "jsonParams": '{"entname": "甘肃万维信息技术有限责任公司","uniscid": "91620100710338815H"}'
			        };
	        	$.post('getInfoByGsxxyz', params, function (data) {
		            $("#table5 tbody tr").remove();
		            if (data.regstateCn.length > 0){
		            	var html = 
			            	"<tr>"+
		                        "<td>"+ "接口输入的企业名称(entname)" +"</td>"+
		                        "<td>"+ data.ent_idx.entname +"</td>"+
		                        "<td>"+ "暂无" +"</td>"+
	                        "</tr>" +
	                        "<tr>" +
		                        "<td>"+ "接口输入的统一社会信用代码(uniscid)" +"</td>"+
		                        "<td>"+ data.ent_idx.uniscid +"</td>"+
		                        "<td>"+ "暂无" +"</td>"+
	                        "</tr>" +
			      			"<tr>"+
					            "<td>"+ "企业登记状态(regstateCn)" +"</td>"+
					            "<td>"+ data.regstateCn +"</td>"+
					            "<td>"+ "暂无" +"</td>"+
		      				"</tr>"+
			      			"<tr>"+
					            "<td>"+ "校验结果代码(entchk_checkres_key)" +"</td>"+
					            "<td>"+ data.entchk_checkres_key +"</td>"+
					            "<td>"+ "01：未查询到主体\r\n 02：输入的企业名称和统一社会信用代码存在歧义\r\n 03：比对后完全一致\r\n 04：比对后存在不一致\r\n 05：输入参数需要满足必填参数规则\r\n 06：查询过程出现错误" +"</td>"+
		      				"</tr>";
                        $("#table5 tbody").append(html);
		            }else{
		                $(".table5 tbody").append(
		                    "<tr><td colspan='3'" +
		                    "style='text-align:center;font:700 14px/25px Microsoft Yahei ;color:red;'>系统查询出错</td></tr>");
		            }
		            $("#table5").show();
		        });
	        	
	        }
	    }
		
		
	</script>
		
</body>

</html>

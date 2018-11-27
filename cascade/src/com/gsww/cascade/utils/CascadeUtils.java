package com.gsww.cascade.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

public class CascadeUtils {
	public static void main(String[] args) {
//		testDbFile();
//		testFolder();
//		testFile();
		System.out.println(""+ System.currentTimeMillis());
	}
	
	
	public static void testFolder() {
		Jdbc jdbc = new Jdbc();
		try{
			String uploadFilePath = "/home/gsidc/cascade/up/";
			String targetCode = "X1100000011121212X";  //国家发改委
			//String targetCode = "2737000000000";  //山东
			//String targetCode = "2752000000000";  //贵州
			//String targetCode = "2764000001111";  //宁夏
			String pubName = "上行文件夹";
			String sourceCode = "X06200000000000000";
			int colSize = 10; //列数
			String type = "4"; //0：数据库表  1：文件     4：文件夹
			String bizCode = "0"; // 业务编码（本阶段只赋值“0”；后期扩展编码由国家平台管理部门统一通知）
			String catalogId = "1527410289871631383";
			String mngId = UUID.randomUUID().toString().replaceAll("-", "");
//			String mngId = "43591c9e6002400e9e95df41f6c5f33a";
			//插入管控数据上行表，用来存放省平台推送给国家平台的资源发布信息
			jdbc.insertMngDataup(mngId,sourceCode,type,bizCode,catalogId,"", targetCode, pubName,colSize);
			//插入数据描述上行表，用来存放省平台给国家平台推送的数据文件的描述信息
			//先写文件，并上传，然后写入数据描述上行表
			String fileName = "test1";
			String filePath = "C://Users/sjjh/" + fileName;
			System.out.println("filename=======" + fileName);
			String fileSize;
			String dataCount = "10";
			String compress = "0";  //是否压缩，0为否，1为是
			File file = new File(filePath);
			fileSize = String.valueOf(file.length());
			jdbc.insertDataDescUp(type,filePath,targetCode,fileSize,dataCount,compress,mngId, uploadFilePath+ fileName);
			//文件上传
			FileInputStream in=new FileInputStream(new File(filePath)); 
			FtpUtil.uploadFile(fileName, in);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testFile() {
		Jdbc jdbc = new Jdbc();
		try{
			String uploadFilePath = "/home/gsidc/cascade/up/";
			//String targetCode = "1100000000000";  //国家发改委
			//String targetCode = "2737000000000";  //山东
			//String targetCode = "2752000000000";  //贵州
			String targetCode = "X1100000011121212X";  //宁夏
			String pubName = "上行文件";
			String sourceCode = "X06200000000000000";
			int colSize = 1; //列数
			String type = "1"; //0：数据库表  1：文件     4：文件夹
			String bizCode = "0"; // 业务编码（本阶段只赋值“0”；后期扩展编码由国家平台管理部门统一通知）
			String catalogId = "1527410289871631383";
			String mngId = UUID.randomUUID().toString().replaceAll("-", "");
//			String mngId = "46e906aeac60499e92ca5506e4e20ed7";
			//插入管控数据上行表，用来存放省平台推送给国家平台的资源发布信息
			jdbc.insertMngDataup(mngId,sourceCode,type,bizCode,catalogId,"", targetCode, pubName,colSize);
			//插入数据描述上行表，用来存放省平台给国家平台推送的数据文件的描述信息
			//先写文件，并上传，然后写入数据描述上行表
			String fileName = "gansu_testfile.txt";
			String filePath = "C://Users/sjjh/" + fileName;
			System.out.println("filename=======" + fileName);
			String fileSize;
			String dataCount = "10";
			String compress = "0";  //是否压缩，0为否，1为是
			File file = new File(filePath);
			fileSize = String.valueOf(file.length());
			jdbc.insertDataDescUp(type,filePath,targetCode,fileSize,dataCount,compress,mngId, uploadFilePath+ fileName);
			//文件上传
			FileInputStream in=new FileInputStream(new File(filePath)); 
			FtpUtil.uploadFile(fileName, in);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static  void testDbFile() {
		Jdbc jdbc = new Jdbc();
		try{
			String uploadFilePath = "/home/gsidc/cascade/up/";
			String tableName="test001";
			String targetCode = "X1100000011121212X";  //国家发改委
			//String targetCode = "2737000000000";  //山东
			//String targetCode = "2752000000000";  //贵州
			//String targetCode = "2764000001111";  //宁夏
			String pubName = "甘肃-部委库表上行测试3";
			String sourceCode = "X06200000000000000";
			String type = "0"; //0：数据库表  1：文件     4：文件夹
			String bizCode = "0"; // 业务编码（本阶段只赋值“0”；后期扩展编码由国家平台管理部门统一通知）
			String catalogId = "1527410289871631383";
			int colSize = 10; //列数
			int rowCount = 5;
			String mngId = UUID.randomUUID().toString().replaceAll("-", "");
//			String mngId = "f1663636b6184376b84ec28beb605fff";
			//插入管控数据上行表，用来存放省平台推送给国家平台的资源发布信息
			jdbc.insertMngDataup(mngId,sourceCode,type,bizCode,catalogId,tableName, targetCode, pubName,colSize);
			//插入数据描述上行表，用来存放省平台给国家平台推送的数据文件的描述信息
			//先写文件，并上传，然后写入数据描述上行表
			String fileName = UUID.randomUUID().toString() + ".dat";
			String filePath = "C://Users/sjjh/" + fileName;
			System.out.println("filename=======" + fileName);
			try {
				TestFileDataProcessor.GenerateFile(filePath, rowCount, colSize);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			String fileSize;
			String dataCount = rowCount + "";
			String compress = "0";  //是否压缩，0为否，1为是
			File file = new File(filePath);
			fileSize = String.valueOf(file.length());
			jdbc.insertDataDescUp(type,filePath,targetCode,fileSize,dataCount,compress,mngId, uploadFilePath+ fileName);
			//文件上传
			FileInputStream in=new FileInputStream(new File(filePath)); 
			FtpUtil.uploadFile(fileName, in);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

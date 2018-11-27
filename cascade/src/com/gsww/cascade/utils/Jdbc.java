package com.gsww.cascade.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tongtech.dip.cascade.datafile.PlatformSecurityUtil;

public class Jdbc {
	
	
	public static void main(String[] args) {
	}

	public static int[] dataTypes = { 4, 6, 1, 91, 12, -5, -5, -1, 12, 12};
	public static int[] dataLen =   { 11, 12, 1, 10, 1024, 20, 20, 65535, 1024, 1024 };

	private static Connection getConnection() {
		String driver = ReadProperties.getPropertyByStr("/jdbc.properties", "jdbc.driver");
		String url = ReadProperties.getPropertyByStr("/jdbc.properties", "jdbc.url");
		String username = ReadProperties.getPropertyByStr("/jdbc.properties", "jdbc.username");
		String password = ReadProperties.getPropertyByStr("/jdbc.properties", "jdbc.password");
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,鍔犺浇瀵瑰簲椹卞姩
			conn = DriverManager.getConnection(url, username, password);// 鑾峰彇杩炴帴
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<Map<String, Object>> getDataDescDownList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from data_desc_down t";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = ps.getMetaData();
			int columnCount = rsmd.getColumnCount();
			Map<String, Object> data = null;
			while (rs.next()) {
				data = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd.getColumnLabel(i)));
				}
				list.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 发布一个资源，直接发布到数据库即可 String pubName = "河南_广东测试发布"; String sourceCode =
	 * "2746000000000"; String targetCode = "2733000000000";
	 * 
	 * @param targetCode
	 * @param pubName
	 */
	public String insertMngDataup(String id ,String sourceCode,String type,String bizCode,String catalogId,String tableName, String targetCode, String pubName, int colSize) {
		try {
			String sql = "insert into mng_data_up values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Connection conn = getConnection();
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			String pubId = id;
			// String pubName = "河南_广东测试发布";
			// String targetCode = "2733000000000";
			String dataSchema = createDataSchema(type,colSize,tableName);
			String status = "1";
			String operat_stat = "0";
			String note = pubName;
			String time = TimeHelper.getCurrentTime();
			String lastModifyTime = null;
			pstmt.setString(1, id);
			pstmt.setString(2, pubId);
			pstmt.setString(3, pubName);
			pstmt.setString(4, sourceCode);
			pstmt.setString(5, targetCode);
			pstmt.setString(6, type);
			pstmt.setString(7, dataSchema);
			pstmt.setString(8, bizCode);
			pstmt.setString(9, catalogId);
			pstmt.setString(10, status);
			pstmt.setString(11, operat_stat);
			pstmt.setString(12, note);
			pstmt.setString(13, time);
			pstmt.setString(14, lastModifyTime);
			
			pstmt.setString(15, null);
			pstmt.setString(16, null);
			pstmt.setString(17, null);
			pstmt.setString(18, null);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			id = null;
		}
		return id;
	}
	
	public static String createDataSchema(String type,int colSize,String tableName) {
		String dataSchema = "";
		if("0".equals(type)) {
			dataSchema = "{\"Table\":{\"name\":\"" + tableName + "\",\"type_name\":\"TABLE\","
					+ "\"FieldList\":[";
			for (int i = 0; i < colSize; i++) {
				boolean end = false;
				if (i == (colSize - 1))
					end = true;
				dataSchema = dataSchema + generateTemplate(i, end);
			}
			dataSchema = dataSchema + "]}}";
		} else {
			dataSchema = null;
		}
		
		return dataSchema;
	}
	

	private static String generateTableString(String tableName, int colSize) {
		String dataSchema = "{\"Table\":{\"name\":\"" + tableName + "\",\"type_name\":\"TABLE\"," + "\"FieldList\":[";
		for (int i = 0; i < colSize; i++) {
			boolean end = false;
			if (i == (colSize - 1))
				end = true;
			dataSchema = dataSchema + generateTemplate(i, end);
		}
		dataSchema = dataSchema + "]}}";
		return dataSchema;
	}

	private static String generateTemplate(int i, boolean end) {
		String key = "false";
		String nu = "true";
		if (i == 0) {
			key = "true";
			nu = "false";
		}
		int type = 4;
		int len = 10;
		if (i < dataTypes.length)
			type = dataTypes[i];
		if (i < dataLen.length)
			len = dataLen[i];
		String template = "{\"name\":\"col%d\",\"type\":\"%d\",\"typelen\":\"%d\",\"primarykey\":\"%s\",\"nullable\":%s,\"comments\":\"\"}";
		String ret = String.format(template, i + 1, type, len, key, nu);
		if (!end)
			ret = ret + ",";
		return ret;
	}

	/**
	 * 在发布的资源上，增加一条描述数据，要时该操作成功，则需要马上将 文件按描述目录生成文件，并且将文件上传到共享区对应的目录下面 确保数据是一致的
	 * String mngId = "8cba9820-53af-46fd-8daa-ffb2ad8093b1"; String filePath =
	 * "/home/tongtech/cascade/up/test001_c616207f-6acf-4b10-85e9-bfa42cd44780.dat";
	 * 
	 * @param mngId
	 * @param filePath
	 * @return
	 */
	public boolean insertDataDescUp(String type,String pathLocal,String targetCode, String fileSize, String dataCount, String compress, String mngId,
			String filePath) {
		try {
			String sql = "insert into data_desc_up values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Connection conn = getConnection();
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String pubId = id;

			String expiryDate = "30";
			String operatStat = "0";
			String time = TimeHelper.getCurrentTime();
			String createTime = time;
			String discardTime = null;
			String lastReadTime = null;
			String fileSignature = "";
			if(!"4".equals(type)) {
				fileSignature = PlatformSecurityUtil.sign(pathLocal);
			}
			System.out.println("filePath=======" + pathLocal + "\nfileSignature =======" + fileSignature);
			pstmt.setString(1, id);
			pstmt.setString(2, mngId);
			pstmt.setString(3, type);
			pstmt.setString(4, fileSize);
			pstmt.setString(5, dataCount);
			pstmt.setString(6, filePath);
			pstmt.setString(7, fileSignature);
			pstmt.setString(8, compress);
			pstmt.setString(9, operatStat);
			pstmt.setString(10, targetCode);
			pstmt.setString(11, createTime);
			pstmt.setString(12, expiryDate);
			pstmt.setString(13, lastReadTime);
			pstmt.setString(14, null);
			pstmt.setString(15, null);
			pstmt.setString(16, null);
			pstmt.setString(17, null);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//   组织机构级联使用到的方法
	//
	//
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 执行推送，去数据库中进行修改、插入
	 * @param RMS_no
	 * @param RMS_version
	 * @param RMS_parentNo
	 * @param RMS_showOrder
	 * @param RMS_trustNo
	 * @param RMS_orgPostNo
	 * @param RMS_level
	 * @param RMS_orgOwner
	 * @param RMS_type
	 * @param RMS_name
	 * @param RMS_status
	 * @return
	 */
	public static String toExecute(String RMS_no,String RMS_version,String RMS_parentNo,String RMS_showOrder,String RMS_trustNo,String RMS_orgPostNo,String RMS_level,String RMS_orgOwner,String RMS_type,String RMS_name,String RMS_status){
		System.out.println("我进了");
		boolean i = toSelectByRMSno(RMS_no);
		System.out.println(i+ "============");
		if(i){
			if(toUpdateByRMSno(RMS_no, RMS_version, RMS_parentNo, RMS_showOrder, RMS_trustNo, RMS_orgPostNo, RMS_level, RMS_orgOwner, RMS_type, RMS_name, RMS_status)!=0){
				return "success";
			}else{
				return "fail";
			}
		}else{
			PreparedStatement ps = null;
			Connection conn = null;
			String sql = "insert into organization_table (RMS_no,RMS_version,RMS_parentNo,RMS_showOrder,RMS_trustNo,RMS_orgPostNo,RMS_level,RMS_orgOwner,RMS_type,RMS_name,RMS_status)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, RMS_no);
				ps.setString(2, RMS_version);
				ps.setString(3, RMS_parentNo);
				ps.setString(4, RMS_showOrder);
				ps.setString(5, RMS_trustNo);
				ps.setString(6, RMS_orgPostNo);
				ps.setString(7, RMS_level);
				ps.setString(8, RMS_orgOwner);
				ps.setString(9, RMS_type);
				ps.setString(10, RMS_name);
				ps.setString(11, RMS_status);
				
				ps.executeUpdate();
				
				ps.close();
				conn.close();
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "fail";
		
	}
	/**
	 * 根据no查询资源是否存在
	 * @param RMS_no
	 * @return
	 */
	public static boolean toSelectByRMSno(String RMS_no){
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet result = null;
		String sql = "select * from organization_table where RMS_no = ?";
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, RMS_no);
			
			result = ps.executeQuery();
			boolean b = result.next();
			
			result.close();
			ps.close();
			conn.close();
			System.out.println(b + "-----^^-------");
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 在数据库storexml中推送过来的XML结构
	 * @param signatureContentDecode
	 */
	public static void storeXml(String signatureContentDecode){
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "insert into storexml (xmltext,time)" +
				"values(?,now())";
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, signatureContentDecode);
			
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 如果资源存在，却requesttype为push的，修改资源
	 * @param RMS_no
	 * @param RMS_version
	 * @param RMS_parentNo
	 * @param RMS_showOrder
	 * @param RMS_trustNo
	 * @param RMS_orgPostNo
	 * @param RMS_level
	 * @param RMS_orgOwner
	 * @param RMS_type
	 * @param RMS_name
	 * @param RMS_status
	 * @return
	 */
	public static int toUpdateByRMSno(String RMS_no, String RMS_version,
			String RMS_parentNo, String RMS_showOrder, String RMS_trustNo,
			String RMS_orgPostNo, String RMS_level, String RMS_orgOwner,
			String RMS_type, String RMS_name, String RMS_status) {
		PreparedStatement ps = null;
		Connection conn = null;
		
		String sql = "update organization_table set RMS_status = ?," +
				"RMS_version = ?," +
				"RMS_parentNo = ?," +
				"RMS_showOrder = ?," +
				"RMS_trustNo = ?," +
				"RMS_orgPostNo = ?," +
				"RMS_level = ?," +
				"RMS_orgOwner = ?," +
				"RMS_type = ?," +
				"RMS_name = ?" +
				" where RMS_no = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, RMS_status);
			ps.setString(2, RMS_version);
			ps.setString(3, RMS_parentNo);
			ps.setString(4, RMS_showOrder);
			ps.setString(5, RMS_trustNo);
			ps.setString(6, RMS_orgPostNo);
			ps.setString(7, RMS_level);
			ps.setString(8, RMS_orgOwner);
			ps.setString(9, RMS_type);
			ps.setString(10, RMS_name);
			ps.setString(11, RMS_no);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
		
	}
	/**
	 * 如果推送过来的requesttype为pushcancle也更新状态为2
	 * @param RMS_no
	 * @param RMS_version
	 * @param RMS_parentNo
	 * @param RMS_showOrder
	 * @param RMS_trustNo
	 * @param RMS_orgPostNo
	 * @param RMS_level
	 * @param RMS_orgOwner
	 * @param RMS_type
	 * @param RMS_name
	 * @param RMS_status
	 */
	public static void toDeleteByRMSstatus(String RMS_no, String RMS_version,
			String RMS_parentNo, String RMS_showOrder, String RMS_trustNo,
			String RMS_orgPostNo, String RMS_level, String RMS_orgOwner,
			String RMS_type, String RMS_name, String RMS_status) {
		RMS_status = "2";
		PreparedStatement ps = null;
		Connection conn = null;
		
		String sql = "update organization_table set RMS_status = ?" +
				" where RMS_no = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, RMS_status);
			ps.setString(2, RMS_no);
			
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}

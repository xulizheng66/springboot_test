package com.gsww.cascade.utils;

import java.util.ArrayList;
import java.util.List;

import com.tongtech.dip.cascade.datafile.FileDataProcessor;
import com.tongtech.dip.cascade.datafile.bean.ColumnData;
import com.tongtech.dip.cascade.datafile.bean.RowData;

public class TestFileDataProcessor {

	private	static String[]  dataValues={null,null,null,"2017-11-15",null,null,null,null,null,"test","test"};


	static String filePath = "D://testFile.dat";
	private static FileDataProcessor fileDataProcessor_write = new FileDataProcessor();
	
	static String[] types = new String[] { "insert", "delete", "update", "insert" };

	public static void main(String[] args) throws Throwable {
	
		long start = System.currentTimeMillis();
		//开始写入数据
		int rowCount = 3;
		int colCount = 11;
		
		for (int i = 0; i < rowCount; i++) {
			RowData<ColumnData> rowData = new RowData<ColumnData>();
			rowData.setOpType(types[(int)Math.random()*5]);
			rowData.setColumns(createColumns(i,colCount));
			fileDataProcessor_write.appendAndWriteRowData(filePath, rowData);
		}
		fileDataProcessor_write.close();//关闭连接
		long end = System.currentTimeMillis();
		readDatas_2(filePath);//读取数据
		long endDisplay = System.currentTimeMillis();
		System.out
				.println(rowCount + "行   *" + colCount + "列数据  ，写入耗时：" + (end - start) + "  读耗时：" + (endDisplay - end));

	}

	public static String  GenerateFile(String filePath ,int rowCount,int colCount)
	{
		//开始写入数据
		//String[] types = new String[] { "A", "B", "C", "D" };
		for (int i = 0; i < rowCount; i++) {
			RowData<ColumnData> rowData = new RowData<ColumnData>();
			rowData.setOpType(types[(int)Math.random()*5]);
			rowData.setColumns(createColumns(i,colCount));
			fileDataProcessor_write.appendAndWriteRowData(filePath, rowData);
		}
		fileDataProcessor_write.close();//关闭连接
// 		readDatas_2(filePath);//读取数据
		return filePath;
	}

	
	/**
	 * 第一种读取文件方法：一次性读取数据文件中的数据
	 */
	public static void readDatas_1() {
		FileDataProcessor fileDataProcessor_read = new FileDataProcessor();
		List<RowData<ColumnData>> readFirstRow = fileDataProcessor_read.readRowDatas(filePath);
		for (RowData<ColumnData> rd : readFirstRow) {
			// System.out.println("OpType:" + rd.getOpType());
			for (ColumnData c : rd.getColumns()) {
//				System.out.print("\t CloumnDataName:" + c.getName());
//				System.out.print("\t DbTyp:" + c.getDbType());
//				System.out.print("\t Value:" + c.getValue());
//				System.out.print("\t class:" + c.getClass());
//				System.out.println();
			}
		}
		fileDataProcessor_read.close();
	}

	/*
	 * 第二种读取文件方法
	 * hasNext,next
	 */
	public static void readDatas_2(String filePath) {
		FileDataProcessor fileDataProcessor_readNext = new FileDataProcessor(filePath);
		while (fileDataProcessor_readNext.hasNext()) {
			RowData<ColumnData> next = fileDataProcessor_readNext.next();

			System.out.println(next.getOpType());
			for (ColumnData c : next.getColumns()) {
//				System.out.print("\t dbType:" + c.getDbType());
//				System.out.print("\t name:" + c.getName());
//				System.out.print("\t value:" + c.getValue());
//				System.out.println();
			}
		}
		fileDataProcessor_readNext.close();
	}
	
	
	
	// 写入，读取，删除测试
	/**
 
		public void appendWriteDelRowData() throws Throwable {

	 

			// 第一行
			RowData rowData_1 = new RowData();
			rowData_1.setOpType("1");
			// 第一列
			ColumnData columnData_11 = new ColumnData();
			columnData_11.setName("name11");
			columnData_11.setDbType(11);
			columnData_11.setPkcolumn(true);
			columnData_11.setValue("第一行，第一列");
			// 第二列
			ColumnData columnData_12 = new ColumnData();
			columnData_12.setName("name12");
			columnData_12.setDbType(12);
			columnData_12.setPkcolumn(false);
			columnData_12.setValue("第一行，第二列");

			List<ColumnData> colums_1 = new ArrayList<ColumnData>();
			colums_1.add(columnData_11);
			colums_1.add(columnData_12);
			rowData_1.setColumns(colums_1);

			// 第二行
			RowData rowData_2 = new RowData();
			rowData_2.setOpType("2");
			// 第一列
			ColumnData columnData_21 = new ColumnData();
			columnData_21.setName("name21");
			columnData_21.setDbType(21);
			columnData_21.setPkcolumn(false);
			columnData_21.setValue("第二行，第一列");
			// 第二列
			ColumnData columnData_22 = new ColumnData();
			columnData_22.setName("name22");
			columnData_22.setDbType(22);
			columnData_22.setPkcolumn(true);
			columnData_22.setValue("第二行，第二列");

			List<ColumnData> colums_2 = new ArrayList<ColumnData>();
			colums_2.add(columnData_21);
			colums_2.add(columnData_22);
			rowData_2.setColumns(colums_2);

			fileDataProcessor_write.appendAndWriteRowData(filePath, rowData_1);
			fileDataProcessor_write.appendAndWriteRowData(filePath, rowData_2);

			// 清除文件
			// File file = new File(filePath);
			// if(file.exists()&&file.isFile()){
			// System.out.println("fileName:"+ file.getName());
			//
			// System.out.println("文件删除："+(file.delete()?"成功":"失败"));
			// }

		}

*/	
		
		// 创建多个列
		public static List<ColumnData> createColumns(int key,int colCount) {
			List<ColumnData> columnList = new ArrayList<ColumnData>();
			for (int i = 0; i < colCount; i++) {
				
				ColumnData cd = new ColumnData();
				if(i<Jdbc.dataTypes.length)
					cd.setDbType( Jdbc.dataTypes[i]); 
				else
					cd.setDbType((int) Math.random() * 10); // 类型从1 至5
				cd.setName("col" + (i+1));
				if(i<dataValues.length && dataValues[i]!=null )
				{
					if(cd.getDbType() != 91) {
						cd.setValue(dataValues[i] +i);
					} else {
						cd.setValue(dataValues[i]);
					}
				}else
					cd.setValue(i);
				
				if(i==0) {
					cd.setPkcolumn(true);
					cd.setValue(key);
				} 
				
				columnList.add(cd);
			}
			return columnList;
		}
}

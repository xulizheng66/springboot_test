package com.gsww.cascade.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tongtech.dip.cascade.datafile.FileDataProcessor;
import com.tongtech.dip.cascade.datafile.bean.ColumnData;
import com.tongtech.dip.cascade.datafile.bean.RowData;

public class FileProcesssorTest {

    static String filePath = "C:\\Users\\sjjh\\2f234ea9e06e4b5682f6655f73839213.dat";
    static FileDataProcessor fileDataProcessor_write = new FileDataProcessor();

    public static void main(String[] args) throws Throwable {
        readDatas_2(filePath);
    }

    public static void readDatas_1(String filePath) {
        FileDataProcessor fileDataProcessor_read = new FileDataProcessor();
        List<RowData<ColumnData>> readFirstRow = fileDataProcessor_read.readRowDatas(filePath);
        for (RowData<ColumnData> rd : readFirstRow) {
            System.out.println("OpType:" + rd.getOpType());
            for (ColumnData c : rd.getColumns()) {
                System.out.print("\t CloumnDataName:" + c.getName());
                System.out.print("\t DbTyp:" + c.getDbType());
                System.out.print("\t Value:" + c.getValue());
                System.out.println();
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
                System.out.print("\t dbType:" + c.getDbType());
                System.out.print("\t name:" + c.getName());
                System.out.print("\t value:" + c.getValue());
                System.out.println();
            }
        }
        fileDataProcessor_readNext.close();
    }


    // 写入，读取，删除测试
    public static String appendWriteDelRowData(String fileName) throws Throwable {
        String path = "D:\\" + fileName;
        /*
         * 写入数据
         */

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

        fileDataProcessor_write.appendAndWriteRowData(path, rowData_1);
        fileDataProcessor_write.appendAndWriteRowData(path, rowData_2);

        // 清除文件
        // File file = new File(filePath);
        // if(file.exists()&&file.isFile()){
        // System.out.println("fileName:"+ file.getName());
        //
        // System.out.println("文件删除："+(file.delete()?"成功":"失败"));
        // }
        return path;
    }


    // 创建多个列
    public static List<ColumnData> createColumns(int colCount) {
        List<ColumnData> columnList = new ArrayList<ColumnData>();
        for (int i = 1; i < colCount; i++) {
            ColumnData cd = new ColumnData();
            cd.setDbType((int) Math.random() * 10); // 类型从1 至5
            cd.setName("c_" + i);
            cd.setValue(i);
            columnList.add(cd);
        }
        return columnList;
    }
}

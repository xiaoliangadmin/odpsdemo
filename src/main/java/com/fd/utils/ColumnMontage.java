package com.fd.utils;

import com.aliyun.odps.Column;
import com.aliyun.odps.Table;
import jxl.Sheet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ColumnMontage {

    //数据库到oss数据集成任务的字段拼接
    public HashMap db2ossCm (Sheet sheet, String tableName, String rejectColumn) {
        HashMap map = new HashMap();

        String[] strArray = rejectColumn.split(",");//切分去不需要同步的字段
        List<String> strList = Arrays.asList(strArray);

        String colStr = "";
        String ossStr = "";
        int k = 0;
        for (int i=1; i<sheet.getRows(); i++) {
            if (tableName.equals(sheet.getCell(1, i).getContents())) {
                String colName = sheet.getCell(2, i).getContents();
                if(!strList.contains(colName)) {
                    colStr += "\"" + colName +"\"" + ",";
                    ossStr += "\"" + k +"\"" + ",";
                }
                k++;
            }
        }

        map.put("colStr", colStr.substring(0, colStr.length()-1));
        map.put("ossStr", ossStr.substring(0, ossStr.length()-1));

        return map;
    }

    //oss到maxcompute数据集成任务的字段拼接
    public HashMap oss2maxcomputeCm (String projectName, String tableName) {
        OdpsConn odpsConn = new OdpsConn();
        HashMap map = new HashMap();

        String colStr = "";
        String ossStr = "";
        int k = 0;

        Table table = odpsConn.getOdpsTableSchema(projectName, tableName);
        for (Column column : table.getSchema().getColumns()) {
            String colName = column.getName();
            colStr += "\"" + colName +"\"" + ",";

            ossStr += "{\"name\": "+ k + ",\"index\": " + k + ",\"type\": \"string\"},";
            k++;
        }

        map.put("colStr", colStr.substring(0, colStr.length()-1));
        map.put("ossStr", ossStr.substring(0, ossStr.length()-1));

        return map;
    }

}

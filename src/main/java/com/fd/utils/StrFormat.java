package com.fd.utils;

import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

/**
 * 替换datax的json模板中的参数值
 */
public class StrFormat {

    public static String format(String input, Map map) {
        StringSubstitutor sub = new StringSubstitutor(map);
        String resolvedString = sub.replace(input);
        return resolvedString;
    }

    public static void main(String[] args) {
        String str = FileUtils.file2str("DataxJsonMysql_2_OSS.txt");
        Map map = new HashMap();
        map.put("fromStepType", "mysql");
        map.put("fromDataSource", "bigdata_rds_mysql");
        map.put("fromSelectedDatabase", "rds_bigdata_pub");
        map.put("fromTableName", "test_data_manage_item_desc");
        map.put("fromWhere", "1=1");
        map.put("concurrent", "1");

        String aaa = format(str, map);
        System.out.println(aaa);

    }
}

package com.fd.utils;

import java.io.*;

/**
 * 将txt模板文件转换为字符串
 */
public class FileUtils {

    public static String file2str(String fileName) {
        InputStream inputStream = FileUtils.class.getResourceAsStream("/" + fileName);
        BufferedReader reader = null;
        StringBuilder sbd = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sbd.append(line).append("\n\r");
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sbd.toString();
    }

    public static void main(String[] args) {

        System.out.println(file2str("DataxJsonMysql_2_OSS.txt"));
    }

}

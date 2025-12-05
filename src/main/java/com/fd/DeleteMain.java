package com.fd;

import com.fd.commons.Service;
import com.fd.model.DataModel;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DeleteMain {

    public static void main(String[] args) {
        File file = new File("D:\\FUDIAN\\Desktop\\ttt_delete.xls");

        try {
            InputStream is = new FileInputStream(file.getAbsolutePath());
            Workbook workbook = Workbook.getWorkbook(is);
            Sheet sheet = workbook.getSheet(0);//删除的节点的时候只需要用到项目空间名称、任务名称、任务类型和任务路径就可以了

            DataModel dataModel = new DataModel();
            for (int i=sheet.getRows()-1; i>0; i--) {
                dataModel.setProjectIdentifier(sheet.getCell(0, i).getContents());//工作空间名称
                dataModel.setFolderName(sheet.getCell(1, i).getContents());//业务流程名称
                dataModel.setFileName(sheet.getCell(2, i).getContents());//任务名称
                dataModel.setFileType(Integer.parseInt(sheet.getCell(3, i).getContents()));//任务类型

                //System.out.println(i + " -- " + dataModel.getFileName());
                Service service = new Service();
                Boolean flag = service.deleteNode(dataModel);
                String messageInfo = "";
                if (flag) {
                    messageInfo = "任务节点 " + dataModel.getFileName() + " 删除成功！";
                } else {
                    messageInfo = "任务节点 " + dataModel.getFileName() + " 删除失败！";
                }
                System.out.println(messageInfo);

                if (!flag) {//创建或提交失败跳出循环，停止继续执行
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

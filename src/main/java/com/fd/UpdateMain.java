package com.fd;

import com.fd.commons.Service;
import com.fd.model.DataModel;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class UpdateMain {

    public static void main(String[] args) {
        File file = new File("D:\\FUDIAN\\Desktop\\ttt_update.xls");

        try {
            InputStream is = new FileInputStream(file.getAbsolutePath());
            Workbook workbook = Workbook.getWorkbook(is);
            Sheet sheet = workbook.getSheet(0);//修改集成等任务配置信息
            Sheet sheet2 = workbook.getSheet(1);//修改sql等任务配置信息
            Sheet sheet3 = workbook.getSheet(2);//字段映射

            Boolean z_flag = true;
            DataModel dataModel = new DataModel();
            //模板1
            for (int i=1; i<sheet.getRows(); i++) {
                dataModel.setProjectId(Long.parseLong(sheet.getCell(0, i).getContents()));//excel中第一列为 工作空间id
                dataModel.setProjectIdentifier(sheet.getCell(1, i).getContents());//工作空间名称
                dataModel.setFolderName(sheet.getCell(2, i).getContents());//业务流程名称
                dataModel.setFileName(sheet.getCell(3, i).getContents());//任务名称
                dataModel.setFileType(Integer.parseInt(sheet.getCell(4, i).getContents()));//任务类型
                dataModel.setFileDescription(sheet.getCell(5, i).getContents());//任务描述
                dataModel.setParaValue(sheet.getCell(6, i).getContents());//参数
                dataModel.setOwner(sheet.getCell(7, i).getContents());//任务责任人
                dataModel.setStop(Boolean.parseBoolean(sheet.getCell(8, i).getContents()));//是否暂停调度
                dataModel.setRerunMode(sheet.getCell(9, i).getContents());//重跑属性
                if (sheet.getCell(10, i).getContents() == null) {
                    dataModel.setAutoRerunTimes(3);//重跑次数
                } else {
                    dataModel.setAutoRerunTimes(Integer.parseInt(sheet.getCell(10, i).getContents()));//重跑次数
                }
                if (sheet.getCell(11, i).getContents() == null) {
                    dataModel.setRerunIntervalMillis(120000);//重跑间隔时间 毫秒
                } else {
                    dataModel.setRerunIntervalMillis(Integer.parseInt(sheet.getCell(11, i).getContents()));//重跑间隔时间 毫秒
                }
                if (sheet.getCell(12, i).getContents() == null) {
                    dataModel.setStartEffectDate(1725120000000L);//生效时间
                } else {
                    dataModel.setStartEffectDate(Long.parseLong(sheet.getCell(12, i).getContents()));//生效时间
                }
                if (sheet.getCell(13, i).getContents() == null) {
                    dataModel.setEndEffectDate(4070880000000L);//失效时间
                } else {
                    dataModel.setEndEffectDate(Long.parseLong(sheet.getCell(13, i).getContents()));//失效时间
                }
                dataModel.setCycleType(sheet.getCell(14, i).getContents());//调度周期
                dataModel.setCronExpress(sheet.getCell(15, i).getContents());//调度时间
                dataModel.setDependentType(sheet.getCell(16, i).getContents());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
                dataModel.setInputList(sheet.getCell(17, i).getContents());//父节点名称,多个用','隔开
                dataModel.setFromStepType(sheet.getCell(18, i).getContents());//源端数据源类型
                dataModel.setFromDataSource(sheet.getCell(19, i).getContents());//源端数据源名称
                dataModel.setFromSelectedDatabase(sheet.getCell(20, i).getContents());//源端数据库名称
                dataModel.setFromTableName(sheet.getCell(21, i).getContents());//源端数据表名称（或者为oss文件名+路径）
                dataModel.setFromWhere(sheet.getCell(22, i).getContents());//源端where条件
                dataModel.setFromSplitPk(sheet.getCell(23, i).getContents());//源端切分键
                dataModel.setFromFileFormat(sheet.getCell(24, i).getContents());//源端文本类型
                dataModel.setFromFieldDelimiter(sheet.getCell(25, i).getContents());//源端列分隔符
                dataModel.setFromLineDelimiter(sheet.getCell(26, i).getContents());//源端行分隔符
                dataModel.setFromEncoding(sheet.getCell(27, i).getContents());//源端字符集
                dataModel.setFromRejectColumn(sheet.getCell(28, i).getContents());//填写不需要同步的字段使用逗号隔开
                dataModel.setToStepType(sheet.getCell(29, i).getContents());//目标端数据源类型
                dataModel.setToDataSource(sheet.getCell(30, i).getContents());//目标端数据源名称
                dataModel.setToSelectedDatabase(sheet.getCell(31, i).getContents());//目标端数据库名称(暂时用不到)
                dataModel.setToTableName(sheet.getCell(32, i).getContents());//目标端数据表名称（或者为oss文件名+路径）
                dataModel.setToPartition(sheet.getCell(33, i).getContents());//目标端表分区
                dataModel.setToTruncate(Boolean.parseBoolean(sheet.getCell(34, i).getContents()));//目标端插入数据是否清空已有数据
                dataModel.setToWriteMode(sheet.getCell(35, i).getContents());//目标端是否替换原有文件
                dataModel.setToFileFormat(sheet.getCell(36, i).getContents());//目标端文本类型
                dataModel.setToFieldDelimiter(sheet.getCell(37, i).getContents());//目标端列分隔符
                dataModel.setToLineDelimiter(sheet.getCell(38, i).getContents());//目标端行分隔符
                dataModel.setToEncoding(sheet.getCell(39, i).getContents());//目标端字符集
                //目的端字段后面追加 40
                dataModel.setConcurrent(sheet.getCell(41, i).getContents());//期望最大并发数
                dataModel.setThrottle(sheet.getCell(42, i).getContents());//是否限流
                dataModel.setRecord(sheet.getCell(43, i).getContents());//允许错误记录数
                dataModel.setDiResourceGroupId(sheet.getCell(44, i).getContents());//集成资源组
                dataModel.setContent(sheet.getCell(45, i).getContents());//节点内容
                dataModel.setResourceGroupIdentifier(sheet.getCell(46, i).getContents());//调度资源组

                String messageInfo = "";
                Boolean flag = false;
                if (dataModel.getFileType() == 99) { //创建虚拟节点
                    Service service = new Service();
                    flag = service.updateVINode(dataModel);
                    if (flag) {
                        messageInfo = "虚拟任务节点 " + dataModel.getFileName() + " 更新并提交成功！";
                    } else {
                        messageInfo = "虚拟任务节点 " + dataModel.getFileName() + " 更新或提交失败！";
                    }
                }
                else if (dataModel.getFileType() == 6) { //创建shell节点
                    Service service = new Service();
                    flag = service.updateShellNode(dataModel);
                    if (flag) {
                        messageInfo = "shell任务节点 " + dataModel.getFileName() + " 更新并提交成功！";
                    } else {
                        messageInfo = "shell任务节点 " + dataModel.getFileName() + " 更新或提交失败！";
                    }
                } else if (dataModel.getFileType() == 23) { //创建数据集成节点
                    // 注意源库表字段需要获取后拼接到json格式里面，目标端有源端按照规则生成并拼接到json中
                    // odps端的表结构也需要创建（可以在前置批量创建，也可以在此次获取到源库表字段后按照规则创建）
                    Service service = new Service();
                    flag = service.updateDINode(dataModel, sheet3);
                    if (flag) {
                        messageInfo = "数据集成节点 " + dataModel.getFileName() + " 更新并提交成功！";
                    } else {
                        messageInfo = "数据集成节点 " + dataModel.getFileName() + " 更新或提交失败！";
                    }
                } else {
                    System.out.println("节点类型配置错误");
                }

                System.out.println(messageInfo);
                TimeUnit.SECONDS.sleep(1);//等待1秒再执行下一个

                if (!flag) {//创建或提交失败跳出循环，停止继续执行
                    z_flag = false;
                    break;
                }
            }

            //模板2
            if (z_flag) {
                for (int i=1; i<sheet2.getRows(); i++) {
                    dataModel.setProjectId(Long.parseLong(sheet2.getCell(0, i).getContents()));//excel中第一列为 工作空间id
                    dataModel.setProjectIdentifier(sheet2.getCell(1, i).getContents());//工作空间名称
                    dataModel.setFolderName(sheet2.getCell(2, i).getContents());//业务流程名称
                    dataModel.setFileName(sheet2.getCell(3, i).getContents());//任务名称
                    dataModel.setFileType(Integer.parseInt(sheet2.getCell(4, i).getContents()));//任务类型
                    dataModel.setFileDescription(sheet2.getCell(5, i).getContents());//任务描述
                    dataModel.setParaValue(sheet2.getCell(6, i).getContents());//参数
                    dataModel.setOwner(sheet2.getCell(7, i).getContents());//任务责任人
                    dataModel.setStop(Boolean.parseBoolean(sheet2.getCell(8, i).getContents()));//是否暂停调度
                    dataModel.setRerunMode(sheet2.getCell(9, i).getContents());//重跑属性
                    if (sheet.getCell(10, i).getContents() == null) {
                        dataModel.setAutoRerunTimes(3);//重跑次数
                    } else {
                        dataModel.setAutoRerunTimes(Integer.parseInt(sheet.getCell(10, i).getContents()));//重跑次数
                    }
                    if (sheet.getCell(11, i).getContents() == null) {
                        dataModel.setRerunIntervalMillis(120000);//重跑间隔时间 毫秒
                    } else {
                        dataModel.setRerunIntervalMillis(Integer.parseInt(sheet.getCell(11, i).getContents()));//重跑间隔时间 毫秒
                    }
                    if (sheet.getCell(12, i).getContents() == null) {
                        dataModel.setStartEffectDate(1725120000000L);//生效时间
                    } else {
                        dataModel.setStartEffectDate(Long.parseLong(sheet.getCell(12, i).getContents()));//生效时间
                    }
                    if (sheet.getCell(13, i).getContents() == null) {
                        dataModel.setEndEffectDate(4070880000000L);//失效时间
                    } else {
                        dataModel.setEndEffectDate(Long.parseLong(sheet.getCell(13, i).getContents()));//失效时间
                    }
                    dataModel.setCycleType(sheet2.getCell(14, i).getContents());//调度周期
                    dataModel.setCronExpress(sheet2.getCell(15, i).getContents());//调度时间
                    dataModel.setDependentType(sheet2.getCell(16, i).getContents());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
                    dataModel.setInputList(sheet2.getCell(17, i).getContents());//父节点名称,多个用','隔开
                    dataModel.setContent(sheet2.getCell(18, i).getContents());//节点内容
                    dataModel.setResourceGroupIdentifier(sheet2.getCell(19, i).getContents());//调度资源组

                    String messageInfo = "";
                    Boolean flag = false;
                    if (dataModel.getFileType() == 99) { //创建虚拟节点
                        Service service = new Service();
                        flag = service.updateVINode(dataModel);
                        if (flag) {
                            messageInfo = "虚拟任务节点 " + dataModel.getFileName() + " 更新并提交成功！";
                        } else {
                            messageInfo = "虚拟任务节点 " + dataModel.getFileName() + " 更新或提交失败！";
                        }
                    } else if (dataModel.getFileType() == 10) { //创建odps sql节点
                        Service service = new Service();
                        flag = service.updateSqlNode(dataModel);
                        if (flag) {
                            messageInfo = "odps sql任务节点 " + dataModel.getFileName() + " 更新并提交成功！";
                        } else {
                            messageInfo = "odps sql任务节点 " + dataModel.getFileName() + " 更新或提交失败！";
                        }
                    }else {
                        System.out.println("节点类型配置错误");
                    }
                    System.out.println(messageInfo);
                    TimeUnit.SECONDS.sleep(1);//等待1秒再执行下一个

                    if (!flag) {//创建或提交失败跳出循环，停止继续执行
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}

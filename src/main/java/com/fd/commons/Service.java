package com.fd.commons;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.fd.model.DataModel;
import com.fd.utils.ColumnMontage;
import com.fd.utils.FileUtils;
import com.fd.utils.StrFormat;
import com.google.gson.Gson;
import jxl.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 调用dataworks的openapi接口
 */
public class Service {

    private String regionId = "default";
    private String accessKeyId = "wsY5JSnrNDhrdaml";
    private String secret = "xyv13zow0TeFAEDEaRxNFkxKJ84auQ";
    private String product = "dataworks-public";
    private String endpoint = "dataworks-public-vpc.res.topcloud.fdb.dev";

    // 创建虚拟节点
    public Boolean createVINode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        CreateFileRequest request = new CreateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileType(dataModel.getFileType());//任务类型 99-虚拟节点
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            CreateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                Boolean f = updateNode(client, dataModel.getProjectIdentifier(), response.getData());//更新任务节点
                if (f) {//判断任务节点是否更新成功
                    flag = submitNode(client, dataModel.getProjectIdentifier(), response.getData());//提交任务节点
                }
            }

        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //创建shell节点
    public Boolean createShellNode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        CreateFileRequest request = new CreateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileType(dataModel.getFileType());//任务类型 6-shell节点
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        request.setContent(dataModel.getContent());//shell节点内容
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            CreateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                Boolean f = updateNode(client, dataModel.getProjectIdentifier(), response.getData());//更新任务节点
                if (f) {//判断任务节点是否更新成功
                    flag = submitNode(client, dataModel.getProjectIdentifier(), response.getData());//提交任务节点
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //创建数据集成节点
    public Boolean createDINode (DataModel dataModel, Sheet sheet) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        CreateFileRequest request = new CreateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileType(dataModel.getFileType());//任务类型 23-数据集成节点
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开

        ColumnMontage columnMontage = new ColumnMontage();
        HashMap colMap = null;
        String jsonModel = "";
        if ("mysql".equals(dataModel.getFromStepType()) && "oss".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonMysql_2_OSS.txt");
            colMap = columnMontage.db2ossCm(sheet, dataModel.getFromTableName(), dataModel.getFromRejectColumn());
        } else if (("db2".equals(dataModel.getFromStepType()) || "apsaradb_for_oceanbase".equals(dataModel.getFromStepType()) || "oracle".equals(dataModel.getFromStepType())) && "oss".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonDB2orOBorORACLE_2_OSS.txt");
            colMap = columnMontage.db2ossCm(sheet, dataModel.getFromTableName(), dataModel.getFromRejectColumn());
        } else if ("oss".equals(dataModel.getFromStepType()) && "odps".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonOSS_2_Maxcompute.txt");
            colMap = columnMontage.oss2maxcomputeCm(dataModel.getProjectIdentifier(), dataModel.getToTableName());
        }

        Map map = new HashMap();
        map.put("fromStepType", dataModel.getFromStepType());
        map.put("fromDataSource", dataModel.getFromDataSource());
        map.put("fromSelectedDatabase", dataModel.getFromSelectedDatabase());
        map.put("fromTableName", dataModel.getFromTableName());
        map.put("fromWhere", dataModel.getFromWhere());
        map.put("fromSplitPk", dataModel.getFromSplitPk());
        map.put("fromEncoding", dataModel.getFromEncoding());
        map.put("fromFieldDelimiter", dataModel.getFromFieldDelimiter());
        if (dataModel.getFromFieldDelimiter() == null) {
            map.put("fromLineDelimiter", "\\r\\n");
        } else {
            map.put("fromLineDelimiter", dataModel.getFromLineDelimiter());
        }
        map.put("fromFileFormat", dataModel.getFromFileFormat());
        map.put("toStepType", dataModel.getToStepType());
        map.put("toDataSource", dataModel.getToDataSource());
        map.put("toFieldDelimiter", dataModel.getToFieldDelimiter());
        if (dataModel.getToLineDelimiter() == null) {
            map.put("toLineDelimiter", "\\r\\n");
        } else {
            map.put("toLineDelimiter", dataModel.getToLineDelimiter());
        }
        map.put("toWriteMode", dataModel.getToWriteMode());
        map.put("toEncoding", dataModel.getToEncoding());
        map.put("toFileFormat", dataModel.getToFileFormat());
        map.put("toTableName", dataModel.getToTableName());
        map.put("record", dataModel.getRecord());
        map.put("concurrent", dataModel.getConcurrent());
        map.put("throttle", dataModel.getThrottle());
        map.put("toPartition", dataModel.getToPartition());
        map.put("toTruncate", dataModel.getToTruncate());
        map.put("ossStr", colMap.get("ossStr"));
        map.put("colStr", colMap.get("colStr"));
        String jsonStr = StrFormat.format(jsonModel, map);
        //System.out.println(jsonStr);

        request.setContent(jsonStr);//数据集成节点的json信息
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            CreateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                Boolean f = updateNode(client, dataModel.getProjectIdentifier(), response.getData());//更新任务节点
                if (f) {//判断任务节点是否更新成功
                    flag = submitNode(client, dataModel.getProjectIdentifier(), response.getData());//提交任务节点
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //创建odps sql任务
    public Boolean createSqlNode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        CreateFileRequest request = new CreateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileType(dataModel.getFileType());//任务类型 10 - odps sql节点
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        request.setContent(dataModel.getContent());//sql节点内容
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            CreateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                Boolean f = updateNode(client, dataModel.getProjectIdentifier(), response.getData());//更新任务节点
                if (f) {//判断任务节点是否更新成功
                    flag = submitNode(client, dataModel.getProjectIdentifier(), response.getData());//提交任务节点
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return flag;
    }

    //更新任务节点，为了在开发页面中解决依赖连线的问题
    public Boolean updateNode(IAcsClient client, String projectName, Long fileId) {
        Boolean flag = false;

        UpdateFileRequest Request = new UpdateFileRequest();

        Request.setProjectIdentifier(projectName);//dataworks工作空间名称
        Request.setFileId(fileId);//文件id
        //Request.setFileDescription("批量生成脚本，通过更新接口追加开发页面的依赖线");

        try {
            TimeUnit.SECONDS.sleep(2);
            UpdateFileResponse response = client.getAcsResponse(Request);
//            System.out.println(new Gson().toJson(response));
            flag = response.getSuccess();//任务节点是否更新成功
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //提交任务节点
    public Boolean submitNode (IAcsClient client, String projectName, Long fileId) {
        Boolean flag = false;

        SubmitFileRequest request = new SubmitFileRequest();
        request.setFileId(fileId);//文件id
        request.setProjectIdentifier(projectName);//dataworks工作空间名称
        request.setComment("test");//提交的版本comment

        try{
            TimeUnit.SECONDS.sleep(1);
            SubmitFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            flag = response.getSuccess();//任务节点是否提交成功
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //获取所有节点信息
    public Map<String, Long> getListFiles (DataModel dataModel) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        ListFilesRequest request = new ListFilesRequest();
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//dataworks工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName());//路径
        request.setKeyword(dataModel.getFileName());//任务名称
        request.setFileTypes(dataModel.getFileType().toString());//任务类型

        HashMap<String, Long> map = new HashMap<String, Long>();
        try {
            ListFilesResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            List<ListFilesResponse.Data.File> list = response.getData().getFiles();
            for (ListFilesResponse.Data.File file : list) {//获取所有匹配到的节点名称和文件id（非节点id），存入map中
//                System.out.println(file.getFileName() + " : " + file.getFileId());
                map.put(file.getFileName(), file.getFileId());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return map;
    }

    //更新虚拟节点
    public Boolean updateVINode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        HashMap<String, Long> hMap = (HashMap) getListFiles(dataModel);

        UpdateFileRequest request = new UpdateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setFileId(hMap.get(dataModel.getFileName()));//文件id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            UpdateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                flag = submitNode(client, dataModel.getProjectIdentifier(), hMap.get(dataModel.getFileName()));//提交任务节点
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //更新shell节点
    public Boolean updateShellNode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        HashMap<String, Long> hMap = (HashMap) getListFiles(dataModel);

        UpdateFileRequest request = new UpdateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setFileId(hMap.get(dataModel.getFileName()));//文件id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        request.setContent(dataModel.getContent());//shell节点内容
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            UpdateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否修改成功
                flag = submitNode(client, dataModel.getProjectIdentifier(), hMap.get(dataModel.getFileName()));//提交任务节点
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //更新数据集成节点
    public Boolean updateDINode (DataModel dataModel, Sheet sheet) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        HashMap<String, Long> hMap = (HashMap) getListFiles(dataModel);

        UpdateFileRequest request = new UpdateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setFileId(hMap.get(dataModel.getFileName()));//文件id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开

        ColumnMontage columnMontage = new ColumnMontage();
        HashMap colMap = null;
        String jsonModel = "";
        if ("mysql".equals(dataModel.getFromStepType()) && "oss".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonMysql_2_OSS.txt");
            colMap = columnMontage.db2ossCm(sheet, dataModel.getFromTableName(), dataModel.getFromRejectColumn());
        } else if (("db2".equals(dataModel.getFromStepType()) || "apsaradb_for_oceanbase".equals(dataModel.getFromStepType()) || "oracle".equals(dataModel.getFromStepType())) && "oss".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonDB2orOBorORACLE_2_OSS.txt");
            colMap = columnMontage.db2ossCm(sheet, dataModel.getFromTableName(), dataModel.getFromRejectColumn());
        } else if ("oss".equals(dataModel.getFromStepType()) && "odps".equals(dataModel.getToStepType())) {
            jsonModel = FileUtils.file2str("DataxJsonOSS_2_Maxcompute.txt");
            colMap = columnMontage.oss2maxcomputeCm(dataModel.getProjectIdentifier(), dataModel.getToTableName());
        }

        Map map = new HashMap();
        map.put("fromStepType", dataModel.getFromStepType());
        map.put("fromDataSource", dataModel.getFromDataSource());
        map.put("fromSelectedDatabase", dataModel.getFromSelectedDatabase());
        map.put("fromTableName", dataModel.getFromTableName());
        map.put("fromWhere", dataModel.getFromWhere());
        map.put("fromSplitPk", dataModel.getFromSplitPk());
        map.put("fromEncoding", dataModel.getFromEncoding());
        map.put("fromFieldDelimiter", dataModel.getFromFieldDelimiter());
        if (dataModel.getFromFieldDelimiter() == null) {
            map.put("fromLineDelimiter", "\\r\\n");
        } else {
            map.put("fromLineDelimiter", dataModel.getFromLineDelimiter());
        }
        map.put("fromFileFormat", dataModel.getFromFileFormat());
        map.put("toStepType", dataModel.getToStepType());
        map.put("toDataSource", dataModel.getToDataSource());
        map.put("toFieldDelimiter", dataModel.getToFieldDelimiter());
        if (dataModel.getToLineDelimiter() == null) {
            map.put("toLineDelimiter", "\\r\\n");
        } else {
            map.put("toLineDelimiter", dataModel.getToLineDelimiter());
        }
        map.put("toWriteMode", dataModel.getToWriteMode());
        map.put("toEncoding", dataModel.getToEncoding());
        map.put("toFileFormat", dataModel.getToFileFormat());
        map.put("toTableName", dataModel.getToTableName());
        map.put("record", dataModel.getRecord());
        map.put("concurrent", dataModel.getConcurrent());
        map.put("throttle", dataModel.getThrottle());
        map.put("toPartition", dataModel.getToPartition());
        map.put("toTruncate", dataModel.getToTruncate());
        map.put("ossStr", colMap.get("ossStr"));
        map.put("colStr", colMap.get("colStr"));
        String jsonStr = StrFormat.format(jsonModel, map);
        //System.out.println(jsonStr);

        request.setContent(jsonStr);//数据集成节点的json信息
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            UpdateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                flag = submitNode(client, dataModel.getProjectIdentifier(), hMap.get(dataModel.getFileName()));//提交任务节点
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    //更新odps sql任务
    public Boolean updateSqlNode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        HashMap<String, Long> hMap = (HashMap) getListFiles(dataModel);

        UpdateFileRequest request = new UpdateFileRequest();
        //request.setProjectId(dataModel.getProjectId());//工作空间id
        request.setFileId(hMap.get(dataModel.getFileName()));//文件id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//工作空间名称
        request.setFileFolderPath("业务流程/" + dataModel.getFolderName()); //批量创建节点,配置其路径
        request.setFileName(dataModel.getFileName());//任务名称
        request.setFileDescription(dataModel.getFileDescription());//任务描述
        request.setParaValue(dataModel.getParaValue());//参数
        //request.setOwner(dataModel.getOwner());//任务责任人 --可以不填写
        //request.setStop(dataModel.getStop());//是否暂停调度 --可以不填写
        //request.setRerunMode(dataModel.getRerunMode());//重跑属性 --可以不填写
        request.setAutoRerunTimes(dataModel.getAutoRerunTimes());//重跑次数
        request.setAutoRerunIntervalMillis(dataModel.getRerunIntervalMillis());//重跑间隔时间 毫秒
        request.setStartEffectDate(dataModel.getStartEffectDate());//生效时间
        request.setEndEffectDate(dataModel.getEndEffectDate());//失效时间
        request.setCycleType(dataModel.getCycleType());//调度周期
        request.setCronExpress(dataModel.getCronExpress());//调度时间
        //request.setDependentType(dataModel.getDependentType());//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList(dataModel.getInputList());//父节点名称,多个用','隔开
        request.setContent(dataModel.getContent());//sql节点内容
        //request.setResourceGroupIdentifier(dataModel.getResourceGroupIdentifier());//调度资源组
        request.setAutoParsing(false);//关闭自动解析

        try{
            UpdateFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                flag = submitNode(client, dataModel.getProjectIdentifier(), hMap.get(dataModel.getFileName()));//提交任务节点
            }
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return flag;
    }

    //删除任务节点
    public Boolean deleteNode (DataModel dataModel) {
        Boolean flag = false;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultProfile.addEndpoint(regionId, product, endpoint);

        IAcsClient client = new DefaultAcsClient(profile);

        HashMap<String, Long> hMap = (HashMap) getListFiles(dataModel);

        //删除任务节点
        DeleteFileRequest request = new DeleteFileRequest();
        request.setFileId(hMap.get(dataModel.getFileName()));//文件id
        request.setProjectIdentifier(dataModel.getProjectIdentifier());//dataworks工作空间名称

        try{
            TimeUnit.SECONDS.sleep(1);
            DeleteFileResponse response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
            flag = response.getSuccess();//任务节点是否提交成功
        } catch (ServerException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
        }

        return flag;
    }

    public static void main(String[] args) {
        DataModel dataModel = new DataModel();
        dataModel.setProjectIdentifier("mpdc_pub_test");
        dataModel.setFolderName("test1/数据集成");
        dataModel.setFileName("mysql2oss");
        dataModel.setFileType(23);

        Service service = new Service();
        HashMap map = (HashMap) service.getListFiles(dataModel);

        System.out.println(map.get("mysql2oss"));

    }



}

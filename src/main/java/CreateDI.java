import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateFileRequest;
import com.aliyuncs.dataworks_public.model.v20200518.CreateFileResponse;
import com.aliyuncs.dataworks_public.model.v20200518.UpdateFileRequest;
import com.aliyuncs.dataworks_public.model.v20200518.UpdateFileResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;


public class CreateDI {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        CreateFileRequest request = new CreateFileRequest();
        request.setProjectId(10124L);//工作空间id
        request.setProjectIdentifier("mpdc_pub_test");//工作空间名称
        request.setFileFolderPath("业务流程/test/数据集成"); //批量创建数据集成任务的路径
        request.setFileName("test_di777");//任务名称
        request.setFileType(23);//任务类型 23代表数据集成任务  10代表sql任务
        request.setFileDescription("测试");//任务描述
        request.setParaValue("a=x b=y");//参数
//        request.setOwner("5261540123617502367");//任务责任人 --可以不用
//        request.setStop(false);//是否暂停调度
//        request.setRerunMode("ALL_ALLOWED");//重跑属性
//        request.setAutoRerunTimes(3);//重跑次数
//        request.setAutoRerunIntervalMillis(120000);//重跑间隔时间 毫秒
//        request.setStartEffectDate(1671608450000L);//生效时间
//        request.setEndEffectDate(1671694850000L);//失效时间
        request.setCycleType("DAY");//调度周期
        request.setCronExpress("00 05 00 * * ?");//调度时间
//        request.setDependentType("NONE");//是否依赖上一周期 //目前没有追究依赖上一周期节点的情况
        request.setInputList("mpdc_pub_test.mysql2maxcompute");//父节点名称,多个用','隔开
        //request.setResourceGroupIdentifier("");//调度资源组

        request.setContent("{\"type\":\"job\",\"version\":\"2.0\",\"steps\":[{\"stepType\":\"odps\",\"parameter\":{\"partition\":[\"ds=20240815\"],\"datasource\":\"odps_first\",\"envType\":1,\"column\":[\"id\",\"start_tm\",\"qhj\"],\"tableComment\":\"null\",\"table\":\"cbs_test\"},\"name\":\"Reader\",\"category\":\"reader\"},{\"stepType\":\"oss\",\"parameter\":{\"fieldDelimiterOrigin\":\",\",\"nullFormat\":\"null\",\"dateFormat\":\"yyyy-MM-ddHH:mm:ss\",\"datasource\":\"oss\",\"envType\":1,\"column\":[\"0\",\"1\",\"2\"],\"writeMode\":\"truncate\",\"encoding\":\"UTF-8\",\"fieldDelimiter\":\",\",\"fileFormat\":\"text\",\"object\":\"test/cbs_test.txt\"},\"name\":\"Writer\",\"category\":\"writer\"}],\"setting\":{\"executeMode\":null,\"errorLimit\":{\"record\":\"\"},\"speed\":{\"concurrent\":2,\"throttle\":false}},\"order\":{\"hops\":[{\"from\":\"Reader\",\"to\":\"Writer\"}]}}");//数据集成任务xml信息

        try{
            CreateFileResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            if (response.getSuccess()) {//判断任务节点是否创建成功
                updateNode(client, request.getProjectId(), response.getData());//提交任务节点
            }
            
        } catch (ServerException e) {
            e.getMessage();
        } catch (ClientException e) {
            e.getMessage();
        }

    }

    public static void updateNode(IAcsClient client, Long projectId, Long fileId) {
        UpdateFileRequest updateFileRequest = new UpdateFileRequest();
        updateFileRequest.setFileDescription("批量生成脚本，并通过更新加线");
        updateFileRequest.setProjectId(projectId);
        updateFileRequest.setFileId(fileId);

        try {
            UpdateFileResponse response = client.getAcsResponse(updateFileRequest);
            System.out.println(new Gson().toJson(response));
        } catch (ClientException e) {

            e.printStackTrace();
        }
    }
}

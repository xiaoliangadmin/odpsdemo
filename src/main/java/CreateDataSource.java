import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateDataSourceRequest;
import com.aliyuncs.dataworks_public.model.v20200518.CreateDataSourceResponse;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectsRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.List;

public class CreateDataSource {

    public static void main(String[] args) {

        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        try {
            //根据工作空间名称获取到工作空间id
            ListProjectsRequest request1 = new ListProjectsRequest();
            request1.setPageNumber(1);
            request1.setPageSize(100);
            ListProjectsResponse response1 = client.getAcsResponse(request1);
            List<ListProjectsResponse.PageResult.Project> list = response1.getPageResult().getProjectList();
            Long projectId = 0L;
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).getProjectIdentifier().equals("mpdc_pub_test")) {
                    projectId = list.get(i).getProjectId();
                    break;
                }
            }
            //System.out.println(projectId);

            //根据工作空间id和数据库连接信息，在dataworks中创建数据源
            CreateDataSourceRequest request2 = new CreateDataSourceRequest();
            request2.setProjectId(projectId);
            request2.setDataSourceType("oracle");//定义数据源类型
            request2.setName("oracle_test");//自定义数据源名称
            request2.setDescription("test");//自定义数据源描述
            request2.setEnvType(1);
            request2.setContent("{\"jdbcUrl\":\"jdbc:oracle:thin:@//100.100.105.170:1521/PROD\",\"username\":\"apps\",\"password\":\"apps\",\"tag\":\"public\"}");//定义数据源详细信息
            CreateDataSourceResponse response2 = client.getAcsResponse(request2);
            System.out.println("oracle_test数据源是否创建成功：" + response2.getSuccess());
            System.out.println("oracle_test数据源的数据源id为：" + response2.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

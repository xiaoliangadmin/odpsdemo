import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.*;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

public class DeleteDataSource {

    public static void main(String[] args) {

        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        try {
            //根据工作空间名，称获取到工作空间id
            ListProjectsRequest request1 = new ListProjectsRequest();
            request1.setPageNumber(1);
            request1.setPageSize(100);
            ListProjectsResponse response1 = client.getAcsResponse(request1);
            List<ListProjectsResponse.PageResult.Project> list1 = response1.getPageResult().getProjectList();
            Long projectId = 0L;
            for (int i=0; i<list1.size(); i++) {
                if (list1.get(i).getProjectIdentifier().equals("mpdc_pub_test")) {
                    projectId = list1.get(i).getProjectId();
                    break;
                }
            }
            //System.out.println(projectId);

            //根据工作空间id和数据源名称，获取数据源id
            ListDataSourcesRequest request2 = new ListDataSourcesRequest();
            request2.setProjectId(projectId);
            request2.setPageNumber(1);
            request2.setPageSize(100);
            ListDataSourcesResponse response2 = client.getAcsResponse(request2);
            List<ListDataSourcesResponse.Data.DataSourcesItem> list2 = response2.getData().getDataSources();
            Long sourceId = 0L;
            for (int i=0; i<list2.size(); i++) {
                if (list2.get(i).getName().equals("oracle_test")) {
                    sourceId = Long.valueOf(list2.get(i).getId());
                }
            }
            //System.out.println(sourceId);

            //根据数据源id，在dataworks中删除数据源
            DeleteDataSourceRequest request3 = new DeleteDataSourceRequest();
            request3.setDataSourceId(sourceId);//数据源id
            DeleteDataSourceResponse response3 = client.getAcsResponse(request3);
            System.out.println("oracle_test数据源是否删除成功：" + response3.getSuccess());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

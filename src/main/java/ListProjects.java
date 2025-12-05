import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateFileResponse;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectsRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.List;

public class ListProjects {

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        ListProjectsRequest request = new ListProjectsRequest();
        request.setPageNumber(1);
        request.setPageSize(100);

        try {
            ListProjectsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            List<ListProjectsResponse.PageResult.Project> list = response.getPageResult().getProjectList();
            for (int i=0; i<list.size(); i++) {
                System.out.println("projectID:" + list.get(i).getProjectId() + "  -projectName:" + list.get(i).getProjectName());
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

}

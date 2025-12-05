import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.ListDataSourcesRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListDataSourcesResponse;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectMembersRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListProjectMembersResponse;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

public class ListProjectMembers {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        ListProjectMembersRequest request = new ListProjectMembersRequest();
        request.setProjectId(10124L);
        request.setPageNumber(1);
        request.setPageSize(5);

        try {
            ListProjectMembersResponse response = client.getAcsResponse(request);
            System.out.println(response.getData().getTotalCount());
            List<ListProjectMembersResponse.Data.ProjectMember> list = response.getData().getProjectMemberList();
            for (int i=0; i<list.size(); i++) {
                System.out.println(list.get(i).getProjectMemberName());
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }
}

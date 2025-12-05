import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateProjectMemberRequest;
import com.aliyuncs.dataworks_public.model.v20200518.CreateProjectMemberResponse;
import com.aliyuncs.profile.DefaultProfile;

public class CreateProjectMember {

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        try {
            CreateProjectMemberRequest request = new CreateProjectMemberRequest();
            request.setProjectId(10124L);
            request.setUserId("5262927327332629771");//陈勇的userid
//            request.setRoleCode("");

            CreateProjectMemberResponse response = client.getAcsResponse(request);
            System.out.println(response.getRequestId() + "-------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

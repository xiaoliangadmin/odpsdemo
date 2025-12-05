import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateFileResponse;
import com.aliyuncs.dataworks_public.model.v20200518.GetInstanceLogRequest;
import com.aliyuncs.dataworks_public.model.v20200518.GetInstanceLogResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

public class GetInstanceLog {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        GetInstanceLogRequest request = new GetInstanceLogRequest();
        request.setInstanceId(9014265599L);
        request.setProjectEnv("PROD");
        request.setRegionId("default");

        try{
            GetInstanceLogResponse response = client.getAcsResponse(request);
            System.out.println(response.getData());

        } catch (ServerException e) {
            e.getMessage();
        } catch (ClientException e) {
            e.getMessage();
        }

    }
}

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.CreateBaselineRequest;
import com.aliyuncs.dataworks_public.model.v20200518.CreateBaselineResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;


public class CreateBaseline {

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        CreateBaselineRequest request= new CreateBaselineRequest();
        request.setBaselineName("test_jixian");
        request.setProjectId(10124L);
        request.setOwner("5261540123617502367");
        request.setPriority(8);
        request.setTaskIds("50655");

        CreateBaselineRequest.OvertimeSettings overtimeSettings = new CreateBaselineRequest.OvertimeSettings();
        overtimeSettings.setCycle(1);
        overtimeSettings.setTime("12:00");
        request.setOvertimeSettingss(java.util.Arrays.asList(overtimeSettings));

        request.setAlertMarginThreshold(30);
        request.setBaselineType("DAILY");

        try{
            CreateBaselineResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }


    }
}

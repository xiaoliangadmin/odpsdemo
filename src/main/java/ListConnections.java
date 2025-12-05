import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.ListConnectionsRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListConnectionsResponse;
import com.aliyuncs.dataworks_public.model.v20200518.ListDataSourcesResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.List;

public class ListConnections {

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        ListConnectionsRequest request = new ListConnectionsRequest();
        request.setProjectId(10124L);
        request.setPageNumber(1);
        request.setPageSize(100);

        try {
            ListConnectionsResponse response = client.getAcsResponse(request);
            //System.out.println(new Gson().toJson(response));
            List<ListConnectionsResponse.Data.ConnectionsItem> list = response.getData().getConnections();
            for (int i=0; i<list.size(); i++) {
                System.out.println(list.get(i).getConnectionType() + ":" + list.get(i).getName());
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }
}

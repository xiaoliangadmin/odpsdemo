import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dataworks_public.model.v20200518.ListDataSourcesResponse;
import com.aliyuncs.dataworks_public.model.v20200518.ListMetaDBRequest;
import com.aliyuncs.dataworks_public.model.v20200518.ListMetaDBResponse;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

//没有验证成功
public class ListMetaDB {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        DefaultProfile.addEndpoint("default",
                "dataworks-public", "dataworks-public-vpc.res.topcloud.fdb.dev");

        IAcsClient client = new DefaultAcsClient(profile);

        ListMetaDBRequest request = new ListMetaDBRequest();
        request.setProjectId(10124L);

        try {
            ListMetaDBResponse response = client.getAcsResponse(request);

            List<ListMetaDBResponse.DatabaseInfo.DbListItem> list = response.getDatabaseInfo().getDbList();
            for (int i=0; i<list.size(); i++) {
                System.out.println(list.get(i).getUUID() + ":" + list.get(i).getType() + ":" + list.get(i).getName());
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

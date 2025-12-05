import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.Tables;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.ResultSet;
import com.aliyun.odps.task.SQLTask;

import java.util.Map;

//查询表
public class TestSql2 {

    static String endPoint = "http://service.cn-kunming-fdbtest-d01.odps.ops.topcloud.fdb.dev/api";
    static String project = "mpdc_pub_test";
    static String sql = "select * from test_zhl_demo;";

    public static void main(String[] args) {
        Account account = new AliyunAccount("wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);

        try {
            Tables table = odps.tables();
            boolean isExists = table.exists("test_zhl_demo");
            if (isExists) {
                Instance instance = SQLTask.run(odps, sql);
                instance.waitForSuccess();

                ResultSet rs = SQLTask.getResultSet(instance);
                long count = rs.getRecordCount();
                System.out.println("[success]\t执行sql成功，并获取" + count + "条结果");

                Map<String, String> results = instance.getTaskResults();
                Map<String, Instance.TaskStatus> taskStatus = instance.getTaskStatus();
                for (Map.Entry<String, Instance.TaskStatus> status : taskStatus.entrySet()) {
                    String result = results.get(status.getKey());
                    System.out.print(result);
                }

            } else {
                System.out.println("你查询的表不存在！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

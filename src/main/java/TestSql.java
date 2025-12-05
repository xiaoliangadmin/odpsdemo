import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;

import java.util.List;
import java.util.Map;
import java.util.Set;

//创建表
public class TestSql {

    static String endPoint = "http://service.cn-kunming-fdbtest-d01.odps.ops.topcloud.fdb.dev/api";
    static String project = "mpdc_pub_test";
    static String sql = "CREATE TABLE IF NOT EXISTS test_zhl_demo(id BIGINT, name STRING);";

    public static void main(String[] args) {
        Account account = new AliyunAccount("wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);

        try {
            Instance instance = SQLTask.run(odps, sql);
            String id = instance.getId();
            instance.waitForSuccess();

//            Set<String> taskNames = instance.getTaskNames();
//            for (String name : taskNames) {
//                Instance.TaskSummary summary = instance.getTaskSummary(name);
//                String s = summary.getSummaryText();
//                System.out.println(s + "-------------------");
//            }

//            Map<String, String> results = instance.getTaskResults();
//            Map<String, Instance.TaskStatus> taskStatus = instance.getTaskStatus();
//            for (Map.Entry<String, Instance.TaskStatus> status : taskStatus.entrySet()) {
//                String result = results.get(status.getKey());
//                System.out.println(result);
//            }

//            List<Record> records = SQLTask.getResult(instance);
//            for (Record r : records) {
//                System.out.println(r.get(0).toString());
//            }
            System.out.println(instance.isSuccessful());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

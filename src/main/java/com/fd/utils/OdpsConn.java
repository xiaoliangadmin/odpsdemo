package com.fd.utils;

import com.aliyun.odps.Column;
import com.aliyun.odps.Odps;
import com.aliyun.odps.Table;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.type.TypeInfo;

public class OdpsConn {

    Account account;
    static Odps odps;

    public OdpsConn() {
        account = new AliyunAccount("wsY5JSnrNDhrdaml", "xyv13zow0TeFAEDEaRxNFkxKJ84auQ");
        odps = new Odps(account);
        odps.setEndpoint("http://service.cn-kunming-fdbtest-d01.odps.ops.topcloud.fdb.dev/api");
    }

    public Table getOdpsTableSchema (String projectName, String tableName) {
        odps.setDefaultProject(projectName);
        Table table = odps.tables().get(tableName);

        return table;
    }

}

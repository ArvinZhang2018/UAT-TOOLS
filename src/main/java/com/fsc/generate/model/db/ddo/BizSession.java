package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.fsc.generate.utils.EUserProps;
import com.fsc.generate.utils.UserPropTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BizSession {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @CrmColumn("account_id")
    private long accountId;
    @CrmColumn("account_type")
    private int accountType;
    @CrmColumn("status")
    private int status;
    private String statusDesc;
    @CrmColumn("did")
    private String did;
    @CrmColumn("sub_account_name")
    private String subAccountName;

    public static final String QUERY_SESSION = "querySession";


    @CrmExecuteSql("select * from login_session_account where account_id = ? order by login_time desc")
    public void querySession() {
    }

    public String getStatusDesc() {
        if(status == 0){
            return "ON";
        }else{
            return "OFF";
        }
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }
}

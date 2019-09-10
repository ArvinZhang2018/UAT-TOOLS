package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.fsc.generate.utils.EUserProps;
import com.fsc.generate.utils.UserPropTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BizVmsUser {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @CrmColumn("uid")
    private long uid;
    @CrmColumn("did")
    private String did;
    @CrmColumn("status")
    private int status;
    @CrmColumn("user_group")
    private String userGroup;
    @CrmColumn("login_type")
    private int loginType;
    private String loginTypeDesc;
    @CrmColumn("plan")
    private int plan;
    private String planDesc;
    @CrmColumn("props")
    private long props;
    @CrmColumn("vendor_id")
    private long vendorId;
    @CrmColumn("act_date")
    private Date actDate;
    private String actDateDesc;
    @CrmColumn("expire_ts")
    private long expireTs;
    private String expireDate;
    private List<BizAccount> loginAccount;
    private String migratedStatusDesc;
    @CrmColumn("accountId")
    private long accountId;
    @CrmColumn("accountType")
    private int accountType;
    @CrmColumn("migratedStatus")
    private int migrateStatus;

    public static final String DELETE_BY_MAC = "deleteByMac";
    public static final String QUERY_BY_UID = "queryByUid";
    public static final String QUERY_BY_MAC = "queryByMac";
    public static final String QUERY_MIGRATED_TASK = "queryMigratedTask";

    public static final String UPDATE_BIZ = "updateBiz";
    public static final String QUERY_BIZ = "queryBiz";

    public static final String UPDATE_TO_INNER_TEST_GROUP = "updateToInnerTestGroup";


    @CrmExecuteSql("delete from vms_user where did = ?")
    public void deleteByMac() {
    }

    @CrmExecuteSql("select * from vms_user where uid = ?")
    public void queryByUid() {
    }

    @CrmExecuteSql("select * from vms_user where did = ?")
    public void queryByMac() {
    }

    @CrmExecuteSql("select accountId,status from vms_user_task where params like ?")
    public void queryMigratedTask() {
    }

    @CrmExecuteSql("select * from vms_user_biz where uid = ?")
    public void queryBiz() {
    }

    @CrmExecuteSql("update vms_user_biz set expire_ts = ? where uid = ?")
    public void updateBiz() {
    }

    @CrmExecuteSql("update vms_user set user_group = 'INNER_TEST' where uid = ?")
    public void updateToInnerTestGroup() {
    }

    public String getMigratedStatusDesc() {
        return migratedStatusDesc;
    }

    public void setMigratedStatusDesc(String migratedStatusDesc) {
        this.migratedStatusDesc = migratedStatusDesc;
    }

    public int getMigrateStatus() {
        return migrateStatus;
    }

    public void setMigrateStatus(int migrateStatus) {
        this.migrateStatus = migrateStatus;
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

    public List<BizAccount> getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(List<BizAccount> loginAccount) {
        this.loginAccount = loginAccount;
    }

    public long getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(long expireTs) {
        this.expireTs = expireTs;
    }

    public String getActDateDesc() {
        return actDateDesc;
    }

    public void setActDateDesc(String actDateDesc) {
        this.actDateDesc = actDateDesc;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getLoginTypeDesc() {
        return loginTypeDesc;
    }

    public void setLoginTypeDesc(String loginTypeDesc) {
        this.loginTypeDesc = loginTypeDesc;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public long getProps() {
        return props;
    }

    public void setProps(long props) {
        this.props = props;
    }

    public String getStatusDesc() {
        String desc;
        switch (status) {
            case 0:
                desc = "NORMAL";
                break;
            case 1:
                desc = "DISABLE";
                break;
            case 2:
                desc = "ACITVE";
                break;
            case 3:
                desc = "WAIT ACTIVE ACK";
                break;
            default:
                desc = "UNKNOWN";
        }
        return desc;
    }

    public String getVendor() {
        String vendor;
        switch ((int) vendorId) {
            case 1000100:
                vendor = "BTV";
                break;
            case 1000200:
                vendor = "MFC";
                break;
            case 1000301:
                vendor = "BTVE";
                break;
            case 2000100:
                vendor = "AKWD";
                break;
            case 2000200:
                vendor = "WXSD";
                break;
            case 3000100:
                vendor = "HQBZ";
                break;
            case 3000300:
                vendor = "PDL";
                break;
            case 3000500:
                vendor = "CELLSHOP/REDPLAY";
                break;
            case 3000600:
                vendor = "LEANDRO";
                break;
            case 3000700:
                vendor = "PDLDUOSAT";
                break;
            case 3000800:
                vendor = "STARTTV";
                break;
            case 3000900:
                vendor = "XTV";
                break;
            case -100:
                vendor = "WEB";
                break;
            default:
                vendor = "UNKNOWN";
        }
        return vendor;
    }

    public boolean isDeviceLogin() {
        return loginType == 0;
    }

    public boolean getCanAddedCloud() {
        return UserPropTools.isPropertySet(props, EUserProps.CONTENTS_AUTO_ADD);
    }

    public boolean isAddedCloud() {
        return UserPropTools.isPropertySet(props, EUserProps.CONTENTS_ADDED);
    }

    public boolean isMigrated() {
        return UserPropTools.isPropertySet(props, EUserProps.MIGRATED_DATA);
    }

}

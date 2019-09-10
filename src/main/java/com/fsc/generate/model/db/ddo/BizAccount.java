package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.fsc.generate.utils.Bit64Tools;
import com.fsc.generate.utils.EAccountProperty;
import com.fsc.generate.utils.EAccountStatus;

import java.util.List;
import java.util.Objects;

public class BizAccount {

    @CrmColumn("uid")
    private long uid;

    @CrmColumn("payment_sys_id")
    private String billUid;

    @CrmColumn("email")
    private String email;

    @CrmColumn("login_id")
    private String loginId;

    @CrmColumn("available_service_time")
    private long availableServiceTime;
    private String availableServiceTimeDesc;

    @CrmColumn("expire_ts")
    private long expireTs;
    private String expireTsDesc;

    @CrmColumn("status")
    private int status;
    private String statusDesc;

    @CrmColumn("product_id")
    private long productId;
    private String productName;

    @CrmColumn("trial")
    private int trial;

    @CrmColumn("property")
    private long property;


    public List<BizSession> sessions;

    public static final String QUERY_ACCOUNT_BY_EMAIL = "queryAccountByEmail";
    public static final String QUERY_ACCOUNT_BY_UID = "queryAccountByUid";
    public static final String QUERY_ACCOUNT_BY_ACCOUNT_CARD = "queryAccountByAccountCard";
    public static final String QUERY_ACCOUNT_BIZ = "queryAccountBiz";
    public static final String QUERY_ACCOUNT_DEVICE = "queryAccountDevice";
    public static final String UPDATE_ACCOUNT_BIZ = "updateAccountBiz";
    public static final String UPDATE_ACCOUNT_AVA_BIZ = "updateAccountAVABiz";
    public static final String UPDATE_ACCOUNT_PLAN = "updateAccountPlan";
    public static final String DELETE_ACCOUNT_BY_UID = "deleteAccountByUid";


    public static final String ACTIVATE_ACCOUNT_BY_EMAIL = "activateAccountByEmail";

    @CrmExecuteSql("select * from mfc_user where email = ?")
    public void queryAccountByEmail(){
    }

    @CrmExecuteSql("select a.*,b.login_id from mfc_user a left join mfc_account_link b on a.uid = b.uid where a.uid = ? ")
    public void queryAccountByUid(){
    }

    @CrmExecuteSql("select a.login_id , b.* from mfc_account_link a,mfc_user b where a.uid = b.uid and login_id = ?")
    public void queryAccountByAccountCard(){
    }

    @CrmExecuteSql("select * from mfc_user_biz where uid = ?")
    public void queryAccountBiz(){
    }

    @CrmExecuteSql("select b.email from mfc_user_device a,mfc_user b where did = ? and a.uid = b.uid order by a.last_modify_time desc")
    public void queryAccountDevice(){
    }

    @CrmExecuteSql("update mfc_user_biz set expire_ts = ? where uid = ?")
    public void updateAccountBiz(){
    }

    @CrmExecuteSql("update mfc_user_biz set available_service_time = ? where uid = ?")
    public void updateAccountAVABiz(){
    }

    @CrmExecuteSql("update mfc_user set trial = 0,device_limit = ?,product =?,product_id =? where uid = ?")
    public void updateAccountPlan(){
    }

    @CrmExecuteSql("update mfc_user set status = 0 where email = ?")
    public void activateAccountByEmail(){
    }

    @CrmExecuteSql("delete from mfc_user where uid = ?")
    public void deleteAccountByUid(){
    }

    public List<BizSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<BizSession> sessions) {
        this.sessions = sessions;
    }

    public long getProperty() {
        return property;
    }

    public void setProperty(long property) {
        this.property = property;
    }

    public String getAvailableServiceTimeDesc() {
        return availableServiceTimeDesc;
    }

    public void setAvailableServiceTimeDesc(String availableServiceTimeDesc) {
        this.availableServiceTimeDesc = availableServiceTimeDesc;
    }

    public String getExpireTsDesc() {
        return expireTsDesc;
    }

    public void setExpireTsDesc(String expireTsDesc) {
        this.expireTsDesc = expireTsDesc;
    }

    public int getTrial() {
        return trial;
    }

    public void setTrial(int trial) {
        this.trial = trial;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLoginId() {
        if(Objects.isNull(loginId)){
            return email;
        }
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getBillUid() {
        return billUid;
    }

    public void setBillUid(String billUid) {
        this.billUid = billUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getAvailableServiceTime() {
        return availableServiceTime;
    }

    public void setAvailableServiceTime(long availableServiceTime) {
        this.availableServiceTime = availableServiceTime;
    }

    public long getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(long expireTs) {
        this.expireTs = expireTs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        switch (status){
            case 0:
                statusDesc = EAccountStatus.ACTIVE.name();
                break;
            case 1:
                statusDesc = EAccountStatus.DISABLE.name();
                break;
            case 2:
                statusDesc = EAccountStatus.LOCKED.name();
                break;
            case 3:
                statusDesc = EAccountStatus.NOTACTIVE.name();
                break;
            default:
                statusDesc ="Unknown";

        }
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public boolean isLoginApp(){
        return Bit64Tools.isSet(property, EAccountProperty.LOGGED_IN_BY_APP.getValue());
    }
    public boolean isTrialled(){
        return Bit64Tools.isSet(property, EAccountProperty.TRIALLED.getValue());
    }
    public boolean isRenewing(){
        return Bit64Tools.isSet(property, EAccountProperty.IS_RENEWING.getValue());
    }
    public boolean isMissingCloud(){
        return Bit64Tools.isSet(property, EAccountProperty.CLOUD_CONTENT_MISSING.getValue());
    }
}

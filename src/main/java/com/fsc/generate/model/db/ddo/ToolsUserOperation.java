package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.valor.mfc.vms.api.model.common.AbstractPrintable;

import java.util.Date;

public class ToolsUserOperation extends AbstractPrintable{

    @CrmColumn("id")
    private long id;
    @CrmColumn("op_id")
    private long operation;
    @CrmColumn("fingerprint")
    private String fingerprint;
    @CrmColumn("click_count")
    private long clickCount;
    @CrmColumn("score")
    private long score;
    @CrmColumn("optimize_count")
    private long optimizeCount;
    @CrmColumn("like_count")
    private long likeCount;
    @CrmColumn("create_time")
    private Date createTime;

    public static final String QUERY_RECORD_BY_OPID_FINGERPRINT = "queryByOpId";
    public static final String QUERY_RECORD_BY_FINGERPRINT = "queryByFingerprint";
    public static final String INSERT_USER_OPERATION = "insertUserOperation";
    public static final String UPDATE_USER_OPERATION_ADD_CLICK_AMOUNT = "addClickAmount";


    @CrmExecuteSql("select * from crm_x_user_operation where op_id = ? and fingerprint = ?")
    public void queryByOpId(){
    }

    @CrmExecuteSql("select * from crm_x_user_operation where fingerprint = ?")
    public void queryByFingerprint(){
    }

    @CrmExecuteSql("insert into crm_x_user_operation(op_id,fingerprint,create_time) value(?,?,?)")
    public void insertUserOperation(){
    }

    @CrmExecuteSql("update crm_x_user_operation set click_count = click_count +1 where op_id = ? and fingerprint = ? ")
    public void addClickAmount(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOperation() {
        return operation;
    }

    public void setOperation(long operation) {
        this.operation = operation;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getOptimizeCount() {
        return optimizeCount;
    }

    public void setOptimizeCount(long optimizeCount) {
        this.optimizeCount = optimizeCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

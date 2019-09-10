package com.fsc.generate.model.db.ddo;

import com.fsc.generate.annotation.CrmColumn;
import com.fsc.generate.annotation.CrmExecuteSql;
import com.valor.mfc.vms.api.model.common.AbstractPrintable;

import java.util.Date;

public class ToolsLog extends AbstractPrintable{

//    select a.id,a.fingerprint,a.operation_id,a.create_time,a.remark,b.tag,b.op_name_cn,b.op_name_en,c.nick_name
    @CrmColumn("id")
    private long id;
    @CrmColumn("fingerprint")
    private String fingerprint;
    @CrmColumn("operation_id")
    private long operationId;
    @CrmColumn("create_time")
    private Date createTime;
    @CrmColumn("remark_cn")
    private String remarkCn;
    @CrmColumn("remark_en")
    private String remarkEn;
    @CrmColumn("tag")
    private String tag;
    @CrmColumn("op_name_cn")
    private String opNameCn;
    @CrmColumn("op_name_en")
    private String opNameEn;
    @CrmColumn("nick_name")
    private String nickName;

    public static final String QUERY_LOG = "queryLog";
    public static final String QUERY_LOG_BY_FINGERPRINT = "queryLogByFingerprint";
    public static final String INSERT_LOG = "insertLog";

    @CrmExecuteSql("select a.id,a.fingerprint,a.operation_id,a.create_time,a.remark_en,a.remark_cn,b.tag,b.op_name_cn,b.op_name_en,c.nick_name from crm_x_log a, crm_x_operation b,crm_x_user c"
            + " where a.operation_id = b.operation and a.fingerprint = c.fingerprint order by a.create_time desc limit 100")
    public void queryLog(){
    }

    @CrmExecuteSql("select a.id,a.fingerprint,a.operation_id,a.create_time,a.remark_cn,a.remark_en,b.tag,b.op_name_cn,b.op_name_en,c.nick_name from crm_x_log a, crm_x_operation b,crm_x_user c "
            + "where a.operation_id = b.operation and a.fingerprint = c.fingerprint and a.fingerprint = ? order by a.create_time desc limit 100")
    public void queryLogByFingerprint(){
    }

    @CrmExecuteSql("insert into crm_x_log(fingerprint,operation_id,create_time,remark_cn,remark_en) value(?,?,?,?,?)")
    public void insertLog(){

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarkCn() {
        return remarkCn;
    }

    public void setRemarkCn(String remarkCn) {
        this.remarkCn = remarkCn;
    }

    public String getRemarkEn() {
        return remarkEn;
    }

    public void setRemarkEn(String remarkEn) {
        this.remarkEn = remarkEn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOpNameCn() {
        return opNameCn;
    }

    public void setOpNameCn(String opNameCn) {
        this.opNameCn = opNameCn;
    }

    public String getOpNameEn() {
        return opNameEn;
    }

    public void setOpNameEn(String opNameEn) {
        this.opNameEn = opNameEn;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

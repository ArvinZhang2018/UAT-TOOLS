package com.fsc.generate.utils;

import com.valor.mfc.vms.api.model.common.AbstractPrintable;
import common.config.tools.config.ConfigTools3;

public class RechargeCardResp extends AbstractPrintable {

    private String id = "";
    private String password = "";
    private String type = "";
    private String feeType;
    private String status;
    private int bizType;
    private long bizValue = 0;
    private long expireTS = 0;
    private int bizSysType;
    private int plan;
    private String usedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public long getBizValue() {
        return bizValue;
    }

    public void setBizValue(long bizValue) {
        this.bizValue = bizValue;
    }

    public long getExpireTS() {
        return expireTS;
    }

    public void setExpireTS(long expireTS) {
        this.expireTS = expireTS;
    }

    public int getBizSysType() {
        return bizSysType;
    }

    public void setBizSysType(int bizSysType) {
        this.bizSysType = bizSysType;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getUsedId() {
        return usedId;
    }

    public void setUsedId(String usedId) {
        this.usedId = usedId;
    }

    @Override
    public String toString() {
        return "<div>{" +
                "cardId=" + id +
                ", plan=" + ConfigTools3.getString("crm.rc.plan.name." + plan) +
                ", days=" + bizValue +
                "}</div>";
    }
}

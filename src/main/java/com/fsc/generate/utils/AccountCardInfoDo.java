/*
 * Created by Gabriel Souza on 30/08/18 17:13
 * Copyright Valoroso Ltd. (c) 2018.  All rights reserved.
 */

package com.fsc.generate.utils;


public class AccountCardInfoDo {

    private String accountId ;
    private String password = "";
    private String systemId = "";
    private String status = "";
    private String type = "";
    private String batchId = "";
    private String sellerId = "";
    private long planId;
    private long serviceTime = 0;
    private char serviceTimeUnit;
    private long serviceTimeTS = 0;
    private String remarks = "";
    private String reason = "";


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public char getServiceTimeUnit() {
        return serviceTimeUnit;
    }

    public void setServiceTimeUnit(char serviceTimeUnit) {
        this.serviceTimeUnit = serviceTimeUnit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }
}

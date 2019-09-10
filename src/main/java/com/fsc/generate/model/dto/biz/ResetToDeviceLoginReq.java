package com.fsc.generate.model.dto.biz;

public class ResetToDeviceLoginReq extends BasicBizRequest {

    private int plan;
    private int vendorId;

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }
}

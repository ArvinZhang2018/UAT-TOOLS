package com.fsc.generate.model.dto.biz;

import common.config.tools.config.ConfigTools3;

public class UpdateAccountPlanReq extends BasicBizRequest {

    private int plan;

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getDeviceLimit(){
        return ConfigTools3.getInt("crm.account.plan.amount.limit."+plan);
    }
    public String getPlanName(){
        return ConfigTools3.getString("crm.account.plan.name."+plan);
    }
}

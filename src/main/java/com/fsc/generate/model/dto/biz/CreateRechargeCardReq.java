package com.fsc.generate.model.dto.biz;

public class CreateRechargeCardReq extends BasicBizRequest {

    private int amount;
    private int plan;
    private int days;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}

package com.fsc.generate.model.dto.biz;

public class CreateAccountCardReq extends BasicBizRequest {

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

package com.fsc.generate.model.pojo;

public class AccountCard {

    private String accountId;
    private String password;

    public AccountCard(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

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

    @Override
    public String toString() {
        return "<div>{" +
                "account=" + accountId +
                ", password=" + password +
                "}</div>";
    }
}

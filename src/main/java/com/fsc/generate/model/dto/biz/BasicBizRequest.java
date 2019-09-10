package com.fsc.generate.model.dto.biz;

import com.fsc.generate.model.dto.BasicRequest;
import com.fsc.generate.utils.StringUtils;

public class BasicBizRequest extends BasicRequest{

    private String mac;
    private long uid;
    private String macOrUid;

    private String emailOrUid;
    private String accountCardOrUid;

    private String emailOrAccountCardOrUid;


    public String getEmailOrAccountCardOrUid() {
        return emailOrAccountCardOrUid;
    }

    public void setEmailOrAccountCardOrUid(String emailOrAccountCardOrUid) {
        this.emailOrAccountCardOrUid = emailOrAccountCardOrUid;
    }

    public String getEmailOrUid() {
        return emailOrUid;
    }

    public void setEmailOrUid(String emailOrUid) {
        this.emailOrUid = emailOrUid;
    }

    public String getAccountCardOrUid() {
        return accountCardOrUid;
    }

    public void setAccountCardOrUid(String accountCardOrUid) {
        this.accountCardOrUid = accountCardOrUid;
    }

    public String getMac() {
        return mac.toLowerCase();
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getMacOrUid() {
        return macOrUid.toLowerCase();
    }

    public void setMacOrUid(String macOrUid) {
        this.macOrUid = macOrUid;
    }
}

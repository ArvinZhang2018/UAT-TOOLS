package com.fsc.generate.model.dto;

import com.valor.mfc.vms.api.model.common.AbstractPrintable;

public class BasicResponse extends AbstractPrintable {

    private int retCode;
    private int errCode;
    private String messageCn = "ok";
    private String messageEn = "ok";
    private Object result;

    public BasicResponse(Object result) {
        this.result = result;
    }

    public BasicResponse(int retCode, int errCode) {
        this.retCode = retCode;
        this.errCode = errCode;
    }

    public BasicResponse(int retCode, int errCode, String messageCn, String messageEn) {
        this.retCode = retCode;
        this.errCode = errCode;
        this.messageCn = messageCn;
        this.messageEn = messageEn;
    }

    public BasicResponse(int retCode, int errCode, String messageCn, String messageEn, Object result) {
        this.retCode = retCode;
        this.errCode = errCode;
        this.messageCn = messageCn;
        this.messageEn = messageEn;
        this.result = result;
    }

    public BasicResponse() {
    }

    public boolean isSuccess() {
        return retCode == 0 && errCode == 0;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessageCn() {
        return messageCn;
    }

    public void setMessageCn(String messageCn) {
        this.messageCn = messageCn;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }
}

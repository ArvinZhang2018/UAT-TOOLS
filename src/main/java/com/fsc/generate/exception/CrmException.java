package com.fsc.generate.exception;

import common.config.tools.config.ConfigTools3;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class CrmException extends Exception {

    private int retCode;
    private int errCode;
    private String messageCn;
    private String messageEn;

    public CrmException() {
    }

    public CrmException(String message) {
        super(message);
    }

    public CrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrmException(Throwable cause) {
        super(cause);
    }

    public CrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static CrmException newCrmException(int retCode, int errCode, String message) {
        String[] errorMessage = getErrorMessage(retCode, errCode, message);
        CrmException crmException = new CrmException(errorMessage[0]);
        crmException.setRetCode(retCode);
        crmException.setErrCode(errCode);
        crmException.setMessageCn(errorMessage[0]);
        crmException.setMessageEn(errorMessage[1]);
        return crmException;
    }

    public static CrmException newCrmException(int retCode, int errCode) {
        String[] errorMessage = getErrorMessage(retCode, errCode, null);
        CrmException crmException = new CrmException(errorMessage[0]);
        crmException.setRetCode(retCode);
        crmException.setErrCode(errCode);
        crmException.setMessageCn(errorMessage[0]);
        crmException.setMessageEn(errorMessage[1]);
        return crmException;
    }

    public static String[] getErrorMessage(int retCode, int errCode, String crmMessage) {
        if (Objects.nonNull(crmMessage)) {
            return new String[]{crmMessage, crmMessage};
        }
        return new String[]{
                ConfigTools3.getString("crm.message.cn." + retCode + "." + errCode, "无法解决的错误!"),
                ConfigTools3.getString("crm.message.en." + retCode + "." + errCode, "Server error!")
        };
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

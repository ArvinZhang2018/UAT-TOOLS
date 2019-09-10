package com.fsc.generate.exception;

public class CrmCode {

    public static final int RET_DB = 100;
    public static final int ERR_DB_QUERY_USER_FAILURE = 10001;

    public static final int RET_HTTP = 200;
    public static final int ERR_HTTP_INVALID_ARGS = 20001;
    public static final int ERR_HTTP_VMS_USER_NOT_FOUND = 20002;
    public static final int ERR_HTTP_VMS_USER_BIZ_NOT_FOUND = 20003;
    public static final int ERR_HTTP_EMAIL_ALREADY_EXIST = 20004;
    public static final int ERR_HTTP_ACCOUNT_NOT_FOUND = 20005;
    public static final int ERR_HTTP_ACCOUNT_BIZ_NOT_FOUND = 20006;
    public static final int ERR_HTTP_ACCOUNT_NOT_LOGIN = 20007;
    public static final int ERR_HTTP_ACCOUNT_NOT_EMAIL = 20008;
    public static final int ERR_HTTP_VMS_USER_ALREADY_EXIST = 20009;

    public static final int RET_API = 300;
    public static final int ERR_API_CALL_FAILURE = 31000;
    public static final int ERR_API_CC_CREATE_CHARGE_CARD_FAILURE = 31001;
    public static final int ERR_API_CC_CREATE_ACCOUNT_CARD_FAILURE = 31002;
    public static final int ERR_API_VMS_USER_CREATE_USER_FAILURE = 32001;
    public static final int ERR_API_ACCOUNT_CREATE_EMAIL_ACCOUNT_FAILURE = 33001;
    public static final int ERR_API_CC_CREATE_RECHARGE_CARD_FAILURE = 33002;


}

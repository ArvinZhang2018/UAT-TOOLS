package com.fsc.generate.utils;

/**
 * File        :
 * Description :
 * Author      : Frank
 * Created     : 2015/10/4 23:58
 */
public enum EAccountProperty {
    LOGGED_IN_BY_APP(BIT_MASK(0)), //APP has logged in
    TRIALLED(BIT_MASK(1)),     //Used trial period
    TRIAL_USER(BIT_MASK(2)),   //Trial period user
    EMAIL_UNVERIFIED(BIT_MASK(3)), //Email need verification
    CLOUD_CONTENT_MISSING(BIT_MASK(4)), //Cloud content needed
    IS_RENEWING(BIT_MASK(5)), //have a renewing plan
    FREE_TO_CHARGE(BIT_MASK(6)), //free trial to membership
    ;

    EAccountProperty(long value) {
        this.value = value;
    }

    private long value;

    public long getValue() {
        return value;
    }

    private static long BIT_MASK(int bit) {
        return 1L << bit;
    }

}

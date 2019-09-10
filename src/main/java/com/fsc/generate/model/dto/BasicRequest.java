package com.fsc.generate.model.dto;

import com.valor.mfc.vms.api.model.common.AbstractPrintable;

public class BasicRequest extends AbstractPrintable{

    private String fingerprint;
    private int operation;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}

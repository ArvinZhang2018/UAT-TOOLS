package com.fsc.generate.enums;

public enum  ToolsVersion {

    V1(1),V2(2);
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    ToolsVersion(int version) {
        this.version = version;
    }
}

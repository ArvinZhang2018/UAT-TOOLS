package com.fsc.generate.enums;

public enum AlertWindowType {

    WARNING("warning"),ERROR("error"),SUCCESS("success"),INFO("info"),QUESTION("question");

    private String content;
    AlertWindowType(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

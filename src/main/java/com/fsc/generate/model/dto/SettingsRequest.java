package com.fsc.generate.model.dto;

public class SettingsRequest extends BasicRequest {

    private String language;
    private int showLog;
    private int toolsVersion;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getShowLog() {
        return showLog;
    }

    public void setShowLog(int showLog) {
        this.showLog = showLog;
    }

    public int getToolsVersion() {
        return toolsVersion;
    }

    public void setToolsVersion(int toolsVersion) {
        this.toolsVersion = toolsVersion;
    }
}

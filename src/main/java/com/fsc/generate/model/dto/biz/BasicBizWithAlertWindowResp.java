package com.fsc.generate.model.dto.biz;

import com.fsc.generate.enums.AlertWindowType;
import com.valor.mfc.vms.api.model.common.AbstractPrintable;

public class BasicBizWithAlertWindowResp extends AbstractPrintable{

    private String alertWindowType = AlertWindowType.SUCCESS.getContent();
    private String title;
    private String html;

    public BasicBizWithAlertWindowResp(String title, String html) {
        this.title = title;
        this.html = html;
    }

    public BasicBizWithAlertWindowResp(String alertWindowType, String title, String html) {
        this.alertWindowType = alertWindowType;
        this.title = title;
        this.html = html;
    }

    public String getAlertWindowType() {
        return alertWindowType;
    }

    public void setAlertWindowType(String alertWindowType) {
        this.alertWindowType = alertWindowType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}

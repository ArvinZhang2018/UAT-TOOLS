package com.fsc.generate.model.dto.biz;

public class BillNotificationRequest extends BasicBizRequest {

    private Long invoiceId;
    private int templateIndex;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getTemplateIndex() {
        return templateIndex;
    }

    public void setTemplateIndex(int templateIndex) {
        this.templateIndex = templateIndex;
    }
}

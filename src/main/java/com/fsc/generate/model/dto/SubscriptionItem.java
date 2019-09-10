package com.fsc.generate.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubscriptionItem implements Serializable {


    @SerializedName("item_running_no")
    @Expose
    private int itemRunningNo;

    @SerializedName("purchase_id")
    @Expose
    private String purchaseId;

    @SerializedName("running_no")
    @Expose
    private int runningNo;

    @SerializedName("creation_cause")
    @Expose
    private String creationCause;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("created")
    @Expose
    private String created;

    public int getItemRunningNo() {
        return itemRunningNo;
    }

    public void setItemRunningNo(int itemRunningNo) {
        this.itemRunningNo = itemRunningNo;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getRunningNo() {
        return runningNo;
    }

    public void setRunningNo(int runningNo) {
        this.runningNo = runningNo;
    }

    public String getCreationCause() {
        return creationCause;
    }

    public void setCreationCause(String creationCause) {
        this.creationCause = creationCause;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}

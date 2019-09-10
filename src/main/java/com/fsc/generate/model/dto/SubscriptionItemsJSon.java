package com.fsc.generate.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionItemsJSon {


    @SerializedName("Count")
    @Expose
    private int count;

    @SerializedName("Items")
    @Expose
    private List<SubscriptionItem> items = new ArrayList<>();

    public SubscriptionItem getSubscriptionItem(String purchaseId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPurchaseId().equals(purchaseId)) {
                return items.get(i);
            }
        }
        return null;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SubscriptionItem> getItems() {
        return items;
    }

    public void setItems(List<SubscriptionItem> items) {
        this.items = items;
    }
}

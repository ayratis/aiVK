package com.iskhakovayrat.aivk.retrofit.longpoll;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Messages {
    @SerializedName("items")
    private List<MessagesItems> items;

    public List<MessagesItems> getItems() {
        return items;
    }

    public void setItems(List<MessagesItems> items) {
        this.items = items;
    }
}

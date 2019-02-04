package com.iskhakovayrat.aivk.model.longpoll;

import com.google.gson.annotations.SerializedName;

public class MessagesItems {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.iskhakovayrat.aivk.model.longpoll;

import com.google.gson.annotations.SerializedName;

public class LongPollHistoryResponse {
    @SerializedName("messages")
    private Messages messages;

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}

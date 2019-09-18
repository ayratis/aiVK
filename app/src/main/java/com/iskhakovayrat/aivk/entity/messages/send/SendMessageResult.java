package com.iskhakovayrat.aivk.entity.messages.send;

import com.google.gson.annotations.SerializedName;

public class SendMessageResult {
    @SerializedName("response")
    private int response;

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}

package com.iskhakovayrat.aivk.retrofit.messages.send;

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

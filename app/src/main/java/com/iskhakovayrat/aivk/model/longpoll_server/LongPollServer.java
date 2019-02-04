package com.iskhakovayrat.aivk.model.longpoll_server;

import com.google.gson.annotations.SerializedName;

public class LongPollServer {
    @SerializedName("response")
    private LongPollServerResponse response;

    public LongPollServerResponse getResponse() {
        return response;
    }

    public void setResponse(LongPollServerResponse response) {
        this.response = response;
    }
}

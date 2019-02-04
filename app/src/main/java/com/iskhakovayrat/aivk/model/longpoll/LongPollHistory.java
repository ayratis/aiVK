package com.iskhakovayrat.aivk.model.longpoll;

import com.google.gson.annotations.SerializedName;

public class LongPollHistory {
    @SerializedName("response")
    private LongPollHistoryResponse response;

    public LongPollHistoryResponse getResponse() {
        return response;
    }

    public void setResponse(LongPollHistoryResponse response) {
        this.response = response;
    }
}

package com.iskhakovayrat.aivk.retrofit.get_history;

import com.google.gson.annotations.SerializedName;

public class ConversationHistory {
    @SerializedName("response")
    private ConversationHistoryResponse response;

    public ConversationHistoryResponse getResponse() {
        return response;
    }

    public void setResponse(ConversationHistoryResponse response) {
        this.response = response;
    }
}

package com.iskhakovayrat.aivk.model.get_conversation;

import com.google.gson.annotations.SerializedName;

public class ConversationGet {
    @SerializedName("response")
    private ConversationResponse response;

    public ConversationResponse getResponse() {
        return response;
    }

    public void setResponse(ConversationResponse response) {
        this.response = response;
    }
}

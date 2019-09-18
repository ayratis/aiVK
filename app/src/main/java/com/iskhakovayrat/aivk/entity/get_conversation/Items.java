package com.iskhakovayrat.aivk.entity.get_conversation;

import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("conversation")
    private Conversation conversation;

    @SerializedName("last_message")
    private LastMessage last_message;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public LastMessage getLast_message() {
        return last_message;
    }

    public void setLast_message(LastMessage last_message) {
        this.last_message = last_message;
    }
}

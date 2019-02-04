package com.iskhakovayrat.aivk.model.get_history;

import com.google.gson.annotations.SerializedName;

public class HistoryItems {
    @SerializedName("date")
    private long date;

    @SerializedName("from_id")
    private int from_id;

    @SerializedName("out")
    private int out;

    @SerializedName("peer_id")
    private int peer_id;

    @SerializedName("text")
    private String text;

    @SerializedName("conversation_message_id")
    private int conversation_message_id;



    @SerializedName("random_id")
    private int randomId;

    public long getDate() {
        return date;
    }

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getPeer_id() {
        return peer_id;
    }

    public void setPeer_id(int peer_id) {
        this.peer_id = peer_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getConversation_message_id() {
        return conversation_message_id;
    }

    public void setConversation_message_id(int conversation_message_id) {
        this.conversation_message_id = conversation_message_id;
    }


}

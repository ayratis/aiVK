package com.iskhakovayrat.aivk.entity.get_conversation;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.get_history.FwdMessages;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;

import java.util.List;

public class LastMessage {
    @SerializedName("date")
    private long date;

    @SerializedName("from_id")
    private int from_id;

    @SerializedName("id")
    private int id;

    @SerializedName("out")
    private int out;

    @SerializedName("peer_id")
    private int peer_id;

    @SerializedName("text")
    private String text;

    @SerializedName("conversation_message_id")
    private long conversation_message_id;

    @SerializedName("random_id")
    private int random_id;

    @SerializedName("fwd_messages")
    private List<FwdMessages> fwdMessages;

    @SerializedName("attachments")
    private List<Attachments> attachments;

    @SerializedName("action")
    private Action action;

    public long getDate() {
        return date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getConversation_message_id() {
        return conversation_message_id;
    }

    public void setConversation_message_id(long conversation_message_id) {
        this.conversation_message_id = conversation_message_id;
    }

    public int getRandom_id() {
        return random_id;
    }

    public void setRandom_id(int random_id) {
        this.random_id = random_id;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }

    public List<FwdMessages> getFwdMessages() {
        return fwdMessages;
    }

    public void setFwdMessages(List<FwdMessages> fwdMessages) {
        this.fwdMessages = fwdMessages;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

package com.iskhakovayrat.aivk.model.get_conversation;

import com.google.gson.annotations.SerializedName;

public class Conversation {
    @SerializedName("peer")
    private Peer peer;

    @SerializedName("in_read")
    private int in_read;

    @SerializedName("out_read")
    private int out_read;

    @SerializedName("last_message_id")
    private int last_message_id;

    @SerializedName("unread_count")
    private int unread_count;

    @SerializedName("can_write")
    private CanWrite can_write;

    @SerializedName("chat_settings")
    private ChatSettings chat_setting;


    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public int getIn_read() {
        return in_read;
    }

    public void setIn_read(int in_read) {
        this.in_read = in_read;
    }

    public int getOut_read() {
        return out_read;
    }

    public void setOut_read(int out_read) {
        this.out_read = out_read;
    }

    public int getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(int last_message_id) {
        this.last_message_id = last_message_id;
    }

    public CanWrite getCan_write() {
        return can_write;
    }

    public void setCan_write(CanWrite can_write) {
        this.can_write = can_write;
    }

    public ChatSettings getChat_setting() {
        return chat_setting;
    }

    public void setChat_setting(ChatSettings chat_setting) {
        this.chat_setting = chat_setting;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }
}

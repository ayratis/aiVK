package com.iskhakovayrat.aivk.entity.get_history;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;

import java.util.List;

public class FwdMessages {

    @SerializedName("date")
    private long date;

    @SerializedName("from_id")
    private int fromId;

    @SerializedName("text")
    private String text;

    @SerializedName("attachments")
    private List<Attachments> attachments;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}

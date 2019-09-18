package com.iskhakovayrat.aivk.entity.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CopyHistory {

    @SerializedName("id")
    private int id;

    @SerializedName("owner_id")
    private long ownerId;

    @SerializedName("date")
    private long date;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("text")
    private String text;

    @SerializedName("attachments")
    private List<Attachments> attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
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

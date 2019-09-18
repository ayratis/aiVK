package com.iskhakovayrat.aivk.entity.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsfeedItems {

    @SerializedName("post_id")
    private long postId;

    @SerializedName("type")
    private String type;

    @SerializedName("source_id")
    private long source_id;

    @SerializedName("date")
    private long date;

    @SerializedName("text")
    private String text;

    @SerializedName("copy_history")
    private List<CopyHistory> copyHistory;

    @SerializedName("attachments")
    private List<Attachments> attachments;

    @SerializedName("comments")
    private Comments comments;

    @SerializedName("likes")
    private Likes likes;

    @SerializedName("reposts")
    private Reposts reposts;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Reposts getReposts() {
        return reposts;
    }

    public void setReposts(Reposts reposts) {
        this.reposts = reposts;
    }

    public List<CopyHistory> getCopyHistory() {
        return copyHistory;
    }

    public void setCopyHistory(List<CopyHistory> copyHistory) {
        this.copyHistory = copyHistory;
    }

    public long getPostId() {
        return postId;
    }
}

package com.iskhakovayrat.aivk.entity.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.entity.newsfeed.Comments;
import com.iskhakovayrat.aivk.entity.newsfeed.Likes;
import com.iskhakovayrat.aivk.entity.newsfeed.Reposts;

import java.util.List;

public class Wall {

    @SerializedName("from_id")
    private int fromId;

    @SerializedName("date")
    private long date;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("text")
    private String text;

    @SerializedName("attachments")
    private List<Attachments> attachments;

    @SerializedName("comments")
    private Comments comments;

    @SerializedName("likes")
    private Likes likes;

    @SerializedName("reposts")
    private Reposts reposts;

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
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

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}

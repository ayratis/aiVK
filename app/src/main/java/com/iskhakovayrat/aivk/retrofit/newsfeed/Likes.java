package com.iskhakovayrat.aivk.retrofit.newsfeed;

import com.google.gson.annotations.SerializedName;

public class Likes {

    @SerializedName("count")
    private int count;

    @SerializedName("user_likes")
    private byte user_likes;

    @SerializedName("can_like")
    private byte can_like;

    @SerializedName("can_publish")
    private byte can_publish;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte getUser_likes() {
        return user_likes;
    }

    public void setUser_likes(byte user_likes) {
        this.user_likes = user_likes;
    }

    public byte getCan_like() {
        return can_like;
    }

    public void setCan_like(byte can_like) {
        this.can_like = can_like;
    }

    public byte getCan_publish() {
        return can_publish;
    }

    public void setCan_publish(byte can_publish) {
        this.can_publish = can_publish;
    }
}

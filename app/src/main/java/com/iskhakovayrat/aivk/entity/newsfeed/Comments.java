package com.iskhakovayrat.aivk.entity.newsfeed;

import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("count")
    private int count;

    @SerializedName("can_post")
    private byte can_post;

    public byte getCan_post() {
        return can_post;
    }

    public void setCan_post(byte can_post) {
        this.can_post = can_post;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

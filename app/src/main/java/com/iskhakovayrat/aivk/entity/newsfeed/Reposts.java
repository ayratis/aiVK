package com.iskhakovayrat.aivk.entity.newsfeed;

import com.google.gson.annotations.SerializedName;

public class Reposts {

    @SerializedName("count")
    private int count;

    @SerializedName("user_reposted")
    private byte user_reposted;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte getUser_reposted() {
        return user_reposted;
    }

    public void setUser_reposted(byte user_reposted) {
        this.user_reposted = user_reposted;
    }
}

package com.iskhakovayrat.aivk.entity.longpoll_server;

import com.google.gson.annotations.SerializedName;

public class LongPollServerResponse {
    @SerializedName("key")
    private String key;

    @SerializedName("server")
    private String server;

    @SerializedName("ts")
    private int ts;

    @SerializedName("pts")
    private int pts;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }
}

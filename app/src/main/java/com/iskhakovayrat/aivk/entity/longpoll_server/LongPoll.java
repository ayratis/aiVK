package com.iskhakovayrat.aivk.entity.longpoll_server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LongPoll {

    @SerializedName("ts")
    private int ts;

    @SerializedName("updates")
    private List<List<Object>> updates;


    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public List<List<Object>> getUpdates() {
        return updates;
    }

    public void setUpdates(List<List<Object>> updates) {
        this.updates = updates;
    }
}

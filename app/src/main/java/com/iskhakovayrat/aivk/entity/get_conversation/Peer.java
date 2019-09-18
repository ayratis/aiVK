package com.iskhakovayrat.aivk.entity.get_conversation;

import com.google.gson.annotations.SerializedName;

public class Peer {

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("local_id")
    private int local_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }
}

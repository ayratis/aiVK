package com.iskhakovayrat.aivk.model.get_conversation;

import com.google.gson.annotations.SerializedName;

public class Groups {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("photo_50")
    private String photo_50;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }
}

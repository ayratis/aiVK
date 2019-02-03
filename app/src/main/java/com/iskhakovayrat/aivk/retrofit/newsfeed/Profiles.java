package com.iskhakovayrat.aivk.retrofit.newsfeed;

import com.google.gson.annotations.SerializedName;

public class Profiles {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;


    @SerializedName("photo_50")
    private String photo_50;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }
}

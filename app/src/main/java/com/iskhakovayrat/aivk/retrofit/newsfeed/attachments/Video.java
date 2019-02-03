package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("owner_id")
    private int owner_id;

    @SerializedName("duration")
    private int duration;

    @SerializedName("description")
    private String description;

    @SerializedName("photo_640")
    private String photo_640;

    @SerializedName("photo_800")
    private String photo_800;

    @SerializedName("first_frame_800")
    private String first_frame_800;


    @SerializedName("access_key")
    private String access_key;

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_800() {
        return photo_800;
    }

    public void setPhoto_800(String photo_800) {
        this.photo_800 = photo_800;
    }

    public String getFirst_frame_800() {
        return first_frame_800;
    }

    public void setFirst_frame_800(String first_frame_800) {
        this.first_frame_800 = first_frame_800;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }
}

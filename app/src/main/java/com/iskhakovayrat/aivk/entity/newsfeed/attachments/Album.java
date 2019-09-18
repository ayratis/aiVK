package com.iskhakovayrat.aivk.entity.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

public class Album {
    @SerializedName("id")
    private int id;

    @SerializedName("thumb")
    private Thumb thumb;

    @SerializedName("title")
    private String title;

    @SerializedName("size")
    private int size;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }
}

package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

public class Link {
    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("desription")
    private String description;

    @SerializedName("photo")
    private Photo photo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}

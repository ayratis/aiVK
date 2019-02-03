package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

public class PhotoSizes {

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

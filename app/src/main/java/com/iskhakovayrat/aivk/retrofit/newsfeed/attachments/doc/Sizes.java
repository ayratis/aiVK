package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments.doc;

import com.google.gson.annotations.SerializedName;

public class Sizes {

    @SerializedName("src")
    private String src;

    @SerializedName("type")
    private String type;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}

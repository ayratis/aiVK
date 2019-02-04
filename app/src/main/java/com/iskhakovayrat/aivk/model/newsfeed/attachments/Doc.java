package com.iskhakovayrat.aivk.model.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.doc.Preview;

public class Doc {

    @SerializedName("title")
    private String title;

    @SerializedName("ext")
    private String ext;

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private int type;

    @SerializedName("preview")
    private Preview preview;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

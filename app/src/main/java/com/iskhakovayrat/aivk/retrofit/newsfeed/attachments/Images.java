package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

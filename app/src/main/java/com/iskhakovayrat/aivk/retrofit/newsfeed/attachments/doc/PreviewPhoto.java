package com.iskhakovayrat.aivk.retrofit.newsfeed.attachments.doc;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreviewPhoto {
    @SerializedName("sizes")
    private List<Sizes> sizes;

    public List<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<Sizes> sizes) {
        this.sizes = sizes;
    }
}

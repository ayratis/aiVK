package com.iskhakovayrat.aivk.model.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("id")
    private long id;

    @SerializedName("sizes")
    private List<PhotoSizes> sizes;

    public List<PhotoSizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<PhotoSizes> sizes) {
        this.sizes = sizes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}

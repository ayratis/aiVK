package com.iskhakovayrat.aivk.entity.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Thumb {

    @SerializedName("id")
    private int id;

    @SerializedName("album_id")
    private int albumId;

    @SerializedName("owner_id")
    private int ownerId;

    @SerializedName("sizes")
    private List<PhotoSizes> sizes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<PhotoSizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<PhotoSizes> sizes) {
        this.sizes = sizes;
    }

    public PhotoSizes getNeededSize() {
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("x")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("y")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("m")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("s")) {
                return size;
            }
        }
        return null;
    }
}

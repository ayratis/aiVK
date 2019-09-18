package com.iskhakovayrat.aivk.entity.newsfeed.attachments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sticker {
    @SerializedName("product_id")
    private int productId;

    @SerializedName("sticker_id")
    private int sticker_id;

    @SerializedName("images")
    private List<Images> images;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSticker_id() {
        return sticker_id;
    }

    public void setSticker_id(int sticker_id) {
        this.sticker_id = sticker_id;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}

package com.iskhakovayrat.aivk.entity.newsfeed.attachments.doc;

import com.google.gson.annotations.SerializedName;

public class Preview {
    @SerializedName("photo")
    private PreviewPhoto photo;

    public PreviewPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(PreviewPhoto photo) {
        this.photo = photo;
    }
}

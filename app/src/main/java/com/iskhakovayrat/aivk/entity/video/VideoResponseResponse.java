package com.iskhakovayrat.aivk.entity.video;

import com.google.gson.annotations.SerializedName;

public class VideoResponseResponse {
    @SerializedName("response")
    private VideoResponse response;

    public VideoResponse getResponse() {
        return response;
    }

    public void setResponse(VideoResponse response) {
        this.response = response;
    }
}

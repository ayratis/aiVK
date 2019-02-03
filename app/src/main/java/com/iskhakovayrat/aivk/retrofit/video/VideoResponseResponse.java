package com.iskhakovayrat.aivk.retrofit.video;

import com.google.gson.annotations.SerializedName;

public class VideoResponseResponse {
    @SerializedName("response")
    VideoResponse response;

    public VideoResponse getResponse() {
        return response;
    }

    public void setResponse(VideoResponse response) {
        this.response = response;
    }
}

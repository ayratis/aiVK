package com.iskhakovayrat.aivk.retrofit.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsfeedResponse {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}

package com.iskhakovayrat.aivk.entity.newsfeed;

import com.google.gson.annotations.SerializedName;

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

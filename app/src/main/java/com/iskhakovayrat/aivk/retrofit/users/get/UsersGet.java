package com.iskhakovayrat.aivk.retrofit.users.get;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.retrofit.newsfeed.Profiles;

import java.util.List;

public class UsersGet {

    @SerializedName("response")
    List<Profiles> response;

    public List<Profiles> getResponse() {
        return response;
    }

    public void setResponse(List<Profiles> response) {
        this.response = response;
    }
}

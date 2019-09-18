package com.iskhakovayrat.aivk.entity.users.get;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.newsfeed.Profiles;

import java.util.List;

public class UsersGet {

    @SerializedName("response")
    private List<Profiles> response;

    public List<Profiles> getResponse() {
        return response;
    }

    public void setResponse(List<Profiles> response) {
        this.response = response;
    }
}

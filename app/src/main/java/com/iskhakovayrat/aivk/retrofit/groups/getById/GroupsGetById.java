package com.iskhakovayrat.aivk.retrofit.groups.getById;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.retrofit.newsfeed.Groups;

import java.util.List;

public class GroupsGetById {
    @SerializedName("response")
    private List<Groups> response;

    public List<Groups> getResponse() {
        return response;
    }

    public void setResponse(List<Groups> response) {
        this.response = response;
    }
}

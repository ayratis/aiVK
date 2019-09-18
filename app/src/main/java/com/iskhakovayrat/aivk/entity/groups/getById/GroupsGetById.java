package com.iskhakovayrat.aivk.entity.groups.getById;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.newsfeed.Groups;

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

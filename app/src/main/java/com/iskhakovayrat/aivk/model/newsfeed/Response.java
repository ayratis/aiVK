package com.iskhakovayrat.aivk.model.newsfeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("items")
    private List<NewsfeedItems> items;

    @SerializedName("groups")
    private List<Groups> groups;

    @SerializedName("profiles")
    private List<Profiles> profiles;

    @SerializedName("next_from")
    private String nextFrom;

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public List<NewsfeedItems> getItems() {
        return items;
    }

    public void setItems(List<NewsfeedItems> items) {
        this.items = items;
    }

    public List<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profiles> profiles) {
        this.profiles = profiles;
    }

    public String getNextFrom() {
        return nextFrom;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }
}

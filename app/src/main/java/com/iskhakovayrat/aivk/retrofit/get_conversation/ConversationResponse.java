package com.iskhakovayrat.aivk.retrofit.get_conversation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConversationResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("items")
    private List<Items> items;

    @SerializedName("unread_count")
    private int unread_count;

    @SerializedName("profiles")
    private List<Profiles> profiles;

    @SerializedName("groups")
    private List<Groups> groups;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public List<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profiles> profiles) {
        this.profiles = profiles;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
}

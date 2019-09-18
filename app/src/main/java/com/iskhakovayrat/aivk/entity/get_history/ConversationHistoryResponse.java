package com.iskhakovayrat.aivk.entity.get_history;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.entity.get_conversation.Conversation;
import com.iskhakovayrat.aivk.entity.get_conversation.Groups;
import com.iskhakovayrat.aivk.entity.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.entity.get_conversation.Profiles;

import java.util.List;

public class ConversationHistoryResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("items")
    private List<LastMessage> items;

    @SerializedName("profiles")
    private List<Profiles> profiles;

    @SerializedName("groups")
    private List<Groups> groups;

    @SerializedName("conversations")
    private List<Conversation> conversations;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LastMessage> getItems() {
        return items;
    }

    public void setItems(List<LastMessage> items) {
        this.items = items;
    }

    public List<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profiles> profiles) {
        this.profiles = profiles;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
}

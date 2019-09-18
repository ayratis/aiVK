package com.iskhakovayrat.aivk.entity.get_conversation;

import com.google.gson.annotations.SerializedName;

public class ChatSettings {
    @SerializedName("title")
    private String title;

    @SerializedName("member_count")
    private int member_count;

    @SerializedName("state")
    private String state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

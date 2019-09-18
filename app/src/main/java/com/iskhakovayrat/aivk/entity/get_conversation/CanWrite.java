package com.iskhakovayrat.aivk.entity.get_conversation;

import com.google.gson.annotations.SerializedName;

public class CanWrite {
    @SerializedName("allowed")
    private String allowed;

    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }
}

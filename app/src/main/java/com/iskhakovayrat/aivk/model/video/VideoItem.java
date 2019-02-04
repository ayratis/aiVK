package com.iskhakovayrat.aivk.model.video;

import com.google.gson.annotations.SerializedName;

public class VideoItem {
    @SerializedName("player")
    private String player;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}

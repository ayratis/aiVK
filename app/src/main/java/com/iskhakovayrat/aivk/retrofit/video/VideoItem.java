package com.iskhakovayrat.aivk.retrofit.video;

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

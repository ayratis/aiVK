package com.iskhakovayrat.aivk.model.get_conversation;

import com.google.gson.annotations.SerializedName;

public class Action {
    public enum Type{
        @SerializedName("chat_photo_update") CHAT_PHOTO_UPDATE,
        @SerializedName("chat_photo_remove") CHAT_PHOTO_REMOVE,
        @SerializedName("chat_create") CHAT_CREATE,
        @SerializedName("chat_title_update") CHAT_TITLE_UPDATE,
        @SerializedName("chat_invite_user") CHAT_INVITE_USER,
        @SerializedName("chat_kick_user") CHAT_KICK_USER,
        @SerializedName("chat_pin_message ") CHAT_PIN_MESSAGE,
        @SerializedName("chat_unpin_message") CHAT_UNPIN_MESSAGE,
        @SerializedName("chat_invite_user_by_link") CHAT_INVITE_USER_BY_LINK
    }

    @SerializedName("type")
    private Type type;

    @SerializedName("member_id")
    private int member_id;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
}

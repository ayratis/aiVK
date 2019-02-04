package com.iskhakovayrat.aivk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class TokenHolder {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER_ID      = "user_id";

    @NonNull
    private final SharedPreferences sp;

    public TokenHolder(SharedPreferences sp) {
        this.sp = sp;
    }

    public void saveSession(@NonNull String userId, @NonNull String token) {
        sp.edit().putString(KEY_USER_ID, userId).apply();
        sp.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }

    public String getToken() {
        return sp.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getUserId() {
        return sp.getString(KEY_USER_ID, null);
    }

    public void clean() {
        sp.edit().remove(KEY_ACCESS_TOKEN).apply();
        sp.edit().remove(KEY_USER_ID).apply();
    }

    public boolean isUserAuthorized() {
        return getToken() != null && getToken().length() > 0;
    }
}

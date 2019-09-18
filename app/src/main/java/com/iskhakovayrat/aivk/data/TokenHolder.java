package com.iskhakovayrat.aivk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

public class TokenHolder {
    private static final String SP_NAME = "token shared preferences";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER_ID      = "user_id";

    @NonNull
    private final SharedPreferences sp;

    public TokenHolder(@NonNull Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Log.d(getClass().getCanonicalName(), "init");
    }

    public void saveSession(@NonNull String userId, @NonNull String token) {
        sp.edit().putString(KEY_USER_ID, userId).apply();
        sp.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }

    public String getToken() {
        return sp.getString(KEY_ACCESS_TOKEN, null);
    }

    public void clear() {
        sp.edit().remove(KEY_ACCESS_TOKEN).apply();
        sp.edit().remove(KEY_USER_ID).apply();
    }

    public boolean isUserAuthorized() {
        return getToken() != null && getToken().length() > 0;
    }
}

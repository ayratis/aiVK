package com.iskhakovayrat.aivk.repository;

import com.iskhakovayrat.aivk.BuildConfig;
import com.iskhakovayrat.aivk.utils.SCOPES;
import com.iskhakovayrat.aivk.data.TokenHolder;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class LoginRepository {

    private TokenHolder tokenHolder;
    private static final String PARAM_USER_ID = "user_id";
    private static final String PARAM_ACCESS_TOKEN = "access_token";
    private static final int REQUEST_SCOPES = SCOPES.WALL + SCOPES.FRIENDS + SCOPES.OFFLINE;
    private static final String LOGIN_URL = BuildConfig.AUTH_URL +
            "?client_id=" + BuildConfig.CLIENT_ID +
            "&redirect_uri=" + BuildConfig.REDIRECT_AUTH_URL +
            "&display=mobile" +
            "&scope=" + REQUEST_SCOPES +
            "&response_type=token" +
            "&v=" + BuildConfig.API_VERSION;

    @Inject
    public LoginRepository(@NonNull TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public String getLoginUrl() {
        return LOGIN_URL;
    }

    public boolean isLoggedIn(String responseUrl) {
        if (responseUrl != null &&
                responseUrl.contains(PARAM_USER_ID) &&
                responseUrl.contains(PARAM_ACCESS_TOKEN)) {
            saveToken(responseUrl);
            return true;
        }
        return false;
    }

    private void saveToken(String responseUrl) {
        String userId = getParamValueFromUrl(responseUrl, PARAM_USER_ID);
        String token = getParamValueFromUrl(responseUrl, PARAM_ACCESS_TOKEN);
        tokenHolder.saveSession(userId, token);
    }

    private String getParamValueFromUrl(@NonNull String url, @NonNull String param) {
        return url
                .replaceAll(".*" + param + "=", "")
                .replaceAll("&.*", "");
    }
}

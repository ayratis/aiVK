package com.iskhakovayrat.aivk.login;

import com.iskhakovayrat.aivk.TokenHolder;

import io.reactivex.annotations.NonNull;

public class LoginPresenter {

    private LoginView view;

    private static final int SCOPES = 998622;

    private static final String LOGIN_URL = "https://oauth.vk.com/authorize" +
            "?client_id=" + "6695831" +
            "&redirect_uri=" + "https://oauth.vk.com/blank.html" +
            "&display=" + "mobile" +
            "&scope=" + SCOPES +
            "&response_type=" + "token" +
            "&v=" + "5.80";

    private static final String PARAM_USER_ID      = "user_id";
    private static final String PARAM_ACCESS_TOKEN = "access_token";

    private final TokenHolder tokenHolder;

    public LoginPresenter(TokenHolder tokenHolder){
        this.tokenHolder = tokenHolder;
    }

    public void attach(LoginView view){
        this.view = view;
        if (view != null) {
            view.loadLoginUrl(LOGIN_URL);
        }
    }

    public void detach(){
        view = null;
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
        String userId = getParamValueFromUrl(responseUrl, "user_id");
        String token = getParamValueFromUrl(responseUrl, "access_token");
        tokenHolder.saveSession(userId, token);

        if (view != null) {
            view.showNewsFeed();
        }
    }

    private String getParamValueFromUrl(@NonNull String url, @NonNull String param) {
        return url
                .replaceAll(".*" + param + "=", "")
                .replaceAll("&.*", "");
    }

}

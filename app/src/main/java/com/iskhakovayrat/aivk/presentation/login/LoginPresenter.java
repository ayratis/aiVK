package com.iskhakovayrat.aivk.presentation.login;

import android.util.Log;

import com.iskhakovayrat.aivk.di.scopes.ActivityScope;
import com.iskhakovayrat.aivk.repository.LoginRepository;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

@ActivityScope
public class LoginPresenter {

    @Nullable
    private LoginView view;
    private LoginRepository loginRepository;

    @Inject
    public LoginPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        Log.d(LoginPresenter.class.getCanonicalName(), "init");
    }

    public void attach(LoginView view) {
        this.view = view;
        if (view != null) view.loadUrl(loginRepository.getLoginUrl());
    }

    public void detach() {
        view = null;
    }

    public void onLogin(String url) {
        boolean isLoggedIn = loginRepository.isLoggedIn(url);
        if (isLoggedIn) {
            if (view != null) view.goToNewsfeed();
        } else {
            if (view != null) view.loadUrl(url);
        }
    }
}

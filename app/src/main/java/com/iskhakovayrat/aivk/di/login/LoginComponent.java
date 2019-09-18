package com.iskhakovayrat.aivk.di.login;

import com.iskhakovayrat.aivk.di.app.AppComponent;
import com.iskhakovayrat.aivk.di.scopes.ActivityScope;
import com.iskhakovayrat.aivk.presentation.login.LoginPresenter;

import dagger.Component;

@Component(dependencies = {AppComponent.class})
@ActivityScope
public interface LoginComponent {

    LoginPresenter loginPresenter();
}

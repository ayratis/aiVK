package com.iskhakovayrat.aivk;

import android.app.Application;

import com.iskhakovayrat.aivk.di.app.AppComponent;
import com.iskhakovayrat.aivk.di.app.AppModule;
import com.iskhakovayrat.aivk.di.app.DaggerAppComponent;
import com.iskhakovayrat.aivk.di.app.NetModule;
import com.iskhakovayrat.aivk.di.login.DaggerLoginComponent;
import com.iskhakovayrat.aivk.di.login.LoginComponent;
import com.iskhakovayrat.aivk.di.newsfeed.DaggerNewsfeedComponent;
import com.iskhakovayrat.aivk.di.newsfeed.NewsfeedComponent;

public class App extends Application {
    private static AppComponent appComponent;
    private static LoginComponent loginComponent;
    private static NewsfeedComponent newsfeedComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    public static LoginComponent openLoginComponent() {
        if (loginComponent == null) {
            loginComponent = DaggerLoginComponent.builder()
                    .appComponent(appComponent)
                    .build();
        }
        return loginComponent;
    }

    public static void closeLoginComponent() {
        loginComponent = null;
    }

    public static NewsfeedComponent openNewsfeedComponent() {
        if (newsfeedComponent == null) {
            newsfeedComponent = DaggerNewsfeedComponent.builder()
                    .appComponent(appComponent)
                    .build();
        }
        return newsfeedComponent;
    }

    public static void closeNewsfeedComponent() {
        newsfeedComponent = null;
    }

}

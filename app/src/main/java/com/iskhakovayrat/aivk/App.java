package com.iskhakovayrat.aivk;

import android.app.Application;

import com.iskhakovayrat.aivk.di.AppComponent;
import com.iskhakovayrat.aivk.di.AppModule;
import com.iskhakovayrat.aivk.di.DaggerAppComponent;
import com.iskhakovayrat.aivk.di.NetModule;

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Api.BASE_URL, LongPollApi.BASE_LONGPOLL_URL))
                .build();

    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

}

package com.iskhakovayrat.aivk.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){
        return application.getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer(){
        return new MediaPlayer();
    }
}

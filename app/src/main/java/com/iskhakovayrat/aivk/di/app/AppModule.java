package com.iskhakovayrat.aivk.di.app;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.data.TokenHolder;

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
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    public TokenHolder provideTokenHolder(Context context){
        return new TokenHolder(context);
    }

    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer(){
        return new MediaPlayer();
    }
}

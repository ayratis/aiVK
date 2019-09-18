package com.iskhakovayrat.aivk.di.app;


import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.data.Api;
import com.iskhakovayrat.aivk.data.TokenHolder;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    TokenHolder tokenHolder();
    MediaPlayer mediaPlayer();
    Api api();
}

package com.iskhakovayrat.aivk.main;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.model.newsfeed.NewsfeedResponse;
import com.iskhakovayrat.aivk.model.video.VideoResponseResponse;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainModel {

    private Api api;
    private TokenHolder tokenHolder;
    private MediaPlayer mp;

    public MainModel(TokenHolder tokenHolder, Api api, MediaPlayer mp) {
        this.api = api;
        this.tokenHolder = tokenHolder;
        this.mp = mp;
    }

    public Observable<NewsfeedResponse> getNewsFeed(int count){

        return api.getNewsfeed(count, Api.NEWSFEED_FILTER_POSTS, tokenHolder.getToken(), Api.V)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NewsfeedResponse> getNewsFeedNext(int count, String startForm){

        return api.getNewsfeedNext(count, Api.NEWSFEED_FILTER_POSTS, tokenHolder.getToken(), Api.V, startForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VideoResponseResponse> getVideo(String videoKey){
        return api.getVideo(videoKey, tokenHolder.getToken(), Api.V)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void audioPlayer(String url){
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.prepareAsync();
        mp.setOnPreparedListener(MediaPlayer::start);
    }

    public TokenHolder getTokenHolder() {
        return tokenHolder;
    }

    public Api getApi() {
        return api;
    }
}

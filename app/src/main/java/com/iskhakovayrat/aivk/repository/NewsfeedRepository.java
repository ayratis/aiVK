package com.iskhakovayrat.aivk.repository;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.data.Api;
import com.iskhakovayrat.aivk.BuildConfig;
import com.iskhakovayrat.aivk.data.TokenHolder;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedResponse;
import com.iskhakovayrat.aivk.entity.video.VideoResponseResponse;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsfeedRepository {

    private static final String NEWSFEED_FILTER_POSTS = "post";

    private Api api;
    private TokenHolder tokenHolder;
    private MediaPlayer mp;

    @Inject
    public NewsfeedRepository(Api api, TokenHolder tokenHolder) {
        this.api = api;
        this.tokenHolder = tokenHolder;
    }

    public Observable<NewsfeedResponse> getNewsFeed(int count, String page){

        return api.getNewsfeed(count, page, NEWSFEED_FILTER_POSTS, tokenHolder.getToken(), BuildConfig.API_VERSION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NewsfeedResponse> getNewsFeedNext(int count, String startForm){

        return api.getNewsfeedNext(count, NEWSFEED_FILTER_POSTS, tokenHolder.getToken(), BuildConfig.API_VERSION, startForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VideoResponseResponse> getVideo(String videoKey){
        return api.getVideo(videoKey, tokenHolder.getToken(), BuildConfig.API_VERSION)
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

    public boolean isAuthorized() {
        return tokenHolder.isUserAuthorized();
    }



}

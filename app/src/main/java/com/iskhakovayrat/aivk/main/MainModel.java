package com.iskhakovayrat.aivk.main;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.retrofit.newsfeed.NewsfeedResponse;
import com.iskhakovayrat.aivk.retrofit.video.VideoResponseResponse;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel {

    private Api api;

    private TokenHolder tokenHolder;

    public MainModel(TokenHolder tokenHolder) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        this.tokenHolder = tokenHolder;
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
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
    }
}

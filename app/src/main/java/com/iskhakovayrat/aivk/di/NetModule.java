package com.iskhakovayrat.aivk.di;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.LongPollApi;
import com.iskhakovayrat.aivk.TokenHolder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @NonNull private String baseUrl;
    @NonNull private String longPollUrl;

    public NetModule(String baseUrl, String longPollUrl) {
        this.baseUrl = baseUrl;
        this.longPollUrl = longPollUrl;
    }

    @Provides
    @Singleton
    public TokenHolder provideTokenHolder(SharedPreferences sp){
        return new TokenHolder(sp);
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }

    @Provides
    @Singleton
    public Api provideApi(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    @Provides
    @Singleton
    public LongPollApi provideLongPollApi(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(longPollUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LongPollApi.class);
    }


}

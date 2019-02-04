package com.iskhakovayrat.aivk.di;

import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.LongPollApi;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.conversation.ConversationModel;
import com.iskhakovayrat.aivk.main.MainModel;
import com.iskhakovayrat.aivk.messages.MessagesModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    public MainModel provideMainModel(TokenHolder tokenHolder, Api api, MediaPlayer mp){
        return new MainModel(tokenHolder, api, mp);
    }

    @Provides
    @Singleton
    public MessagesModel provideMessagesModel(TokenHolder tokenHolder, Api api, LongPollApi longPollApi){
        return new MessagesModel(tokenHolder, api, longPollApi);
    }

    @Provides
    @Singleton
    public ConversationModel provideConversationModel(TokenHolder tokenHolder, Api api,
                                                      LongPollApi longPollApi, MediaPlayer mp){
        return new ConversationModel(tokenHolder, api, longPollApi, mp);
    }
}

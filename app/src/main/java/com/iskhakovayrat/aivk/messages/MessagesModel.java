package com.iskhakovayrat.aivk.messages;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.Constants;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.retrofit.longpoll.LongPollHistory;
import com.iskhakovayrat.aivk.retrofit.longpoll.LongPollHistoryResponse;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPollServer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MessagesModel {

    private Api api;
    private Api longPollApi;
    private OkHttpClient okHttpClient;

    TokenHolder tokenHolder;

    public MessagesModel(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        Retrofit longPollRetrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_LONGPOLL_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        longPollApi = longPollRetrofit.create(Api.class);
    }

    public Observable<ConversationGet> loadMessages(){
        return api.getConversations(tokenHolder.getToken(), "all","5.80", Api.EXTENDED_YES)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ConversationGet> loadConversationsNext(int startMessageId){
        return api.getConversationsNext(tokenHolder.getToken(), "all","5.80", Api.EXTENDED_YES, startMessageId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ConversationGet> loadUnreadMessages(){
        return api.getConversations(tokenHolder.getToken(), "unread","5.80", Api.EXTENDED_YES)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LongPollServer> loadLongPollServer(){
        return api.getLongPollServer(tokenHolder.getToken(), "5.80", Api.NEED_PTS_YES)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LongPollHistory> loadLongPollHistory(int ts, int pts){
        return api.getLongPollHistory(tokenHolder.getToken(), "5.80", ts, pts)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LongPoll> longPolling(String server, String key, int ts){
        return longPollApi.longPolling(server, Api.ACT, key, ts,
                25, 2, Api.VERSION_LONGPOLL_DEFAULT)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ConversationHistory> getMessageById(int id){
        return api.getMessageById(tokenHolder.getToken(), "5.80", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<Integer> flags(int flag){
        int[] flagArray = {Constants.UNREAD, Constants.OUTBOX, Constants.REPLIED, Constants.IMPORTANT,
                Constants.CHAT, Constants.FRIENDS, Constants.SPAM,
                Constants.DELETED, Constants.FIXED, Constants.MEDIA};
        List<Integer> result = new ArrayList<>();
        for(int i = flagArray.length - 1; i > 0; i--){
            if(flag >= flagArray[i]) {
                result.add(flagArray[i]);
                flag -= flagArray[i];
            }
        }
        return result;
    }

    public Observable<ConversationGet> loadConversationsById(String peerIds){
        return api.getConversationsById(tokenHolder.getToken(), peerIds,"5.80")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}

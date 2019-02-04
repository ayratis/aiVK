package com.iskhakovayrat.aivk.messages;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.Constants;
import com.iskhakovayrat.aivk.LongPollApi;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.model.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.model.longpoll.LongPollHistory;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPollServer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagesModel {

    private Api api;
    private LongPollApi longPollApi;

    TokenHolder tokenHolder;

    public MessagesModel(TokenHolder tokenHolder, Api api, LongPollApi longPollApi) {
        this.tokenHolder = tokenHolder;
        this.api = api;
        this.longPollApi = longPollApi;
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
        return longPollApi.longPolling(server, LongPollApi.ACT, key, ts,
                25, 2, LongPollApi.VERSION_LONGPOLL_DEFAULT)
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

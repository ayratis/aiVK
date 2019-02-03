package com.iskhakovayrat.aivk.messages;

import com.iskhakovayrat.aivk.Constants;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPollServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MessagesPresenter {

    private MessagesView view;
    private MessagesModel model;

    private TokenHolder tokenHolder;

    private Disposable messagesDisposable;
    private Disposable longpollServerDisposabe;
    private Disposable longPollDisposable;
    private Disposable unreadMessagesDisposable;
    private Disposable newMessagesDisposable;
    private Disposable conversationsNextDisposable;

    private String key;
    private String server;
    private int firstTs;
    private AtomicInteger ts;


    public MessagesPresenter(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
        model = new MessagesModel(tokenHolder);
    }

    public void attach(MessagesView messagesView) {
        this.view = messagesView;
        loadMessages();
        longPoll1();
    }

    public void detach() {
        view = null;
        longPollDisposable.dispose();
//        newMessageDisposable.dispose();
    }

    private void loadMessages() {
        if (messagesDisposable == null) {
            messagesDisposable = model.loadMessages()
                    .doOnTerminate(() -> {
                        messagesDisposable.dispose();
                        messagesDisposable = null;
                    })
                    .subscribe(conversationGet -> view.showMessages(conversationGet),
                            error -> {
                            });
        }

    }

    public void loadConversationsNext(int startMessageId) {
        conversationsNextDisposable = model.loadConversationsNext(startMessageId)
                .subscribeWith(new DisposableObserver<ConversationGet>() {
                    @Override
                    public void onNext(ConversationGet conversationGet) {
                        view.showMoreConversations(conversationGet);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadNewMessages(int id) {
        if (newMessagesDisposable == null) {
            newMessagesDisposable = model.getMessageById(id)
                    .doOnTerminate(() -> {
                        newMessagesDisposable.dispose();
                        newMessagesDisposable = null;
                    })
                    .subscribe(conversationHistory -> view.showNewMessage(conversationHistory.getResponse().getItems().get(0)));
        }
    }

    private void longPoll() {
        if (longpollServerDisposabe == null) {
            longpollServerDisposabe = model.loadLongPollServer()
                    .doOnTerminate(() -> {
                        longpollServerDisposabe.dispose();
                        longpollServerDisposabe = null;
                    })
                    .map(LongPollServer::getResponse)
                    .subscribe(longPollServer -> {
                        server = longPollServer.getServer();
                        key = longPollServer.getKey();
                        firstTs = longPollServer.getTs();
                        Observable<LongPollParams> keyWithTsObservable = Observable.just(new LongPollParams(server, key, firstTs));
                        Observable<LongPoll> o = keyWithTsObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(keyWithTs -> {
                                    ts = new AtomicInteger(keyWithTs.ts);
                                    return Observable.just(keyWithTs.key)
                                            .flatMap(key -> model.longPolling(server, key, ts.get()))
                                            .doOnNext(longPoll -> {
                                                ts.set(longPoll.getTs());
                                                if(longPoll.getUpdates() != null && !longPoll.getUpdates().isEmpty()){
                                                    getUnreadMessages(longPoll.getUpdates());
                                                }
                                            })
                                            .repeat();
                                })
                                .retry()
                                .share();

                        longPollDisposable = o.subscribe();
                    });
        }

    }

    private void longPoll1() {
        if (longpollServerDisposabe == null) {
            longpollServerDisposabe = model.loadLongPollServer()
                    .doOnTerminate(() -> {
                        longpollServerDisposabe.dispose();
                        longpollServerDisposabe = null;
                    })
                    .map(LongPollServer::getResponse)
                    .subscribe(longPollServer -> {
                        server = longPollServer.getServer().substring(longPollServer.getServer().length()-6);
                        key = longPollServer.getKey();
                        ts = new AtomicInteger(longPollServer.getTs());
                        longPollDisposable = Observable.just(key)
                                .flatMap(key -> model.longPolling(server, key, ts.get()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .retry()
                                .share()
                                .repeat()
                                .subscribe(longPoll -> {
                                    ts.set(longPoll.getTs());
                                    if(longPoll.getUpdates() != null)
                                    getUnreadMessages(longPoll.getUpdates());
                                });
                    });
        }

    }

    private void getUnreadMessages(List<List<Object>> updates) {
        for (int i = 0; i < updates.size(); i++) {
            double d = (Double) updates.get(i).get(0);
            if (d == 4.0) {
                int flag = (int) (double) updates.get(i).get(2);
                List<Integer> flags = model.flags(flag);
                if (flags.contains(Constants.OUTBOX)) {
                    int newMessageId = (int) (double) updates.get(i).get(1);
                    loadNewMessages(newMessageId);
                } else {
                    loadUnreadMessages();
                }
            } else if (d == 7.0) {
                int peerIdSeven = (int) (double) updates.get(i).get(1);
                view.setOutboxByPeerReaded(peerIdSeven);
            } else if (d == 6.0) {
                int peerIdSix = (int) (double) updates.get(i).get(1);
                view.setInboxByPeerReaded(peerIdSix);
            } else if (d == 8.0){
                int userId = Math.abs((int) (double) updates.get(i).get(1));
                if((int) (double) updates.get(i).get(2) == 7){
                    view.setUserOnlineWeb(userId);
                } else {
                    view.setUserOnlinePhone(userId);
                }
            } else if (d == 9.0){
                int userId = Math.abs((int) (double) updates.get(i).get(1));
                view.setUserOffline(userId);
            }
        }
    }

    private void loadUnreadMessages() {
        if (unreadMessagesDisposable == null) {
            unreadMessagesDisposable = model.loadUnreadMessages()
                    .doOnTerminate(() -> {
                        unreadMessagesDisposable.dispose();
                        unreadMessagesDisposable = null;
                    })
                    .subscribe(conversationGet -> view.showUnreadMessages(conversationGet),
                            error -> {
                            });
        }

    }

    private static class LongPollParams {
        public final String server;
        public final String key;
        public final int ts;

        public LongPollParams(String server, String key, int ts) {
            this.server = server;
            this.key = key;
            this.ts = ts;
        }
    }

    public void getDialog(String peerId) {
        view.openDialog(peerId);
    }
}



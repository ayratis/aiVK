package com.iskhakovayrat.aivk.conversation;

import com.iskhakovayrat.aivk.Constants;
import com.iskhakovayrat.aivk.model.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPollServer;
import com.iskhakovayrat.aivk.model.messages.send.SendMessageResult;
import com.iskhakovayrat.aivk.model.newsfeed.Attachments;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ConversationPresenter {

    private ConversationView view;
    private ConversationModel model;
    private Disposable conversationDisposabe;
    private Disposable sendMessageDisposable;
    private int userId;
    private Disposable longpollServerDisposabe;
    private Disposable longPollDisposable;
    private Disposable conversationDisposabeNext;
    private Disposable videoDisposable;
    private Disposable groupDisposable;
    private Disposable markAsReadDisposable;

    private int peerId;
    private int nextMessageId;

    private String server;
    private String key;
    private int firstTs;
    private AtomicInteger ts;


    public ConversationPresenter(ConversationModel conversationModel) {
        model = conversationModel;
    }

    public void attach(ConversationView conversationView, int id) {
        this.view = conversationView;
        getConversationHistory(id);
        longPoll();
        peerId = id;
        markAsRead(id);
    }

    public void detach() {
        view = null;
        longPollDisposable.dispose();
    }

    private void getConversationHistory(int peerId) {
        if (conversationDisposabe == null) {
            conversationDisposabe = model.loadConversationHistory(peerId)
                    .doOnDispose(() -> {
                        conversationDisposabe.dispose();
                        conversationDisposabe = null;
                    })
                    .subscribe(conversationHistory -> {
                                nextMessageId = conversationHistory.getResponse().getItems()
                                        .get(conversationHistory.getResponse().getItems().size() - 1)
                                        .getId();
                                String title = model.getConversationTitle(conversationHistory);
                                view.showTitle(title);
                                view.showConversation(conversationHistory);
                                userId = model.getPeerId(conversationHistory);
                                int onlineState = model.getConversationOnlineState(conversationHistory);
                                switch (onlineState) {
                                    case 0:
                                        view.setUserOffline();
                                        break;
                                    case 1:
                                        view.setUserOnlinePhone();
                                        break;
                                    case 2:
                                        view.setUserOnlineWeb();
                                        break;
                                    default:
                                        break;
                                }
                            },
                            error -> {
                            });
        }
    }

    public void getConversationHistoryNext(int messageId) {
        conversationDisposabeNext = model.loadConversationHistoryNext(peerId, messageId)
                .subscribeWith(new DisposableObserver<ConversationHistory>() {

                    @Override
                    public void onNext(ConversationHistory conversationHistory) {
                        view.addConversationHistory(conversationHistory);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void sendMessage(String message) {
        sendMessageDisposable = model.querySendMessage(userId, message)
                .subscribeWith(new DisposableObserver<SendMessageResult>() {

                    @Override
                    public void onNext(SendMessageResult sendMessageResult) {
                        view.clearEditText();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                        server = longPollServer.getServer().substring(longPollServer.getServer().length()-6);
                        key = longPollServer.getKey();
                        firstTs = longPollServer.getTs();
                        Observable<KeyWithTs> keyWithTsObservable = Observable.just(new KeyWithTs(key, firstTs));
                        Observable<LongPoll> o = keyWithTsObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(keyWithTs -> {
                                    ts = new AtomicInteger(keyWithTs.ts);
                                    return Observable.just(keyWithTs.key)
                                            .flatMap(key -> model.longPolling(server, key, ts.get()))
                                            .doOnNext(longPoll -> {
                                                ts.set(longPoll.getTs());
                                                if (longPoll.getUpdates() != null && !longPoll.getUpdates().isEmpty()) {
                                                    getLongPollUpdates(longPoll.getUpdates());
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

    private void getLongPollUpdates(List<List<Object>> updates) {
        for (int i = 0; i < updates.size(); i++) {
            double d = (Double) updates.get(i).get(0);
            if (d == 4.0) {
                int id = (int) (double) updates.get(i).get(3);
                if (id == peerId) {
                    int flag = (int) (double) updates.get(i).get(2);
                    List<Integer> flags = model.flags(flag);
                    LastMessage item = new LastMessage();
                    item.setId((int) (double) updates.get(i).get(1));
                    item.setPeer_id((int) (double) updates.get(i).get(3));
                    item.setDate((int) (double) updates.get(i).get(4));
                    item.setText((String) updates.get(i).get(5));

                    if (flags.contains(Constants.OUTBOX)) {
                        item.setOut(1);
                    } else {
                        item.setOut(0);
                        markAsRead(peerId);
                    }
                    view.addMessage(item);
                }
            } else if (d == 7.0) {
                int id = (int) (double) updates.get(i).get(1);
                if (id == peerId) {
                    int messageId = (int) (double) updates.get(i).get(2);
                    view.setOutboxReaded(messageId);
                }
            } else if (d == 6.0) {
                int id = (int) (double) updates.get(i).get(1);
                if (id == peerId) {

                }
            } else if (d == 8.0) {
                int userId = Math.abs((int) (double) updates.get(i).get(1));
                if (userId == peerId) {
                    if ((int) (double) updates.get(i).get(2) == 7) {
                        view.setUserOnlineWeb();
                    } else {
                        view.setUserOnlinePhone();
                    }
                }

            } else if (d == 9.0) {
                int userId = Math.abs((int) (double) updates.get(i).get(1));
                if (userId == peerId) {
                    view.setUserOffline();
                }
            }
        }
    }


    private void markAsRead(int peerId) {
        markAsReadDisposable = model.markAsRead(peerId)
                .subscribeWith(new DisposableObserver<SendMessageResult>() {
                    @Override
                    public void onNext(SendMessageResult sendMessageResult) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private static class KeyWithTs {
        public final String key;
        public final int ts;

        public KeyWithTs(String key, int ts) {
            this.key = key;
            this.ts = ts;
        }
    }

    public void onAttachmentClick(Attachments attachment) {
        switch (attachment.getType()) {
            case LINK:
                view.openLink(attachment.getLink().getUrl());
                break;
            case VIDEO:
                String accessKey = attachment.getVideo().getAccess_key();
                long videoId = attachment.getVideo().getId();
                long ownerId = attachment.getVideo().getOwner_id();
                String videoKey = ownerId + "_" + videoId + "_" + accessKey;
                videoDisposable = model.getVideo(videoKey)
                        .doOnTerminate(() -> {
                            videoDisposable.dispose();
                            videoDisposable = null;
                        })
                        .subscribe(videoResponseResponse -> view
                                        .openVideo(videoResponseResponse.getResponse().getItems().get(0).getPlayer()),
                                error -> {
                                });
                break;
            case AUDIO:
                view.playAudio();
                model.audioPlayer(attachment.getAudio().getUrl());
            case DOC:
                if (attachment.getDoc().getType() == 5) {
                    view.playAudio();
                    model.audioPlayer(attachment.getDoc().getUrl());
                }
            default:
                break;

        }
    }

}

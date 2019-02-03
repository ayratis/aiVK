package com.iskhakovayrat.aivk.main;

import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.retrofit.newsfeed.Attachments;

import io.reactivex.disposables.Disposable;

public class MainPresenter {

    private MainModel model;
    private Disposable newsfeedDisposable;
    private Disposable videoDisposable;
    private Disposable newsfeedDisposableNext;

    private MainView mainView;

    private String nextFrom;

    private TokenHolder tokenHolder;

    public MainPresenter(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
        model = new MainModel(tokenHolder);
    }

    public void attach(MainView mainView) {
        this.mainView = mainView;
        if(tokenHolder.isUserAuthorized()) {
            loadNewsFeed(10);
        } else {
            mainView.startLoginActivityAndFinish();
        }
    }

    public void detach() {
        mainView = null;
    }


    public void loadNewsFeed(int count) {
        if (newsfeedDisposable == null) {
            newsfeedDisposable = model.getNewsFeed(count)
                    .doOnTerminate(() -> {
                        newsfeedDisposable.dispose();
                        newsfeedDisposable = null;
                    })
                    .subscribe(newsfeedResponse -> {
                                nextFrom = newsfeedResponse.getResponse().getNextFrom();
                                mainView.showPosts(newsfeedResponse.getResponse());
                            },
                            error -> {
                            });
        }
    }

    public void onAttachmentClick(Attachments attachment) {
        switch (attachment.getType()) {
            case LINK:
                mainView.openLink(attachment.getLink().getUrl());
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
                        .subscribe(videoResponseResponse -> mainView
                                        .openVideo(videoResponseResponse.getResponse().getItems().get(0).getPlayer()),
                                error -> {
                                });
                break;
            case AUDIO:
                mainView.playAudio();
                model.audioPlayer(attachment.getAudio().getUrl());
            case DOC:
                if(attachment.getDoc().getType() == 5) {
                    mainView.playAudio();
                    model.audioPlayer(attachment.getDoc().getUrl());
                }
            default:
                break;

        }
    }

    public void loadNewsFeedNext(int count) {
        if (newsfeedDisposableNext == null) {
            newsfeedDisposableNext = model.getNewsFeedNext(count, nextFrom)
                    .doOnTerminate(() -> {
                        newsfeedDisposableNext.dispose();
                        newsfeedDisposableNext = null;
                    })
                    .subscribe(newsfeedResponse -> {
                                nextFrom = newsfeedResponse.getResponse().getNextFrom();
                                mainView.addPosts(newsfeedResponse.getResponse());
                            },
                            error -> {
                            });
        }
    }

}

package com.iskhakovayrat.aivk.presentation.newsfeed;

import com.iskhakovayrat.aivk.di.scopes.ActivityScope;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.entity.newsfeed.Groups;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.entity.newsfeed.Profiles;
import com.iskhakovayrat.aivk.repository.NewsfeedRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;

@ActivityScope
public class NewsfeedPresenter {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_PAGE = "";

    private NewsfeedRepository repository;
    private Disposable newsfeedDisposable;
    private Disposable videoDisposable;

    private List<NewsfeedItems> items = new ArrayList<>();
    private List<Groups> groups = new ArrayList<>();
    private List<Profiles> profiles = new ArrayList<>();

    @Nullable
    private NewsfeedView mainView;

    private String nextFrom;

    @Inject
    public NewsfeedPresenter(NewsfeedRepository repository) {
        this.repository = repository;
    }

    public void attach(NewsfeedView mainView) {
        this.mainView = mainView;
        if (repository.isAuthorized()) {
            refresh();
        } else {
            mainView.goToLogin();
        }
    }

    public void detach() {
        mainView = null;
    }

    public void refresh() {
        items.clear();
        groups.clear();
        profiles.clear();
        loadNewsFeed(DEFAULT_PAGE, true);
    }

    private void loadNewsFeed(String page, boolean showLoading) {
        if (newsfeedDisposable == null) {
            if (mainView != null) mainView.showLoadig(showLoading);
            newsfeedDisposable = repository.getNewsFeed(DEFAULT_PAGE_SIZE, page)
                    .doOnTerminate(() -> {
                        newsfeedDisposable.dispose();
                        newsfeedDisposable = null;
                    })
                    .subscribe(newsfeedResponse -> {

                                nextFrom = newsfeedResponse.getResponse().getNextFrom();
                                items.addAll(newsfeedResponse.getResponse().getItems());
                                groups.addAll(newsfeedResponse.getResponse().getGroups());
                                profiles.addAll(newsfeedResponse.getResponse().getProfiles());

                                if (mainView != null) {
                                    mainView.setNewsfeedItems(items, groups, profiles);
                                    mainView.showLoadig(false);
                                }
                            },
                            error -> {
                                if (mainView != null) {
                                    mainView.showToast(error.getMessage());
                                    mainView.showLoadig(false);
                                }
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
                videoDisposable = repository.getVideo(videoKey)
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
                repository.audioPlayer(attachment.getAudio().getUrl());
            case DOC:
                if (attachment.getDoc().getType() == 5) {
                    mainView.playAudio();
                    repository.audioPlayer(attachment.getDoc().getUrl());
                }
            default:
                break;

        }
    }

    public void loadNewsFeedNext() {
        loadNewsFeed(nextFrom, false);
    }

}

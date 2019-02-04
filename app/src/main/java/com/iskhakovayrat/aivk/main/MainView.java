package com.iskhakovayrat.aivk.main;

import com.iskhakovayrat.aivk.model.newsfeed.Response;

public interface MainView {

    void showPosts(Response response);

    void addPosts(Response response);

    void openLink(String url);

    void openVideo(String url);

    void playAudio();

    void startLoginActivityAndFinish();
}

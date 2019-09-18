package com.iskhakovayrat.aivk.presentation.newsfeed;

import com.iskhakovayrat.aivk.entity.newsfeed.Groups;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.entity.newsfeed.Profiles;

import java.util.List;

public interface NewsfeedView {

    void openLink(String url);

    void openVideo(String url);

    void playAudio();

    void goToLogin();

    void showToast(String message);

    void setNewsfeedItems(List<NewsfeedItems> items, List<Groups> groups, List<Profiles> profiles);

    void showLoadig(boolean show);
}

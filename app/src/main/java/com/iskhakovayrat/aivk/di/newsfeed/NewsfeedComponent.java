package com.iskhakovayrat.aivk.di.newsfeed;

import com.iskhakovayrat.aivk.di.app.AppComponent;
import com.iskhakovayrat.aivk.di.scopes.ActivityScope;
import com.iskhakovayrat.aivk.presentation.newsfeed.NewsfeedPresenter;

import dagger.Component;

@Component(dependencies = {AppComponent.class})
@ActivityScope
public interface NewsfeedComponent {
    NewsfeedPresenter newsfeedPresenter();
}


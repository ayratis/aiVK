package com.iskhakovayrat.aivk.ui.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.iskhakovayrat.aivk.App;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Groups;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.entity.newsfeed.Profiles;
import com.iskhakovayrat.aivk.presentation.newsfeed.LoadMoreListener;
import com.iskhakovayrat.aivk.presentation.newsfeed.NewsfeedPresenter;
import com.iskhakovayrat.aivk.presentation.newsfeed.NewsfeedView;
import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.ui.login.LoginActivity;
import com.iskhakovayrat.aivk.ui.newsfeed.adapters.NewsfeedAdapter;

import java.util.List;

public class NewsfeedActivity extends AppCompatActivity implements NewsfeedView {

    private NewsfeedPresenter presenter;
    private NewsfeedAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        presenter = App.openNewsfeedComponent().newsfeedPresenter();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

        RecyclerView newsfeedRecycler = findViewById(R.id.newsfeedRecyclerView);
        newsfeedRecycler.setNestedScrollingEnabled(false);

        OnAttachmentClickListener onAttachmentClickListener = attachment -> presenter.onAttachmentClick(attachment);
        LoadMoreListener loadMore = nextMessageId -> presenter.loadNewsFeedNext();

        adapter = new NewsfeedAdapter(onAttachmentClickListener, loadMore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        newsfeedRecycler.setLayoutManager(layoutManager);
        newsfeedRecycler.setAdapter(adapter);
        newsfeedRecycler.smoothScrollToPosition(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) App.closeNewsfeedComponent();
    }


    @Override
    public void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void openVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void playAudio() {

    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewsfeedItems(List<NewsfeedItems> items, List<Groups> groups, List<Profiles> profiles) {
        adapter.setData(items, groups, profiles);
    }

    @Override
    public void showLoadig(boolean show) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(show));
    }
}

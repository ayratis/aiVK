package com.iskhakovayrat.aivk.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.iskhakovayrat.aivk.LoadMore;
import com.iskhakovayrat.aivk.NewsfeedAdapter;
import com.iskhakovayrat.aivk.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.di.Injector;
import com.iskhakovayrat.aivk.login.LoginActivity;
import com.iskhakovayrat.aivk.messages.MessagesActivity;
import com.iskhakovayrat.aivk.model.newsfeed.Response;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter presenter;

    private RecyclerView newsfeedRecycler;
    private Button messagesButton;
    private Button refreshButton;
    private NewsfeedAdapter adapter;

    private OnAttachmentClickListener onAttachmentClickListener;
    private LoadMore loadMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Injector.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.attach(this);

        newsfeedRecycler = findViewById(R.id.newsfeedRecyclerView);

        messagesButton = findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MessagesActivity.class);
            startActivity(intent);
        });

        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(view -> {
            presenter.detach();
            presenter.attach(this);
        });

        onAttachmentClickListener = attachment -> presenter.onAttachmentClick(attachment);
        loadMore = nextMessageId -> presenter.loadNewsFeedNext(10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showPosts(com.iskhakovayrat.aivk.model.newsfeed.Response response) {
        adapter = new NewsfeedAdapter(response, onAttachmentClickListener, loadMore);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        newsfeedRecycler.setLayoutManager(layoutManager);
        newsfeedRecycler.setAdapter(adapter);
        newsfeedRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void addPosts(Response response) {
        adapter.addPosts(response);
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
    public void startLoginActivityAndFinish() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

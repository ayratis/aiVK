package com.iskhakovayrat.aivk.conversation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iskhakovayrat.aivk.ConversationRecyclerAdapter;
import com.iskhakovayrat.aivk.LoadMore;
import com.iskhakovayrat.aivk.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.di.Injector;
import com.iskhakovayrat.aivk.model.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;

import javax.inject.Inject;

public class ConversationActivity extends AppCompatActivity implements ConversationView {

    @Inject
    ConversationPresenter presenter;

    public static final String PEER_ID_KEY = "peer_id";

    private TextView title;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button sendMessageButon;
    private TextView peerOnlineState;
    private ImageView peerOnlineMobileIcon;
    private Button backButton;

    private ConversationRecyclerAdapter adapter;
    private LoadMore loadMore;
    private OnAttachmentClickListener onAttachmentClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Injector.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        title = findViewById(R.id.peerTitle);
        recyclerView = findViewById(R.id.conv_history_recycler);
        editText = findViewById(R.id.editText);
        peerOnlineMobileIcon = findViewById(R.id.peerOnlineMobileIcon);
        peerOnlineState = findViewById(R.id.peerOnlineState);

        backButton = findViewById(R.id.backToMessagesButton);
        backButton.setOnClickListener(view -> finish());

        sendMessageButon = findViewById(R.id.sendButton);
        sendMessageButon.setOnClickListener(view -> presenter.sendMessage(editText.getText().toString()));

        String peerId = getIntent().getStringExtra(PEER_ID_KEY);
        presenter.attach(this, Integer.valueOf(peerId));

        loadMore = nextMessageId -> presenter.getConversationHistoryNext(nextMessageId);

        onAttachmentClickListener = attachment -> presenter.onAttachmentClick(attachment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showConversation(ConversationHistory conversationHistory) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        adapter = new ConversationRecyclerAdapter(conversationHistory.getResponse(),
                onAttachmentClickListener, loadMore);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addConversationHistory(ConversationHistory conversationHistory) {
        adapter.addConversationHistory(conversationHistory);
    }

    @Override
    public void showTitle(String title) {
        if(title != null ) this.title.setText(title);
        else this.title.setText("");
    }

    @Override
    public void clearEditText() {
        editText.getText().clear();
    }

    @Override
    public void addMessage(LastMessage item) {
        adapter.addMessage(item);

        recyclerView.smoothScrollToPosition(0);
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
    public void setOutboxReaded(int messageId) {
        adapter.setOutboxReaded(messageId);
    }

    @Override
    public void setUserOnlinePhone() {
        peerOnlineMobileIcon.setVisibility(View.VISIBLE);
        peerOnlineState.setVisibility(View.VISIBLE);
        peerOnlineMobileIcon.setBackgroundResource(R.drawable.ic_online_phone);
        peerOnlineState.setText("online");
    }

    @Override
    public void setUserOnlineWeb() {
        peerOnlineMobileIcon.setVisibility(View.GONE);
        peerOnlineState.setVisibility(View.VISIBLE);
        peerOnlineState.setText("online");
    }

    @Override
    public void setUserOffline() {
        peerOnlineMobileIcon.setVisibility(View.GONE);
        peerOnlineState.setVisibility(View.GONE);
    }

}

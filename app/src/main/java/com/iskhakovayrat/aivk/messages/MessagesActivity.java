package com.iskhakovayrat.aivk.messages;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iskhakovayrat.aivk.LoadMore;
import com.iskhakovayrat.aivk.MessagesRecyclerAdapter;
import com.iskhakovayrat.aivk.OnDialogClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.conversation.ConversationActivity;
import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.get_conversation.LastMessage;

public class MessagesActivity extends AppCompatActivity implements MessagesView {

    private RecyclerView messagesRecycler;
    private Button backButton;

    private MessagesPresenter messagesPresenter;

    private OnDialogClickListener onDialogClickListener;

    private MessagesRecyclerAdapter adapter;

    private LoadMore loadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messagesRecycler = findViewById(R.id.messages_recycler);
        messagesPresenter = new MessagesPresenter(new TokenHolder(this));
        messagesPresenter.attach(this);

        backButton = findViewById(R.id.back_to_newsfeed_button);
        backButton.setOnClickListener(v -> finish());

        onDialogClickListener = peerId -> messagesPresenter.getDialog(peerId);
        loadMore = nextMessageId -> messagesPresenter.loadConversationsNext(nextMessageId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messagesPresenter.detach();
    }

    @Override
    public void showMessages(ConversationGet conversationGet) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MessagesRecyclerAdapter(conversationGet.getResponse(), onDialogClickListener, loadMore);
        messagesRecycler.setLayoutManager(layoutManager);
        messagesRecycler.setAdapter(adapter);
    }

    @Override
    public void showMoreConversations(ConversationGet conversationGet) {
        adapter.addMoreConversations(conversationGet);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        messagesPresenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        messagesPresenter.detach();
    }

    @Override
    public void openDialog(String peerId) {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(ConversationActivity.PEER_ID_KEY, peerId);
        startActivity(intent);
    }

    @Override
    public void showNewMessage(LastMessage item) {
        adapter.replaceItem(item);
    }

    @Override
    public void showUnreadMessages(ConversationGet conversationGet) {
        adapter.replaceItems(conversationGet);
//        messagesRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void setOutboxByPeerReaded(int peerId) {
        adapter.setOutboxReaded(peerId);
    }

    @Override
    public void setInboxByPeerReaded(int peerId) {
        adapter.setInboxReaded(peerId);
    }

    @Override
    public void setUserOnlinePhone(int userId) {
        adapter.setUserOnlinePhone(userId);
    }

    @Override
    public void setUserOnlineWeb(int userId) {
        adapter.setUserOnlineWeb(userId);
    }

    @Override
    public void setUserOffline(int userId) {
        adapter.setUserOffline(userId);
    }


}

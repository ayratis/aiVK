package com.iskhakovayrat.aivk.messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.iskhakovayrat.aivk.LoadMore;
import com.iskhakovayrat.aivk.MessagesRecyclerAdapter;
import com.iskhakovayrat.aivk.OnDialogClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.conversation.ConversationActivity;
import com.iskhakovayrat.aivk.di.Injector;
import com.iskhakovayrat.aivk.model.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.model.get_conversation.LastMessage;

import javax.inject.Inject;

public class MessagesActivity extends AppCompatActivity implements MessagesView {

    @Inject MessagesPresenter presenter;

    private RecyclerView messagesRecycler;
    private Button backButton;

    private OnDialogClickListener onDialogClickListener;

    private MessagesRecyclerAdapter adapter;

    private LoadMore loadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Injector.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messagesRecycler = findViewById(R.id.messages_recycler);
        presenter.attach(this);

        backButton = findViewById(R.id.back_to_newsfeed_button);
        backButton.setOnClickListener(v -> finish());

        onDialogClickListener = peerId -> presenter.getDialog(peerId);
        loadMore = nextMessageId -> presenter.loadConversationsNext(nextMessageId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
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
        presenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detach();
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

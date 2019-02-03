package com.iskhakovayrat.aivk.messages;

import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.get_conversation.LastMessage;

public interface MessagesView {

    void showMessages(ConversationGet conversationGet);

    void showMoreConversations(ConversationGet conversationGet);

    void openDialog(String peerId);

    void showNewMessage(LastMessage item);

    void showUnreadMessages(ConversationGet conversationGet);

    void setOutboxByPeerReaded(int peerId);

    void setInboxByPeerReaded(int peerId);

    void setUserOnlinePhone(int userId);

    void setUserOnlineWeb(int userId);

    void setUserOffline(int userId);
}

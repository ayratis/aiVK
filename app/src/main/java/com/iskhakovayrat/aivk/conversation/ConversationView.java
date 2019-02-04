package com.iskhakovayrat.aivk.conversation;

import com.iskhakovayrat.aivk.model.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;

public interface ConversationView {
    void showConversation(ConversationHistory conversationHistory);

    void addConversationHistory(ConversationHistory conversationHistory);

    void showTitle(String title);

    void clearEditText();

    void addMessage(LastMessage item);

    void openLink(String url);

    void openVideo(String url);

    void playAudio();

    void setOutboxReaded(int messageId);

    void setUserOnlinePhone();

    void setUserOnlineWeb();

    void setUserOffline();
}

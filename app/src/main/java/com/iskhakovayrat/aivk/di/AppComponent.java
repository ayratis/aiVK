package com.iskhakovayrat.aivk.di;


import com.iskhakovayrat.aivk.FwdMessagesAdapter;
import com.iskhakovayrat.aivk.WallAdapter;
import com.iskhakovayrat.aivk.conversation.ConversationActivity;
import com.iskhakovayrat.aivk.login.LoginActivity;
import com.iskhakovayrat.aivk.main.MainActivity;
import com.iskhakovayrat.aivk.messages.MessagesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, PresenterModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(MessagesActivity messagesActivity);

    void inject(ConversationActivity conversationActivity);

    void inject(LoginActivity loginActivity);

    void inject(FwdMessagesAdapter fwdMessagesAdapter);

    void inject(WallAdapter wallAdapter);
}

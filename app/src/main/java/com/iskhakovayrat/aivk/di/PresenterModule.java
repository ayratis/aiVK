package com.iskhakovayrat.aivk.di;

import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.conversation.ConversationModel;
import com.iskhakovayrat.aivk.conversation.ConversationPresenter;
import com.iskhakovayrat.aivk.login.LoginPresenter;
import com.iskhakovayrat.aivk.main.MainModel;
import com.iskhakovayrat.aivk.main.MainPresenter;
import com.iskhakovayrat.aivk.messages.MessagesModel;
import com.iskhakovayrat.aivk.messages.MessagesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter provideMainPresenter(MainModel mainModel){
        return new MainPresenter(mainModel);
    }

    @Provides
    MessagesPresenter provideMessagesPresenter(MessagesModel messagesModel){
        return new MessagesPresenter(messagesModel);
    }

    @Provides
    ConversationPresenter provideConversationPresenter(ConversationModel conversationModel){
        return new ConversationPresenter(conversationModel);
    }

    @Provides
    LoginPresenter provideLoginPresenter(TokenHolder tokenHolder){
        return new LoginPresenter(tokenHolder);
    }
}

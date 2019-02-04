package com.iskhakovayrat.aivk.di;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.iskhakovayrat.aivk.App;
import com.iskhakovayrat.aivk.FwdMessagesAdapter;
import com.iskhakovayrat.aivk.WallAdapter;
import com.iskhakovayrat.aivk.conversation.ConversationActivity;
import com.iskhakovayrat.aivk.login.LoginActivity;
import com.iskhakovayrat.aivk.main.MainActivity;
import com.iskhakovayrat.aivk.messages.MessagesActivity;


public class Injector {

    private static AppComponent getAppComponent(@NonNull FragmentActivity initialScreen) {
        return ((App) initialScreen.getApplicationContext()).getAppComponent();
    }

    public static void inject(@NonNull MainActivity activity){
        getAppComponent(activity).inject(activity);
    }

    public static void inject(@NonNull MessagesActivity activity){
        getAppComponent(activity).inject(activity);
    }

    public static void inject(@NonNull ConversationActivity activity){
        getAppComponent(activity).inject(activity);
    }

    public static void inject(@NonNull LoginActivity activity){
        getAppComponent(activity).inject(activity);
    }

    public static void inject(@NonNull FwdMessagesAdapter fwdMessagesAdapter){
        App.getAppComponent().inject(fwdMessagesAdapter);
    }

    public static void inject(@NonNull WallAdapter wallAdapter){
        App.getAppComponent().inject(wallAdapter);
    }

}

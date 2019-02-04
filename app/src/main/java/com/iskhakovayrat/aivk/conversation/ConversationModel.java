package com.iskhakovayrat.aivk.conversation;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.iskhakovayrat.aivk.Api;
import com.iskhakovayrat.aivk.Constants;
import com.iskhakovayrat.aivk.LongPollApi;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.model.get_conversation.Peer;
import com.iskhakovayrat.aivk.model.get_conversation.Profiles;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.model.groups.getById.GroupsGetById;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.model.longpoll_server.LongPollServer;
import com.iskhakovayrat.aivk.model.messages.send.SendMessageResult;
import com.iskhakovayrat.aivk.model.video.VideoResponseResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConversationModel {

    private TokenHolder tokenHolder;
    private Api api;
    private LongPollApi longPollApi;
    private MediaPlayer mp;


    public ConversationModel(TokenHolder tokenHolder, Api api, LongPollApi longPollApi, MediaPlayer mp) {
        this.tokenHolder = tokenHolder;
        this.api = api;
        this.longPollApi = longPollApi;
        this.mp = mp;
    }

    public Observable<ConversationHistory> loadConversationHistory(int peerId) {
        return api.getConversationHistory(tokenHolder.getToken(), "5.80", peerId, Api.EXTENDED_YES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ConversationHistory> loadConversationHistoryNext(int peerId, int startMessageId) {
        return api.getConversationHistoryNext(tokenHolder.getToken(), "5.80", peerId, Api.EXTENDED_YES, startMessageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getConversationTitle(ConversationHistory conversationHistory) {
        Peer peer = conversationHistory.getResponse().getConversations().get(0).getPeer();
        if (peer.getType().equals("user")) {
            List<Profiles> profiles = conversationHistory.getResponse().getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (peer.getId() == profiles.get(i).getId()) {
                    return profiles.get(i).getFirst_name() + " " + profiles.get(i).getLast_name();
                }
            }
        } else if (peer.getType().equals("chat")) {
            return conversationHistory.getResponse().getConversations().get(0).getChat_setting().getTitle();
        } else if (peer.getType().equals("group")) {
            return conversationHistory.getResponse().getGroups().get(0).getName();
        }
        return null;
    }

    public int getConversationOnlineState(ConversationHistory conversationHistory) {
        Peer peer = conversationHistory.getResponse().getConversations().get(0).getPeer();
        if (peer.getType().equals("user")) {
            List<Profiles> profiles = conversationHistory.getResponse().getProfiles();
            for (int i = 0; i < profiles.size(); i++) {
                if (peer.getId() == profiles.get(i).getId()) {
                    if (profiles.get(i).getOnline() == 0 && profiles.get(i).getOnline_mobile() == 0) {
                        return 0;
                    } else if (profiles.get(i).getOnline_mobile() == 1) {
                        return 1;
                    } else if (profiles.get(i).getOnline() == 1) {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public Observable<SendMessageResult> querySendMessage(int userId, String message) {
        return api.sendMessage(tokenHolder.getToken(), Api.V, userId, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public int getPeerId(ConversationHistory conversationHistory) {
        int peerId = conversationHistory.getResponse().getConversations().get(0).getPeer().getId();
        return peerId;
    }

    public Observable<LongPollServer> loadLongPollServer() {
        return api.getLongPollServer(tokenHolder.getToken(), Api.V, Api.NEED_PTS_YES)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GroupsGetById> loadGroupsById(long group_id) {
        return api.getGroupsInfo(tokenHolder.getToken(), Api.V, group_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LongPoll> longPolling(String server, String key, int ts) {
        return longPollApi.longPolling(server, LongPollApi.ACT, key, ts,
                LongPollApi.LONGPOLL_DEFAULT_WAIT, LongPollApi.LONGPOLL_DEFAULT_MODE, LongPollApi.VERSION_LONGPOLL_DEFAULT)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public List<Integer> flags(int flag) {
        int[] flagArray = {Constants.UNREAD, Constants.OUTBOX, Constants.REPLIED,
                Constants.IMPORTANT, Constants.CHAT, Constants.FRIENDS, Constants.SPAM,
                Constants.DELETED, Constants.FIXED, Constants.MEDIA};
        List<Integer> result = new ArrayList<>();
        for (int i = flagArray.length - 1; i > 0; i--) {
            if (flag >= flagArray[i]) {
                result.add(flagArray[i]);
                flag -= flagArray[i];
            }
        }
        return result;
    }

    public Observable<VideoResponseResponse> getVideo(String videoKey) {
        return api.getVideo(videoKey, tokenHolder.getToken(), Api.V)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void audioPlayer(String url) {
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.prepareAsync();
        mp.setOnPreparedListener(MediaPlayer::start);
    }

    public Observable<SendMessageResult> markAsRead(int peerId) {
        return api.markAsRead(tokenHolder.getToken(), Api.V, peerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

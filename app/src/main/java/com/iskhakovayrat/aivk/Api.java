package com.iskhakovayrat.aivk;

import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.retrofit.groups.getById.GroupsGetById;
import com.iskhakovayrat.aivk.retrofit.longpoll.LongPollHistory;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPoll;
import com.iskhakovayrat.aivk.retrofit.longpoll_server.LongPollServer;
import com.iskhakovayrat.aivk.retrofit.messages.send.SendMessageResult;
import com.iskhakovayrat.aivk.retrofit.newsfeed.NewsfeedResponse;
import com.iskhakovayrat.aivk.retrofit.users.get.UsersGet;
import com.iskhakovayrat.aivk.retrofit.video.VideoResponseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {
    String BASE_URL = "https://api.vk.com/";
    String NEWSFEED_FILTER_POSTS = "post";
    String PHOTO_50 = "photo_50";
    String ACCESSTOKEN = "98b6f646fe5b8380ac1940d5c1d55f8264e79087ecd6bd3233fa8581280886838aaf5340da111cea5fae9";
    String EXTENDED_YES = "1";
    String NEED_PTS_YES = "1";
    int LONGPOLL_DEFAULT_WAIT = 25;
    int LONGPOLL_DEFAULT_MODE = 2;
    String V = "5.80";
    String BASE_LONGPOLL_URL = "https://imv4.vk.com/";
    String ACT = "a_check";
    String VERSION_LONGPOLL_DEFAULT = "2";


    @GET("https://api.vk.com/method/newsfeed.get")
    Observable<NewsfeedResponse> getNewsfeed(@Query("count") int count,
                                             @Query("filters") String filters,
                                             @Query("access_token") String accessToken,
                                             @Query("v") String version);

    @GET("https://api.vk.com/method/newsfeed.get")
    Observable<NewsfeedResponse> getNewsfeedNext(@Query("count") int count,
                                                 @Query("filters") String filters,
                                                 @Query("access_token") String accessToken,
                                                 @Query("v") String version,
                                                 @Query("start_from") String startFrom);

    @GET("https://api.vk.com/method/video.get")
    Observable<VideoResponseResponse> getVideo(@Query("videos") String videoKey,
                                               @Query("access_token") String accessToken,
                                               @Query("v") String version);

    @GET("https://api.vk.com/method/messages.getLongPollServer")
    Observable<LongPollServer> getLongPollServer(@Query("access_token") String accessToken,
                                                 @Query("v") String version,
                                                 @Query("need_pts") String need_pts);

    @GET("{server}")
    Observable<LongPoll> longPolling(@Path ("server") String server,
                                     @Query("act") String act,
                                     @Query("key") String key,
                                     @Query("ts") int ts,
                                     @Query("wait") int wait,
                                     @Query("mode") int mode,
                                     @Query("version") String version);

    @GET("https://api.vk.com/method/messages.getConversations")
    Observable<ConversationGet> getConversations(@Query("access_token") String accessToken,
                                                 @Query("filter") String filter,
                                                 @Query("v") String version,
                                                 @Query("extended") String extended);

    @GET("https://api.vk.com/method/messages.getConversations")
    Observable<ConversationGet> getConversationsNext(@Query("access_token") String accessToken,
                                                     @Query("filter") String filter,
                                                     @Query("v") String version,
                                                     @Query("extended") String extended,
                                                     @Query("start_message_id") int startMessageId);

    @GET("https://api.vk.com/method/messages.getConversationsById")
    Observable<ConversationGet> getConversationsById(@Query("access_token") String accessToken,
                                                     @Query("peer_ids") String peerIds,
                                                     @Query("v") String version);

    @GET("https://api.vk.com/method/messages.getHistory")
    Observable<ConversationHistory> getConversationHistory(@Query("access_token") String accessToken,
                                                           @Query("v") String version,
                                                           @Query("peer_id") int peer_id,
                                                           @Query("extended") String extended);

    @GET("https://api.vk.com/method/messages.getHistory")
    Observable<ConversationHistory> getConversationHistoryNext(@Query("access_token") String accessToken,
                                                               @Query("v") String version,
                                                               @Query("peer_id") int peer_id,
                                                               @Query("extended") String extended,
                                                               @Query("start_message_id") int startMessageId);

    @GET("https://api.vk.com/method/messages.getLongPollHistory")
    Observable<LongPollHistory> getLongPollHistory(@Query("access_token") String accessToken,
                                                   @Query("v") String version,
                                                   @Query("ts") int ts,
                                                   @Query("pts") int pts);


    @POST("https://api.vk.com/method/messages.send")
    Observable<SendMessageResult> sendMessage(@Query("access_token") String accessToken,
                                              @Query("v") String version,
                                              @Query("user_id") int user_id,
                                              @Query("message") String message);

    @GET("https://api.vk.com/method/messages.getById")
    Observable<ConversationHistory> getMessageById(@Query("access_token") String accessToken,
                                                   @Query("v") String version,
                                                   @Query("message_ids") int id);


    @GET("https://api.vk.com/method/groups.getById")
    Observable<GroupsGetById> getGroupsInfo(@Query("access_token") String accessToken,
                                            @Query("v") String version,
                                            @Query("group_id") long group_id);

    @GET("https://api.vk.com/method/groups.getById")
    Call<GroupsGetById> getGroupsInfoCall(@Query("access_token") String accessToken,
                                          @Query("v") String version,
                                          @Query("group_id") long group_id);

    @GET("https://api.vk.com/method/users.get")
    Call<UsersGet> getProfileInfoCall(@Query("access_token") String accessToken,
                                      @Query("v") String version,
                                      @Query("user_id") long user_id,
                                      @Query("fields") String fields);

    @POST("https://api.vk.com/method/messages.markAsRead")
    Observable<SendMessageResult> markAsRead(@Query("access_token") String accessToken,
                                             @Query("v") String version,
                                             @Query("peer_id") int peerId);

}

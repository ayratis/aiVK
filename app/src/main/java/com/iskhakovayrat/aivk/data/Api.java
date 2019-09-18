package com.iskhakovayrat.aivk.data;

import com.iskhakovayrat.aivk.entity.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.entity.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.entity.groups.getById.GroupsGetById;
import com.iskhakovayrat.aivk.entity.longpoll.LongPollHistory;
import com.iskhakovayrat.aivk.entity.longpoll_server.LongPollServer;
import com.iskhakovayrat.aivk.entity.messages.send.SendMessageResult;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedResponse;
import com.iskhakovayrat.aivk.entity.users.get.UsersGet;
import com.iskhakovayrat.aivk.entity.video.VideoResponseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Api {
    String BASE_URL = "https://api.vk.com/";
    String NEWSFEED_FILTER_POSTS = "post";
    String PHOTO_50 = "photo_50";
    String ACCESSTOKEN = "98b6f646fe5b8380ac1940d5c1d55f8264e79087ecd6bd3233fa8581280886838aaf5340da111cea5fae9";
    String EXTENDED_YES = "1";
    String NEED_PTS_YES = "1";
    String V = "5.80";


    @GET("https://api.vk.com/method/newsfeed.get")
    Observable<NewsfeedResponse> getNewsfeed(@Query("count") int count,
                                             @Query("start_from") String page,
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

package com.iskhakovayrat.aivk;

import com.iskhakovayrat.aivk.model.longpoll_server.LongPoll;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LongPollApi {
    int LONGPOLL_DEFAULT_WAIT = 25;
    int LONGPOLL_DEFAULT_MODE = 2;

    String BASE_LONGPOLL_URL = "https://imv4.vk.com/";
    String ACT = "a_check";
    String VERSION_LONGPOLL_DEFAULT = "2";


    @GET("{server}")
    Observable<LongPoll> longPolling(@Path("server") String server,
                                     @Query("act") String act,
                                     @Query("key") String key,
                                     @Query("ts") int ts,
                                     @Query("wait") int wait,
                                     @Query("mode") int mode,
                                     @Query("version") String version);

}

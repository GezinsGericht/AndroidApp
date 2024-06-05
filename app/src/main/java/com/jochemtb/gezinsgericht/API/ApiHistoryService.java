package com.jochemtb.gezinsgericht.API;


import com.jochemtb.gezinsgericht.API.History.HistoryListRequest;
import com.jochemtb.gezinsgericht.API.History.HistoryListResponse;
import com.jochemtb.gezinsgericht.domain.HistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiHistoryService {

//    @Headers({"Content-Type: application/json"})
    @GET("history-list")
    Call<List<HistoryItem>> getHistory();


}

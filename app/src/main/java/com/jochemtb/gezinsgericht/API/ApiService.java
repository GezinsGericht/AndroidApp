package com.jochemtb.gezinsgericht.API;


import com.jochemtb.gezinsgericht.API.History.HistoryListRequest;
import com.jochemtb.gezinsgericht.API.History.HistoryListResponse;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordRequest;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordResponse;
import com.jochemtb.gezinsgericht.API.Login.LoginRequest;
import com.jochemtb.gezinsgericht.API.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("login/reset")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @Headers("Content-Type: application/json")
    @POST("history/list")
    Call<HistoryListResponse> getHistory(@Body HistoryListRequest historyRequest);
}

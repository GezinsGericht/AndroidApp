package com.jochemtb.gezinsgericht.API;

import com.google.gson.JsonObject;
import com.jochemtb.gezinsgericht.API.Login.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiQuizService {
        @Headers("Content-Type: application/json")
        @POST("quiz")
        Call<BaseResponse> submitQuizAnsersValues(@Body JsonObject combinedQuestionIdAndAnswerValues);
}

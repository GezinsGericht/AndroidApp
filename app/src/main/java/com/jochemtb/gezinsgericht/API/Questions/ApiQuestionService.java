package com.jochemtb.gezinsgericht.API.Questions;

import com.google.gson.JsonObject;
import com.jochemtb.gezinsgericht.domain.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiQuestionService {

    @Headers("Content-Type: application/json")
    @POST("questions")
    Call<List<Question>> getQuestions(@Body JsonObject questionIds);
}


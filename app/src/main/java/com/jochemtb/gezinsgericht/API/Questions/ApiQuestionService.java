package com.jochemtb.gezinsgericht.API.Questions;

import com.jochemtb.gezinsgericht.API.Login.LoginRequest;
import com.jochemtb.gezinsgericht.API.Login.LoginResponse;
import com.jochemtb.gezinsgericht.domain.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiQuestionService {

    @Headers("Content-Type: application/json")
    @POST("questions")
    Call<List<Question>> getQuestions(@Body List<Integer> questionIds);
}


package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jochemtb.gezinsgericht.API.Questions.ApiQuestionService;
import com.jochemtb.gezinsgericht.domain.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionRepository {
    private Context context;
    private SharedPreferences sharedPref;
//    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String API_URL = "http://81.206.200.166:3000/api/";
    private static final String LOG_TAG = "QuestionRepository";
    private  List<Question> retrievedQuestions;

    public QuestionRepository(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public void getQuestions(List<Integer> questionIds, OnQuestionsRetrievedCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiQuestionService apiQuestionService = retrofit.create(ApiQuestionService.class);

        apiQuestionService.getQuestions(questionIds).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Log.d(LOG_TAG, "Opgehaalde Questions: " + response.body());
                if (response.isSuccessful()) {
                    List<Question> questionsList = response.body();
                    if (questionsList != null && !questionsList.isEmpty()) {
                        callback.onQuestionsRetrieved(questionsList);
                    } else {
                        Log.e(LOG_TAG, "Opgehaalde vragenlijst empty");
                        callback.onQuestionsRetrieved(new ArrayList<>());
                    }
                } else {
                    Log.e(LOG_TAG, "Ophalen vragen failed");
                    callback.onQuestionsRetrieved(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "getQuestions " + t.getMessage());
                callback.onQuestionsRetrieved(new ArrayList<>());
            }
        });
    }
    public interface OnQuestionsRetrievedCallback {
        void onQuestionsRetrieved(List<Question> questions);
    }
}




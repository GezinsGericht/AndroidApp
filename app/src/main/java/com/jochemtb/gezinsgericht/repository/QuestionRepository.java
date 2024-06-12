package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.Questions.ApiQuestionService;
import com.jochemtb.gezinsgericht.API.Questions.RetrievedSession;
import com.jochemtb.gezinsgericht.domain.Question;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionRepository {
    private Context context;
    private SharedPreferences sharedPref;
//        private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
        private static final String API_URL = "http://81.206.200.166:3000/api/";
    private static final String LOG_TAG = "QuestionRepository";
    private List<Question> retrievedQuestions;

    public QuestionRepository(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public void getQuestions(List<Integer> questionIds, OnQuestionsRetrievedCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiQuestionService apiQuestionService = retrofit.create(ApiQuestionService.class);

        // Convert List<Integer> to JsonArray
        JsonArray questionIdsJsonArray = new JsonArray();
        for (Integer id : questionIds) {
            questionIdsJsonArray.add(id);
        }

        // Create a JsonObject with the correct JSON structure
        JsonObject requestBody = new JsonObject();
        requestBody.add("questionIds", questionIdsJsonArray);

        // Convert JsonObject to string for logging
        String requestBodyString = new Gson().toJson(requestBody);
        Log.e(LOG_TAG, "Request Body: " + requestBodyString);

        // Make the API call with the updated request body
        apiQuestionService.getQuestions(requestBody).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    List<Question> questionsList = response.body();
                    if (questionsList != null && !questionsList.isEmpty()) {
                        callback.onQuestionsRetrieved(questionsList);
                    } else {
                        Log.e(LOG_TAG, "Opgehaalde vragenlijst empty");
                        callback.onQuestionsFailed("Opgehaalde vragenlijst empty");  // Return an empty list if null or empty
                    }
                } else {
                    Log.e(LOG_TAG, "Ophalen vragen failed: " + response.message());
                    callback.onQuestionsFailed("Ophalen vragen failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(LOG_TAG, "getQuestions failed: " + t.getMessage());
                callback.onQuestionsFailed("getQuestions failed: " + t.getMessage());  // Return an empty list on failure
            }
        });
    }

    public void getSessionQuiz(OnSessionQuizRetrievedCallback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiQuestionService apiQuestionService = retrofit.create(ApiQuestionService.class);
        apiQuestionService.getSessionQuiz().enqueue(new Callback<List<RetrievedSession>>() {
            @Override
            public void onResponse(Call<List<RetrievedSession>> call, Response<List<RetrievedSession>> response) {
                Log.d(LOG_TAG, "Results response: " + response.body());
                Log.d(LOG_TAG, "Results response: " + response);
                Log.d(LOG_TAG, "Results response body: " + response.body());
                Log.d(LOG_TAG, "Results response code: " + response.code());
                Log.d(LOG_TAG, "Results response message: " + response.message());
                Log.d(LOG_TAG, "Results response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<RetrievedSession> responseList = response.body();
                    if (responseList != null && !responseList.isEmpty()) {
                        RetrievedSession responseSession = responseList.get(0);
                        callback.OnSessionQuizRetrieved(responseSession);
                    } else {
                        Log.e(LOG_TAG, "Opgehaalde SessionQuiz empty");
                        callback.OnSessionQuizFailed("Opgehaalde SessionQuiz empty");  // Return an empty list if null or empty
                    }
                } else {
                    Log.e(LOG_TAG, "Ophalen SessionQuiz failed: " + response.message());
                    callback.OnSessionQuizFailed("Ophalen SessionQuiz failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RetrievedSession>> call, Throwable t) {
                Log.e(LOG_TAG, "getSessionQuiz failed: " + t.getMessage());
                callback.OnSessionQuizFailed("getSessionQuiz failed: " + t.getMessage());  // Return an empty list on failure
            }
        });

    }

    public interface OnQuestionsRetrievedCallback {
        void onQuestionsRetrieved(List<Question> questions);
        void onQuestionsFailed(String errorMessage);
    }

    public interface OnSessionQuizRetrievedCallback{
        void OnSessionQuizRetrieved(RetrievedSession retrievedSession);
        void OnSessionQuizFailed(String errorMessage);
    }
}

 class QuestionIdsWrapper {
    private int[] questionIds;

    public QuestionIdsWrapper(int[] questionIds) {
        this.questionIds = questionIds;
    }

    public int[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(int[] questionIds) {
        this.questionIds = questionIds;
    }
}




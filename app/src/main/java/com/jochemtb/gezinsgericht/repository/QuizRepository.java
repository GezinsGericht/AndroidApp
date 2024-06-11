package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jochemtb.gezinsgericht.API.ApiQuizService;
import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.Login.BaseResponse;
import com.jochemtb.gezinsgericht.API.Questions.ApiQuestionService;
import com.jochemtb.gezinsgericht.domain.Question;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class QuizRepository {
        private Context context;
        private SharedPreferences sharedPref;
        private static final String API_URL = "http://81.206.200.166:3000/api/";
        private static final String LOG_TAG = "QuizRepository";


       private List<Integer> questionIds;
         private List<Integer> questionIdsResult;

        public QuizRepository(Context context) {
            this.context = context;
            sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        }

        public void submitQuizAnswers(List<Integer> questionIds, List<Integer> questionIdsResult, OnQuizSubmitedCallback callback) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiQuizService apiQuizService = retrofit.create(ApiQuizService.class);

            // Convert List<Integer> to JsonArray
            JsonArray questionIdsJsonArray = new JsonArray();
            for (Integer id : questionIds) {
                questionIdsJsonArray.add(id);
            }

            // Convert List<Integer> to JsonArray
            JsonArray questionAnswerValues = new JsonArray();
            for (Integer id : questionIdsResult) {
                questionAnswerValues.add(id);
            }

            // Create a JsonObject with Questionid
            JsonObject requestBody = new JsonObject();
            requestBody.add("QuestionId", questionIdsJsonArray);

            // Create a JsonObject with AnswerValue
            requestBody.add("AnswerValue", questionAnswerValues);

            // Convert JsonObject to string for logging
            String requestBodyString = new Gson().toJson(requestBody);
            Log.e(LOG_TAG, "Request Body: " + requestBodyString);

            // Make the API call with the updated request body
            apiQuizService.submitQuizAnsersValues(requestBody).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Log.d(LOG_TAG, "Results response: " + response.body());
                    Log.d(LOG_TAG, "Results response: " + response);
                    Log.d(LOG_TAG, "Results response body: " + response.body());
                    Log.d(LOG_TAG, "Results response code: " + response.code());
                    Log.d(LOG_TAG, "Results response message: " + response.message());
                    Log.d(LOG_TAG, "Results response error body: " + response.errorBody());
                    if (response.isSuccessful()) {
                        Log.i(LOG_TAG, "Quiz submitted successfully");
                        callback.onQuizSucces("Quiz submitted successfully");
                        }
                     else {
                        Log.e(LOG_TAG, "QuizSubmit not succesfull: " + response.message());
                        callback.onQuizError("QuizSubmit not succesfull: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.e(LOG_TAG, "onFailure: " + t.getMessage());
                    callback.onQuizError("onFailure: " + t.getMessage());
                }
            });
        }

        public interface OnQuizSubmitedCallback {
            void onQuizSucces(String message);
            void onQuizError(String errorMessage);
        }
    }

    class QuizWrapper {
        private int[] questionIds;
        private int[] answerIds;

        public QuizWrapper(int[] questionIds, int[] answerIds) {
            this.questionIds = questionIds;
            this.answerIds = answerIds;
        }

        public int[] getQuestionIds() {
            return questionIds;
        }

        public void setQuestionIds(int[] questionIds) {
            this.questionIds = questionIds;
        }

        public int[] getAnswerIds() {
            return answerIds;
        }

        public void setAnswerIds(int[] answerIds) {
            this.answerIds = answerIds;
        }
    }






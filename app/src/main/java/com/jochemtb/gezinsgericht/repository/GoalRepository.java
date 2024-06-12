package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.Goal.GoalResponse;
import com.jochemtb.gezinsgericht.dao.GoalDao;
import com.jochemtb.gezinsgericht.API.Goal.GoalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoalRepository {

//    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String API_URL = "http://81.206.200.166:3000/api/";
    private static final String LOG_TAG = "GoalRepository";
    private static GoalDao goalDao;
    private Context context;
    private SharedPreferences sharedPref;

    public GoalRepository(Context context){
        this.context = context;
        this.goalDao = new GoalDao();
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public static GoalDao getDao() {
        return goalDao;
    }

    public void getGoal(GoalCallback callback, Intent intent) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        int mSessionId = intent.getIntExtra("session", 0);

        GoalService apiGoalService = retrofit.create(GoalService.class);

        // Log the values of JWT token and session ID for debugging
        Log.d(LOG_TAG, "Session ID: " + mSessionId);

        // Log the request details
        Log.d(LOG_TAG, "Sending request with Session ID: " + mSessionId);

        apiGoalService.getGoalAnswer(String.valueOf(mSessionId)).enqueue(new Callback<List<GoalResponse>>() {
            @Override
            public void onResponse(Call<List<GoalResponse>> call, Response<List<GoalResponse>> response) {
                Log.d(LOG_TAG, "Goal response: " + response);
                Log.d(LOG_TAG, "Goal response body: " + response.body());
                Log.d(LOG_TAG, "Goal response code: " + response.code());
                Log.d(LOG_TAG, "Goal response message: " + response.message());
                Log.d(LOG_TAG, "Goal response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<GoalResponse> goalItems = response.body();
                    if (goalItems != null && !goalItems.isEmpty()) {
                        goalDao.setGoalList(new ArrayList<>(goalItems));
                        callback.onGoalFetched("API CALL SUCCESS GOAL");
                    } else {
                        callback.onGoalError("API CALL RESPONSE NOT CORRECT");
                    }
                } else {
                    callback.onGoalError("API CALL FAILED GOAL") ;
                }
            }

            @Override
            public void onFailure(Call<List<GoalResponse>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                callback.onGoalError("API CALL FAILED GOAL, error: " + t.getMessage());
            }
        });
    }

    public interface GoalCallback {
        void onGoalFetched(String message);
        void onGoalError(String errorMessage);
    }
}

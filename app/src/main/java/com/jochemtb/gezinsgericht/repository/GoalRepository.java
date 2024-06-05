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

    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String LOG_TAG = "GoalRepository";
    private GoalDao goalDao;
    private Context context;
    private SharedPreferences sharedPref;

    public GoalRepository(Context context){
        this.context = context;
        this.goalDao = new GoalDao();
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public GoalDao getDao() {
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


        String sessionId = intent.getStringExtra("sessionId");

        GoalService apiHistoryService = retrofit.create(GoalService.class);

        // Log the values of JWT token and session ID for debugging
        Log.d(LOG_TAG, "Session ID: " + sessionId);

        // Log the request details
        Log.d(LOG_TAG, "Sending request with Session ID: " + sessionId);

        apiHistoryService.getGoalAnswer(sessionId).enqueue(new Callback<List<GoalResponse>>() {
            @Override
            public void onResponse(Call<List<GoalResponse>> call, Response<List<GoalResponse>> response) {
                Log.d(LOG_TAG, "History response: " + response);
                Log.d(LOG_TAG, "History response body: " + response.body());
                Log.d(LOG_TAG, "History response code: " + response.code());
                Log.d(LOG_TAG, "History response message: " + response.message());
                Log.d(LOG_TAG, "History response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<GoalResponse> historyItems = response.body();
                    if (historyItems != null && !historyItems.isEmpty()) {
                        goalDao.setGoalList(new ArrayList<>(historyItems));
                        Toast.makeText(context, "API CALL SUCCESS HISTORY", Toast.LENGTH_LONG).show();
                        callback.onGoalFetched();
                    } else {
                        Toast.makeText(context, "API CALL RESPONSE NOT CORRECT", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "API CALL FAILED HISTORY", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GoalResponse>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "API CALL FAILED HISTORY, error: " + t.getMessage());
                Toast.makeText(context, "API CALL FAILED HISTORY, error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface GoalCallback {
        void onGoalFetched();
    }
}

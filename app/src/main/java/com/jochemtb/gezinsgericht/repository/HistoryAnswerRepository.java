package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;
import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerService;
import com.jochemtb.gezinsgericht.API.URL;
import com.jochemtb.gezinsgericht.dao.HistoryAnswerDao;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryAnswerRepository {

    private HistoryAnswerDao historyAnswerDao;
    private Context context;
    private SharedPreferences sharedPref;
    private static String API_URL;
    private static final String LOG_TAG = "HistoryAnswerRepository";

    public HistoryAnswerRepository(Context context) {
        URL url = new URL();
        API_URL = url.getApiUrl();

        this.context = context;
        this.historyAnswerDao = new HistoryAnswerDao();
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public HistoryAnswerDao getDao() {
        return historyAnswerDao;
    }

    public void getHistory(HistoryAnswersCallback callback, Intent intent) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        int mSessionId = intent.getIntExtra("session", 0);

//          Storing JWT token and session ID for demonstration purposes
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("session_id", "1"); // Replace with actual session ID for testing
//        editor.apply();

        HistoryAnswerService apiHistoryService = retrofit.create(HistoryAnswerService.class);

//        Get the JWT token and sessionId from SharedPreferences
//        String sessionId = sharedPref.getString("session_id", "");


        // Log the values of JWT token and session ID for debugging
        Log.d(LOG_TAG, "Session ID: " + mSessionId);


        // Log the request details
        Log.d(LOG_TAG, "Sending request with Session ID: " + mSessionId);

        apiHistoryService.getHistoryAnswer(String.valueOf(mSessionId)).enqueue(new Callback<List<HistoryAnswerResponse>>() {
            @Override
            public void onResponse(Call<List<HistoryAnswerResponse>> call, Response<List<HistoryAnswerResponse>> response) {
                Log.d(LOG_TAG, "History response: " + response);
                Log.d(LOG_TAG, "History response body: " + response.body());
                Log.d(LOG_TAG, "History response code: " + response.code());
                Log.d(LOG_TAG, "History response message: " + response.message());
                Log.d(LOG_TAG, "History response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<HistoryAnswerResponse> historyItems = response.body();
                    if (historyItems != null && !historyItems.isEmpty()) {
                        historyAnswerDao.setHistoryAnswerList(new ArrayList<>(historyItems));
                        callback.onHistoryAnswersFetched("API CALL SUCCESS HISTORY");
                    } else {
                        callback.onHistoryAnswersError("API CALL RESPONSE NOT CORRECT");
                    }
                } else {
                    callback.onHistoryAnswersError("API CALL FAILED HISTORY");
                }
            }

            @Override
            public void onFailure(Call<List<HistoryAnswerResponse>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                callback.onHistoryAnswersError("API CALL FAILED HISTORY, error: " + t.getMessage());
            }
        });
    }



    public interface HistoryAnswersCallback {
        void onHistoryAnswersFetched(String message);
        void onHistoryAnswersError(String errorMessage);
    }

}

package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.API.ApiHistoryService;
import com.jochemtb.gezinsgericht.API.History.HistoryListRequest;
import com.jochemtb.gezinsgericht.API.History.HistoryListResponse;
import com.jochemtb.gezinsgericht.dao.HistoryDao;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.domain.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

    private HistoryDao historyDao;
    private Context context;
    private SharedPreferences sharedPref;
    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String LOG_TAG = "HistoryRepository";

    public HistoryRepository(Context context) {
        this.context = context;
        this.historyDao = new HistoryDao();
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public HistoryDao getDao(){
        return historyDao;
    }


    public void getHistory(HistoryCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHistoryService apiHistoryService = retrofit.create(ApiHistoryService.class);

        apiHistoryService.getHistory().enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                Log.d(LOG_TAG, "History response: " + response.body());
                Log.d(LOG_TAG, "History response: " + response);
                Log.d(LOG_TAG, "History response body: " + response.body());
                Log.d(LOG_TAG, "History response code: " + response.code());
                Log.d(LOG_TAG, "History response message: " + response.message());
                Log.d(LOG_TAG, "History response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<HistoryItem> historyItems = response.body();
                    if (historyItems != null && !historyItems.isEmpty()) {
                        historyDao.setHistoryList(new ArrayList<>(historyItems));

                        Toast.makeText(context, "API CALL SUCCESS HISTORY", Toast.LENGTH_LONG).show();
                        callback.onHistoryFetched();
                    } else {
                        Toast.makeText(context, "API CALL RESPONSE NOT CORRECT", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "API CALL FAILED HISTORY", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "API CALL FAILED HISTORY, error: " + t.getMessage());
                Toast.makeText(context, "API CALL FAILED HISTORY, error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface HistoryCallback {
        void onHistoryFetched();
    }

}

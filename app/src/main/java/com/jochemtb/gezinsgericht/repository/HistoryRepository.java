package com.jochemtb.gezinsgericht.repository;

import android.content.Context;

import android.content.SharedPreferences;
import android.util.Log;

import com.jochemtb.gezinsgericht.API.ApiHistoryService;
import com.jochemtb.gezinsgericht.API.URL;
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
    private static String API_URL;
    private static final String LOG_TAG = "HistoryRepository";

    public HistoryRepository(Context context) {
        URL url = new URL();
        API_URL = url.getApiUrl();

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
                        callback.onHistoryFetched("API CALL SUCCESS HISTORY");
                    } else {
                        callback.onHistoryError("API CALL RESPONSE NOT CORRECT");
                    }
                } else {
                    callback.onHistoryError("API CALL FAILED HISTORY");
                }
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                callback.onHistoryError("API CALL FAILED HISTORY, error: " + t.getMessage());
            }
        });
    }

    public interface HistoryCallback {
        void onHistoryFetched(String message);
        void onHistoryError(String errorMessage);
    }

}

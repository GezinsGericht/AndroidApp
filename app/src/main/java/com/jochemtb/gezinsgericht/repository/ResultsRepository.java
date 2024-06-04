package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.ApiResultsService;
import com.jochemtb.gezinsgericht.dao.ResultsDao;
import com.jochemtb.gezinsgericht.domain.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsRepository {
    private static final String LOG_TAG = "ResultsRepository";
    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private SharedPreferences sharedPref;
    private ResultsDao resultsDao;
    private Context context;

    public ResultsRepository(Context context) {
        this.context = context;
        this.resultsDao = new ResultsDao();
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public ResultsDao getDao() {
        return resultsDao;
    }

    public void getResults(ResultsCallback callback, String sessionId) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiResultsService apiResultsService = retrofit.create(ApiResultsService.class);

        apiResultsService.getResults(sessionId).enqueue(new Callback<List<ResultsItem>>() {
            @Override
            public void onResponse(Call<List<ResultsItem>> call, Response<List<ResultsItem>> response) {
                Log.d(LOG_TAG, "Results response: " + response.body());
                Log.d(LOG_TAG, "Results response: " + response);
                Log.d(LOG_TAG, "Results response body: " + response.body());
                Log.d(LOG_TAG, "Results response code: " + response.code());
                Log.d(LOG_TAG, "Results response message: " + response.message());
                Log.d(LOG_TAG, "Results response error body: " + response.errorBody());
                if (response.isSuccessful()) {
                    List<ResultsItem> results = response.body();
                    if (results != null) {
                        Log.d(LOG_TAG, "Username: " + results.get(1).getUserName());
                        resultsDao.setResults(new ArrayList<>(results));
                        callback.onResultsFetched(results);
                        Toast.makeText(context, "API CALL SUCCESS RESULTS", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "API CALL RESPONSE NOT CORRECT", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "API CALL FAILED RESULTS", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResultsItem>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "API CALL FAILED RESULTS, error: " + t.getMessage());
                Toast.makeText(context, "API CALL FAILED RESULTS, error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface ResultsCallback {
        void onResultsFetched(List<ResultsItem> results);
    }

}

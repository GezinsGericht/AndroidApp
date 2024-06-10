package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.LineChart.ApiLineChartService;
import com.jochemtb.gezinsgericht.dao.LineChartDao;
import com.jochemtb.gezinsgericht.domain.HistoryItem;
import com.jochemtb.gezinsgericht.domain.LineChartEntry;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LineChartRepository {

    private LineChartDao lineChartDao;
    private Context context;
    private SharedPreferences sharedPref;
    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String LOG_TAG = "LineChartRepository";

    public LineChartRepository(Context context) {
        lineChartDao = new LineChartDao();
        this.context = context;
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public LineChartDao getDao(){
        return lineChartDao;
    }
    public void getLineChart(final LineChartCallback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLineChartService apiLinechartService = retrofit.create(ApiLineChartService.class);
        apiLinechartService.getLineChart().enqueue(new Callback<List<LineChartEntry>>() {
            @Override
            public void onResponse(Call<List<LineChartEntry>> call, Response<List<LineChartEntry>> response) {
                if (response.isSuccessful()) {
                    List<LineChartEntry> lineChartEntries = response.body();
                    if (lineChartEntries != null && !lineChartEntries.isEmpty()) {
                        lineChartDao.setLineChartlist(new ArrayList<>(lineChartEntries));
                        callback.onLineChartFetched(lineChartEntries);  // Pass the data to the callback
                    } else {
                        callback.onLineChartError("No data received");
                    }
                } else {
                    callback.onLineChartError("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<List<LineChartEntry>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                callback.onLineChartError("API call failed: " + t.getMessage());
            }
        });
    }

    public interface LineChartCallback{
        void onLineChartFetched(List<LineChartEntry> entries);
        void onLineChartError(String errorMessage);
    }

}
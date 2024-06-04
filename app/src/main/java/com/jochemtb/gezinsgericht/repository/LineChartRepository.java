package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.Linechart.ApiLinechartService;
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
    public void getLineChart(LineChartCallback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLinechartService apiLinechartService = retrofit.create(ApiLinechartService.class);
        apiLinechartService.getLineChart().enqueue(new Callback<List<LineChartEntry>>() {
            @Override
            public void onResponse(Call<List<LineChartEntry>> call, Response<List<LineChartEntry>> response) {
                Log.d(LOG_TAG,"LineChart Response" + response.body());
                Log.d(LOG_TAG,"LineChart Response" + response);
                Log.d(LOG_TAG,"LineChart Response Body" + response.body());
                Log.d(LOG_TAG,"LineChart Response Code" + response.code());
                Log.d(LOG_TAG,"LineChart Response Message" + response.message());
                Log.d(LOG_TAG,"LineChart Response Error Body" + response.errorBody());
                if (response.isSuccessful()) {
                    List<LineChartEntry> lineChartEntries = response.body();
                    if (lineChartEntries != null && !lineChartEntries.isEmpty()) {
                        lineChartDao.setLineChartlist(new ArrayList<>(lineChartEntries));

                        Toast.makeText(context, "API CALL SUCCESS LINECHART", Toast.LENGTH_LONG).show();
                        callback.onLineChartFetched();
                    } else {
                        Toast.makeText(context, "API CALL RESPONSE NOT CORRECT", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "API CALL FAILED LINECHART  ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LineChartEntry>> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "API CALL FAILED LINECHART, error: " + t.getMessage());
                Toast.makeText(context, "API CALL FAILED LINECHART, error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public interface LineChartCallback{
        void onLineChartFetched();
    }
}

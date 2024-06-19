package com.jochemtb.gezinsgericht.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jochemtb.gezinsgericht.API.AuthInterceptor;
import com.jochemtb.gezinsgericht.API.Name.NameResponse;
import com.jochemtb.gezinsgericht.API.Name.NameService;
import com.jochemtb.gezinsgericht.API.URL;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NameRepository {

    private static String API_URL;
    private Context context;
    private NameService nameService;

    private static final String LOG_TAG = "NameRepository";

    public NameRepository(Context context) {
        URL url = new URL();
        API_URL = url.getApiUrl();

        this.context = context; // Assign the passed context to the class variable

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context)) // Pass the context to AuthInterceptor
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nameService = retrofit.create(NameService.class);
    }

    public void getName(String userId, final NameCallback callback) {
        Call<NameResponse> call = nameService.getName(userId);
        call.enqueue(new Callback<NameResponse>() {
            @Override
            public void onResponse(@NonNull Call<NameResponse> call, @NonNull retrofit2.Response<NameResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NameResponse nameResponse = response.body();
                    String name = nameResponse.getName();
                    callback.onNameFetched(name);
                } else {
                    callback.onNameError("Failed to fetch name");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NameResponse> call, @NonNull Throwable t) {
                callback.onNameError(t.getMessage());
            }
        });
    }

    public interface NameCallback {
        void onNameFetched(String name);

        void onNameError(String errorMessage);
    }
}

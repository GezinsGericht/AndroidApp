package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.type.DateTime;
import com.jochemtb.gezinsgericht.API.ApiService;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordRequest;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordResponse;
import com.jochemtb.gezinsgericht.API.Login.LoginRequest;
import com.jochemtb.gezinsgericht.API.Login.LoginResponse;
import com.jochemtb.gezinsgericht.GUI.MainActivity;
import com.jochemtb.gezinsgericht.dao.LoginDao;


import java.time.Instant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private LoginDao loginDao;
    private Context context;
    private SharedPreferences sharedPref;
    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String LOG_TAG = "UserRepository";
    private static final String RESET_TOKEN = "resetToken";

    public UserRepository(Context context) {
        this.context = context;
        this.loginDao = new LoginDao(context);
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    public void loginUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(LOG_TAG, "Login response: " + response.body());
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getData() != null) {
                        String token = loginResponse.getData().getToken();
                        loginDao.saveToken(token);
                        Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, MainActivity.class));
                    } else {
                        Toast.makeText(context, "Login failed: " + loginResponse.getError(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, "Login error: " + t.getMessage());
                Toast.makeText(context, "Login error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void forgotPassword(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email);

        apiService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                long now = System.currentTimeMillis() / 1000;
                sharedPref.edit().putLong(RESET_TOKEN, now).apply();
                if (response.isSuccessful()) {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();
                    if (forgotPasswordResponse != null) {
//                        long now = System.currentTimeMillis() / 1000;
//                        sharedPref.edit().putLong(RESET_TOKEN, now).apply();
                        Log.d(LOG_TAG, "Reset token: " + now);
                        Toast.makeText(context, forgotPasswordResponse.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Error: No response message", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Error: Unable to send forgot password request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Log.e(LOG_TAG, "Forgot password error: " + t.getMessage());
                Toast.makeText(context, "Forgot password error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


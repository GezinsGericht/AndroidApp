package com.jochemtb.gezinsgericht.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.jochemtb.gezinsgericht.API.Login.ApiService;
import com.jochemtb.gezinsgericht.API.Login.BaseResponse;
import com.jochemtb.gezinsgericht.API.Login.ChangePasswordRequest;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordRequest;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordResponse;
import com.jochemtb.gezinsgericht.API.Login.LoginRequest;
import com.jochemtb.gezinsgericht.API.Login.LoginResponse;
import com.jochemtb.gezinsgericht.API.Login.TokenRequest;
import com.jochemtb.gezinsgericht.API.Login.TokenResponse;
import com.jochemtb.gezinsgericht.GUI.MainActivity;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.dao.LoginDao;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private LoginDao loginDao;
    private Context context;
    private SharedPreferences sharedPref;
//    private static final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/";
    private static final String API_URL = "http://81.206.200.166:3000/api/";
    private static final String LOG_TAG = "UserRepository";
    private static final String RESET_TOKEN = "resetToken";
    private static final String RESET_EMAIL = "resetEmail";

    private int attemptsLeft;
    private boolean returnBool;

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
                if (response.isSuccessful()) {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();
                    if (forgotPasswordResponse != null) {
                        long now = System.currentTimeMillis() / 1000;
                        sharedPref.edit().putLong(RESET_TOKEN, now).apply();
                        sharedPref.edit().putString(RESET_EMAIL, email).apply();

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


    public void changePassword(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(email, password);

        apiService.changePassword(changePasswordRequest).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(LOG_TAG, "Change password response: " + response.body().getMessage());
                    Toast.makeText(context, R.string.succes_pass_change, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(LOG_TAG, "Forgot password error: " + t.getMessage());
                Toast.makeText(context, "Forgot password error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkPresentToken(String token, int attemptsInput, TokenCheckCallback callback) {
        new TokenCheckTask(token, attemptsInput, callback).execute();
    }

    private static class TokenCheckTask extends AsyncTask<Void, Void, Boolean> {
        private String token;
        private int attemptsInput;
        private TokenCheckCallback callback;

        public TokenCheckTask(String token, int attemptsInput, TokenCheckCallback callback) {
            this.token = token;
            this.attemptsInput = attemptsInput;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            // Perform API call in the background
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            TokenRequest tokenRequest = new TokenRequest(token);

            try {
                Response<TokenResponse> response = apiService.checkPresentToken(tokenRequest).execute();

                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    return tokenResponse != null && tokenResponse.getData() != null;
                } else {
                    // Handle the case where the token check failed
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isValid) {
            // Update UI based on the result
            if (callback != null) {
                callback.onTokenChecked(isValid);
            }
        }
    }


    public interface TokenCheckCallback {
        void onTokenChecked(boolean isValid);
    }
}



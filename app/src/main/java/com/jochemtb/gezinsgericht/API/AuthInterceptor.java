package com.jochemtb.gezinsgericht.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private SharedPreferences sharedPreferences;

    public AuthInterceptor(Context context) {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sharedPreferences.getString("jwtToken", null);
        Request originalRequest = chain.request();


        if (token != null) {
            Log.d("INTERCEPTOR", "Retrieved token: " + token);
        } else {
            Log.w("INTERCEPTOR", "Token is null, proceeding without Authorization header");
        }
        if (token == null) {
            return chain.proceed(originalRequest);
        }

        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token);

        Log.d("INTERCEPTOR", "Adding Authorization header to request: " + token);

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
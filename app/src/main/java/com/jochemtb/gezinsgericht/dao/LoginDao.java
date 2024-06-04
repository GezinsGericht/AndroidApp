package com.jochemtb.gezinsgericht.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginDao {
    private static final String LOG_TAG = "LoginDao";
    private static final String PREFS_NAME = "SharedPrefApp";
    private static final String TOKEN_KEY = "jwtToken";
    private static final String DEVICE_KEY = "deviceKey";
    private SharedPreferences sharedPreferences;

    public LoginDao(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        Log.i(LOG_TAG, "saveToken");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    private void saveDeviceToken(String deviceToken) {
        Log.i(LOG_TAG, "saveDeviceToken");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_KEY, deviceToken);
        editor.apply();
    }

    public void savePassword(String password) {
        Log.i(LOG_TAG, "savePassword");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }
}
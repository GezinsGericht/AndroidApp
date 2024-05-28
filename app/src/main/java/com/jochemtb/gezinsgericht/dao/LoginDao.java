package com.jochemtb.gezinsgericht.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginDao {
    private static final String LOG_TAG = "LoginDao";
    private static final String PREFS_NAME = "SharedPrefApp";
    private static final String TOKEN_KEY = "jwtToken";
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

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }
}

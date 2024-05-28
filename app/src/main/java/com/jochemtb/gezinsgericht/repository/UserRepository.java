package com.jochemtb.gezinsgericht.repository;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jochemtb.gezinsgericht.GUI.MainActivity;
import com.jochemtb.gezinsgericht.dao.LoginDao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserRepository {
    private LoginDao loginDao;
    private Context context;
    private final String API_URL = "https://getlab-gezinsgericht.azurewebsites.net/api/login";
    private final String LOG_TAG = "UserRepository";
    private String mail;
    private String pass;

    public UserRepository(Context context) {
        this.context = context;
        this.loginDao = new LoginDao(context);
    }

    public void loginUser(String email, String password) {
        mail=email;
        pass=password;
        new LoginTask().execute(email, password);
    }

        private class LoginTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                Log.d(LOG_TAG, "Logintask --> doInBackground");

                try {
                    OkHttpClient client = new OkHttpClient();

                    // Prepare JSON body
                    JSONObject jsonInput = new JSONObject();
                    jsonInput.put("email", mail);
                    jsonInput.put("password", pass);
                    String jsonBody = jsonInput.toString();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);

                    Log.d(LOG_TAG, body.toString());
                    // Build the request
                    Request request = new Request.Builder()
                            .url(API_URL)
                            .post(body)
                            .addHeader("accept", "application/json")
                            .build();

                    // Execute the request
                    Response response = client.newCall(request).execute();

                    // Handle the response
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Exception: " + e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Exception: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d(LOG_TAG, "onPostExecute");
                super.onPostExecute(result);
                try {
                    Log.i(LOG_TAG, "Result "+result);
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONObject data = jsonResponse.getJSONObject("data");
                    Log.d(LOG_TAG, "Response: "+jsonResponse.toString());
                    if (data.has("token")) {
                        String token = data.getString("token");
                        loginDao.saveToken(token);
                        Log.d(LOG_TAG, "loginDao.saveToken");
                        Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context, MainActivity.class));
                    } else {
                        Toast.makeText(context, "Login failed: " + jsonResponse.getString("error"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
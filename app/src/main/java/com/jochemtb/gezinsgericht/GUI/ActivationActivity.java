package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jochemtb.gezinsgericht.R;

import com.jochemtb.gezinsgericht.repository.UserRepository;

public class ActivationActivity extends AppCompatActivity {

    Button confirmButton;
    private UserRepository userRepository;
    SharedPreferences sharefPref;
    private static final String RESET_TOKEN = "resetToken";
    private static final String LOG_TAG = "ActivationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(this);
        sharefPref = getSharedPreferences("sharedPref", MODE_PRIVATE);
        Log.d(LOG_TAG, "onCreate: resetToken="+sharefPref.getLong(RESET_TOKEN, 0)+", isFiveMinutesPassed="+isFiveMinutesPassed(sharefPref.getLong(RESET_TOKEN, 0)));
        if(isFiveMinutesPassed(sharefPref.getLong(RESET_TOKEN, 0))){
            Toast.makeText(this, "Activation link has expired, please request a new one.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ActivationActivity.this, LoginActivity.class));
        }
        setContentView(R.layout.activity_activation);
        confirmButton = findViewById(R.id.BT_login_submit);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userRepository.updatePassword()
                startActivity(new Intent(ActivationActivity.this, LoginActivity.class));
            }
        });
    }

    public static boolean isFiveMinutesPassed(long timestampInSeconds) {
        long currentTimeInSeconds = System.currentTimeMillis() / 1000; // Current time in seconds
        long fiveMinutesInSeconds = 5 * 60; // 5 minutes in seconds

        // Check if the difference between current time and the provided timestamp is greater than 5 minutes
        return (currentTimeInSeconds - timestampInSeconds) >= fiveMinutesInSeconds;
    }
}

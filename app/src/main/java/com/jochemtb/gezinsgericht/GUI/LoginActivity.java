package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private UserRepository userRepository;

    Button loginBtn;
    Button passwordForgot;

    EditText emailField;
    EditText passwordField;

    TextView title;
    ProgressBar loadingIcon;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.BT_login_submit); // Corrected ID
        passwordForgot = findViewById(R.id.BT_login_forgotPassword);
        emailField = findViewById(R.id.ET_login_email);
        passwordField = findViewById(R.id.ET_login_password); // Corrected ID
        title = findViewById(R.id.TV_login_title);
        loadingIcon = findViewById(R.id.PB_login_loadingLogo);

        userRepository = new UserRepository(this);

        setLoadingScreen(true);

        // Check if user is already logged in
        userRepository.checkPresentToken(sharedPref.getString("jwtToken", null), 3, new UserRepository.TokenCheckCallback() {
            @Override
            public void onTokenChecked(boolean isValid) {
                if (isValid) {
                    // Token is valid, proceed to MainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Finish LoginActivity to prevent going back
                } else {
                    setLoadingScreen(false);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMandatoryFields(emailField, passwordField)) {
                    userRepository.loginUser(
                            emailField.getText().toString(),
                            passwordField.getText().toString()
                    );
                } else {
                    Toast.makeText(LoginActivity.this, "Een of meerdere verplichte velden niet ingevuld", Toast.LENGTH_LONG).show();
                }
            }
        });

        passwordForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMandatoryFields(emailField)) {
                    startActivity(new Intent(LoginActivity.this, ActivationActivity.class));
                    userRepository.forgotPassword(emailField.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "Vul een email address in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setLoadingScreen(boolean loading) {
        if (loading) {
            loadingIcon.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);
            passwordForgot.setVisibility(View.INVISIBLE);
            emailField.setVisibility(View.INVISIBLE);
            passwordField.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
        } else {
            loadingIcon.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            passwordForgot.setVisibility(View.VISIBLE);
            emailField.setVisibility(View.VISIBLE);
            passwordField.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkMandatoryFields(EditText field1) {
        String text = field1.getText().toString();
        return text != null && !text.isEmpty();
    }

    private boolean checkMandatoryFields(EditText field1, EditText field2) {
        String text1 = field1.getText().toString();
        String text2 = field2.getText().toString();
        return (text1 != null && !text1.isEmpty()) && (text2 != null && !text2.isEmpty());
    }
}

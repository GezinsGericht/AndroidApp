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
        loginBtn = findViewById(R.id.BT_activation_submit);
        passwordForgot = findViewById(R.id.BT_login_forgotPassword);
        emailField = findViewById(R.id.ET_login_email);
        passwordField = findViewById(R.id.ET_login_password);
        title = findViewById(R.id.TV_login_title);

        loadingIcon = findViewById(R.id.PB_login_loadingLogo);

        userRepository = new UserRepository(this);


        setLoadingScreen(true);

        //Check if user is already logged in
        userRepository.checkPresentToken(sharedPref.getString("jwtToken", null), 3, new UserRepository.TokenCheckCallback() {
            @Override
            public void onTokenChecked(boolean isValid) {

                if (isValid) {
                    // Token is valid, proceed to MainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Finish LoginActivity to prevent going back
                } else{
                    setLoadingScreen(false);
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMandatoryFields(emailField, passwordField)){
                    userRepository.loginUser(
                            emailField.getText().toString(),
                            passwordField.getText().toString()
                    );
                } else {
                    //TODO weghalen omzeiling.
//                    sharedPref.edit().putString("jwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjQsIkZhbWlseUlkIjoxLCJSb2xlIjoiQ0hJTEQiLCJpYXQiOjE3MTcxNTMwNTEsImV4cCI6MTcxNzc1Nzg1MX0.5HknxVogBRLt_RQnnh4NHLe_5L0aX2RA9l3DcdQ9Hi0").apply();
                    sharedPref.edit().putString("jwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOjEsIkZhbWlseUlkIjoxLCJSb2xlIjoiQ0hJTEQiLCJpYXQiOjE3MTcxNTMwNTEsImV4cCI6MTcxNzc1Nzg1MX0.YZs92oUrNxOuR9vX7WOs9biMR9CdHzccMPF3wTRkAKU").apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    Toast.makeText(getBaseContext(), "Een of meerdere verplichte velden niet ingevuld", Toast.LENGTH_LONG).show();
                }
            }
        });

        passwordForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMandatoryFields(emailField)){
                    userRepository.forgotPassword(emailField.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "Vul een email address in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setLoadingScreen(boolean loading){
        if(loading){
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


    private boolean checkMandatoryFields(EditText field1){
        String text = field1.getText().toString();
        if( text == null || text.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkMandatoryFields(EditText field1, EditText field2){
        String text1 = field1.getText().toString();
        String text2 = field2.getText().toString();
        if( (text1 == null || text1.isEmpty()) || (text2 == null || text2.isEmpty())){
            return false;
        } else {
            return true;
        }
    }
}
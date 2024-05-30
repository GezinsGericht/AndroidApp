package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.BT_activation_submit);
        passwordForgot = findViewById(R.id.BT_login_forgotPassword);
        emailField = findViewById(R.id.ET_login_email);
        passwordField = findViewById(R.id.ET_login_password);

        userRepository = new UserRepository(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMandatoryFields(emailField, passwordField)){
                    userRepository.loginUser(
                            emailField.getText().toString(),
                            passwordField.getText().toString()
                    );
                } else {
                    Toast.makeText(getBaseContext(), "Een of meerdere verplichte velden niet ingevuld", Toast.LENGTH_LONG).show();
                }
            }
        });
        passwordForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMandatoryFields(emailField)){
                    startActivity(new Intent(LoginActivity.this, ActivationActivity.class));
                } else {
                    Toast.makeText(getBaseContext(), "Vul een email address in", Toast.LENGTH_LONG).show();
                }

            }
        });

        passwordForgot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(checkMandatoryFields(emailField)) {
                    userRepository.forgotPassword(emailField.getText().toString());
                    return true;
                }
                return false;
            }
        });
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
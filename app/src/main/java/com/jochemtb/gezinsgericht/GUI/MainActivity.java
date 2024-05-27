package com.jochemtb.gezinsgericht.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jochemtb.gezinsgericht.R;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTv;
    private CheckBox checkbox_1, checkbox_2, checkbox_3, checkbox_4, checkbox_5, checkbox_6, checkbox_7;
    private Button navbar_1,navbar_2, navbar_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewComponents();
        navToSession();

        usernameTv.setText("Sietse 't Hooft");
    }

    private void initViewComponents() {
        usernameTv = findViewById(R.id.TV_homepage_username);

        checkbox_1 = findViewById(R.id.CB_homepage_1);
        checkbox_2 = findViewById(R.id.CB_homepage_2);
        checkbox_3 = findViewById(R.id.CB_homepage_3);
        checkbox_4 = findViewById(R.id.CB_homepage_4);
        checkbox_5 = findViewById(R.id.CB_homepage_5);
        checkbox_6 = findViewById(R.id.CB_homepage_6);
        checkbox_7 = findViewById(R.id.CB_homepage_7);

        navbar_1 = findViewById(R.id.BTN_navbar1);
        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);

    }

    private void navToSession(){
        navbar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });
    }
}
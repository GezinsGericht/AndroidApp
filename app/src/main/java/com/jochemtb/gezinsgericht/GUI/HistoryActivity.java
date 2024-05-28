package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import com.jochemtb.gezinsgericht.adapters.HistoryListAdapter;

public class HistoryActivity extends AppCompatActivity {

    private final String LOG_TAG = "HistoryActivity";
    RecyclerView rvHistoryList;
    private HistoryListAdapter mAdapter;
    private Button navbar_1, navbar_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViewComponents();


        //Navigeer naar de main activity
        navbar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            }
        });

        //Navigeer naar de quiz activity
        navbar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, QuizActivity.class));
            }
        });
    }

    //Method om de view componenten te initialiseren
    private void initViewComponents() {
        navbar_1 = findViewById(R.id.BTN_navbar1);
        navbar_3 = findViewById(R.id.BTN_navbar3);

        rvHistoryList = findViewById(R.id.RV_history_list);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryListAdapter(this);
        rvHistoryList.setAdapter(mAdapter);
        Log.i(LOG_TAG, "initViewComponents");
    }

}


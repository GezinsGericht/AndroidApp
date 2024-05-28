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
import com.jochemtb.gezinsgericht.domain.Professional;
import com.jochemtb.gezinsgericht.domain.Session;

import java.time.LocalDate;
import java.util.ArrayList;


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

        LocalDate today = LocalDate.now();

        //TEMP Profs aanmaken
        Professional Prof1 = new Professional(1, "John Doe", "JohnDoe@gmail.com");
        Session Session1 = new Session(today, Prof1);
        Session Session2 = new Session(LocalDate.of(2024, 5, 16), Prof1);
        Session Session3 = new Session(LocalDate.of(2024, 5, 25), Prof1);

        ArrayList<Session> Sessions = new ArrayList();
        Sessions.add(Session1);
        Sessions.add(Session2);
        Sessions.add(Session3);

        rvHistoryList = findViewById(R.id.RV_history_list);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryListAdapter(this, Sessions);
        //TEMP Set onClickListener
//        mAdapter.setOnItemClickListener(new HistoryListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                startActivity(new Intent(HistoryActivity.this, ResultsActivity.class));
//                Log.i(LOG_TAG, "onItemClick: " + position);
//            }
//        });
        rvHistoryList.setAdapter(mAdapter);


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

        Log.i(LOG_TAG, "initViewComponents");
    }

}


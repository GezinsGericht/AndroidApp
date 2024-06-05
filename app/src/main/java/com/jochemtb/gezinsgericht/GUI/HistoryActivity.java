package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import com.jochemtb.gezinsgericht.dao.HistoryDao;
import com.jochemtb.gezinsgericht.repository.HistoryRepository;
import com.jochemtb.gezinsgericht.adapters.HistoryListAdapter;

import com.jochemtb.gezinsgericht.domain.FamilyResults;
import com.jochemtb.gezinsgericht.domain.Habitat;
import com.jochemtb.gezinsgericht.domain.Professional;
import com.jochemtb.gezinsgericht.domain.Score;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HistoryActivity extends AppCompatActivity implements HistoryRepository.HistoryCallback {

    private final String LOG_TAG = "HistoryActivity";
    RecyclerView rvHistoryList;
    private HistoryListAdapter mAdapter;
    private Button navbar_1, navbar_3, navbar_2;
    private HistoryRepository historyRepository;
    private ProgressBar loadingScreen;

    private HistoryDao historyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViewComponents();
        setLoadingScreen(true);

//        Session session = new Session(HistoryRepository());
        historyRepository = new HistoryRepository(this);
        historyRepository.getHistory(this);
        this.historyDao = historyRepository.getDao();

        rvHistoryList = findViewById(R.id.RV_history_list);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryListAdapter(this, historyDao.getHistoryList());
        rvHistoryList.setAdapter(mAdapter);

        LocalDate today = LocalDate.now();

        //TEMP DATA
        Professional Prof1 = new Professional(1, "John Doe", "JohnDoe@gmail.com");

        User user1 = new User(1, "PARENT", "Hans de Vliering", "h.devliereing@gmail.com", "Vliering1");
        User user2 = new User(2, "CHILD", "Harm de Vliering", "harm.devliereing@gmail.com", "Vliering1");
        User user3 = new User(3, "PARENT", "Chantal de Vliering", "ch.devliereing@gmail.com", "Vliering1");

        HashMap<Integer, Integer> scores1 = new HashMap<>();
        scores1.put(1, 15);
        scores1.put(2, 8);
        scores1.put(3, 2);
        scores1.put(4, 9);
        scores1.put(5, 5);
        scores1.put(6, 2);
        scores1.put(7, 11);

        HashMap<Integer, Integer> scores2 = new HashMap<>();
        scores2.put(1, 5);
        scores2.put(2, 13);
        scores2.put(3, 4);
        scores2.put(4, 12);
        scores2.put(5, 10);
        scores2.put(6, 3);
        scores2.put(7, 5);

        HashMap<Integer, Integer> scores3 = new HashMap<>();
        scores3.put(1, 12);
        scores3.put(2, 2);
        scores3.put(3, 14);
        scores3.put(4, 5);
        scores3.put(5, 1);
        scores3.put(6, 12);
        scores3.put(7, 5);

        Score score1 = new Score(user1,scores1);
        Score score2 = new Score(user2, scores2);
        Score score3 = new Score(user3, scores3);

        HashMap<User, Score> familyScores = new HashMap<>();
        familyScores.put(user1, score1);
        familyScores.put(user2, score2);
        familyScores.put(user3, score3);

        FamilyResults familyResults = new FamilyResults(familyScores);

        Session Session1 = new Session(today, Prof1, familyResults);
        Session Session2 = new Session(LocalDate.of(2024, 5, 16), Prof1, familyResults);
        Session Session3 = new Session(LocalDate.of(2024, 5, 25), Prof1, familyResults);

        ArrayList<Session> Sessions = new ArrayList();
        Sessions.add(Session1);
        Sessions.add(Session2);
        Sessions.add(Session3);
        //TOT HIER

        rvHistoryList = findViewById(R.id.RV_history_list);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryListAdapter(this, Sessions);
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
        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        loadingScreen = findViewById(R.id.PB_History_loading);

        Log.i(LOG_TAG, "initViewComponents");
    }

    private void setLoadingScreen(Boolean bool){
        if(bool){
            navbar_1.setVisibility(View.INVISIBLE);
            navbar_3.setVisibility(View.INVISIBLE);
            navbar_2.setVisibility(View.INVISIBLE);
            loadingScreen.setVisibility(View.VISIBLE);
        } else{
            navbar_1.setVisibility(View.VISIBLE);
            navbar_3.setVisibility(View.VISIBLE);
            navbar_2.setVisibility(View.VISIBLE);
            loadingScreen.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHistoryFetched() {
        setLoadingScreen(false);
        mAdapter.setHistoryList(historyDao.getHistoryList());
        mAdapter.notifyDataSetChanged();
        Log.i(LOG_TAG, "History data updated in adapter");
    }

//    //TEMP DATA
//    Professional Prof1 = new Professional(1, "John Doe", "JohnDoe@gmail.com");
//
//    User user1 = new User(1, "PARENT", "Hans de Vliering", "h.devliereing@gmail.com", "Vliering1");
//    User user2 = new User(2, "CHILD", "Harm de Vliering", "harm.devliereing@gmail.com", "Vliering1");
//    User user3 = new User(3, "PARENT", "Chantal de Vliering", "ch.devliereing@gmail.com", "Vliering1");
//
//    HashMap<Integer, Integer> scores1 = new HashMap<>();
//        scores1.put(1, 15);
//        scores1.put(2, 8);
//        scores1.put(3, 2);
//        scores1.put(4, 9);
//        scores1.put(5, 5);
//        scores1.put(6, 2);
//        scores1.put(7, 11);
//
//    HashMap<Integer, Integer> scores2 = new HashMap<>();
//        scores2.put(1, 5);
//        scores2.put(2, 13);
//        scores2.put(3, 4);
//        scores2.put(4, 12);
//        scores2.put(5, 10);
//        scores2.put(6, 3);
//        scores2.put(7, 5);
//
//    HashMap<Integer, Integer> scores3 = new HashMap<>();
//        scores3.put(1, 12);
//        scores3.put(2, 2);
//        scores3.put(3, 14);
//        scores3.put(4, 5);
//        scores3.put(5, 1);
//        scores3.put(6, 12);
//        scores3.put(7, 5);
//
//    Score score1 = new Score(user1,scores1);
//    Score score2 = new Score(user2, scores2);
//    Score score3 = new Score(user3, scores3);
//
//    HashMap<User, Score> familyScores = new HashMap<>();
//        familyScores.put(user1, score1);
//        familyScores.put(user2, score2);
//        familyScores.put(user3, score3);
//
//    FamilyResults familyResults = new FamilyResults(familyScores);
//
//    Session Session1 = new Session(today, Prof1, familyResults);
//    Session Session2 = new Session(LocalDate.of(2024, 5, 16), Prof1, familyResults);
//    Session Session3 = new Session(LocalDate.of(2024, 5, 25), Prof1, familyResults);
//
//    ArrayList<Session> Sessions = new ArrayList();
//        Sessions.add(Session1);
//        Sessions.add(Session2);
//        Sessions.add(Session3);
//    //TOT HIER

}


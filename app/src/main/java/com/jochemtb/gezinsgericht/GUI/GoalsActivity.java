package com.jochemtb.gezinsgericht.GUI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.jochemtb.gezinsgericht.API.Goal.GoalResponse;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.GoalListAdapter;
import com.jochemtb.gezinsgericht.domain.Goal;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.repository.GoalRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoalsActivity extends AppCompatActivity implements GoalRepository.GoalCallback{

    private RecyclerView recyclerView;
    private GoalListAdapter goalListAdapter;
    private Session mSession;
    private GoalRepository goalRepository;
    private final String LOG_TAG = "GoalsActivity";
    private List<Goal> goalList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        Intent intent = getIntent();
        mSession = (Session) intent.getSerializableExtra("session");

        Log.d(LOG_TAG, "InitViewComponents called");
        recyclerView = findViewById(R.id.RV_goal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton();

        goalList = new ArrayList<>();

        goalListAdapter = new GoalListAdapter(goalList);
        recyclerView.setAdapter(goalListAdapter);

        goalRepository = new GoalRepository(this);
        goalRepository.getGoal(this, intent);

//        initViewComponents();
    }

//    private void initViewComponents(){
//        Log.d(LOG_TAG, "InitViewComponents called");
//        recyclerView = findViewById(R.id.RV_goal_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        backButton();
//
//        goalList = new ArrayList<>();
//
//        goalListAdapter = new GoalListAdapter(goalList);
//        recyclerView.setAdapter(goalListAdapter);
//
//        goalRepository = new GoalRepository(this);
//        goalRepository.getGoal(this, intent);
//    }

    public void onGoalFetched(){

    }
    private void backButton(){
        Log.d(LOG_TAG, "backButton called");
        ImageButton back = findViewById(R.id.back_to_results);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, ResultsActivity.class);
            intent.putExtra("session", mSession);
            startActivity(intent);
        });
    }
}

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
import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.GoalListAdapter;
import com.jochemtb.gezinsgericht.domain.Goal;
import com.jochemtb.gezinsgericht.domain.GroupedHistoryAnswer;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.repository.GoalRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoalsActivity extends AppCompatActivity implements GoalRepository.GoalCallback{

    private RecyclerView recyclerView;
    private GoalListAdapter goalListAdapter;
    private int mSessionId;
    private GoalRepository goalRepository;
    private final String LOG_TAG = "GoalsActivity";
    private List<Goal> goalList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", mSessionId);
        Log.d(LOG_TAG, "Given intent:" + mSessionId);

        initViewComponents(intent);
    }

    private void initViewComponents(Intent intent){
        Log.d(LOG_TAG, "InitViewComponents called");
        recyclerView = findViewById(R.id.RV_goal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton();

        goalList = new ArrayList<>();

        goalListAdapter = new GoalListAdapter(goalList);
        recyclerView.setAdapter(goalListAdapter);

        goalRepository = new GoalRepository(this);
        goalRepository.getGoal(this, intent);
    }

    public void onGoalFetched(){
        List<GoalResponse> goalAnswers = GoalRepository.getDao().getGoalList();

        // Group the goalAnswers by Habitat_Name
        Map<String, List<GoalResponse>> groupedMap = new HashMap<>();
        for (GoalResponse response : goalAnswers) {
            if (!groupedMap.containsKey(response.getGoalContent())) {
                groupedMap.put(response.getGoalContent(), new ArrayList<>());
            }
            groupedMap.get(response.getGoalContent()).add(response);
        }

        goalList.clear();
        for (Map.Entry<String, List<GoalResponse>> entry : groupedMap.entrySet()) {
            goalList.add(new Goal(entry.getKey(), entry.getValue()));
        }

        // Notify adapter about data changes
        goalListAdapter.notifyDataSetChanged();
    }
    private void backButton(){
        Log.d(LOG_TAG, "backButton called");
        ImageButton back = findViewById(R.id.back_to_results);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, ResultsActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }
}

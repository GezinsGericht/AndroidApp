package com.jochemtb.gezinsgericht.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.GoalListAdapter;
import com.jochemtb.gezinsgericht.domain.Goal;
import com.jochemtb.gezinsgericht.domain.Session;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private GoalListAdapter goalListAdapter;
    private List<Goal> goalList;
    private ArrayList<String> actions;
    private Session mSession;
    private final String LOG_TAG = "GoalsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        Intent intent = getIntent();
        mSession = (Session) intent.getSerializableExtra("session");
        Log.d(LOG_TAG, "Intent loaded");
        initViewComponents();
    }

    private void initViewComponents(){
        Log.d(LOG_TAG, "InitViewComponents called");
        recyclerView = findViewById(R.id.RV_goal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton();

        makeDummyDate();
        goalListAdapter = new GoalListAdapter(goalList);
        recyclerView.setAdapter(goalListAdapter);
    }

    private void makeDummyDate(){
        actions = new ArrayList<>();
        goalList = new ArrayList<>();

        actions.add("Ik ga meer fortnite spelen");
        actions.add("Ik ga vaker mijn moeders credit gebruiken");
        actions.add("Ik ga meer mensen scammen");
        actions.add("Meer vbuck kopen");
        actions.add("Goede vraag wat de laatste actie is");

        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
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

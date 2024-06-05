package com.jochemtb.gezinsgericht.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.GoalListAdapter;
import com.jochemtb.gezinsgericht.domain.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private GoalListAdapter goalListAdapter;
    private List<Goal> goalList;
    private ArrayList<String> actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        recyclerView = findViewById(R.id.RV_goal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actions = new ArrayList<>();
        actions.add("Ik ga meer fortnite spelen");
        actions.add("Ik ga vaker mijn moeders credit gebruiken");
        actions.add("Ik ga meer mensen scammen");
        actions.add("Meer vbuck kopen");
        actions.add("Goede vraag wat de laatste actie is");

        goalList = new ArrayList<>();
        goalList.add(new Goal("Doel 1: Meer Vbucks", actions));
        // Voeg meer doelen toe aan de lijst indien nodig

        goalListAdapter = new GoalListAdapter(goalList);
        recyclerView.setAdapter(goalListAdapter);
    }
}

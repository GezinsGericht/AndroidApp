package com.jochemtb.gezinsgericht.domain;

import com.jochemtb.gezinsgericht.API.Goal.GoalResponse;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    private String goal;
    private List<GoalResponse> actions;

    public Goal(String goal, List<GoalResponse> actions){
        this.goal = goal;
        this.actions = actions;
    }

    public String getGoal() {
        return goal;
    }

    public List<GoalResponse> getActions() {
        return actions;
    }
}

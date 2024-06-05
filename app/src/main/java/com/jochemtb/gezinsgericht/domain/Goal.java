package com.jochemtb.gezinsgericht.domain;

import java.util.ArrayList;

public class Goal {
    private String goal;
    private ArrayList<String> actions;

    public Goal(String goal, ArrayList<String> actions){
        this.goal = goal;
        this.actions = actions;
    }

    public String getGoal() {
        return goal;
    }

    public ArrayList<String> getActions() {
        return actions;
    }
}

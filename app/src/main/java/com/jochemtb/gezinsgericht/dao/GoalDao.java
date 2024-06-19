package com.jochemtb.gezinsgericht.dao;

import com.jochemtb.gezinsgericht.API.Goal.GoalResponse;

import java.util.List;

public class GoalDao {
    private List<GoalResponse> goalList;

    public List<GoalResponse> getGoalList(){return goalList;}

    public void setGoalList(List<GoalResponse> goalList){
        this.goalList = goalList;
    }
}

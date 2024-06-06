package com.jochemtb.gezinsgericht.API.Goal;

public class GoalResponse {
    private String GoalContent;
    private String Actions;

    // Getters and setters
    public String getGoalContent() {
        return GoalContent;
    }

    public void setGoalContent(String goalContent) {
        GoalContent = goalContent;
    }

    public String getActions() {
        return Actions;
    }

    public void setActions(String actions) {
        Actions = actions;
    }
}

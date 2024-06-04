package com.jochemtb.gezinsgericht.domain;

public class ResultsItem {

    private int weight;
    private int HabitatId;
    private int UserId;
    private int AnswerValue;
    private String UserName;

    public ResultsItem(int weight, int habitatId, int userId, int answerValue, String userName) {
        this.weight = weight;
        HabitatId = habitatId;
        UserId = userId;
        UserName = userName;
        AnswerValue = answerValue;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHabitatId() {
        return HabitatId;
    }

    public void setHabitatId(int habitatId) {
        HabitatId = habitatId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getAnswerValue() {
        return AnswerValue;
    }

    public void setAnswerValue(int answerValue) {
        AnswerValue = answerValue;
    }

    public String getUserName() { return UserName; }

    public void setUserName(String userName) { UserName = userName; }

}

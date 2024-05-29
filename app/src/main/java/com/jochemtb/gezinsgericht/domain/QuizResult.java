package com.jochemtb.gezinsgericht.domain;

import java.util.ArrayList;

public class QuizResult {

    private ArrayList<Question> questionList;
    private ArrayList<Integer> antselected;

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }



    public void setAntselected(ArrayList<Integer> antselected) {
        this.antselected = antselected;
    }

    public ArrayList<Integer> getAntselected() {
        return antselected;
    }

    public QuizResult(ArrayList<Question> questionList, ArrayList<Integer> antselected) {
        this.questionList = questionList;
        this.antselected = antselected;
    }

    public void addToAntSelected(int ant) {
        antselected.add(ant);
    }
}

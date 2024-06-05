package com.jochemtb.gezinsgericht.domain;



import java.util.ArrayList;

public class Quiz {
    public Quiz() {
    }

    private ArrayList<Question> questionList;
    private int totalQuestions;

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Quiz(ArrayList<Question> questionList, int totalQuestions) {
        this.questionList = questionList;
        this.totalQuestions = totalQuestions;
    }

}

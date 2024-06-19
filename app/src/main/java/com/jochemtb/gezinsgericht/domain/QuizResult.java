package com.jochemtb.gezinsgericht.domain;

import java.util.ArrayList;

public class QuizResult {

    private ArrayList<Question> questionList;
    private ArrayList<Integer> AnswerValue;
    private ArrayList<Integer> QuestionId;

    public ArrayList<Integer> getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(ArrayList<Integer> questionId) {
        QuestionId = questionId;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }



    public void setAntselected(ArrayList<Integer> antselected) {
        this.AnswerValue = antselected;
    }

    public ArrayList<Integer> getAntselected() {
        return AnswerValue;
    }

    public QuizResult(ArrayList<Question> questionList, ArrayList<Integer> antselected) {
        this.questionList = questionList;
        this.AnswerValue = antselected;
    }

    public void addToAntSelected(int ant) {
        AnswerValue.add(ant);
    }
}

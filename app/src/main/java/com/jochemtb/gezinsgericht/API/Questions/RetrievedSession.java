package com.jochemtb.gezinsgericht.API.Questions;

import java.util.ArrayList;
import java.util.List;

public class RetrievedSession {
    private int SessionId;
    private String QuestionList;

    public RetrievedSession(int sessionId, String questionList) {
        SessionId = sessionId;
        QuestionList = questionList;
    }

    public int getSessionId() {
        return SessionId;
    }

    public void setSessionId(int sessionId) {
        SessionId = sessionId;
    }

    public ArrayList<Integer> getQuestionList() {
        ArrayList<Integer> questionList = new ArrayList<>();
        String[] questionListString = QuestionList.split(",");
        for (String questionId : questionListString) {
            questionList.add(Integer.parseInt(questionId));
        }
        return questionList;
    }

    public void setQuestionList(ArrayList<Integer> questionList) {
        StringBuilder questionListString = new StringBuilder();
        for (Integer questionId : questionList) {
            questionListString.append(questionId).append(",");
        }
        QuestionList = questionListString.toString();
    }
}

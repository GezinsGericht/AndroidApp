// HistoryAnswerResponse.java
package com.jochemtb.gezinsgericht.API.HistoryAnswers;

public class HistoryAnswerResponse {
    private String Habitat_Name;
    private String Questions;
    private String Answers;

    // Getters and setters
    public String getHabitat_Name() {
        return Habitat_Name;
    }

    public void setHabitat_Name(String habitat_Name) {
        Habitat_Name = habitat_Name;
    }

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }
}

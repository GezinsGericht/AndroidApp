package com.jochemtb.gezinsgericht.domain;

public class Question {
    private int QuestionId;
    private String Question;
    private int Weight;
    private String Answer1;

    private String Answer2;
    private String Answer3;
    private String Answer4;
    private String Answer5;

    public Question(int questionid, String question, int weight, String answer1, String answer2, String answer3,
            String answer4, String answer5) {
        this.QuestionId = questionid;
        this.Question = question;
        this.Weight = weight;
        this.Answer1 = answer1;
        this.Answer2 = answer2;
        this.Answer3 = answer3;
        this.Answer4 = answer4;
        this.Answer5 = answer5;
    }

    public int getQuestionid() {
        return QuestionId;
    }

    public void setQuestionid(int questionid) {
        this.QuestionId = questionid;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        this.Weight = weight;
    }
    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        this.Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        this.Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        this.Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        this.Answer4 = answer4;
    }

    public String getAnswer5() {
        return Answer5;
    }

    public void setAnswer5(String answer5) {

        this.Answer5 = answer5;

    }

}

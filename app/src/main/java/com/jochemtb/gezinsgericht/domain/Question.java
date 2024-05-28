package com.jochemtb.gezinsgericht.domain;

public class Question {
    private int questionid;
    private String question;
    private int weight;
//    private Habitat habitat;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;

    public Question(int questionid, String question, int weight, String answer1, String answer2, String answer3, String answer4, String answer5) {
        this.questionid = questionid;
        this.question = question;
        this.weight = weight;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

//    public Habitat getHabitat() {
//        return habitat;
//    }

//    public void setHabitat(Habitat habitat) {
//        this.habitat = habitat;
//    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

//    public Question(String question, int weight, Habitat habitat, String answer1, String answer2, String answer3, String answer4, String answer5) {
//        this.question = question;
//        this.weight = weight;
//        this.habitat = habitat;
//        this.answer1 = answer1;
//        this.answer2 = answer2;
//        this.answer3 = answer3;
//        this.answer4 = answer4;
//        this.answer5 = answer5;
//    }
}
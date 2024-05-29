package com.jochemtb.gezinsgericht.domain;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private List<String> questionstring;
    private List<String[]> possibleAnswers;
    private List<Question> qs;

    public QuizManager() {
        questionstring = new ArrayList<>();
        possibleAnswers = new ArrayList<>();
        qs = new ArrayList<>();
    }

    public Quiz generateQuiz() {
        questionstring.add("Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?");
        questionstring.add("Hoe actief bent u betrokken bij sociale activiteiten en evenementen?");
        questionstring.add("Hoe tevreden bent u over de plek waar u woont?");

        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});

        for (int i = 0; i < questionstring.size(); i++) {
            qs.add(new Question(i + 1,
                    questionstring.get(i),
                    1,
                    possibleAnswers.get(i)[0],
                    possibleAnswers.get(i)[1],
                    possibleAnswers.get(i)[2],
                    possibleAnswers.get(i)[3],
                    possibleAnswers.get(i)[4]));
        }

        Quiz quiz = new Quiz();
        quiz.setQuestionList((ArrayList<Question>) qs);
        quiz.setTotalQuestions(qs.size());
        return quiz;
    }

    public List<Question> getQuestions() {
        return qs;
    }
}

package com.jochemtb.gezinsgericht.domain;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private ArrayList<String> questionstring;
    private List<String[]> possibleAnswers;
    private ArrayList<Question> qs = new ArrayList<>();

    public QuizManager() {
        questionstring = new ArrayList<>(); // Initialize the questionstring list
        possibleAnswers = new ArrayList<>(); // Initialize the possibleAnswers list
    }

    public Quiz generateQuiz(Quiz q) {
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
        q.setQuestionList(qs);
        q.setTotalQuestions(qs.size());
        return q;
    }
}

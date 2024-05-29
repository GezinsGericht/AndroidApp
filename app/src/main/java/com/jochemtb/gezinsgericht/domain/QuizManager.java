package com.jochemtb.gezinsgericht.domain;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.jochemtb.gezinsgericht.GUI.QuizActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private List<String> questionstring;
    private List<String[]> possibleAnswers;
    private List<Question> qs;
    private Context context;
    private int ant;

    public QuizManager(Context context) {
        questionstring = new ArrayList<>();
        possibleAnswers = new ArrayList<>();
        qs = new ArrayList<>();
        this.context = context;
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

    public void saveSelectedAnswer(List<Integer> selectedAnswers, RadioGroup answersGroup, int currentQuestionIndex, QuizResult quizResult) {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            View radioButton = answersGroup.findViewById(selectedId);
            int index = answersGroup.indexOfChild(radioButton);
            Log.d("Selected answer", Integer.toString(index));
            quizResult.setQuestionList((ArrayList<Question>) qs);
            selectedAnswers.set(currentQuestionIndex, index);
            quizResult.addToAntSelected(index);
            Log.d(quizResult.getQuestionList().get(currentQuestionIndex).getQuestion(), quizResult.getAntselected().toString());

        } else {
            selectedAnswers.set(currentQuestionIndex, -1);
        }
        answersGroup.clearCheck();
    }

    public void checkIfFinalQuestion(int currentQuestionIndex, Quiz quiz, QuizActivity.QuestionView questionView) {
        if (currentQuestionIndex < quiz.getTotalQuestions()) {
            questionView.displayQuestion(currentQuestionIndex);
        }
    }

    public List<Integer> getUnansweredQuestionIndices(List<Integer> selectedAnswers) {
        List<Integer> unansweredQuestions = new ArrayList<>();
        for (int i = 0; i < selectedAnswers.size(); i++) {
            if (selectedAnswers.get(i) == -1) {
                unansweredQuestions.add(i);
            }
        }
        return unansweredQuestions;
    }

    public void submitData(List<Integer> selectedAnswers, Quiz quiz) {
        for (int i = 0; i < quiz.getTotalQuestions(); i++) {
            String answer = selectedAnswers.get(i) == -1 ? "No answer selected" : Integer.toString(selectedAnswers.get(i));
            // Perform submission logic here
        }
    }

    public void updateProgressBar(List<Integer> selectedAnswers, ProgressBar progressBar, Quiz quiz) {
        int answeredQuestions = 0;
        for (int answer : selectedAnswers) {
            if (answer != -1) {
                answeredQuestions++;
            }
        }
        progressBar.setProgress((answeredQuestions) * 100 / quiz.getTotalQuestions());
    }
}

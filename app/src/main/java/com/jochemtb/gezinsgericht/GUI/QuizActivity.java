package com.jochemtb.gezinsgericht.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.Question;
import com.jochemtb.gezinsgericht.domain.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup answersGroup;
    private Button nextButton;
    private Button previousButton;
    private Button confirmButton;
    private TextView questionNumber;
    private ProgressBar progressBar;

    private List<String> questions;
    private List<String[]> possibleAnswers;
    private List<String> selectedAnswers;
    private ArrayList<String> questionstring;
    private int currentQuestionIndex = 0;
    private ArrayList<Question> qs = new ArrayList<>();
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.TV_quiz_questionText);
        answersGroup = findViewById(R.id.RG_quiz_answersGroup);
        nextButton = findViewById(R.id.BT_quiz_next);
        previousButton = findViewById(R.id.BT_quiz_prev);
        confirmButton = findViewById(R.id.BT_quiz_confirm);
        questionNumber = findViewById(R.id.TV_quiz_questionNumber);
        progressBar = findViewById(R.id.PB_quiz_quizProgress);

        // Initialize the question strings
        questionstring = new ArrayList<>();
        questionstring.add("Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?");
        questionstring.add("Hoe actief bent u betrokken bij sociale activiteiten en evenementen?");
        questionstring.add("Hoe tevreden bent u over de plek waar u woont?");

        possibleAnswers = new ArrayList<>();
        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});

        selectedAnswers = new ArrayList<>(Arrays.asList(new String[questionstring.size()]));

        // Create the quiz and questions
        createQuestion();
        createQuiz();

        // Set up the initial question and answers
        displayQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(v -> {
            int selectedId = answersGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            } else {
                RadioButton selectedRadioButton = findViewById(selectedId);
                selectedAnswers.set(currentQuestionIndex, selectedRadioButton.getText().toString());
                answersGroup.clearCheck();
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questionstring.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                nextButton.setVisibility(View.INVISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
            }

            // Update progress bar
            updateProgressBar();
        });

        previousButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion(currentQuestionIndex);
            }
            if (currentQuestionIndex >= questionstring.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                nextButton.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.INVISIBLE);
            }
        });

        confirmButton.setOnClickListener(v -> {
            // Handle the submission of answers
            for (int i = 0; i < questionstring.size(); i++) {
                Toast.makeText(QuizActivity.this, questionstring.get(i) + ": " + selectedAnswers.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createQuiz() {
        quiz = new Quiz(qs, qs.size());
    }

    public void createQuestion() {
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
    }

    private void displayQuestion(int index) {
        String selectedAnswer = selectedAnswers.get(index); // Preload the previously selected answer
        questionText.setText(quiz.getQuestionList().get(index).getQuestion());
        answersGroup.removeAllViews();

        String[] answers = {quiz.getQuestionList().get(index).getAnswer1(),
                quiz.getQuestionList().get(index).getAnswer2(),
                quiz.getQuestionList().get(index).getAnswer3(),
                quiz.getQuestionList().get(index).getAnswer4(),
                quiz.getQuestionList().get(index).getAnswer5()};
        for (String answer : answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer);
            answersGroup.addView(radioButton);
        }

        if (selectedAnswer != null) {
            for (int i = 0; i < answersGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) answersGroup.getChildAt(i);
                if (radioButton.getText().equals(selectedAnswer)) {
                    radioButton.setChecked(true);
                    break;
                }
            }
        }

        // Update the question number and progress bar
        questionNumber.setText(String.format("%d", index + 1));
        progressBar.setProgress((index) * 100 / questionstring.size());

        // Update the visibility of the previous button
        previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void updateProgressBar() {
        progressBar.setProgress((currentQuestionIndex) * 100 / questionstring.size());
    }
}

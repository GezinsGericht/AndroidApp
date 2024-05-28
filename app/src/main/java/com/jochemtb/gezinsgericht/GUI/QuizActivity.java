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
    private int currentQuestionIndex = 0;

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

        // Initialize the questions and possible answers
        questions = Arrays.asList(
                "Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?",
                "Hoe actief bent u betrokken bij sociale activiteiten en evenementen?",
                "Hoe tevreden bent u over de plek waar u woont?"
        );

        possibleAnswers = new ArrayList<>();
        possibleAnswers.add(new String[]{"Helemaal geen aandacht","Weinig aandacht","Neutraal", "Redelijk veel aandacht","Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht","Weinig aandacht","Neutraal", "Redelijk veel aandacht","Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht","Weinig aandacht","Neutraal", "Redelijk veel aandacht","Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht","Weinig aandacht","Neutraal", "Redelijk veel aandacht","Zeer veel aandacht"});
        possibleAnswers.add(new String[]{"Helemaal geen aandacht","Weinig aandacht","Neutraal", "Redelijk veel aandacht","Zeer veel aandacht"});

        selectedAnswers = new ArrayList<>(Arrays.asList(new String[questions.size()]));

        // Set up the initial question and answers
        displayQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(v -> {
            int selectedId = answersGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            } else{
                RadioButton selectedRadioButton = findViewById(selectedId);
                selectedAnswers.set(currentQuestionIndex, selectedRadioButton.getText().toString());
                answersGroup.clearCheck();
            }



            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
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
            if (currentQuestionIndex >= questions.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                nextButton.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.INVISIBLE);
            }
        });

        confirmButton.setOnClickListener(v -> {
            // Handle the submission of answers
            for (int i = 0; i < questions.size(); i++) {
                Toast.makeText(QuizActivity.this, questions.get(i) + ": " + selectedAnswers.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion(int index) {
        String selectedAnswer = selectedAnswers.get(index); // Preload the previously selected answer
        questionText.setText(questions.get(index));
        answersGroup.removeAllViews();


        String[] answers = possibleAnswers.get(index);
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
        progressBar.setProgress((index) * 100 / questions.size());

        // Update the visibility of the previous button
        previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void updateProgressBar() {
        progressBar.setProgress((currentQuestionIndex) * 100 / questions.size());
    }
}

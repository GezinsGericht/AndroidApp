package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
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
import com.jochemtb.gezinsgericht.domain.QuizManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView confirmText;
    private RadioGroup answersGroup;
    private Button nextButton;
    private Button previousButton;
    private Button confirmButton;
    private TextView questionNumber;
    private ProgressBar progressBar;

    private List<String> selectedAnswers;
    private int currentQuestionIndex = 0;
    private Quiz quiz;
    private QuizManager quizManager;
    private QuestionView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();
        initQuiz();

        nextButton.setOnClickListener(v -> handleNextButtonClick());
        previousButton.setOnClickListener(v -> handlePreviousButtonClick());
        confirmButton.setOnClickListener(v -> handleConfirmButtonClick());

        // Set up the initial question and answers
        questionView.displayQuestion(currentQuestionIndex);
        confirmText.setText("Dit is het aflsuitscherm van de vragenlijst. Enige onbeantwoorde vragen zullen worden weergegeven op het scherm. U kunt ernaartoe navigeren door op de knop met het vraagnummer te klikken");
    }

    private void initViews() {
        questionText = findViewById(R.id.TV_quiz_questionText);
        confirmText = findViewById(R.id.TV_quiz_confirm);
        answersGroup = findViewById(R.id.RG_quiz_answersGroup);
        nextButton = findViewById(R.id.BT_quiz_next);
        previousButton = findViewById(R.id.BT_quiz_prev);
        confirmButton = findViewById(R.id.BT_quiz_confirm);
        questionNumber = findViewById(R.id.TV_quiz_questionNumber);
        progressBar = findViewById(R.id.PB_quiz_quizProgress);
    }

    private void initQuiz() {
        quizManager = new QuizManager();
        quiz = new Quiz();  // Instantiate quiz before passing it to generateQuiz
        quizManager.generateQuiz(quiz);

        selectedAnswers = new ArrayList<>(Arrays.asList(new String[quiz.getTotalQuestions()]));
        questionView = new QuestionView();
    }

    private void handleNextButtonClick() {
        saveSelectedAnswer();

        currentQuestionIndex++;
        if (currentQuestionIndex < quiz.getTotalQuestions()) {
            questionView.displayQuestion(currentQuestionIndex);
        } else {
            prepareForQuizSubmission();
        }

        updateProgressBar();
    }

    private void handlePreviousButtonClick() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            questionView.displayQuestion(currentQuestionIndex);
        }
        if (currentQuestionIndex < quiz.getTotalQuestions()) {
            nextButton.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
        }
    }

    private void handleConfirmButtonClick() {
        for (int i = 0; i < quiz.getTotalQuestions(); i++) {
            String answer = selectedAnswers.get(i) == null ? "No answer selected" : selectedAnswers.get(i);
            Toast.makeText(QuizActivity.this, quiz.getQuestionList().get(i).getQuestion() + ": " + answer, Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(QuizActivity.this, ResultsActivity.class));
    }

    private void saveSelectedAnswer() {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            selectedAnswers.set(currentQuestionIndex, selectedRadioButton.getText().toString());
        } else {
            selectedAnswers.set(currentQuestionIndex, null);
        }
        answersGroup.clearCheck();
    }

    private void prepareForQuizSubmission() {
        answersGroup.setVisibility(View.INVISIBLE);
        questionText.setVisibility(View.INVISIBLE);
        confirmText.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    private void updateProgressBar() {
        progressBar.setProgress((currentQuestionIndex) * 100 / quiz.getTotalQuestions());
    }

    private class QuestionView {

        void displayQuestion(int index) {
            String selectedAnswer = selectedAnswers.get(index); // Preload the previously selected answer
            answersGroup.removeAllViews();

            Question currentQuestion = quiz.getQuestionList().get(index);
            questionText.setText(currentQuestion.getQuestion());
            String[] answers = {currentQuestion.getAnswer1(),
                    currentQuestion.getAnswer2(),
                    currentQuestion.getAnswer3(),
                    currentQuestion.getAnswer4(),
                    currentQuestion.getAnswer5()};
            for (String answer : answers) {
                RadioButton radioButton = new RadioButton(QuizActivity.this);
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
            progressBar.setProgress((index) * 100 / quiz.getTotalQuestions());

            // Update the visibility of the previous button
            previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
        }
    }
}

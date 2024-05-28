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
import com.jochemtb.gezinsgericht.domain.QuizManager;

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

    private List<String> selectedAnswers;
    private int currentQuestionIndex = 0;
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

        QuizManager qm = new QuizManager();
        quiz = new Quiz();  // Instantiate quiz before passing it to generateQuiz
        qm.generateQuiz(quiz);

        selectedAnswers = new ArrayList<>(Arrays.asList(new String[quiz.getTotalQuestions()]));

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
            if (currentQuestionIndex < quiz.getTotalQuestions()) {
                displayQuestion(currentQuestionIndex);
            } else {
                answersGroup.setVisibility(View.INVISIBLE);
                questionText.setVisibility(View.INVISIBLE);
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
            if (currentQuestionIndex < quiz.getTotalQuestions()) {
                nextButton.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.INVISIBLE);
            }
        });

        confirmButton.setOnClickListener(v -> {
            // Handle the submission of answers
            for (int i = 0; i < quiz.getTotalQuestions(); i++) {
                Toast.makeText(QuizActivity.this, quiz.getQuestionList().get(i).getQuestion() + ": " + selectedAnswers.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion(int index) {
        String selectedAnswer = selectedAnswers.get(index); // Preload the previously selected answer
        answersGroup.removeAllViews();

        questionText.setText(quiz.getQuestionList().get(index).getQuestion());
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
        progressBar.setProgress((index) * 100 / quiz.getTotalQuestions());

        // Update the visibility of the previous button
        previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void updateProgressBar() {
        progressBar.setProgress((currentQuestionIndex) * 100 / quiz.getTotalQuestions());
    }
}

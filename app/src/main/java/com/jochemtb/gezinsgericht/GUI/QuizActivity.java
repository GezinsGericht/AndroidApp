package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.Question;
import com.jochemtb.gezinsgericht.domain.Quiz;
import com.jochemtb.gezinsgericht.domain.QuizManager;

import java.util.ArrayList;
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
    private LinearLayout unansweredQuestionsLayout;
    private ScrollView scrollView;

    private List<Integer> selectedAnswers;
    private int currentQuestionIndex = 0;
    private Quiz quiz;
    private QuizManager quizManager;
    private QuestionView questionView;
    private int progressCounter = 0;

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
        confirmText.setText("Dit is het afsluitscherm van de vragenlijst. Enige onbeantwoorde vragen zullen worden weergegeven op het scherm. U kunt ernaartoe navigeren door op de knop te klikken.");
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
        unansweredQuestionsLayout = findViewById(R.id.LL_quiz_unansweredQuestions);
        scrollView = findViewById(R.id.SV_quiz_scrollView);
    }

    private void initQuiz() {
        quizManager = new QuizManager();
        quiz = quizManager.generateQuiz();

        selectedAnswers = new ArrayList<>(quiz.getTotalQuestions());
        for (int i = 0; i < quiz.getTotalQuestions(); i++) {
            selectedAnswers.add(-1); // Initialize all answers with -1 (indicating no answer selected)
        }
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
            answersGroup.setVisibility(View.VISIBLE);
            questionText.setVisibility(View.VISIBLE);
            confirmText.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
            unansweredQuestionsLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void handleConfirmButtonClick() {
        for (int i = 0; i < quiz.getTotalQuestions(); i++) {
            String answer = selectedAnswers.get(i) == -1 ? "No answer selected" : Integer.toString(selectedAnswers.get(i));
            Toast.makeText(QuizActivity.this, quiz.getQuestionList().get(i).getQuestion() + ": " + answer, Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(QuizActivity.this, ResultsActivity.class));
    }

    private void saveSelectedAnswer() {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            View radioButton = answersGroup.findViewById(selectedId);
            int index = answersGroup.indexOfChild(radioButton);
            selectedAnswers.set(currentQuestionIndex, index);
        } else {
            selectedAnswers.set(currentQuestionIndex, -1);
        }
        answersGroup.clearCheck();
    }

    private void prepareForQuizSubmission() {
        answersGroup.setVisibility(View.GONE);
        questionText.setVisibility(View.INVISIBLE);
        questionNumber.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);


        List<Integer> unansweredQuestionIndices = getUnansweredQuestionIndices();
        if (unansweredQuestionIndices.isEmpty()) {
            confirmText.setText("U heeft alle vragen beantwoord. Klik op 'BEVESTIGEN' om door te gaan.");
            confirmButton.setVisibility(View.VISIBLE);
        } else {
            displayUnansweredQuestions(unansweredQuestionIndices);
        }
        confirmText.setVisibility(View.VISIBLE);
    }

    private List<Integer> getUnansweredQuestionIndices() {
        List<Integer> unansweredQuestions = new ArrayList<>();
        for (int i = 0; i < selectedAnswers.size(); i++) {
            if (selectedAnswers.get(i) == -1) {
                unansweredQuestions.add(i);
            }
        }
        return unansweredQuestions;
    }

    private void displayUnansweredQuestions(List<Integer> unansweredQuestions) {
        unansweredQuestionsLayout.removeAllViews();
        unansweredQuestionsLayout.setVisibility(View.VISIBLE);

        for (int index : unansweredQuestions) {
            Button questionButton = new Button(this);
            questionButton.setText(String.format("Vraag %d", index + 1));
            questionButton.setOnClickListener(v -> navigateToQuestion(index));
            unansweredQuestionsLayout.addView(questionButton);
        }
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void navigateToQuestion(int index) {
        currentQuestionIndex = index;
        questionView.displayQuestion(index);
        answersGroup.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        confirmText.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);
        unansweredQuestionsLayout.setVisibility(View.INVISIBLE);
    }

    private void updateProgressBar() {
        int answeredQuestions = 0;
        for (int answer : selectedAnswers) {
            if (answer != -1) {
                answeredQuestions++;
            }
        }
        progressBar.setProgress((answeredQuestions) * 100 / quiz.getTotalQuestions());
    }

    private class QuestionView {

        void displayQuestion(int index) {
            Log.d("QuestionView", String.valueOf(index));
            int selectedAnswerIndex = selectedAnswers.get(index); // Preload the previously selected answer
            answersGroup.removeAllViews();

            Question currentQuestion = quiz.getQuestionList().get(index);
            questionText.setText(currentQuestion.getQuestion());
            String[] answers = {currentQuestion.getAnswer1(),
                    currentQuestion.getAnswer2(),
                    currentQuestion.getAnswer3(),
                    currentQuestion.getAnswer4(),
                    currentQuestion.getAnswer5()};
            for (String answer : answers) {
                if (answer != null && !answer.isEmpty()) {
                    RadioButton radioButton = new RadioButton(QuizActivity.this);
                    radioButton.setText(answer);
                    radioButton.setEnabled(true);
                    Log.d("QuestionView", String.valueOf(radioButton.isClickable()));
                    Log.d("QuestionView", String.valueOf(index));
                    answersGroup.addView(radioButton);
                }
            }

            // Force the RadioGroup to redraw itself and recalculate its layout
            answersGroup.invalidate();
            answersGroup.requestLayout();

            if (selectedAnswerIndex != -1) {
                RadioButton selectedRadioButton = (RadioButton) answersGroup.getChildAt(selectedAnswerIndex);
                selectedRadioButton.setChecked(true);
            }

            // Update the question number
            questionNumber.setText(String.format("%d", index + 1));

            previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE); // Update the visibility of the previous button
        }
    }
}

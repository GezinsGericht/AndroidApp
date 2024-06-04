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
import com.jochemtb.gezinsgericht.domain.QuizResult;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements QuizManager.QuizGenerationListener {

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

    private List<Integer> selectedAnswers = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private Quiz quiz;
    private QuizManager quizManager;
    private QuestionView questionView;
    private QuizResult quizResult;

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

        confirmText.setText(
                "Dit is het afsluitscherm van de vragenlijst. Enige onbeantwoorde vragen zullen worden weergegeven op het scherm. U kunt ernaartoe navigeren door op de knop te klikken.");
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
        quizManager = QuizManager.getInstance();
        quizManager.setContext(this);
        quizManager.setQuizGenerationListener(this);
        quizManager.generateQuiz();
    }

    @Override
    public void onQuizGenerated() {
        quiz = quizManager.getCurrentQuiz();
        if (quiz != null) {
            selectedAnswers = new ArrayList<>(quiz.getTotalQuestions());
            for (int i = 0; i < quiz.getTotalQuestions(); i++) {
                selectedAnswers.add(-1); // Initialize all answers with -1 (indicating no answer selected)
            }
            quizResult = new QuizResult((ArrayList<Question>) quiz.getQuestionList(), new ArrayList<>());
            questionView = new QuestionView();
            questionView.displayQuestion(currentQuestionIndex); // Display the first question
        } else {
            Log.e("QuizActivity", "Quiz is null after generation");
        }
    }

    private void handleNextButtonClick() {
        if (quiz != null) {
            quizManager.saveSelectedAnswer(selectedAnswers, answersGroup, currentQuestionIndex, quizResult);
            currentQuestionIndex++;
            quizManager.checkIfFinalQuestion(currentQuestionIndex, quiz, questionView);
            if (currentQuestionIndex == quiz.getTotalQuestions()) {
                prepareForQuizSubmission();
            }
            quizManager.updateProgressBar(selectedAnswers, progressBar, quiz);
        } else {
            Toast.makeText(this, "Quiz is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePreviousButtonClick() {
        if (quiz != null) {
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
        } else {
            Toast.makeText(this, "Quiz is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleConfirmButtonClick() {
        if (quiz != null) {
            quizManager.submitData(selectedAnswers, quiz);
            startActivity(new Intent(QuizActivity.this, ResultsActivity.class));
        } else {
            Toast.makeText(this, "Quiz is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show();
        }
    }

    public void prepareForQuizSubmission() {
        if (quiz != null) {
            answersGroup.setVisibility(View.GONE);
            questionText.setVisibility(View.INVISIBLE);
            questionNumber.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);

            List<Integer> unansweredQuestionIndices = quizManager.getUnansweredQuestionIndices(selectedAnswers);
            if (unansweredQuestionIndices.isEmpty()) {
                confirmText.setText("U heeft alle vragen beantwoord. Klik op 'BEVESTIGEN' om door te gaan.");
                confirmButton.setVisibility(View.VISIBLE);
            } else {
                displayUnansweredQuestions(unansweredQuestionIndices);
            }
            confirmText.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Quiz is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show();
        }
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
        if (quiz != null) {
            currentQuestionIndex = index;
            questionView.displayQuestion(index);
            answersGroup.setVisibility(View.VISIBLE);
            questionText.setVisibility(View.VISIBLE);
            questionNumber.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
            confirmText.setVisibility(View.GONE);
            unansweredQuestionsLayout.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Quiz is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show();
        }
    }

    public class QuestionView {

        public void displayQuestion(int index) {
            if (quiz != null) {
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

                for (int i = 0; i < answers.length; i++) {
                    if (answers[i] != null && !answers[i].isEmpty()) {
                        RadioButton radioButton = new RadioButton(QuizActivity.this);
                        setUpRadioButton(radioButton, answers[i], i, index, answersGroup);
                        answersGroup.addView(radioButton);
                    }
                }

                answersGroup.invalidate(); // Force the RadioGroup to redraw itself and recalculate its layout
                answersGroup.requestLayout(); // Request a layout pass to ensure the RadioGroup is properly laid out

                if (selectedAnswerIndex != -1) {
                    RadioButton selectedRadioButton = (RadioButton) answersGroup.getChildAt(selectedAnswerIndex);
                    selectedRadioButton.setChecked(true);
                }

                // Update the question number
                questionNumber.setText(String.format("%d", index + 1));

                previousButton.setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE); // Update the visibility of the previous button
            }
        }

        private void setUpRadioButton(final RadioButton radioButton, String answer, int answerIndex, int questionIndex,
                                      RadioGroup answersGroup) {
            radioButton.setTextSize(20);
            radioButton.setPadding(0, 20, 0, 20);
            radioButton.setText(answer);

            radioButton.setOnClickListener(v -> {
                selectedAnswers.set(questionIndex, answerIndex); // Update the selected answer for the current question
            });
        }
    }
}

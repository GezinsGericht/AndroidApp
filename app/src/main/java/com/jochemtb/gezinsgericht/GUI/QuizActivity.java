package com.jochemtb.gezinsgericht.GUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
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
    private ProgressBar progressBar, loadingScreen;
    private LinearLayout unansweredQuestionsLayout;
    private ScrollView scrollView;

    private List<Integer> selectedAnswers = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private Quiz quiz;
    private QuizManager quizManager;
    private QuestionView questionView;
    private QuizResult quizResult;
    private final String LOG_TAG = "QuizActivity";

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Do nothing
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();
        setLoadingScreen(true);
        initQuiz();

        nextButton.setOnClickListener(v -> handleNextButtonClick());
        previousButton.setOnClickListener(v -> handlePreviousButtonClick());
        confirmButton.setOnClickListener(v -> handleConfirmButtonClick());

        confirmText.setText(
                R.string.dit_is_het_afsluitscherm_van_de_vragenlijst_enige_onbeantwoorde_vragen_zullen_worden_weergegeven_op_het_scherm_u_kunt_ernaartoe_navigeren_door_op_de_knop_te_klikken);
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
        loadingScreen = findViewById(R.id.PB_quiz_loadingIcon);
    }

    private void setLoadingScreen(Boolean bool){
        if(bool){
            questionText.setVisibility(View.GONE);
            confirmText.setVisibility(View.GONE);
            answersGroup.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
            questionNumber.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            loadingScreen.setVisibility(View.VISIBLE);
        } else {
            questionText.setVisibility(View.VISIBLE);
            answersGroup.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
            questionNumber.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loadingScreen.setVisibility(View.GONE);
        }
    }

    private void initQuiz() {
        quizManager = QuizManager.getInstance();
        quizManager.setContext(this);
        quizManager.setQuizGenerationListener(this);
        quizManager.generateQuiz();
    }


    @Override
    public void onQuizGenerated() {
        this.quiz = quizManager.getCurrentQuiz();
        if (quiz != null) {
            setLoadingScreen(false);
            selectedAnswers = new ArrayList<>(quiz.getTotalQuestions());
            for (int i = 0; i < quiz.getTotalQuestions(); i++) {
                selectedAnswers.add(-1); // Initialize all answers with -1 (indicating no answer selected)
            }
            quizResult = new QuizResult((ArrayList<Question>) quiz.getQuestionList(), new ArrayList<>());
            questionView = new QuestionView();
            questionView.displayQuestion(currentQuestionIndex); // Display the first question
        } else {
            Log.e("QuizActivity", "Quiz is null after generation");
            Toast.makeText(this, R.string.somethingWentWrongToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizActivity.this, MainActivity.class));
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
            Log.e(LOG_TAG, "Quiz is not yet loaded.");
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
                unansweredQuestionsLayout.setVisibility(View.GONE);
                questionNumber.setVisibility(View.VISIBLE);
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
                confirmText.setText(R.string.u_heeft_alle_vragen_beantwoord_klik_op_bevestigen_om_door_te_gaan);
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
//            questionButton.setBackgroundColor(getResources().getColor(R.color.aqua));
            questionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.aqua)));

            questionButton.setOnClickListener(v -> {
                        navigateToQuestion(index);
                        unansweredQuestionsLayout.setVisibility(View.GONE);
                    }
            );
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
            if (quiz != null && !quiz.getQuestionList().isEmpty()) {
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
            } else {
                Log.e("QuestionView", "Quiz or question list is null/empty");
                // Handle the case where the quiz or question list is null/empty, e.g., show a message to the user
            }
        }

        private void setUpRadioButton(final RadioButton radioButton, String answer, int answerIndex, int questionIndex, RadioGroup answersGroup) {
            radioButton.setTextSize(20);
            radioButton.setPadding(0, 20, 0, 20);
            radioButton.setText(answer);

            radioButton.setOnClickListener(v -> {
                if((radioButton.isChecked() && selectedAnswers.get(questionIndex) == answerIndex)) { // If the radio button is checked
                    radioButton.setChecked(false); // Uncheck the radio button
                    selectedAnswers.set(questionIndex, -1); // Update the selected answer for the current question
                    answersGroup.clearCheck(); // Clear the checked radio button

                } else{
                    radioButton.setChecked(true); // Check the radio button
                    selectedAnswers.set(questionIndex, answerIndex); // Update the selected answer for the current question
                }
            });
        }
    }
}

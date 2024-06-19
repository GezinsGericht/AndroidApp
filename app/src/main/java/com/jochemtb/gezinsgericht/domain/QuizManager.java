package com.jochemtb.gezinsgericht.domain;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.jochemtb.gezinsgericht.API.Questions.RetrievedSession;
import com.jochemtb.gezinsgericht.GUI.QuizActivity;
import com.jochemtb.gezinsgericht.repository.QuestionRepository;
import com.jochemtb.gezinsgericht.repository.QuizRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class QuizManager implements QuizRepository.OnQuizSubmitedCallback {

    private static QuizManager instance;
    private int sessionId;
    private List<String> questionstring;
    private List<String[]> possibleAnswers;

    private List<Question> questionList;
    private Quiz currentQuiz;
    private QuizRepository quizRepository;
    private QuizResult quizResult;

    private QuizGenerationListener quizGenerationListener;
    private QuestionRepository questionRepository;

    private List<Integer> questionIds;
    private List<Integer> questionIdsResult;
    private final String LOG_TAG = "QuizManager";
    private final int MAX_TRIES = 3;
    private Context context;
    private int ant;
    private int timesTried;

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    public QuizManager() {
        questionstring = new ArrayList<>();
        possibleAnswers = new ArrayList<>();
        questionList = new ArrayList<>();
        questionIds = new ArrayList<>();
        questionIdsResult = new ArrayList<>();
        timesTried = 0;
    }

    public void setQuizGenerationListener(QuizGenerationListener listener) {
        this.quizGenerationListener = listener;
    }



    public interface QuizGenerationListener {
        void onQuizGenerated();
        void onQuizAbort();
    }
    private boolean result=false;

    public void getSessionQuiz(){

        questionRepository.getSessionQuiz(new QuestionRepository.OnSessionQuizRetrievedCallback() {
            @Override
            public void OnSessionQuizRetrieved(RetrievedSession retrievedSession) {
                Log.d(LOG_TAG, "OnSessionQuizRetrieved");
                questionIds.clear();
                questionIds.addAll(retrievedSession.getQuestionList());
                sessionId = retrievedSession.getSessionId();
                Log.i(LOG_TAG, "questionIds list: "+questionIds+" SessionId= "+sessionId);
                generateQuiz();
            }

            @Override
            public void OnSessionQuizFailed(String errorMessage) {
                Log.d(LOG_TAG, "OnSessionQuizFailed: "+errorMessage);
                quizGenerationListener.onQuizAbort();
            }
        });
    }

    private void generateQuiz() {
        timesTried++;

        if (questionIds == null) {
            Log.e(LOG_TAG, "QuestionIds is null");
            return;
        }

        if (context == null) {
            Log.e(LOG_TAG, "Context is null");
            return;
        }



        questionRepository.getQuestions(questionIds, new QuestionRepository.OnQuestionsRetrievedCallback() {
            @Override
            public void onQuestionsRetrieved(List<Question> questions) {
                if (questions != null && !questions.isEmpty()) {
                    questionList = questions;
                    Log.d(LOG_TAG, "Questions retrieved from database: " + questionList.size());
                    currentQuiz = new Quiz();

                    // Random order of the questions
                    questionList = randomizeQuestions((ArrayList<Question>) questionList);
                    currentQuiz.setQuestionList((ArrayList<Question>) questionList);
                    currentQuiz.setTotalQuestions(questionList.size());
                    Log.d(LOG_TAG, "1th Total questions: " + currentQuiz.getTotalQuestions() + " Question list: " + currentQuiz.getQuestionList().size() + " Question list: " + currentQuiz.getQuestionList().toString());

                    if (quizGenerationListener != null) {
                        quizGenerationListener.onQuizGenerated();
                    }
                } else {
                    Log.e(LOG_TAG, "No questions retrieved, or questions list is empty.");
                }
            }

            @Override
            public void onQuestionsFailed(String errorMessage) {
                Log.e(LOG_TAG, "onQuestionsFailed: " + errorMessage);
                if(timesTried < MAX_TRIES) {
                    Log.i(LOG_TAG, "Trying to generate quiz again. Attempt: " + timesTried);
                    generateQuiz();
                } else {
                    Log.e(LOG_TAG, "Max tries reached, stopping quiz generation");
                    currentQuiz=null;
                    quizGenerationListener.onQuizGenerated();
                }
            }
        });
    }

    private ArrayList<Question> randomizeQuestions(ArrayList<Question> questions) {
        Collections.shuffle(questions);
        return questions;
    }

    public void saveSelectedAnswer(List<Integer> selectedAnswers, RadioGroup answersGroup, int currentQuestionIndex, QuizResult quizResult) {
        this.quizResult = quizResult;
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            View radioButton = answersGroup.findViewById(selectedId);
            int index = answersGroup.indexOfChild(radioButton);
            Log.d(LOG_TAG, "Selected answer" + Integer.toString(index));

            quizResult.setQuestionList((ArrayList<Question>) questionList);
            selectedAnswers.set(currentQuestionIndex, index);
            Log.d(LOG_TAG,"QuestionId: "+String.valueOf(quizResult.getQuestionList().get(currentQuestionIndex).getQuestionid()));

            questionIdsResult.add(quizResult.getQuestionList().get(currentQuestionIndex).getQuestionid());
            Log.d(LOG_TAG,String.valueOf(questionIdsResult));

            quizResult.setQuestionId((ArrayList<Integer>) questionIdsResult);
            quizResult.addToAntSelected(index + 1);
            Log.d(LOG_TAG, quizResult.getAntselected().toString());

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
        Log.d(LOG_TAG, "Submitting quiz answers");
        quizRepository.submitQuizAnswers(quizResult.getQuestionId(), quizResult.getAntselected(), this);
    }

    @Override
    public void onQuizSucces(String message) {
        Log.i(LOG_TAG, "Quiz submitted successfully");
        clearData();
    }

    @Override
    public void onQuizError(String errorMessage) {
        Log.e(LOG_TAG, "Quiz submission failed: " + errorMessage);
        clearData();
    }

    private void clearData() {
        quizResult.setQuestionId(new ArrayList<>());
        quizResult.setAntselected(new ArrayList<>());
        quizResult.setQuestionList(new ArrayList<Question>());
        questionIdsResult.clear();
        Log.i(LOG_TAG, "Quiz result cleared");
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

    public List<String> getQuestionstring() {
        return questionstring;
    }

    public void setQuestionstring(List<String> questionstring) {
        this.questionstring = questionstring;
    }

    public List<String[]> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String[]> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public QuizGenerationListener getQuizGenerationListener() {
        return quizGenerationListener;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        quizRepository = new QuizRepository(context);
        questionRepository = new QuestionRepository(context);
    }

    public int getAnt() {
        return ant;
    }

    public void setAnt(int ant) {
        this.ant = ant;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}

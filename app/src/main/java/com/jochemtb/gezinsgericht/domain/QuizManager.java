package com.jochemtb.gezinsgericht.domain;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.jochemtb.gezinsgericht.GUI.QuizActivity;
import com.jochemtb.gezinsgericht.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public final class QuizManager {

    private static QuizManager instance;
    private List<String> questionstring;
    private List<String[]> possibleAnswers;
    private List<Question> questionList;
    private Quiz currentQuiz;

    private QuizGenerationListener quizGenerationListener;

    private List<Integer> questionIds;
    private final String LOG_TAG = "QuizManager";
    private Context context;
    private int ant;
    public static QuizManager getInstance() {
        if(instance == null) {
            instance = new QuizManager();
        }

        return instance;
    }

    public QuizManager(Context context) {
        this();
        this.context = context;
    }

    public QuizManager() {
        questionstring = new ArrayList<>();
        possibleAnswers = new ArrayList<>();
        questionList = new ArrayList<>();
        questionIds = new ArrayList<>();
        questionIds.add(1);
        questionIds.add(2);
        questionIds.add(3);
    }
    public void setQuizGenerationListener(QuizGenerationListener listener) {
        this.quizGenerationListener = listener;
    }

    public interface QuizGenerationListener {
        void onQuizGenerated();
    }

    public void generateQuiz() {
        if (questionIds == null) {
            Log.e(LOG_TAG, "QuestionIds is null");
            return;
        }

        if (context == null) {
            Log.e(LOG_TAG, "Context is null");
            return;
        }

        QuestionRepository questionRepository = new QuestionRepository(context);
        questionRepository.getQuestions(questionIds, new QuestionRepository.OnQuestionsRetrievedCallback() {
            @Override
            public void onQuestionsRetrieved(List<Question> questions) {
                if (questions != null) {
                    questionList = questions;
                    Log.d(LOG_TAG, "Questions retrieved from database: " + questionList.size());
                    currentQuiz = new Quiz();
                    currentQuiz.setQuestionList((ArrayList<Question>) questionList);
                    currentQuiz.setTotalQuestions(questionList.size());

                    if (quizGenerationListener != null) {
                        quizGenerationListener.onQuizGenerated();
                    }
                } else {
                    Log.e(LOG_TAG, "Failed to retrieve questions");
                }
            }
        });
    }


//        questionstring.add("Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?");
//        questionstring.add("Hoe actief bent u betrokken bij sociale activiteiten en evenementen?");
//        questionstring.add("Hoe tevreden bent u over de plek waar u woont?");
//
//        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
//        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});
//        possibleAnswers.add(new String[]{"Helemaal geen aandacht", "Weinig aandacht", "Neutraal", "Redelijk veel aandacht", "Zeer veel aandacht"});

//        for (int i = 0; i < questionstring.size(); i++) {
//            questionList.add(new Question(i + 1,
//                    questionstring.get(i),
//                    1,
//                    possibleAnswers.get(i)[0],
//                    possibleAnswers.get(i)[1],
//                    possibleAnswers.get(i)[2],
//                    possibleAnswers.get(i)[3],
//                    possibleAnswers.get(i)[4]));
//        }

//        Quiz quiz = new Quiz();
//        quiz.setQuestionList((ArrayList<Question>) questionList);
//        quiz.setTotalQuestions(questionList.size());
//        return quiz;


    public List<Question> getQuestions() {
        return questionList;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public void saveSelectedAnswer(List<Integer> selectedAnswers, RadioGroup answersGroup, int currentQuestionIndex, QuizResult quizResult) {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            View radioButton = answersGroup.findViewById(selectedId);
            int index = answersGroup.indexOfChild(radioButton);
            Log.d("Selected answer", Integer.toString(index));
            quizResult.setQuestionList((ArrayList<Question>) questionList);
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getAnt() {
        return ant;
    }

    public void setAnt(int ant) {
        this.ant = ant;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }
}

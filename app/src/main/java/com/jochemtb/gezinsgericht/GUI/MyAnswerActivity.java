package com.jochemtb.gezinsgericht.GUI;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import java.util.ArrayList;
import java.util.List;

public class MyAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_myanswers);

        //DatabaseHelper dbHelper = new DatabaseHelper();
        //List<int[]> answers = dbHelper.getAnswers();
        //List<String[]> questions = dbHelper.getQuestions();
        List<String[]> answers = new ArrayList<>();
        List<String[]> questions = new ArrayList<>();
        String[] answeredquestions = {
                "Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?",
                "Hoe actief bent u betrokken bij sociale activiteiten en evenementen?",
                "Hoe tevreden bent u over de plek waar u woont?"
        };
        String[] answersgiven = {
                "Weinig aandacht",
                "Neutraal",
                "Zeer tevreden"
        };

        questions.add(answeredquestions);
        answers.add(answersgiven);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAnswerAdapter adapter = new MyAnswerAdapter(answers, questions);
        recyclerView.setAdapter(adapter);
    }
}

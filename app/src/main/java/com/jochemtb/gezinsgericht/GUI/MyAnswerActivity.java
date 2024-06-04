package com.jochemtb.gezinsgericht.GUI;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.MyAnswerAdapter;
import com.jochemtb.gezinsgericht.domain.Session;

import java.util.ArrayList;
import java.util.List;

public class MyAnswerActivity extends AppCompatActivity {

    private int mSessionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_myanswers);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", 0);

        setupBackButton(); //Sets up the "<--" button

        //DatabaseHelper dbHelper = new DatabaseHelper();
        //List<int[]> answers = dbHelper.getAnswers();
        //List<String[]> questions = dbHelper.getQuestions();

        List<String> answers = new ArrayList<>();
        List<String> questions = new ArrayList<>();

        questions.add("Hoeveel aandacht besteed u aan gezonde voeding en lichaamsbeweging?");
        questions.add("Hoe actief bent u betrokken bij sociale activiteiten en evenementen?");
        questions.add("Hoe tevreden bent u over de plek waar u woont?");

        answers.add("Weinig aandacht");
        answers.add("Neutraal");
        answers.add("Zeer tevreden");

        RecyclerView recyclerView = findViewById(R.id.recycler_view); //Sets the data from MyAnswerAdapter to the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAnswerAdapter adapter = new MyAnswerAdapter(answers, questions);
        recyclerView.setAdapter(adapter);
    }

    private void setupBackButton(){ //Navigates you back to ResultsActivity
        ImageButton back = findViewById(R.id.back_to_results);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(MyAnswerActivity.this, ResultsActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }
}

package com.jochemtb.gezinsgericht.GUI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import java.util.List;

public class MyAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myanswers);

        DatabaseHelper dbHelper = new DatabaseHelper();
        List<String[]> answers = dbHelper.getAnswers();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAnswerAdapter adapter = new MyAnswerAdapter(answers);
        recyclerView.setAdapter(adapter);
    }
}

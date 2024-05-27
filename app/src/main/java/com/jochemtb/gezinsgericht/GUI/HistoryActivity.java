package com.jochemtb.gezinsgericht.GUI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;

import adapters.HistoryListAdapter;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistoryList;
    private HistoryListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistoryList = findViewById(R.id.RV_history_list);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new HistoryListAdapter(this);
        rvHistoryList.setAdapter(mAdapter);

    }
}

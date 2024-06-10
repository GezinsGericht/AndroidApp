// MyAnswerActivity.java
package com.jochemtb.gezinsgericht.GUI;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.adapters.HistoryAnswerAdapter;
import com.jochemtb.gezinsgericht.domain.GroupedHistoryAnswer;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.repository.HistoryAnswerRepository;
import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAnswerActivity extends AppCompatActivity implements HistoryAnswerRepository.HistoryAnswersCallback {

    private int mSessionId;
    private Session mSession;
    private HistoryAnswerRepository historyAnswerRepository;
    private HistoryAnswerAdapter adapter;
    private List<GroupedHistoryAnswer> groupedHistoryAnswers;
    private final String LOG_TAG = "MyAnswerActivity";
    private ProgressBar loadingScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_myanswers);
        loadingScreen = findViewById(R.id.PB_myAnswers_loadingIcon);
        setLoadingScreen(true);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", mSessionId);

        setupBackButton(); // Sets up the "<--" button

        groupedHistoryAnswers = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view); // Sets the data from HistoryAnswerAdapter to the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAnswerAdapter(groupedHistoryAnswers);
        recyclerView.setAdapter(adapter);

        // Initialize the repository and fetch the data
        historyAnswerRepository = new HistoryAnswerRepository(this);
        historyAnswerRepository.getHistory(this, intent); // Pass the Intent here

    }

    private void setLoadingScreen(boolean bool) {

        if(bool){
            loadingScreen.setVisibility(ProgressBar.VISIBLE);
        } else {
            loadingScreen.setVisibility(ProgressBar.GONE);
        }
    }

    @Override
    public void onHistoryAnswersFetched(String message) {
        Log.i(LOG_TAG, "onHistoryAnswersFetched: " + message);
        List<HistoryAnswerResponse> historyAnswers = historyAnswerRepository.getDao().getHistoryAnswerList();

        // Group the historyAnswers by Habitat_Name
        Map<String, List<HistoryAnswerResponse>> groupedMap = new HashMap<>();
        for (HistoryAnswerResponse response : historyAnswers) {
            if (!groupedMap.containsKey(response.getHabitat_Name())) {
                groupedMap.put(response.getHabitat_Name(), new ArrayList<>());
            }
            groupedMap.get(response.getHabitat_Name()).add(response);
        }

        groupedHistoryAnswers.clear();
        for (Map.Entry<String, List<HistoryAnswerResponse>> entry : groupedMap.entrySet()) {
            groupedHistoryAnswers.add(new GroupedHistoryAnswer(entry.getKey(), entry.getValue()));
        }

        // Notify adapter about data changes
        adapter.notifyDataSetChanged();
        setLoadingScreen(false);
    }

    @Override
    public void onHistoryAnswersError(String errorMessage) {
        Log.e(TAG, "onHistoryAnswersError: " + errorMessage);
        Toast.makeText(this, R.string.somethingWentWrongToast, Toast.LENGTH_LONG).show();
        startActivity(new Intent(MyAnswerActivity.this, ResultsActivity.class).putExtra("session", mSessionId));
    }


    private void setupBackButton() { // Navigates you back to ResultsActivity
        ImageButton back = findViewById(R.id.back_to_results);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(MyAnswerActivity.this, ResultsActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }
}

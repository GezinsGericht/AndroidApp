package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.RadarDataSet;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.dao.ResultsDao;
import com.jochemtb.gezinsgericht.domain.RadarChart;
import com.jochemtb.gezinsgericht.domain.ResultsItem;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.domain.User;
import com.jochemtb.gezinsgericht.repository.HistoryRepository;
import com.jochemtb.gezinsgericht.repository.ResultsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity implements ResultsRepository.ResultsCallback {

    private final String LOG_TAG = "ResultsActivity";
    private RadarChart radarChartHelper;
    private Session mSession;
    private int mSessionId;
    private ResultsRepository resultsRepository;
    private ResultsDao resultsDao;
    private ProgressBar loadingScreen;
    private HashMap<Integer, HashMap<Integer, Double>> userHabitatAverageValues;
    private GridLayout mResultsCheckboxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", 0);
        Log.i(LOG_TAG, "Session: " + mSessionId);

        initViewComponents(); //Sets the checkboxes by id

        resultsRepository = new ResultsRepository(this);
        resultsRepository.getResults(this, String.valueOf(mSessionId));
        this.resultsDao = resultsRepository.getDao();


    }

    private void initViewComponents() {
        radarChartHelper = new RadarChart(findViewById(R.id.Radarchart));
        mResultsCheckboxes = findViewById(R.id.GL_results_checkboxes);

    }

    private void createCheckboxes(HashMap<Integer, HashMap<Integer, Double>> userHabitatAverageValues) {
        Log.d(LOG_TAG, "UserHabitatAverageValues: " + userHabitatAverageValues);

        // Get the users from the session
        Set<Integer> userIds = userHabitatAverageValues.keySet();
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};

        // Create a checkbox for each userId
        int colorIndex = 0;
        for (int userId : userIds) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(String.valueOf(userId));
            checkBox.setChecked(false);
            int color = colors[colorIndex % colors.length];
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // unchecked
                            new int[]{android.R.attr.state_checked}  // checked
                    },
                    new int[]{
                            Color.BLACK,
                            color
                    }
            );
            checkBox.setButtonTintList(colorStateList);
            mResultsCheckboxes.addView(checkBox);
            colorIndex++;
        }
    }

    private void setupCheckBoxListeners() {
        int checkboxCount = mResultsCheckboxes.getChildCount();
        // Define the colors for the datasets
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};
        // Set up a listener for each checkbox
        for (int i = 0; i < checkboxCount; i++) {
            CheckBox checkBox = (CheckBox) mResultsCheckboxes.getChildAt(i);
            RadarDataSet dataSet = radarChartHelper.getDataSet(i);
            int color = colors[i % colors.length];

            checkBox.setOnClickListener(v -> {
                if (checkBox.isChecked()) {
                    checkBox.setTextColor(color);
                    radarChartHelper.toggleDataSetVisibility(dataSet, true);
                } else {
                    checkBox.setTextColor(Color.BLACK);
                    radarChartHelper.toggleDataSetVisibility(dataSet, false);
                }
            });
        }
    }

    private void setupMyAnswerButton() {
        Button myAnswer = findViewById(R.id.results_show);
        myAnswer.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, MyAnswerActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }

    private void setupCloseButton() {
        Button afsluiten = findViewById(R.id.results_close);
        afsluiten.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class))); //Switches the page to MainActivity
    }

    private void setLoadingScreen(Boolean bool) {
        if (bool) {
            mResultsCheckboxes.setVisibility(View.INVISIBLE);
            loadingScreen.setVisibility(View.VISIBLE);
        } else {
            mResultsCheckboxes.setVisibility(View.INVISIBLE);
            loadingScreen.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResultsFetched(List<ResultsItem> results) {
        Set<Integer> uniqueUserIds = new HashSet<>();
        for (ResultsItem item : results) {
            uniqueUserIds.add(item.getUserId());
        }
        // Create a map to store the AnswerValues for each HabitatId per user
        HashMap<Integer, HashMap<Integer, List<Integer>>> userHabitatAnswerValues = new HashMap<>();

        // Populate the map
        for (ResultsItem item : results) {
            int userId = item.getUserId();
            int habitatId = item.getHabitatId();
            int answerValue = item.getAnswerValue();

            // If the user is not in the map, add them
            if (!userHabitatAnswerValues.containsKey(userId)) {
                userHabitatAnswerValues.put(userId, new HashMap<>());
            }

            // If the habitat is not in the user's map, add it
            if (!userHabitatAnswerValues.get(userId).containsKey(habitatId)) {
                userHabitatAnswerValues.get(userId).put(habitatId, new ArrayList<>());
            }

            // Add the answer value to the list for this user and habitat
            userHabitatAnswerValues.get(userId).get(habitatId).add(answerValue);
        }

        // Create a map to store the average AnswerValue for each HabitatId per user
        userHabitatAverageValues = new HashMap<>();

        // Calculate the averages
        for (int userId : userHabitatAnswerValues.keySet()) {
            userHabitatAverageValues.put(userId, new HashMap<>());
            for (int habitatId : userHabitatAnswerValues.get(userId).keySet()) {
                List<Integer> answerValues = userHabitatAnswerValues.get(userId).get(habitatId);
                double average = answerValues.stream().mapToInt(val -> val).average().orElse(0.0);
                userHabitatAverageValues.get(userId).put(habitatId, average);
                Log.d(LOG_TAG, "User " + userId + " Habitat " + habitatId + " Average " + average);
            }
        }
        Log.d(LOG_TAG, "UserHabitatAverageValues: " + userHabitatAverageValues);
        createCheckboxes(userHabitatAverageValues);
        radarChartHelper.createDataSetFromSession(userHabitatAverageValues); //Sets the dummy data with help from RadarChartHelper
        setupCheckBoxListeners(); //Sets the functionality for the checkboxes
        setupCloseButton(); //Sets up the "afsluiten" button
        setupMyAnswerButton(); //Sets up the "mijn antwoorden" button
        Log.i(LOG_TAG, "Results data updated");

    }

}

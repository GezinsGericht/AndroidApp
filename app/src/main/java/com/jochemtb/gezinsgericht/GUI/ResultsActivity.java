package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.RadarDataSet;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.dao.ResultsDao;
import com.jochemtb.gezinsgericht.domain.RadarChart;
import com.jochemtb.gezinsgericht.domain.ResultsItem;
import com.jochemtb.gezinsgericht.repository.ResultsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity implements ResultsRepository.ResultsCallback {

    private final String LOG_TAG = "ResultsActivity";
    private RadarChart radarChartHelper;
    private int mSessionId;
    private ResultsRepository resultsRepository;
    private ResultsDao resultsDao;
    private HashMap<String, HashMap<Integer, Double>> userHabitatAverageValues;
    private GridLayout mResultsCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", 0);
        Log.i(LOG_TAG, "Session: " + mSessionId);

        setupCloseButton(); // Sets up the "afsluiten" button
        setupMyAnswerButton(); // Sets up the "mijn antwoorden" button
        setupGoalsButton(); // Sets up the "goals" button
        initViewComponents(); // Sets the checkboxes by id

        resultsRepository = new ResultsRepository(this);
        resultsRepository.getResults(this, String.valueOf(mSessionId));
        this.resultsDao = resultsRepository.getDao();

    }

    private void initViewComponents() {
        radarChartHelper = new RadarChart(findViewById(R.id.Radarchart));
        mResultsCheckboxes = findViewById(R.id.GL_results_checkboxes);

    }

    private void createCheckboxes(HashMap<String, HashMap<Integer, Double>> userHabitatAverageValues) {
        Log.d(LOG_TAG, "UserHabitatAverageValues: " + userHabitatAverageValues);

        // Get the users from the session
        Set<String> userNames = userHabitatAverageValues.keySet();
        int[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA };

        // Create a checkbox for each userId
        int colorIndex = 0;
        for (String userName : userNames) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(userName);
            checkBox.setChecked(false);
            int color = colors[colorIndex % colors.length];
            ColorStateList colorStateList = new ColorStateList(
                    new int[][] {
                            new int[] { -android.R.attr.state_checked }, // unchecked
                            new int[] { android.R.attr.state_checked } // checked
                    },
                    new int[] {
                            Color.BLACK,
                            color
                    });
            checkBox.setButtonTintList(colorStateList);
            mResultsCheckboxes.addView(checkBox);

            if (colorIndex == 0) {
                checkBox.setChecked(true);
                checkBox.setTextColor(color);
                RadarDataSet dataSet = radarChartHelper.getDataSet(0);
                radarChartHelper.toggleDataSetVisibility(dataSet, true);
            }

            colorIndex++;
        }

    }

    private void setupCheckBoxListeners() {
        int checkboxCount = mResultsCheckboxes.getChildCount();
        // Define the colors for the datasets
        int[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA };
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
        Button close = findViewById(R.id.results_close);
        close.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class))); // Switches
                                                                                                            // the page
                                                                                                            // to
                                                                                                            // MainActivity
    }

    private void setupGoalsButton() {
        Button goals = findViewById(R.id.results_goals);
        goals.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, GoalsActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }

    @Override
    public void onResultsFetched(List<ResultsItem> results) {
        Set<String> users = new HashSet<>();
        for (ResultsItem item : results) {
            users.add(item.getName());
            Log.d(LOG_TAG, "User: " + item.getName());
        }
        // Create a map to store the AnswerValues for each HabitatId per user
        HashMap<String, HashMap<Integer, List<Integer>>> userHabitatAnswerValues = new HashMap<>();

        // Populate the map
        for (ResultsItem item : results) {
            String userName = item.getName();
            int habitatId = item.getHabitatId();
            int answerValue = item.getAnswerValue();
            Log.d(LOG_TAG, "User: " + userName + " Habitat: " + habitatId + " Answer: " + answerValue);

            // If the user is not in the map, add them
            if (!userHabitatAnswerValues.containsKey(userName)) {
                userHabitatAnswerValues.put(userName, new HashMap<>());
            }

            // If the habitat is not in the user's map, add it
            if (!userHabitatAnswerValues.get(userName).containsKey(habitatId)) {
                userHabitatAnswerValues.get(userName).put(habitatId, new ArrayList<>());
            }

            // Add the answer value to the list for this user and habitat
            userHabitatAnswerValues.get(userName).get(habitatId).add(answerValue);
        }

        // Create a map to store the average AnswerValue for each HabitatId per user
        userHabitatAverageValues = new HashMap<>();

        // Calculate the averages
        for (String userName : userHabitatAnswerValues.keySet()) {
            userHabitatAverageValues.put(userName, new HashMap<>());
            for (int habitatId : userHabitatAnswerValues.get(userName).keySet()) {
                List<Integer> answerValues = userHabitatAnswerValues.get(userName).get(habitatId);
                double average = answerValues.stream().mapToInt(val -> val).average().orElse(0.0);
                userHabitatAverageValues.get(userName).put(habitatId, average);
                Log.d(LOG_TAG, "User " + userName + " Habitat " + habitatId + " Average " + average);
            }
        }

        Log.d(LOG_TAG, "UserHabitatAverageValues: " + userHabitatAverageValues);
        radarChartHelper.createDataSetFromSession(userHabitatAverageValues); // Sets the dummy data with help from
                                                                             // RadarChartHelper
        createCheckboxes(userHabitatAverageValues);
        setupCheckBoxListeners(); // Sets the functionality for the checkboxes
        Log.i(LOG_TAG, "Results data updated");

    }

}

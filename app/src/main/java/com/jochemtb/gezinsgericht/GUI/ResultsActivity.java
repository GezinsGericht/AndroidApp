package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.RadarDataSet;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.dao.ResultsDao;
import com.jochemtb.gezinsgericht.domain.RadarChart;
import com.jochemtb.gezinsgericht.domain.ResultsItem;
import com.jochemtb.gezinsgericht.repository.NameRepository;
import com.jochemtb.gezinsgericht.repository.ResultsRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity implements ResultsRepository.ResultsCallback {

    private final String LOG_TAG = "ResultsActivity";
    private RadarChart radarChartHelper;
    private int mSessionId;
    private String mLoggedUserName;
    private ResultsRepository resultsRepository;
    private NameRepository nameRepository;
    private SharedPreferences sharedPref;
    private ResultsDao resultsDao;
    private HashMap<String, HashMap<Integer, Double>> userHabitatAverageValues;
    private GridLayout mResultsCheckboxes;
    private TextView title, description;
    private Button myAnswer, goals, close;
    private View mainChart;
    private ProgressBar loadingScreen;

    private int colorIndex = 0;
    private int row = 0;
    private int col = 1;
    private int columnCount = 3; // Aantal kolommen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        mSessionId = intent.getIntExtra("session", 0);
        Log.i(LOG_TAG, "Session: " + mSessionId);
        sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);

        setupCloseButton(); // Sets up the "afsluiten" button
        setupMyAnswerButton(); // Sets up the "mijn antwoorden" button
        setupGoalsButton(); // Sets up the "goals" button
        initViewComponents(); // Sets the checkboxes by id
        setLoadingScreen(true); // Sets the loading screen


        nameRepository = new NameRepository(this);
        resultsRepository = new ResultsRepository(this);
        resultsRepository.getResults(this, String.valueOf(mSessionId));
        this.resultsDao = resultsRepository.getDao();

    }

    private void initViewComponents() {
        radarChartHelper = new RadarChart(findViewById(R.id.RC_result_mainChart));
        mResultsCheckboxes = findViewById(R.id.GL_results_checkboxes);

        title = findViewById(R.id.results_title);
        description = findViewById(R.id.results_text);

        myAnswer = findViewById(R.id.BT_results_show); // Button to show my answers
        goals = findViewById(R.id.BT_results_goals); // Button to show goals
        close = findViewById(R.id.BT_results_close); // Button to close the results

        loadingScreen = findViewById(R.id.PB_results_loading);
//        mainChart = findViewById(R.id.RC_result_mainChart);
    }

    private void upCol(){
        col++;
        if (col == columnCount) {
            col = 0;
            row++;
        }
    }

    private void createCheckboxes(HashMap<String, HashMap<Integer, Double>> userHabitatAverageValues) {
        Log.d(LOG_TAG, "Creating checkboxes");

        String apiKey = sharedPref.getString("jwtToken", "");
        Log.d(LOG_TAG, "API Key: " + apiKey); // Log the API key for debugging
        String userId = extractUserIdFromApiKey(apiKey);

        nameRepository.getName(userId, new NameRepository.NameCallback() {
            @Override
            public void onNameFetched(String loggedInUserName) {
                Log.d(LOG_TAG, "Name: " + loggedInUserName);

                Set<String> userNames = userHabitatAverageValues.keySet();
                Log.d(LOG_TAG, "Usernames: " + userNames);
                int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};




                String[] userNamePart = loggedInUserName.split(" ");
                mLoggedUserName = userNamePart[0];
//                userNames.remove(loggedInUserName);
//
//                // Start the loop with the logged-in user's userName
//                createCheckboxForUser(loggedInUserName, colors, colorIndex, row, col, columnCount);
//                colorIndex++;
//                col++;
//                if (col == columnCount) {
//                    col = 0;
//                    row++;
//                }

                // Continue with the rest of the userNames
                for (String userName : userNames) {
                    createCheckboxForUser(userName, colors, colorIndex, row, col, columnCount);
                    colorIndex++;
                    col++;
                    if (col == columnCount) {
                        col = 0;
                        row++;
                    }
                }
                setupCheckBoxListeners(); // Sets the functionality for the checkboxes
            }

            @Override
            public void onNameError(String errorMessage) {
                Log.e(LOG_TAG, "Failed to fetch name: " + errorMessage);
            }
        });
    }

    private void createCheckboxForUser(String userName, int[] colors, int colorIndex, int row, int col, int columnCount) {
        CheckBox checkBox = new CheckBox(ResultsActivity.this);
        checkBox.setText(userName);
        checkBox.setChecked(false);

        //mooie kleurtjes
        int color = colors[colorIndex % colors.length];
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, // unchecked
                        new int[]{android.R.attr.state_checked} // checked
                },
                new int[]{
                        Color.BLACK,
                        color
                });
        checkBox.setButtonTintList(colorStateList);

        // Stel de positie van de checkbox in
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(col, 1, 1f);
        params.rowSpec = GridLayout.spec(row, 1, 1f);
        checkBox.setLayoutParams(params);



        // Eerste checkbox aanvinken
        if (userName.equals(mLoggedUserName)) {
            Log.d(LOG_TAG, "Checkbox for logged in user: " + userName);
            checkBox.setChecked(true);
            checkBox.setTextColor(color);
            params.rowSpec = GridLayout.spec(0, 1, 1f);
            params.columnSpec = GridLayout.spec(0, 1, 1f);
            checkBox.setLayoutParams(params);
            RadarDataSet dataSet = radarChartHelper.getDataSet(colorIndex);
            radarChartHelper.toggleDataSetVisibility(dataSet, true);
        }
        mResultsCheckboxes.addView(checkBox);
        Log.d(LOG_TAG, "Checkbox created for user: " + userName);
    }

    private void setupCheckBoxListeners() {
        Log.d(LOG_TAG, "Setting up checkbox listeners");
        int checkboxCount = mResultsCheckboxes.getChildCount();

        // Define the colors for the datasets
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};

        // Set up a listener for each checkbox
        for (int i = 0; i < checkboxCount; i++) {
            CheckBox checkBox = (CheckBox) mResultsCheckboxes.getChildAt(i);
            Log.d(LOG_TAG, "Checkbox: " + checkBox.getText());
            RadarDataSet dataSet = radarChartHelper.getDataSet(i);
            Log.d(LOG_TAG, "DataSet: " + radarChartHelper.getDataSet(i));
            int color = colors[i % colors.length];

            //aan en uit zetten
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

    private void setLoadingScreen(boolean bool) {
        if(bool){
            mResultsCheckboxes.setVisibility(GridLayout.GONE);
            title.setVisibility(TextView.GONE);
            description.setVisibility(TextView.GONE);
            myAnswer.setVisibility(Button.GONE);
            goals.setVisibility(Button.GONE);
            close.setVisibility(Button.GONE);
//            mainChart.setVisibility(View.GONE);
            loadingScreen.setVisibility(ProgressBar.VISIBLE);
        } else {
            mResultsCheckboxes.setVisibility(GridLayout.VISIBLE);
            title.setVisibility(TextView.VISIBLE);
            description.setVisibility(TextView.VISIBLE);
            myAnswer.setVisibility(Button.VISIBLE);
            goals.setVisibility(Button.VISIBLE);
            close.setVisibility(Button.VISIBLE);
//            mainChart.setVisibility(View.VISIBLE);
            loadingScreen.setVisibility(ProgressBar.GONE);
        }
    }

    private void setupMyAnswerButton() {
        Button myAnswer = findViewById(R.id.BT_results_show);
        myAnswer.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, MyAnswerActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }

    private void setupCloseButton() {
        Button close = findViewById(R.id.BT_results_close);
        close.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, HistoryActivity.class))); // Switches the page to MainActivity
    }

    private void setupGoalsButton() {
        Button goals = findViewById(R.id.BT_results_goals);
        goals.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, GoalsActivity.class);
            intent.putExtra("session", mSessionId);
            startActivity(intent);
        });
    }

    @Override
    public void onResultsFetched(List<ResultsItem> results) {
        // Create a map to store the AnswerValues for each HabitatId per user
        HashMap<String, HashMap<Integer, List<Integer>>> userHabitatAnswerValues = new HashMap<>();
        Log.d(LOG_TAG, "Results fetched: " + results.size() + " items: "+results);

        // Continue populating the map for other users
        for (ResultsItem item : results) {
            Log.d(LOG_TAG, "ResultsItem: " + item.getName() + " " + item.getHabitatId() + " " + item.getAnswerValue());
            //Only get first name
            String[] userNamePart = item.getName().split(" ");
            String userName = userNamePart[0];
            int habitatId = item.getHabitatId();
            int answerValue = item.getAnswerValue();

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


            // Create a map to store the average AnswerValue for each HabitatId per user
            userHabitatAverageValues = new HashMap<>();

            // Calculate the averages
            for (String name : userHabitatAnswerValues.keySet()) {
                userHabitatAverageValues.put(name, new HashMap<>());
                for (int habitatID : userHabitatAnswerValues.get(name).keySet()) {
                    List<Integer> answerValues = userHabitatAnswerValues.get(name).get(habitatID);
                    double average = answerValues.stream().mapToInt(val -> val).average().orElse(0.0);
                    userHabitatAverageValues.get(name).put(habitatID, average);
                }
            }
        }
        radarChartHelper.createDataSetFromSession(userHabitatAverageValues); // Sets the data for the radar chart
        createCheckboxes(userHabitatAverageValues); // Creates the checkboxes for the users
        setLoadingScreen(false);
        Log.i(LOG_TAG, "Results data updated");
    }


    private String extractUserIdFromApiKey(String apiKey) {

        apiKey = sharedPref.getString("jwtToken", "");

        try {
            if (apiKey == null || apiKey.isEmpty()) {
                Log.e(LOG_TAG, "API key is null or empty.");
                return null;
            }

            String[] splitString = apiKey.split("\\.");
            if (splitString.length != 3) {
                Log.e(LOG_TAG, "Invalid JWT token structure.");
                return null;
            }

            String base64EncodedBody = splitString[1];
            Log.d(LOG_TAG, "Encoded Body: " + base64EncodedBody);

            String body = new String(Base64.decode(base64EncodedBody, Base64.DEFAULT));
            Log.d(LOG_TAG, "Decoded Body: " + body);

            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("UserId");

        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to parse API key: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void onResultsError(String errorMessage) {
        Log.e(LOG_TAG, "onResultsError: " + errorMessage);
        Toast.makeText(this, R.string.somethingWentWrongToast, Toast.LENGTH_LONG).show();
        startActivity(new Intent(ResultsActivity.this, HistoryActivity.class));

    }

}

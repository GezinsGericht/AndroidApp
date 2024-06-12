package com.jochemtb.gezinsgericht.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.LineChartEntry;
import com.jochemtb.gezinsgericht.domain.LineChartHelper;
import com.jochemtb.gezinsgericht.repository.LineChartRepository;
import com.jochemtb.gezinsgericht.repository.NameRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LineChartRepository.LineChartCallback {

    private LineChartHelper lineChartHelper;
    private Button navbar_1, navbar_2, navbar_3;
    private RadioGroup radioGroup;

    private ImageView settingsLogo;
    private LineChartRepository lineChartRepository;
    private List<LineChartEntry> allEntries = new ArrayList<>();
    private Map<RadioButton, String> radioButtonHabitatMap = new HashMap<>();
    private TextView TV_Welcome, TV_WelcomeGG, usernameTv, titleRadioGroup;
    private ProgressBar loadingScreen;
    private View dividerBottom, dividerTop;
    private LineChart lineChart;

    private SharedPreferences sharedPref;
    private NameRepository nameRepository;
    private final String LOG_TAG = "HomepageActivity";

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        loadingScreen = findViewById(R.id.PB_main_loadingIcon);
        lineChartHelper = new LineChartHelper((LineChart) findViewById(R.id.chart_homepage));
        lineChartRepository = new LineChartRepository(this);
        TV_Welcome = findViewById(R.id.TV_welkom_message);
        TV_WelcomeGG = findViewById(R.id.TV_welkom_message_gg);
        usernameTv = findViewById(R.id.TV_homepage_username);
        settingsLogo = findViewById(R.id.IV_main_settings);
        navbar_1 = findViewById(R.id.BTN_navbar1);
        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        radioGroup = findViewById(R.id.RG_homepage);
        titleRadioGroup = findViewById(R.id.tv_PersoonlijkeProgressie);
        dividerBottom = findViewById(R.id.dividerBtm);
        dividerTop = findViewById(R.id.dividerTop);
        lineChart = findViewById(R.id.chart_homepage);
        sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);

        //setLoadingScreen
        setLoadingScreen(true);

        // Set up navigation
        navToSession();

        // Initialize checkboxes
        initViewComponents();
        setupRadioButtonListeners(radioGroup);

        // Fetch data when the activity is created
        lineChartRepository.getLineChart(this);

        // Fetch and display the user name
        nameRepository = new NameRepository(this); // Create an instance of NameRepository

        String apiKey = sharedPref.getString("jwtToken", "");
        Log.d(LOG_TAG, "API Key: " + apiKey); // Log the API key for debugging
        String userId = extractUserIdFromApiKey(apiKey);

        if (userId != null) {
            fetchAndDisplayUserName(userId);
        } else {
            Log.d(LOG_TAG, "Invalid API key"); // Log for debugging
        }
    }

    private void setLoadingScreen(boolean bool) {
        if (bool){
            TV_Welcome.setVisibility(View.GONE);
            TV_WelcomeGG .setVisibility(View.GONE);
            usernameTv.setVisibility(View.GONE);
            settingsLogo.setVisibility(View.GONE);
            navbar_1.setVisibility(View.GONE);
            navbar_2.setVisibility(View.GONE);
            navbar_3.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            titleRadioGroup.setVisibility(View.GONE);
            dividerBottom.setVisibility(View.GONE);
            dividerTop.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
            loadingScreen.setVisibility(View.VISIBLE);
        } else {
            TV_Welcome.setVisibility(View.VISIBLE);
            TV_WelcomeGG .setVisibility(View.VISIBLE);
            usernameTv.setVisibility(View.VISIBLE);
            settingsLogo.setVisibility(View.VISIBLE);
            navbar_1.setVisibility(View.VISIBLE);
            navbar_2.setVisibility(View.VISIBLE);
            navbar_3.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            titleRadioGroup.setVisibility(View.VISIBLE);
            dividerBottom.setVisibility(View.VISIBLE);
            dividerTop.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.INVISIBLE);
            lineChart.setVisibility(View.INVISIBLE);
            loadingScreen.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLineChartFetched(List<LineChartEntry> entries) {
        allEntries = entries; // Save all entries
        updateChartBasedOnSelectedHabitat();
        setLoadingScreen(false);
    }

    @Override
    public void onLineChartError(String errorMessage) {
        Log.d(LOG_TAG, "onLineChartError");
        Log.e(LOG_TAG, errorMessage);
        setLoadingScreen(false);
        Toast.makeText(this, R.string.somethingWentWrongToast,Toast.LENGTH_SHORT).show();
    }

    private void initViewComponents() {
        radioGroup = findViewById(R.id.RG_homepage);
        RadioButton radiobutton_1 = findViewById(R.id.RB_homepage_1);
        RadioButton radiobutton_2 = findViewById(R.id.RB_homepage_2);
        RadioButton radiobutton_3 = findViewById(R.id.RB_homepage_3);
        RadioButton radiobutton_4 = findViewById(R.id.RB_homepage_4);
        RadioButton radiobutton_5 = findViewById(R.id.RB_homepage_5);
        RadioButton radiobutton_6 = findViewById(R.id.RB_homepage_6);
        RadioButton radiobutton_7 = findViewById(R.id.RB_homepage_7);

        radioButtonHabitatMap.put(radiobutton_1, "Zingeving");
        radioButtonHabitatMap.put(radiobutton_2, "Werk & Activiteiten");
        radioButtonHabitatMap.put(radiobutton_3, "Financiën");
        radioButtonHabitatMap.put(radiobutton_4, "Psychische gezondheid");
        radioButtonHabitatMap.put(radiobutton_5, "Wonen");
        radioButtonHabitatMap.put(radiobutton_6, "Lichamelijke gezondheid");
        radioButtonHabitatMap.put(radiobutton_7, "Sociale relaties");

    }
    private RadioButton lastCheckedRadioButton = null;

    private void setupRadioButtonListeners(RadioGroup radioGroup) {
        for (final Map.Entry<RadioButton, String> entry : radioButtonHabitatMap.entrySet()) {
            entry.getKey().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton clickedRadioButton = (RadioButton) v;
                    if (clickedRadioButton.equals(lastCheckedRadioButton)) {
                        // If the clicked radio button is already checked, uncheck it and show welcome text
                        lastCheckedRadioButton = null;
                        clickedRadioButton.setChecked(false);
                        showWelcomeText();
                    } else {
                        // If the clicked radio button is not checked, check it and update the chart
                        if (lastCheckedRadioButton != null) {
                            lastCheckedRadioButton.setChecked(false);
                        }
                        lastCheckedRadioButton = clickedRadioButton;
                        updateChartBasedOnSelectedHabitat();
                    }
                }
            });
        }
    }


    private void updateChartBasedOnSelectedHabitat() {
        boolean anyChecked = false;
        for (Map.Entry<RadioButton, String> entry : radioButtonHabitatMap.entrySet()) {
            if (entry.getKey().isChecked()) {
                updateChart(entry.getValue());
                anyChecked = true;
                break;
            }
        }

        if (!anyChecked) {
            showWelcomeText();
        }
    }

    private void updateChart(String habitatName) {
        List<Entry> chartEntries = new ArrayList<>();
        final List<String> dates = new ArrayList<>();

        for (LineChartEntry entry : allEntries) {
            if (entry.getHabitat_Name().equals(habitatName)) {
                float yValue = Float.parseFloat(entry.getAverage_Answer_Score());
                chartEntries.add(new Entry(chartEntries.size(), yValue));
                dates.add(entry.getSessie_Datum());
            }
        }

        lineChartHelper.clearEntries();
        lineChartHelper.addEntries(chartEntries);
        lineChartHelper.addDataSet(habitatName, Color.RED, 2.0f);

        // Hide welcome messages
        TV_Welcome.setVisibility(View.GONE);
        TV_WelcomeGG.setVisibility(View.GONE);

        // Show the chart
        lineChartHelper.getLineChart().setVisibility(View.VISIBLE);

        lineChartHelper.getLineChart().getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < dates.size()) {
                    return dates.get(index);
                } else {
                    return ""; // Return an empty string for out-of-bounds indices
                }
            }
        });
    }

    private void navToSession() {
        settingsLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                try {
                    Log.i(LOG_TAG, "Uitloggen");
                    sharedPref.edit().remove("jwtToken").apply();
                    Toast.makeText(getBaseContext(), "Uitloggen succesvol", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish(); // Optional: Finish current activity after logout
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error: " + e.getMessage());
                }
            }
        });

        navbar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HistoryActivity
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        navbar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to QuizActivity
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });
    }

    private void showWelcomeText() {
        // Show welcome messages
        TV_Welcome.setVisibility(View.VISIBLE);
        TV_WelcomeGG.setVisibility(View.VISIBLE);

        // Hide the chart
        lineChartHelper.getLineChart().setVisibility(View.INVISIBLE);
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


    private void fetchAndDisplayUserName(String userId) {
        Log.d(LOG_TAG, "Fetching name for user ID: " + userId); // Log for debugging

        nameRepository.getName(userId, new NameRepository.NameCallback() {
            @Override
            public void onNameFetched(String name) {
                Log.d(LOG_TAG, "Fetched name: " + name); // Log for debugging
                usernameTv.setText(name);
            }

            @Override
            public void onNameError(String errorMessage) {
                Log.e(LOG_TAG, "Failed to fetch user name: " + errorMessage); // Log for debugging
            }
        });
    }
}


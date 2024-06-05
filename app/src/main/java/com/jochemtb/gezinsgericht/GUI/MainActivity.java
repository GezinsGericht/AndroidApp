package com.jochemtb.gezinsgericht.GUI;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.LineChartEntry;
import com.jochemtb.gezinsgericht.domain.LineChartHelper;
import com.jochemtb.gezinsgericht.repository.LineChartRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LineChartRepository.LineChartCallback {

    private LineChartHelper lineChartHelper;
    private Button navbar_2, navbar_3;

    private ImageView settingsLogo;
    private LineChartRepository lineChartRepository;
    private List<LineChartEntry> allEntries = new ArrayList<>();
    private Map<CheckBox, String> checkBoxHabitatMap = new HashMap<>();
    private TextView TV_Welcome, TV_WelcomeGG, usernameTv;

    private SharedPreferences sharedPref;
    private final String LOG_TAG = "HomepageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        lineChartHelper = new LineChartHelper((LineChart) findViewById(R.id.chart_homepage));
        lineChartRepository = new LineChartRepository(this);
        TV_Welcome = findViewById(R.id.TV_welkom_message);
        TV_WelcomeGG = findViewById(R.id.TV_welkom_message_gg);
        usernameTv = findViewById(R.id.TV_homepage_username);
        settingsLogo = findViewById(R.id.IV_main_settings);
        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);

        // Set up navigation
        navToSession();

        // Initialize checkboxes
        initViewComponents();
        setupCheckboxListeners();

        // Fetch data when the activity is created
        lineChartRepository.getLineChart(this);

        // Set the username
        usernameTv.setText(sharedPref.getString("username", "NAME_PLACEHOLDER"));
    }

    @Override
    public void onLineChartFetched(List<LineChartEntry> entries) {
        allEntries = entries;  // Save all entries
        updateChartBasedOnSelectedHabitat();
    }

    private void initViewComponents() {
        CheckBox checkbox_1 = findViewById(R.id.CB_homepage_1);
        CheckBox checkbox_2 = findViewById(R.id.CB_homepage_2);
        CheckBox checkbox_3 = findViewById(R.id.CB_homepage_3);
        CheckBox checkbox_4 = findViewById(R.id.CB_homepage_4);
        CheckBox checkbox_5 = findViewById(R.id.CB_homepage_5);
        CheckBox checkbox_6 = findViewById(R.id.CB_homepage_6);
        CheckBox checkbox_7 = findViewById(R.id.CB_homepage_7);

        checkBoxHabitatMap.put(checkbox_1, "Zingeving");
        checkBoxHabitatMap.put(checkbox_2, "Werk & Activiteiten");
        checkBoxHabitatMap.put(checkbox_3, "Financiën");
        checkBoxHabitatMap.put(checkbox_4, "Psychische gezondheid");
        checkBoxHabitatMap.put(checkbox_5, "Wonen");
        checkBoxHabitatMap.put(checkbox_6, "Lichamelijke gezondheid");
        checkBoxHabitatMap.put(checkbox_7, "Sociale relaties");

        settingsLogo = findViewById(R.id.IV_main_settings);

        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);

    }

    private void setupCheckboxListeners() {
        for (final CheckBox checkBox : checkBoxHabitatMap.keySet()) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (CheckBox cb : checkBoxHabitatMap.keySet()) {
                            if (cb != checkBox) {
                                cb.setChecked(false);
                            }
                        }
                        updateChartBasedOnSelectedHabitat();
                    } else {
                        boolean anyChecked = false;
                        for (CheckBox cb : checkBoxHabitatMap.keySet()) {
                            if (cb.isChecked()) {
                                anyChecked = true;
                                break;
                            }
                        }
                        if (!anyChecked) {
                            showWelcomeText();
                        }
                    }
                }
            });
        }
    }

    private void updateChartBasedOnSelectedHabitat() {
        boolean anyChecked = false;
        for (Map.Entry<CheckBox, String> entry : checkBoxHabitatMap.entrySet()) {
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
                return dates.size() > (int) value ? dates.get((int) value) : "";
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
}


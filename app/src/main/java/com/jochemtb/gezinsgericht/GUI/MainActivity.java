package com.jochemtb.gezinsgericht.GUI;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.jochemtb.gezinsgericht.GUI.HistoryActivity;
import com.jochemtb.gezinsgericht.GUI.QuizActivity;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.longrunning.WaitOperationRequest;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.LineChartHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTv;
    private CheckBox checkbox_1, checkbox_2, checkbox_3, checkbox_4, checkbox_5, checkbox_6, checkbox_7;
    private Button navbar_2, navbar_3;
    private LineChartHelper lineChartHelper;
    private ImageView settingsLogo;
    private LineChart progressionChart;
    private SharedPreferences sharedPref;
    private final String LOG_TAG = "HomepageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String sessionId = intent.getStringExtra("sessionId");

        initViewComponents();
        navToSession();
        // Build the chart with initial data
        lineChartHelper = new LineChartHelper(findViewById(R.id.chart_homepage));
        checkbox_1.setChecked(true);  // Set the first checkbox to checked by default

        usernameTv.setText("Sietse 't Hooft"); // Dummie data
    }

    private void initViewComponents() {
        usernameTv = findViewById(R.id.TV_homepage_username);

        checkbox_1 = findViewById(R.id.CB_homepage_1);
        checkbox_2 = findViewById(R.id.CB_homepage_2);
        checkbox_3 = findViewById(R.id.CB_homepage_3);
        checkbox_4 = findViewById(R.id.CB_homepage_4);
        checkbox_5 = findViewById(R.id.CB_homepage_5);
        checkbox_6 = findViewById(R.id.CB_homepage_6);
        checkbox_7 = findViewById(R.id.CB_homepage_7);

        // Initialize LineChartHelper with your LineChart view
        lineChartHelper = new LineChartHelper(findViewById(R.id.chart_homepage));

        // Set up listeners for each checkbox
        setupCheckboxListeners();

        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        Log.d(LOG_TAG, "InitViewComponents done");
    }

    private void setupCheckboxListeners() {
        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(checkbox_1);
        checkBoxes.add(checkbox_2);
        checkBoxes.add(checkbox_3);
        checkBoxes.add(checkbox_4);
        checkBoxes.add(checkbox_5);
        checkBoxes.add(checkbox_6);
        checkBoxes.add(checkbox_7);

        for (final CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // Uncheck all other checkboxes
                        for (CheckBox cb : checkBoxes) {
                            if (cb != checkBox) {
                                cb.setChecked(false);
                            }
                        }
                        // Update chart based on the selected checkbox
                        updateChart(checkBox);
                    } else {
                        // Ensure at least one checkbox is checked
                        boolean anyChecked = false;
                        for (CheckBox cb : checkBoxes) {
                            if (cb.isChecked()) {
                                anyChecked = true;
                                break;
                            }
                        }
                        if (!anyChecked) {
                            checkBox.setChecked(true);
                        }
                    }
                }
            });
        }
    }

    private void updateChart(CheckBox checkBox) {
        // Clear existing entries
        lineChartHelper.clearEntries();

        // Make entries based on the selected checkbox
        List<Entry> entries = new ArrayList<>();
        // Add data based on which checkbox is selected
        if (checkBox == checkbox_1) {
            entries.add(new Entry(0, 0f));
            entries.add(new Entry(1, 3f));
            entries.add(new Entry(2, 9f));
            entries.add(new Entry(3, 7f));
            entries.add(new Entry(4, 13f));
        } else if (checkBox == checkbox_2) {
            entries.add(new Entry(0, 2f));
            entries.add(new Entry(1, 5f));
            entries.add(new Entry(2, 8f));
            entries.add(new Entry(3, 10f));
            entries.add(new Entry(4, 12f));
        } else if (checkBox == checkbox_3) {
            entries.add(new Entry(0, 1f));
            entries.add(new Entry(1, 4f));
            entries.add(new Entry(2, 7f));
            entries.add(new Entry(3, 9f));
            entries.add(new Entry(4, 11f));
        } else if (checkBox == checkbox_4) {
            entries.add(new Entry(0, 3f));
            entries.add(new Entry(1, 6f));
            entries.add(new Entry(2, 8f));
            entries.add(new Entry(3, 11f));
            entries.add(new Entry(4, 15f));
        } else if (checkBox == checkbox_5) {
            entries.add(new Entry(0, 1.5f));
            entries.add(new Entry(1, 4.5f));
            entries.add(new Entry(2, 6.5f));
            entries.add(new Entry(3, 8.5f));
            entries.add(new Entry(4, 10.5f));
        } else if (checkBox == checkbox_6) {
            entries.add(new Entry(0, 0.5f));
            entries.add(new Entry(1, 1.5f));
            entries.add(new Entry(2, 3.5f));
            entries.add(new Entry(3, 5.5f));
            entries.add(new Entry(4, 7.5f));
        } else if (checkBox == checkbox_7) {
            entries.add(new Entry(0, 2.5f));
            entries.add(new Entry(1, 5.5f));
            entries.add(new Entry(2, 8.5f));
            entries.add(new Entry(3, 10.5f));
            entries.add(new Entry(4, 13.5f));
        }

        // Add entries to LineChartHelper
        lineChartHelper.addEntries(entries);

        // Add a dataset to the chart with the updated entries
        lineChartHelper.addDataSet("Label", Color.BLUE, 2.0f);
    }

    private void navToSession() {
//         //TESTING
//         sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE);
//         Toast.makeText(this, sharedPref.getString("jwtToken", "Error"), Toast.LENGTH_LONG).show();

//         usernameTv.setText("Sietse 't Hooft"); // Dummie data
//     }


//     private void buildChart() {
//         // Makes description that is visible above the chart
//         Description description = new Description();
//         description.setText("Vooruitgang patiënt");
//         description.setPosition(250f, 15f);
//         description.setTextSize(10f);
//         progressionChart.setDescription(description);
//         progressionChart.getAxisRight().setDrawLabels(false);

//         // Makes the x-as
//         XAxis xAxis = progressionChart.getXAxis();
//         xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//         xAxis.setValueFormatter(new IndexAxisValueFormatter());
//         xAxis.setLabelCount(4);
//         xAxis.setGranularity(1f);

//         // Makes the y-as
//         YAxis yAxis = progressionChart.getAxisLeft();
//         yAxis.setAxisMinimum(0f);
//         yAxis.setAxisMaximum(15f);
//         yAxis.setAxisLineWidth(2f);
//         yAxis.setAxisLineColor(Color.BLACK);
//         yAxis.setLabelCount(10);

//         // Makes the entries (points)
//         List<Entry> entries = new ArrayList<>();
//         entries.add(new Entry(0, 0f));
//         entries.add(new Entry(1, 3f));
//         entries.add(new Entry(2, 9f));
//         entries.add(new Entry(3, 7f));
//         entries.add(new Entry(4, 13f));

//         // Makes the line(s)
//         LineDataSet dataSet = new LineDataSet(entries, "vooruitgang");
//         dataSet.setColor(Color.BLUE);
//         dataSet.setLineWidth(5f);

//         LineData lineData = new LineData(dataSet);

//         progressionChart.setData(lineData);
//         progressionChart.invalidate();
//     }


//     private void navToSession() {
//         settingsLogo.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 try {
//                     Log.i(LOG_TAG, "Uitloggen");
//                     sharedPref.edit().remove("jwtToken").apply();
//                     Toast.makeText(getBaseContext(), "Uitloggen succesvol", Toast.LENGTH_LONG).show();
//                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                 } catch (Exception e) {
//                     Log.e(LOG_TAG, "Error: " + e.getMessage());
//                 }
//             }
//         });

        navbar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        navbar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });
    }



    private void initViewComponents() {
        usernameTv = findViewById(R.id.TV_homepage_username);

        checkbox_1 = findViewById(R.id.CB_homepage_1);
        checkbox_2 = findViewById(R.id.CB_homepage_2);
        checkbox_3 = findViewById(R.id.CB_homepage_3);
        checkbox_4 = findViewById(R.id.CB_homepage_4);
        checkbox_5 = findViewById(R.id.CB_homepage_5);
        checkbox_6 = findViewById(R.id.CB_homepage_6);
        checkbox_7 = findViewById(R.id.CB_homepage_7);

        progressionChart = findViewById(R.id.chart_homepage);
        settingsLogo = findViewById(R.id.IV_main_settings);

        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        Log.d(LOG_TAG, "InitViewCompents done");
    }


}


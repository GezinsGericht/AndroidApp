package com.jochemtb.gezinsgericht.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jochemtb.gezinsgericht.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTv;
    private CheckBox checkbox_1, checkbox_2, checkbox_3, checkbox_4, checkbox_5, checkbox_6, checkbox_7;
    private Button navbar_1,navbar_2, navbar_3;

    private LineChart progressionChart;

    private final String LOG_TAG = "HomepageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewComponents();
        navToSession();
        buildChart();

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

        progressionChart = findViewById(R.id.chart_homepage);

        navbar_1 = findViewById(R.id.BTN_navbar1);
        navbar_2 = findViewById(R.id.BTN_navbar2);
        navbar_3 = findViewById(R.id.BTN_navbar3);
        Log.d(LOG_TAG, "InitViewCompents done");
    }

    private void buildChart(){
        //Makes description that is visible above the chart
        Description description = new Description();
        description.setText("Vooruitgang patiënt");
        description.setPosition(250f, 15f);
        description.setTextSize(10f);
        progressionChart.setDescription(description);
        progressionChart.getAxisRight().setDrawLabels(false);

        //Makes the x-as
        XAxis xAxis = progressionChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter());
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);

        //Makes the y-as
        YAxis yAxis = progressionChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(15f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        //Makes the entries (points)
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0f));
        entries.add(new Entry(1, 3f));
        entries.add(new Entry(2, 9f));
        entries.add(new Entry(3, 7f));
        entries.add(new Entry(4, 13f));

        //Makes the line(s)
        LineDataSet dataSet = new LineDataSet(entries, "vooruitgang");
        dataSet.setColor(Color.BLUE);
        dataSet.setLineWidth(5f);

        LineData lineData = new LineData(dataSet);

        progressionChart.setData(lineData);
        progressionChart.invalidate();
    }

    private void navToSession(){
        navbar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });
    }
}
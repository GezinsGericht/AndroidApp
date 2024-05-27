package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.jochemtb.gezinsgericht.R;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private RadarChart resultsChart;
    private RadarDataSet set1, set2, set3, set4, set5, set6;
    private CheckBox g1, g2, g3, g4, g5, g6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initViewComponents();
        initRadarChart();
        initDataSets();
        setupCheckBoxListeners();
        setupCloseButton();
    }

    private void initViewComponents() {
        resultsChart = findViewById(R.id.Radarchart);
        g1 = findViewById(R.id.g_1);
        g2 = findViewById(R.id.g_2);
        g3 = findViewById(R.id.g_3);
        g4 = findViewById(R.id.g_4);
        g5 = findViewById(R.id.g_5);
        g6 = findViewById(R.id.g_6);
    }

    private void initRadarChart() {
        String[] labels = {"Financiën", "Werk en activiteiten", "Sociale relaties", "Wonen", "Psychische gezondheid", "Zingeving", "Lichamelijke gezondheid"};

        YAxis axis = resultsChart.getYAxis();
        axis.setTextSize(10F);
        axis.setAxisMaximum(15F);  // Set a fixed maximum
        axis.setAxisMinimum(0F);   // Set a fixed minimum
        axis.setLabelCount(6, true); // Set the number of labels

        XAxis xAxis = resultsChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextSize(14F);
        xAxis.setSpaceMin(38F);

        resultsChart.getLegend().setEnabled(false);
        resultsChart.setWebLineWidthInner(1.2F);
        resultsChart.setScaleX(1F);
        resultsChart.setScaleY(1F);
        resultsChart.setClickable(true);
        resultsChart.setFocusable(true);
        resultsChart.setRotationEnabled(false);
    }

    private void initDataSets() {
        List<RadarEntry> entry1 = createDummyDataSet(3, 10, 8, 3, 12, 5, 9);
        List<RadarEntry> entry2 = createDummyDataSet(4, 7, 7, 15, 5, 8, 7);
        List<RadarEntry> entry3 = createDummyDataSet(6, 13, 7, 4, 14, 13, 7);
        List<RadarEntry> entry4 = createDummyDataSet(15, 3, 5, 8, 12, 11, 4);
        List<RadarEntry> entry5 = createDummyDataSet(12, 11, 3, 9, 13, 5, 8);
        List<RadarEntry> entry6 = createDummyDataSet(8, 9, 6, 7, 8, 6, 5);

        set1 = createRadarDataSet(entry1, "Gezinslid 1", Color.RED);
        set2 = createRadarDataSet(entry2, "Gezinslid 2", Color.BLUE);
        set3 = createRadarDataSet(entry3, "Gezinslid 3", Color.GREEN);
        set4 = createRadarDataSet(entry4, "Gezinslid 4", Color.DKGRAY);
        set5 = createRadarDataSet(entry5, "Gezinslid 5", Color.YELLOW);
        set6 = createRadarDataSet(entry6, "Gezinslid 6", Color.MAGENTA);

        List<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        dataSets.add(set6);

        RadarData data = new RadarData(dataSets);
        data.setDrawValues(false);
        resultsChart.setData(data);
    }

    private List<RadarEntry> createDummyDataSet(int... values) {
        List<RadarEntry> entries = new ArrayList<>();
        for (int value : values) {
            entries.add(new RadarEntry(value));
        }
        return entries;
    }

    private RadarDataSet createRadarDataSet(List<RadarEntry> entries, String label, int color) {
        RadarDataSet dataSet = new RadarDataSet(entries, label);
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawFilled(true);
        dataSet.setColor(color);
        dataSet.setFillColor(color);
        dataSet.setVisible(false);
        return dataSet;
    }

    private void setupCheckBoxListeners() {
        setupCheckBoxListener(g1, set1, Color.RED);
        setupCheckBoxListener(g2, set2, Color.BLUE);
        setupCheckBoxListener(g3, set3, Color.GREEN);
        setupCheckBoxListener(g4, set4, Color.DKGRAY);
        setupCheckBoxListener(g5, set5, Color.YELLOW);
        setupCheckBoxListener(g6, set6, Color.MAGENTA);
    }

    private void setupCheckBoxListener(CheckBox checkBox, RadarDataSet dataSet, int color) {
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                checkBox.setTextColor(color);
                dataSet.setVisible(true);
            } else {
                checkBox.setTextColor(Color.BLACK);
                dataSet.setVisible(false);
            }
            resultsChart.notifyDataSetChanged(); // Notify the chart that the data has changed
            resultsChart.invalidate(); // Refresh the chart
        });
    }

    private void setupCloseButton() {
        Button afsluiten = findViewById(R.id.results_close);
        afsluiten.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class)));
    }
}

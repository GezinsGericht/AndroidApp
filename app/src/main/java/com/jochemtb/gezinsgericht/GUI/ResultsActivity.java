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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        RadarChart resultsChart = findViewById(R.id.Radarchart);
        CheckBox g1 = findViewById(R.id.g_1);
        CheckBox g2 = findViewById(R.id.g_2);
        CheckBox g3 = findViewById(R.id.g_3);
        CheckBox g4 = findViewById(R.id.g_4);
        CheckBox g5 = findViewById(R.id.g_5);
        CheckBox g6 = findViewById(R.id.g_6);
        Button afsluiten = findViewById(R.id.results_close);

        // Dummy data for gezinslid 1
        List<RadarEntry> entry1 = new ArrayList<>();
        entry1.add(new RadarEntry(3));
        entry1.add(new RadarEntry(10));
        entry1.add(new RadarEntry(8));
        entry1.add(new RadarEntry(3));
        entry1.add(new RadarEntry(12));
        entry1.add(new RadarEntry(5));
        entry1.add(new RadarEntry(9));

        // Dummy data for gezinslid 2
        List<RadarEntry> entry2 = new ArrayList<>();
        entry2.add(new RadarEntry(4));
        entry2.add(new RadarEntry(7));
        entry2.add(new RadarEntry(7));
        entry2.add(new RadarEntry(15));
        entry2.add(new RadarEntry(5));
        entry2.add(new RadarEntry(8));
        entry2.add(new RadarEntry(7));

        // Dummy data for gezinslid 3
        List<RadarEntry> entry3 = new ArrayList<>();
        entry3.add(new RadarEntry(6));
        entry3.add(new RadarEntry(13));
        entry3.add(new RadarEntry(7));
        entry3.add(new RadarEntry(4));
        entry3.add(new RadarEntry(14));
        entry3.add(new RadarEntry(13));
        entry3.add(new RadarEntry(7));

        // Dummy data for gezinslid 4
        List<RadarEntry> entry4 = new ArrayList<>();
        entry4.add(new RadarEntry(15));
        entry4.add(new RadarEntry(3));
        entry4.add(new RadarEntry(5));
        entry4.add(new RadarEntry(8));
        entry4.add(new RadarEntry(12));
        entry4.add(new RadarEntry(11));
        entry4.add(new RadarEntry(4));

        // Dummy data for gezinslid 5
        List<RadarEntry> entry5 = new ArrayList<>();
        entry5.add(new RadarEntry(12));
        entry5.add(new RadarEntry(11));
        entry5.add(new RadarEntry(3));
        entry5.add(new RadarEntry(9));
        entry5.add(new RadarEntry(13));
        entry5.add(new RadarEntry(5));
        entry5.add(new RadarEntry(8));

        // Dummy data for gezinslid 6
        List<RadarEntry> entry6 = new ArrayList<>();
        entry6.add(new RadarEntry(8));
        entry6.add(new RadarEntry(9));
        entry6.add(new RadarEntry(6));
        entry6.add(new RadarEntry(7));
        entry6.add(new RadarEntry(8));
        entry6.add(new RadarEntry(6));
        entry6.add(new RadarEntry(5));

        RadarDataSet set1 = new RadarDataSet(entry1, "Gezinslid 1");
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawFilled(true);
        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setVisible(false);

        RadarDataSet set2 = new RadarDataSet(entry2, "Gezinslid 2");
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawFilled(true);
        set2.setColor(Color.BLUE);
        set2.setFillColor(Color.BLUE);
        set2.setVisible(false);

        RadarDataSet set3 = new RadarDataSet(entry3, "Gezinslid 3");
        set3.setDrawHighlightCircleEnabled(true);
        set3.setDrawFilled(true);
        set3.setColor(Color.GREEN);
        set3.setFillColor(Color.GREEN);
        set3.setVisible(false);

        RadarDataSet set4 = new RadarDataSet(entry4 ,"Gezinslid 4");
        set4.setDrawHighlightCircleEnabled(true);
        set4.setDrawFilled(true);
        set4.setColor(Color.DKGRAY);
        set4.setFillColor(Color.DKGRAY);
        set4.setVisible(false);

        RadarDataSet set5 = new RadarDataSet(entry5 ,"Gezinslid 5");
        set5.setDrawHighlightCircleEnabled(true);
        set5.setDrawFilled(true);
        set5.setColor(Color.YELLOW);
        set5.setFillColor(Color.YELLOW);
        set5.setVisible(false);

        RadarDataSet set6 = new RadarDataSet(entry6 ,"Gezinslid 6");
        set6.setDrawHighlightCircleEnabled(true);
        set6.setDrawFilled(true);
        set6.setColor(Color.MAGENTA);
        set6.setFillColor(Color.MAGENTA);
        set6.setVisible(false);

        List<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        dataSets.add(set6);

        String[] label = {"Financiën",
                "Werk en activiteiten",
                "Sociale relaties",
                "Wonen",
                "Psychische gezondheid",
                "Zingeving",
                "Lichamelijke gezondheid"};


        RadarData data = new RadarData(dataSets);
        data.setDrawValues(false);

        YAxis axis = resultsChart.getYAxis();
        axis.setTextSize(10F);
        axis.setAxisMaximum(15F);  // Set a fixed maximum
        axis.setAxisMinimum(0F);   // Set a fixed minimum
        axis.setLabelCount(6, true); // Set the number of labels

        XAxis habitats = resultsChart.getXAxis();
        habitats.setValueFormatter(new IndexAxisValueFormatter(label));
        habitats.setTextSize(14F);
        habitats.setSpaceMin(38F);

        resultsChart.getLegend().setEnabled(false);
        resultsChart.setWebLineWidthInner(1.2F);
        resultsChart.setScaleX(1F);
        resultsChart.setScaleY(1F);
        resultsChart.setData(data);
        resultsChart.setClickable(true);
        resultsChart.setFocusable(true);
        resultsChart.setRotationEnabled(false);

        g1.setOnClickListener(v -> {
            if (g1.isChecked()) {
                g1.setTextColor(Color.RED);
                set1.setVisible(true);
            } else {
                g1.setTextColor(Color.BLACK);
                set1.setVisible(false);
            }
            resultsChart.notifyDataSetChanged(); // Notify the chart that the data has changed
            resultsChart.invalidate(); // Refresh the chart
        });

        g2.setOnClickListener(v -> {
            if (g2.isChecked()) {
                g2.setTextColor(Color.BLUE);
                set2.setVisible(true);
            } else {
                g2.setTextColor(Color.BLACK);
                set2.setVisible(false);
            }
            resultsChart.notifyDataSetChanged(); // Notify the chart that the data has changed
            resultsChart.invalidate(); // Refresh the chart
        });

        g3.setOnClickListener(v -> {
            if (g3.isChecked()) {
                g3.setTextColor(Color.GREEN);
                set3.setVisible(true);
            } else {
                g3.setTextColor(Color.BLACK);
                set3.setVisible(false);
            }
            resultsChart.notifyDataSetChanged(); // Notify the chart that the data has changed
            resultsChart.invalidate(); // Refresh the chart
        });

        g4.setOnClickListener(v -> {
            if (g4.isChecked()) {
                g4.setTextColor(Color.DKGRAY);
                set4.setVisible(true);
            } else {
                g4.setTextColor(Color.BLACK);
                set4.setVisible(false);
            }
            resultsChart.notifyDataSetChanged();
            resultsChart.invalidate();
        });

        g5.setOnClickListener(v -> {
            if (g5.isChecked()) {
                g5.setTextColor(Color.YELLOW);
                set5.setVisible(true);
            } else {
                g5.setTextColor(Color.BLACK);
                set5.setVisible(false);
            }
            resultsChart.notifyDataSetChanged();
            resultsChart.invalidate();
        });

        g6.setOnClickListener(v -> {
            if (g6.isChecked()) {
                g6.setTextColor(Color.MAGENTA);
                set6.setVisible(true);
            } else {
                g6.setTextColor(Color.BLACK);
                set6.setVisible(false);
            }
            resultsChart.notifyDataSetChanged();
            resultsChart.invalidate();
        });
        afsluiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            }
        });
    }
}
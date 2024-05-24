package com.jochemtb.gezinsgericht.GUI;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
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

        List<RadarEntry> entry1 = new ArrayList<>();

        entry1.add(new RadarEntry(15f));
        entry1.add(new RadarEntry(10f));
        entry1.add(new RadarEntry(8f));
        entry1.add(new RadarEntry(3f));
        entry1.add(new RadarEntry(12f));
        entry1.add(new RadarEntry(5f));
        entry1.add(new RadarEntry(5f));

        List<RadarEntry> entry2 = new ArrayList<>();

        entry2.add(new RadarEntry(15f));
        entry2.add(new RadarEntry(10f));
        entry2.add(new RadarEntry(8f));
        entry2.add(new RadarEntry(3f));
        entry2.add(new RadarEntry(12f));
        entry2.add(new RadarEntry(5f));
        entry2.add(new RadarEntry(5f));

        List<RadarEntry> entry3 = new ArrayList<>();

        entry3.add(new RadarEntry(15f));
        entry3.add(new RadarEntry(10f));
        entry3.add(new RadarEntry(8f));
        entry3.add(new RadarEntry(3f));
        entry3.add(new RadarEntry(12f));
        entry3.add(new RadarEntry(5f));
        entry3.add(new RadarEntry(5f));

        List<RadarEntry> entry4 = new ArrayList<>();

        entry4.add(new RadarEntry(15f));
        entry4.add(new RadarEntry(10f));
        entry4.add(new RadarEntry(8f));
        entry4.add(new RadarEntry(3f));
        entry4.add(new RadarEntry(12f));
        entry4.add(new RadarEntry(5f));
        entry4.add(new RadarEntry(5f));

        List<RadarEntry> entry5 = new ArrayList<>();

        entry5.add(new RadarEntry(15f));
        entry5.add(new RadarEntry(10f));
        entry5.add(new RadarEntry(8f));
        entry5.add(new RadarEntry(3f));
        entry5.add(new RadarEntry(12f));
        entry5.add(new RadarEntry(5f));
        entry5.add(new RadarEntry(5f));

        List<RadarEntry> entry6 = new ArrayList<>();

        entry6.add(new RadarEntry(15f));
        entry6.add(new RadarEntry(10f));
        entry6.add(new RadarEntry(8f));
        entry6.add(new RadarEntry(3f));
        entry6.add(new RadarEntry(12f));
        entry6.add(new RadarEntry(5f));
        entry6.add(new RadarEntry(5f));

        List<RadarEntry> entry7 = new ArrayList<>();

        entry7.add(new RadarEntry(15f));
        entry7.add(new RadarEntry(10f));
        entry7.add(new RadarEntry(8f));
        entry7.add(new RadarEntry(3f));
        entry7.add(new RadarEntry(12f));
        entry7.add(new RadarEntry(5f));
        entry7.add(new RadarEntry(5f));

        List<RadarEntry> entry8 = new ArrayList<>();

        entry8.add(new RadarEntry(15f));
        entry8.add(new RadarEntry(10f));
        entry8.add(new RadarEntry(8f));
        entry8.add(new RadarEntry(3f));
        entry8.add(new RadarEntry(12f));
        entry8.add(new RadarEntry(5f));
        entry8.add(new RadarEntry(5f));

        List<RadarEntry> entry9 = new ArrayList<>();

        entry9.add(new RadarEntry(15f));
        entry9.add(new RadarEntry(10f));
        entry9.add(new RadarEntry(8f));
        entry9.add(new RadarEntry(3f));
        entry9.add(new RadarEntry(12f));
        entry9.add(new RadarEntry(5f));
        entry9.add(new RadarEntry(5f));

        List<RadarEntry> entry10 = new ArrayList<>();

        entry10.add(new RadarEntry(15f));
        entry10.add(new RadarEntry(10f));
        entry10.add(new RadarEntry(8f));
        entry10.add(new RadarEntry(3f));
        entry10.add(new RadarEntry(12f));
        entry10.add(new RadarEntry(5f));
        entry10.add(new RadarEntry(5f));

        RadarDataSet set1 = new RadarDataSet(entry1, "Gezinslid 1");
        set1.setColor(R.color.yellow);
        set1.setFillColor(R.color.yellow);
        RadarDataSet set2 = new RadarDataSet(entry2 ,"Gezinslid 2");
        set2.setColor(R.color.blue);
        set2.setFillColor(R.color.blue);
        RadarDataSet set3 = new RadarDataSet(entry3 ,"Gezinslid 3");
        set3.setColor(R.color.purple);
        set3.setFillColor(R.color.purple);
        RadarDataSet set4 = new RadarDataSet(entry4 ,"Gezinslid 4");
        set4.setColor(R.color.red);
        set4.setFillColor(R.color.red);
        RadarDataSet set5 = new RadarDataSet(entry5 ,"Gezinslid 5");
        set5.setColor(R.color.green);
        set5.setFillColor(R.color.green);
        RadarDataSet set6 = new RadarDataSet(entry6 ,"'Gezinslid 6");
        set6.setColor(R.color.orange);
        set6.setFillColor(R.color.orange);
        RadarDataSet set7 = new RadarDataSet(entry7 ,"Gezinslid 7");
        set7.setColor(R.color.dark_blue);
        set7.setFillColor(R.color.dark_blue);
        RadarDataSet set8 = new RadarDataSet(entry8 ,"Gezinslid 8");
        set8.setColor(R.color.pink);
        set8.setFillColor(R.color.pink);
        RadarDataSet set9 = new RadarDataSet(entry9 ,"Gezinslid 9");
        set9.setColor(R.color.night_black);
        set9.setFillColor(R.color.night_black);
        RadarDataSet set10 = new RadarDataSet(entry10 ,"Gezinslid 10");
        set10.setColor(R.color.dark_grey);
        set10.setFillColor(R.color.dark_grey);

        List<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        dataSets.add(set6);
        dataSets.add(set7);
        dataSets.add(set9);
        dataSets.add(set9);
        dataSets.add(set10);

        RadarData data = new RadarData(dataSets);
        data.setValueTextSize(20f);
        data.setDrawValues(false);

        resultsChart.setData(data);
    }
}

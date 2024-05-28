package com.jochemtb.gezinsgericht.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.RadarDataSet;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.FamilyResults;
import com.jochemtb.gezinsgericht.domain.RadarChartHelper;
import com.jochemtb.gezinsgericht.domain.Score;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    private RadarChartHelper radarChartHelper;
    private CheckBox g1, g2, g3, g4, g5, g6;
    private Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        mSession = (Session) intent.getSerializableExtra("session");

        initViewComponents(); //Sets the checkboxes by id
        setupRadarChartHelper(mSession); //Sets the dummy data with help from RadarChartHelper
        setupCheckBoxListeners(); //Sets the fuctionality for the checkboxes
        setupCloseButton(); //Sets up the "afsluiten" button
        setupMyanswerButton(); //Sets up the "mijn antwoorden" button
    }

    private void initViewComponents() {
        radarChartHelper = new RadarChartHelper(findViewById(R.id.Radarchart));
        g1 = findViewById(R.id.g_1);
        g2 = findViewById(R.id.g_2);
        g3 = findViewById(R.id.g_3);
        g4 = findViewById(R.id.g_4);
        g5 = findViewById(R.id.g_5);
        g6 = findViewById(R.id.g_6);
    }

    private void setupRadarChartHelper(Session session) { //Sets the data and the colors for the chart per family member

        if (session != null) {
            List<User> users = new ArrayList<>(session.getUsers());
            int colorIndex = 0;
            int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};

            for (User user : users) {
                Score score = session.getResults().getResultMap().get(user);
                Map<Integer, Integer> scoresMap = score.getScores();
                int[] scores = scoresMap.values().stream().mapToInt(i->i).toArray();
                List<RadarEntry> entry = radarChartHelper.createDummyDataSet(scores);
                radarChartHelper.addDataSet(entry, "Gezinslid " + (colorIndex + 1), colors[colorIndex % colors.length]);
                colorIndex++;
            }
        } else {
            List<RadarEntry> entry1 = radarChartHelper.createDummyDataSet(3, 10, 8, 3, 12, 5, 9);
            List<RadarEntry> entry2 = radarChartHelper.createDummyDataSet(4, 7, 7, 15, 5, 8, 7);
            List<RadarEntry> entry3 = radarChartHelper.createDummyDataSet(6, 13, 7, 4, 14, 13, 7);
            List<RadarEntry> entry4 = radarChartHelper.createDummyDataSet(15, 3, 5, 8, 12, 11, 4);
            List<RadarEntry> entry5 = radarChartHelper.createDummyDataSet(12, 11, 3, 9, 13, 5, 8);
            List<RadarEntry> entry6 = radarChartHelper.createDummyDataSet(8, 9, 6, 7, 8, 6, 5);

            radarChartHelper.addDataSet(entry1, "Gezinslid 1", Color.RED);
            radarChartHelper.addDataSet(entry2, "Gezinslid 2", Color.BLUE);
            radarChartHelper.addDataSet(entry3, "Gezinslid 3", Color.GREEN);
            radarChartHelper.addDataSet(entry4, "Gezinslid 4", Color.DKGRAY);
            radarChartHelper.addDataSet(entry5, "Gezinslid 5", Color.YELLOW);
            radarChartHelper.addDataSet(entry6, "Gezinslid 6", Color.MAGENTA);
        }
    }

    private void setupCheckBoxListeners() { //Sets the colors for when checkbox is selected
        setupCheckBoxListener(g1, radarChartHelper.getDataSet(0), Color.RED);
        setupCheckBoxListener(g2, radarChartHelper.getDataSet(1), Color.BLUE);
        setupCheckBoxListener(g3, radarChartHelper.getDataSet(2), Color.GREEN);
//        setupCheckBoxListener(g4, radarChartHelper.getDataSet(3), Color.DKGRAY);
//        setupCheckBoxListener(g5, radarChartHelper.getDataSet(4), Color.YELLOW);
//        setupCheckBoxListener(g6, radarChartHelper.getDataSet(5), Color.MAGENTA);
    }

    private void setupCheckBoxListener(CheckBox checkBox, RadarDataSet dataSet, int color) { //Changes the color of the text by checkbox when selected and reverts when deselected
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

    private void setupMyanswerButton() {
        Button myanswer = findViewById(R.id.results_show);
        myanswer.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MyAnswerActivity.class))); //Switches the page to MyAnswerActivity
    }

    private void setupCloseButton() {
        Button afsluiten = findViewById(R.id.results_close);
        afsluiten.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class))); //Switches the page to MainActivity
    }
}

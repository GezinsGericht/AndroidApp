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
import com.jochemtb.gezinsgericht.domain.RadarChart;
import com.jochemtb.gezinsgericht.domain.Session;
import com.jochemtb.gezinsgericht.domain.User;

import java.util.Set;

public class ResultsActivity extends AppCompatActivity {

    private final String LOG_TAG = "ResultsActivity";
    private RadarChart radarChartHelper;
    private Session mSession;
    private GridLayout mResultsCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        mSession = (Session) intent.getSerializableExtra("session");
        Log.i(LOG_TAG, "Session: " + mSession);

        initViewComponents(mSession); //Sets the checkboxes by id
        radarChartHelper.createDataSetFromSession(mSession); //Sets the dummy data with help from RadarChartHelper
        setupCheckBoxListeners(); //Sets the functionality for the checkboxes
        setupCloseButton(); //Sets up the "afsluiten" button
        setupMyAnswerButton(); //Sets up the "mijn antwoorden" button

    }

    private void initViewComponents(Session session) {
        radarChartHelper = new RadarChart(findViewById(R.id.Radarchart));
        mResultsCheckboxes = findViewById(R.id.GL_results_checkboxes);

        // Get the users from the session
        Set<User> users = session.getUsers();
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};

        // Create a checkbox for each user
        int colorIndex = 0;
        for (User user : users) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(user.getName());
            checkBox.setChecked(false);
            int color = colors[colorIndex % colors.length];
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // unchecked
                            new int[]{android.R.attr.state_checked}  // checked
                    },
                    new int[]{
                            Color.BLACK,
                            color
                    }
            );
            checkBox.setButtonTintList(colorStateList);
            mResultsCheckboxes.addView(checkBox);
            colorIndex++;
        }
    }

    private void setupCheckBoxListeners() {
        int checkboxCount = mResultsCheckboxes.getChildCount();
        // Define the colors for the datasets
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};
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
            intent.putExtra("session", mSession);
            startActivity(intent);
        });
    }

    private void setupCloseButton() {
        Button afsluiten = findViewById(R.id.results_close);
        afsluiten.setOnClickListener(v -> startActivity(new Intent(ResultsActivity.this, MainActivity.class))); //Switches the page to MainActivity
    }
}

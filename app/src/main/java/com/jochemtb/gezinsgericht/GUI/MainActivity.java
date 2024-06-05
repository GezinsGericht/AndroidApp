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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.jochemtb.gezinsgericht.GUI.HistoryActivity;
import com.jochemtb.gezinsgericht.GUI.QuizActivity;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jochemtb.gezinsgericht.R;
import com.jochemtb.gezinsgericht.domain.LineChartEntry;
import com.jochemtb.gezinsgericht.domain.LineChartHelper;
import com.jochemtb.gezinsgericht.repository.LineChartRepository;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LineChartRepository.LineChartCallback {

    private LineChartHelper lineChartHelper;
    private LineChartRepository lineChartRepository;
    private List<LineChartEntry> allEntries = new ArrayList<>();
    private Map<CheckBox, String> checkBoxHabitatMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChartHelper = new LineChartHelper(findViewById(R.id.chart_homepage));
        lineChartRepository = new LineChartRepository(this);

        TextView TV_Welcome = findViewById(R.id.TV_welkom_message);
        TextView TV_WelcomeGG = findViewById(R.id.TV_welkom_message_gg);

        TV_Welcome.setVisibility(View.VISIBLE);
        TV_WelcomeGG.setVisibility(View.VISIBLE);

        lineChartHelper.getLineChart().setVisibility(View.INVISIBLE);

        initViewComponents();
        setupCheckboxListeners();

        lineChartRepository.getLineChart(this);  // Fetch data when the activity is created

        TextView TV_Homepage = findViewById(R.id.TV_homepage_username);
        TV_Homepage.setText("Name_PlaceHolder");
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

        setupCheckboxListeners();
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
                            checkBox.setChecked(true);
                        }
                    }
                }
            });
        }
    }

    private void updateChartBasedOnSelectedHabitat() {
        for (Map.Entry<CheckBox, String> entry : checkBoxHabitatMap.entrySet()) {
            if (entry.getKey().isChecked()) {
                updateChart(entry.getValue());
                break;
            }
        }
    }

    private void updateChart(String habitatName) {
        List<Entry> chartEntries = new ArrayList<>();
        final List<String> dates = new ArrayList<>();

        for (int i = 0; i < allEntries.size(); i++) {
            LineChartEntry entry = allEntries.get(i);
            if (entry.getHabitat_Name().equals(habitatName)) {
                float yValue = Float.parseFloat(entry.getAverage_Answer_Score());
                chartEntries.add(new Entry(chartEntries.size(), yValue));
                dates.add(entry.getSessie_Datum());
            }
        }

        TextView TV_Welcome = findViewById(R.id.TV_welkom_message);
        TextView TV_WelcomeGG = findViewById(R.id.TV_welkom_message_gg);

        lineChartHelper.clearEntries();
        lineChartHelper.addEntries(chartEntries);
        lineChartHelper.addDataSet(habitatName, Color.RED, 2.0f);

        TV_Welcome.setVisibility(View.GONE);
        TV_WelcomeGG.setVisibility(View.GONE);

        lineChartHelper.getLineChart().setVisibility(View.VISIBLE);

        lineChartHelper.getLineChart().getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dates.size() > (int) value ? dates.get((int) value) : "";
            }
        });
    }
}
package com.jochemtb.gezinsgericht.domain;

import android.graphics.Color;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RadarChart {

    private com.github.mikephil.charting.charts.RadarChart radarChart;
    private List<IRadarDataSet> dataSets;

    public RadarChart(com.github.mikephil.charting.charts.RadarChart radarChart) {
        this.radarChart = radarChart;
        this.dataSets = new ArrayList<>();
        initRadarChart();
    }

    private void initRadarChart() {
        String[] labels = {"Financiën", "Werk en activiteiten", "Sociale relaties", "Wonen", "Psychische gezondheid", "Zingeving", "Lichamelijke gezondheid"};

        YAxis axis = radarChart.getYAxis();
        axis.setTextSize(10F);
        axis.setAxisMaximum(15F);
        axis.setAxisMinimum(0F);
        axis.setLabelCount(6, true);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextSize(14F);
        xAxis.setSpaceMin(38F);

        radarChart.getLegend().setEnabled(false);
        radarChart.setWebLineWidthInner(1.2F);
        radarChart.setScaleX(1F);
        radarChart.setScaleY(1F);
        radarChart.setClickable(true);
        radarChart.setFocusable(true);
        radarChart.setRotationEnabled(false);
    }

    public void createDataSetFromSession(HashMap<String, HashMap<Integer, Double>> userHabitatAverageValues) {
        // Get the users from the session
        Set<String> userNames = userHabitatAverageValues.keySet();

        // Define the colors for the datasets
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA};

        // Create a dataset for each user
        int colorIndex = 0;
        for (String userName : userNames) {
            HashMap<Integer, Double> habitatAverageValues = userHabitatAverageValues.get(userName);
            List<RadarEntry> entries = new ArrayList<>();
            for (double averageValue : habitatAverageValues.values()) {
                entries.add(new RadarEntry((float) averageValue));
            }
            addDataSet(entries, "Gezinslid " + (colorIndex + 1), colors[colorIndex % colors.length]);
            colorIndex++;
        }
    }

    public void addDataSet(List<RadarEntry> entries, String label, int color) {
        RadarDataSet dataSet = new RadarDataSet(entries, label);
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawFilled(true);
        dataSet.setColor(color);
        dataSet.setFillColor(color);
        dataSet.setVisible(false);
        dataSets.add(dataSet);

        RadarData data = new RadarData(dataSets);
        data.setDrawValues(false);
        radarChart.setData(data);
    }

    public void toggleDataSetVisibility(RadarDataSet dataSet, boolean isVisible) {
        dataSet.setVisible(isVisible);
        radarChart.notifyDataSetChanged();
        radarChart.invalidate();
    }

    public List<RadarEntry> createDataSet(int... values) {
        List<RadarEntry> entries = new ArrayList<>();
        for (int value : values) {
            entries.add(new RadarEntry(value));
        }
        return entries;
    }

    public RadarDataSet getDataSet(int index) {
        return (RadarDataSet) dataSets.get(index);
    }
}

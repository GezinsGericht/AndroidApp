package com.jochemtb.gezinsgericht.domain;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineChartHelper {
    private LineChart lineChart;
    private List<Entry> entries;
    private LineDataSet dataSet;

    public LineChartHelper(LineChart lineChart) {
        this.lineChart = lineChart;
        this.entries = new ArrayList<>();
        initLineChart();
    }

    private void initLineChart() {
        Description description = new Description();
        description.setText("Voortgangsgrafiek");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);
        makeXaxis();
        makeYaxis();
    }

    private void makeXaxis() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
    }

    private void makeYaxis() {
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(5f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(5);
    }

    public void addEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void addDataSet(String label, int color, float width) {
        dataSet = new LineDataSet(entries, label);
        dataSet.setColor(color);
        dataSet.setLineWidth(width);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    public LineChart getLineChart() {
        return lineChart;
    }

    public void clearEntries() {
        entries.clear();
    }
}


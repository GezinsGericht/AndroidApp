package com.jochemtb.gezinsgericht.domain;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.List;

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
        lineChart.setDescription(null);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        makeXaxis();
        makeYaxis();
    }

    private void makeXaxis() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGridLineWidth(1f);
    }

    private void makeYaxis() {
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(5f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setDrawGridLines(false); // Disable default grid lines
        yAxis.setDrawAxisLine(true);
        yAxis.setGranularityEnabled(true);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(6, true); // Ensure labels at 0, 1, 2, 3, 4, 5

        // Add custom limit lines
        addCustomYLimitLines(yAxis);
    }

    private void addCustomYLimitLines(YAxis yAxis) {
        float[] yPositions = {0f, 1f, 2f, 3f, 4f, 5f};
        yAxis.removeAllLimitLines(); // Clear previous limit lines
        for (float y : yPositions) {
            LimitLine limitLine = new LimitLine(y);
            limitLine.setLineColor(Color.GRAY);
            limitLine.setLineWidth(1f);
            yAxis.addLimitLine(limitLine);
        }
    }

    public void addEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void addDataSet(String label, int color, float width) {
        dataSet = new LineDataSet(entries, label);
        dataSet.setValueTextSize(12F);
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

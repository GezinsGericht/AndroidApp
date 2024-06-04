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
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jochemtb.gezinsgericht.API.Linechart.LineChartResponse;
import com.jochemtb.gezinsgericht.dao.LineChartDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LineChartHelper {
    private LineChart lineChart;
    private Description description;
    private List<Entry> entries;
    private LineChartDao lineChartDao;
    private LineDataSet dataSet;
    private final String LOG_TAG = "LineChartHelper";

    public LineChartHelper(LineChart lineChart){
        this.lineChart = lineChart;
        this.description = new Description();
        this.entries = new ArrayList<>();
        initLineChart();
    }

    // This method makes the chart (without the dataLine)
    private void initLineChart(){
        makeDiscription();
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        makeXaxis();
        makeYaxis();
        Log.d(LOG_TAG, "initLineChart done");
    }

    // This method makes the description
    private void makeDiscription(){
        description.setText("Vooruitgangsgrafiek");
        description.setPosition(250f, 15f);
        description.setTextSize(10f);
    }

    // This method makes the X-as of the chart
    private void makeXaxis(){
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter());
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
    }

    // This method makes the Y-as of the chart
    private void makeYaxis(){
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(15f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
    }

    // This method adds the entries send by the mainActivity
    public void addEntries(List<Entry> entries){
        this.entries = entries;
        Log.d(LOG_TAG, "Entries added" + this.entries);
    }

    // This method creates the line in the chart, with the extra options send by the mainActivity
    public void addDataSet(String label, int color, float width){
        dataSet = new LineDataSet(entries, label);
        dataSet.setColor(color);
        dataSet.setLineWidth(width);
        Log.d(LOG_TAG, "Dataset added");
        makeLine();
    }

    // This method prints the line on the chart
    private void makeLine(){
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        Log.d(LOG_TAG, "Dataline added");
    }
    //This method clears the entries
    public void clearEntries() {
        entries.clear();
    }

    public void createDataSetForLineChart(){
        lineChartDao.setLineChartlist();
    }
}

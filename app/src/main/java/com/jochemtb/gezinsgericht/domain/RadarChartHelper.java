package com.jochemtb.gezinsgericht.domain;

import android.graphics.Color;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

public class RadarChartHelper {

    private RadarChart radarChart;
    private List<IRadarDataSet> dataSets;

    public RadarChartHelper(RadarChart radarChart) {
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

    public List<RadarEntry> createDummyDataSet(int... values) {
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

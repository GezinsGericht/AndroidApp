package com.jochemtb.gezinsgericht.dao;

import com.jochemtb.gezinsgericht.domain.LineChartEntry;

import java.util.ArrayList;

public class LineChartDao {

    private ArrayList<LineChartEntry> LineChartlist;

    public LineChartDao(ArrayList<LineChartEntry> lineChartlist) {
        LineChartlist = lineChartlist;
    }

    public LineChartDao() {
    }

    public ArrayList<LineChartEntry> getLineChartlist() {
        return LineChartlist;
    }

    public void setLineChartlist(ArrayList<LineChartEntry> lineChartlist) {
        LineChartlist = lineChartlist;
    }
}

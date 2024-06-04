package com.jochemtb.gezinsgericht.API.Linechart;

import com.jochemtb.gezinsgericht.domain.LineChartEntry;

import java.util.ArrayList;

public class LineChartResponse {
    private ArrayList<LineChartEntry> list;

    public LineChartResponse(ArrayList<LineChartEntry> list) {
        this.list = list;
    }

    public ArrayList<LineChartEntry> getList() {
        return list;
    }

    public void setList(ArrayList<LineChartEntry> list) {
        this.list = list;
    }
}

package com.jochemtb.gezinsgericht.API.History;

import com.jochemtb.gezinsgericht.domain.HistoryItem;

import java.util.ArrayList;


public class HistoryListResponse {
    private ArrayList<HistoryItem> list;

    public HistoryListResponse() {
        this.list = new ArrayList<HistoryItem>();
    }

    public ArrayList<HistoryItem> getList() {
        return list;
    }

    public void setList(ArrayList<HistoryItem> list) {
        this.list = list;
    }
}



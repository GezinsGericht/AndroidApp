package com.jochemtb.gezinsgericht.dao;

import com.jochemtb.gezinsgericht.domain.HistoryItem;

import java.util.ArrayList;

public class HistoryDao {
    private ArrayList<HistoryItem> historyList;

    public HistoryDao(ArrayList<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    public HistoryDao(){

    }

    public ArrayList<HistoryItem> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ArrayList<HistoryItem> historyList) {
        this.historyList = historyList;
    }
}

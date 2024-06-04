package com.jochemtb.gezinsgericht.dao;

import com.jochemtb.gezinsgericht.domain.ResultsItem;

import java.util.ArrayList;

public class ResultsDao {

    private ArrayList<ResultsItem> results;

    public ResultsDao(ArrayList<ResultsItem> results) {
        this.results = results;
    }

    public ResultsDao(){

    }

    public ArrayList<ResultsItem> getResults() {
        return results;
    }
    public void setResults(ArrayList<ResultsItem> results) {
        this.results = results;
    }
}

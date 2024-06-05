// HistoryAnswerDao.java
package com.jochemtb.gezinsgericht.dao;

import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;
import java.util.List;

public class HistoryAnswerDao {
    private List<HistoryAnswerResponse> historyAnswerList;

    public List<HistoryAnswerResponse> getHistoryAnswerList() {
        return historyAnswerList;
    }

    public void setHistoryAnswerList(List<HistoryAnswerResponse> historyAnswerList) {
        this.historyAnswerList = historyAnswerList;
    }
}

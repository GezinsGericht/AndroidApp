// GroupedHistoryAnswer.java
package com.jochemtb.gezinsgericht.domain;

import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;
import java.util.List;

public class GroupedHistoryAnswer {
    private String habitatName;
    private List<HistoryAnswerResponse> historyAnswers;

    public GroupedHistoryAnswer(String habitatName, List<HistoryAnswerResponse> historyAnswers) {
        this.habitatName = habitatName;
        this.historyAnswers = historyAnswers;
    }

    public String getHabitatName() {
        return habitatName;
    }

    public List<HistoryAnswerResponse> getHistoryAnswers() {
        return historyAnswers;
    }
}

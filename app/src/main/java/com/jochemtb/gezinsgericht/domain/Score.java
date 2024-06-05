package com.jochemtb.gezinsgericht.domain;

import java.io.Serializable;
import java.util.Map;

public class Score implements Serializable {

    private User familyMember;
    private Map<Integer, Integer> scores;

    public Score(User familyMember, Map<Integer, Integer> scores) {
        this.familyMember = familyMember;
        this.scores = scores;
    }
    public User getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(User familyMember) {
        this.familyMember = familyMember;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public Integer getResults(Integer key) {
        return scores.get(key);
    }



}

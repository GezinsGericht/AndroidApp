package com.jochemtb.gezinsgericht.domain;

import java.util.Map;

public class Score {

    private User familyMember;
    private Map<String, Integer> scores;

    public Score(User familyMember, Map<String, Integer> scores) {
        this.familyMember = familyMember;
        this.scores = scores;
    }

    public User getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(User familyMember) {
        this.familyMember = familyMember;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }



}

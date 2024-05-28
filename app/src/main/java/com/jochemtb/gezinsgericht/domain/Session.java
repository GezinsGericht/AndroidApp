package com.jochemtb.gezinsgericht.domain;

import java.time.LocalDate;

public class Session {

    private LocalDate date;
    private Professional prof;

    private FamilyResults results;

    public Session(LocalDate date, Professional prof, FamilyResults results) {
        this.date = date;
        this.prof = prof;
        this.results = results;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Professional getProf() {
        return prof;
    }

    public void setProf(Professional prof) {
        this.prof = prof;
    }

    public FamilyResults getResults() {
        return results;
    }

    public void setResults(FamilyResults results) {
        this.results = results;
    }

}

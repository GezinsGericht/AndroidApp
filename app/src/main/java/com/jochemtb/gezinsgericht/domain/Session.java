package com.jochemtb.gezinsgericht.domain;

import java.time.LocalDate;

public class Session {

    private LocalDate date;
    private Professional prof;

    public Session(LocalDate date, Professional prof) {
        this.date = date;
        this.prof = prof;
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

}

package com.jochemtb.gezinsgericht.domain;

public class HistoryItem {
        private int SessionId;
        private String Professional_Name;
        private String Date;

    public HistoryItem(int sessionId, String professional_Name, String date) {
        SessionId = sessionId;
        Professional_Name = professional_Name;
        Date = date;
    }

    public int getSessionId() {
            return SessionId;
        }

        public void setSessionId(int sessionId) {
            SessionId = sessionId;
        }

        public String getProfessional_Name() {
            return Professional_Name;
        }

        public void setProfessional_Name(String professional_Name) {
            Professional_Name = professional_Name;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }
    }


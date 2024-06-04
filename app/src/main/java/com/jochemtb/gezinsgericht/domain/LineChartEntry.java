package com.jochemtb.gezinsgericht.domain;

public class LineChartEntry {
    private String Sessie_Datum;
    private String Habitat_Name;
    private String Average_Answer_Score;

    public LineChartEntry(String habitat_Name,String Sessie_Datum, String Average_Answer_Score) {
        Sessie_Datum = Sessie_Datum;
        Habitat_Name = habitat_Name;
        Average_Answer_Score = Average_Answer_Score;
    }

    public String getDate() {
        return Sessie_Datum;
    }

    public void setDate(String Sessie_Datum) {
        Sessie_Datum = Sessie_Datum;
    }

    public String getHabitat_Name() {
        return Habitat_Name;
    }

    public void setHabitat_Name(String habitat_Name) {
        Habitat_Name = habitat_Name;
    }

    public String getAverage_Answer_Score() {
        return Average_Answer_Score;
    }

    public void setAverage_Score(String Average_Answer_Score) {
        Average_Answer_Score = Average_Answer_Score;
    }
}

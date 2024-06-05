package com.jochemtb.gezinsgericht.API.LineChart;

public class LineChartEntry {
    private String Sessie_Datum;
    private String Habitat_Name;
    private String Average_Answer_Score;

    public LineChartEntry(String Sessie_Datum, String Habitat_Name, String Average_Answer_Score) {
        this.Sessie_Datum = Sessie_Datum;
        this.Habitat_Name = Habitat_Name;
        this.Average_Answer_Score = Average_Answer_Score;
    }

    public String getSessie_Datum() {
        return Sessie_Datum;
    }

    public void setSessie_Datum(String sessie_Datum) {
        Sessie_Datum = sessie_Datum;
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

    public void setAverage_Answer_Score(String average_Answer_Score) {
        Average_Answer_Score = average_Answer_Score;
    }
}

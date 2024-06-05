package com.jochemtb.gezinsgericht.API.LineChart;

import com.jochemtb.gezinsgericht.domain.LineChartEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiLineChartService {

    @GET("sessions")
    Call<List<LineChartEntry>> getLineChart();
}
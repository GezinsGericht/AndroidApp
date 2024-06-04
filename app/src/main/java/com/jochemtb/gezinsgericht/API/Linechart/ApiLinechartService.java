package com.jochemtb.gezinsgericht.API.Linechart;

import com.github.mikephil.charting.data.Entry;
import com.jochemtb.gezinsgericht.domain.HistoryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiLinechartService {

    @GET("linechart")
    Call<List<Entry>> getLineChart();
}

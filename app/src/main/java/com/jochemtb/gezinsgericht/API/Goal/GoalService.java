package com.jochemtb.gezinsgericht.API.Goal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GoalService {
    @GET("goal/{sessionId}")
    Call<List<GoalResponse>> getGoalAnswer(@Path("sessionId") String sessionId);
}

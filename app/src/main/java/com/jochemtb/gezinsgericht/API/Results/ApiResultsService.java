package com.jochemtb.gezinsgericht.API.Results;
import com.jochemtb.gezinsgericht.domain.ResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiResultsService {

    @GET("radarchart-history/{sessionId}")
    Call<List<ResultsItem>> getResults(@Path("sessionId") String sessionId);


}

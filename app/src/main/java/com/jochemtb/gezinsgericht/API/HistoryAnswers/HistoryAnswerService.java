// HistoryAnswerService.java
package com.jochemtb.gezinsgericht.API.HistoryAnswers;

import com.jochemtb.gezinsgericht.API.HistoryAnswers.HistoryAnswerResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface HistoryAnswerService {
    @GET("questions-history-user/{sessionId}")
    Call<List<HistoryAnswerResponse>> getHistoryAnswer(@Path("sessionId") String sessionId);
}

package com.jochemtb.gezinsgericht.API.Name;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NameService {
    @GET("users/{userId}")
    Call<NameResponse> getName(@Path("userId") String userId);
}

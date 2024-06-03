package com.jochemtb.gezinsgericht.API.Login;


import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordRequest;
import com.jochemtb.gezinsgericht.API.Login.ForgotPasswordResponse;
import com.jochemtb.gezinsgericht.API.Login.LoginRequest;
import com.jochemtb.gezinsgericht.API.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("login/reset")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @Headers("Content-Type: application/json")
    @POST("validate/renew")
    Call<TokenResponse> checkPresentToken(@Body TokenRequest tokenRequest);
}

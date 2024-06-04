package com.jochemtb.gezinsgericht.API.Login;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiLoginService {
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

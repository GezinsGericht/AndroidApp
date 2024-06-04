package com.jochemtb.gezinsgericht.API.Login;

public class ChangePasswordRequest {
    private String email;

    private String password;

    public ChangePasswordRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


}

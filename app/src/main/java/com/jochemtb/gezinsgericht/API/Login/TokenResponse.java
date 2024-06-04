package com.jochemtb.gezinsgericht.API.Login;

public class TokenResponse {

    private String message;
    private String data;

    public TokenResponse(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public TokenResponse(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
}


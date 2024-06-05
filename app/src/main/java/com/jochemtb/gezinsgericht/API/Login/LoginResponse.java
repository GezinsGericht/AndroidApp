package com.jochemtb.gezinsgericht.API.Login;

public class LoginResponse {
    private Data data;
    private String error;

    public Data getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public class Data {
        private String token;

        public String getToken() {
            return token;
        }
    }
}

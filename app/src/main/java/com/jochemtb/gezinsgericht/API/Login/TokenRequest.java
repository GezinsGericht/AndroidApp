package com.jochemtb.gezinsgericht.API.Login;

public class TokenRequest {

        private String token;

        public TokenRequest(String token) {
            this.token = token;
        }

        // Getter and setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }



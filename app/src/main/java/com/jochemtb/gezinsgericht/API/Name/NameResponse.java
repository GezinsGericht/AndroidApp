package com.jochemtb.gezinsgericht.API.Name;

import com.google.gson.annotations.SerializedName;

public class NameResponse {
    @SerializedName("data")
    private UserData userData;

    public String getName() {
        if (userData != null) {
            return userData.getName();
        } else {
            return null;
        }
    }

    private static class UserData {
        @SerializedName("Name")
        private String name;

        public String getName() {
            return name;
        }
    }
}

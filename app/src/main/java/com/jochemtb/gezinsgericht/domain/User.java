package com.jochemtb.gezinsgericht.domain;

public class User {

    private int id;
    private String role;
    private String name;
    private String email;
    private String deviceToken;

    public User(int id, String role, String name, String email, String deviceToken) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.deviceToken = deviceToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


}

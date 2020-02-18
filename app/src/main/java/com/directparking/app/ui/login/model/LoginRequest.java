package com.directparking.app.ui.login.model;

public class LoginRequest {

    private String fcmToken;
    private String username;
    private String password;

    public LoginRequest(String fcmToken, String username, String password) {
        this.fcmToken = fcmToken;
        this.username = username;
        this.password = password;
    }
}
package com.directparking.app.ui.password.change.model;

public class ChangePasswordRequest {

    private String userId;
    private String oldPassword;
    private String NewPassword;

    public ChangePasswordRequest(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.NewPassword = newPassword;
    }
}
package com.directparking.app.ui.login.model;

public class LoginResponse {

    private boolean status;
    private UserData userDetails;
    private CarData carDetails;
    private LocationConfig locationConfig;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public UserData getUserDetails() {
        return userDetails;
    }

    public CarData getCarDetails() {
        return carDetails;
    }

    public LocationConfig getLocationConfig() {
        return locationConfig;
    }

    public String getMsg() {
        return msg;
    }
}
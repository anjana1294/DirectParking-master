package com.directparking.app.ui.profile.view.model;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

public class MyProfileResponse {
    private UserData userDetails;
    private CarData carDetails;
    private String msg;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public UserData getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserData userDetails) {
        this.userDetails = userDetails;
    }

    public CarData getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarData carDetails) {
        this.carDetails = carDetails;
    }
}

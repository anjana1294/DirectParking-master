package com.directparking.app.ui.signup.model;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

public class SignupRequest {

    private String fcmToken;
    private UserData userDetails;
    private CarData carDetails;

    public SignupRequest(String fcmToken, UserData userDetails, CarData carDetails) {
        this.fcmToken = fcmToken;
        this.userDetails = userDetails;
        this.carDetails = carDetails;
    }
}
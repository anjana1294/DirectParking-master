package com.directparking.app.ui.profile.edit.model;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

public class UpdateProfileRequest {

    private String fcmToken;
    private UserData userDetails;
    private CarData carDetails;

    UpdateProfileRequest(String fcmToken, UserData userDetails, CarData carDetails) {
        this.fcmToken = fcmToken;
        this.userDetails = userDetails;
        this.carDetails = carDetails;
    }

    public static class Builder {

        private String fcmToken;
        private UserData userDetails;
        private CarData carDetails;

        public Builder setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
            return this;
        }

        public Builder setUserDetails(UserData userDetails) {
            this.userDetails = userDetails;
            return this;
        }

        public Builder setCarDetails(CarData carDetails) {
            this.carDetails = carDetails;
            return this;
        }

        public UpdateProfileRequest build() {
            return new UpdateProfileRequest(fcmToken, userDetails, carDetails);
        }
    }
}
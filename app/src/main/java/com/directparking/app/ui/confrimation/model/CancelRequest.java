package com.directparking.app.ui.confrimation.model;

/**
 * Created by Mohit on 17/8/18.
 */

public class CancelRequest {
    private String userId;
    private String rideId;

    public CancelRequest(String userId, String rideId) {
        this.userId = userId;
        this.rideId = rideId;
    }

    public static class Builder {
        private String userId;
        private String rideId;

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setRideId(String rideId) {
            this.rideId = rideId;
            return this;
        }

        public CancelRequest build() {
            return new CancelRequest(userId, rideId);
        }
    }
}

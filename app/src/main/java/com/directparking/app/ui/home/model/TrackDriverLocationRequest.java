package com.directparking.app.ui.home.model;

public class TrackDriverLocationRequest {

    private String userId;
    private String rideId;

    public TrackDriverLocationRequest(String userId, String rideId) {
        this.userId = userId;
        this.rideId = rideId;
    }
}
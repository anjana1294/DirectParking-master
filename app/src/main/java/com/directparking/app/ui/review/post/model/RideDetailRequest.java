package com.directparking.app.ui.review.post.model;

public class RideDetailRequest {

    private String userId;
    private String rideId;
    private String isRider;

    public RideDetailRequest(String userId, String rideID,String isRider) {
        this.userId = userId;
        this.rideId = rideID;
        this.isRider = isRider;
    }
}
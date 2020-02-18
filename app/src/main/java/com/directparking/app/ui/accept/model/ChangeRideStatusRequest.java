package com.directparking.app.ui.accept.model;

/**
 * Created by root on 1/8/18.
 */

public class ChangeRideStatusRequest {
    private String userId;
    private String rideId;
    private String driverId;
    private String status;

    public ChangeRideStatusRequest(String userId, String rideId, String driverId, String status) {
        this.userId = userId;
        this.rideId = rideId;
        this.driverId = driverId;
        this.status = status;
    }
}

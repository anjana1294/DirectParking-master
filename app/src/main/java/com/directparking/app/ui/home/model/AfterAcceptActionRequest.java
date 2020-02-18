package com.directparking.app.ui.home.model;

/**
 * Created by root on 6/8/18.
 */

public class AfterAcceptActionRequest {
    private String userId;
    private String rideId;
    private String reason;
    private int isRider;
    private String status;

    public AfterAcceptActionRequest(String userId, String rideId, String reason, int isRider, String status) {
        this.userId = userId;
        this.rideId = rideId;
        this.reason = reason;
        this.isRider = isRider;
        this.status = status;

    }

}

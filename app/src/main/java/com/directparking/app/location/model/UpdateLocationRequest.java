package com.directparking.app.location.model;

public class UpdateLocationRequest {

    private String userId;
    private LocationData locationData;

    public UpdateLocationRequest(String userId, LocationData locationData) {
        this.userId = userId;
        this.locationData = locationData;
    }
}
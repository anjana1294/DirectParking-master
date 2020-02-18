package com.directparking.app.ui.home.model;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class  RideDetailResponse implements Serializable {

    private boolean status;
    private UserData userDetails;
    private int isRider;
    private CarData carDetails;
    private RideLocationData rideInfo;
    private MyRideData requestedRide;
    private String msg;
    @SerializedName("RatingData")
    private RatingData ratingData;

    public RatingData getRatingData() {
        return ratingData;
    }

    public void setRatingData(RatingData ratingData) {
        this.ratingData = ratingData;
    }

    public boolean isStatus() {
        return status;
    }

    public UserData getUserDetails() {
        return userDetails;
    }

    public CarData getCarDetails() {
        return carDetails;
    }

    public RideLocationData getRideLocationData() {
        return rideInfo;
    }

    public String getMsg() {
        return msg;
    }

    public int getIsRider() {
        return isRider;
    }

    public MyRideData getRequestedRide() {
        return requestedRide;
    }
}
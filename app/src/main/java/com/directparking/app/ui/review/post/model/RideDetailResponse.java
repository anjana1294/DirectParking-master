package com.directparking.app.ui.review.post.model;

import com.directparking.app.ui.home.model.RideLocationData;
import com.directparking.app.ui.login.model.UserData;

public class RideDetailResponse {

private boolean status;
private UserData userDetails;
private int isRider;
private RideLocationData rideDetail;
private String msg;

public boolean isStatus() {
    return status;
}

public UserData getUserDetails() {
    return userDetails;
}

public RideLocationData getRideLocationData() {
    return rideDetail;
}

public String getMsg() {
    return msg;
}

public int getIsRider() {
    return isRider;
}
}
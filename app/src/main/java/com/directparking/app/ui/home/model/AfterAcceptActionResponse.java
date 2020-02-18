package com.directparking.app.ui.home.model;

/**
 * Created by root on 6/8/18.
 */

public class AfterAcceptActionResponse {
    private String msg;
    private boolean status;
    private String totalRideTime;

    public String getTotalTime() {
        return totalRideTime;
    }
    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

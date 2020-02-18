package com.directparking.app.ui.history.model;

import java.util.List;

/**
 * Created by root on 21/8/18.
 */

public class HistoryRideResponse {

    private String msg;
    private boolean status;
    private List<HistoryRide> tripData;
    private List<HistoryRide> rideData;

    public List<HistoryRide> getTripData() {
        return tripData;
    }

    public List<HistoryRide> getRideData() {
        return rideData;
    }

    public boolean isStatus() {
        return status;
    }


    public String getMsg() {
        return msg;
    }


}

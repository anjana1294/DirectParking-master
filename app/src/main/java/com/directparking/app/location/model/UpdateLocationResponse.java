package com.directparking.app.location.model;

public class UpdateLocationResponse {

    private String msg;
    private boolean status;
    private long interval;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public long getInterval() {
        return interval;
    }
}
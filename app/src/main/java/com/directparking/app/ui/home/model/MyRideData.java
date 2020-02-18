package com.directparking.app.ui.home.model;

public class MyRideData {

    private String id;
    private String parking_no;
    private String schedule_time;
    private String pick_address;
    private String drop_address;
    private double pick_lat;
    private double pick_log;
    private double drop_lat;
    private double drop_log;


    public MyRideData(String id, String parking_no, String schedule_time,
                      String pick_address, String drop_address, double pick_lat,
                      double pick_log, double drop_lat, double drop_log) {
        this.id = id;
        this.parking_no = parking_no;
        this.schedule_time = schedule_time;
        this.pick_address = pick_address;
        this.drop_address = drop_address;
        this.pick_lat = pick_lat;
        this.pick_log = pick_log;
        this.drop_lat = drop_lat;
        this.drop_log = drop_log;
    }

    public String getId() {
        return id;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public String getPick_address() {
        return pick_address;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public String getParking_no() {
        return parking_no;
    }

    public double getPick_lat() {
        return pick_lat;
    }

    public double getPick_log() {
        return pick_log;
    }

    public double getDrop_lat() {
        return drop_lat;
    }

    public double getDrop_log() {
        return drop_log;
    }
}
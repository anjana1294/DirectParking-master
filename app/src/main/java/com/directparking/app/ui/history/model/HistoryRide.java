package com.directparking.app.ui.history.model;

import java.io.Serializable;

/**
 * Created by root on 1/8/18.
 */

public class HistoryRide implements Serializable {
    private String id;
    private String user_id;
    private String schedule_date;
    private String schedule_time;
    private String pick_address;
    private String drop_address;
    private String status;
    private String created;

    private double pick_lat;
    private double pick_log;
    private double drop_lat;
    private double drop_log;

    private String username;
    private String profile;
    private String fname;
    private String lname;
    private int rating;


    public HistoryRide(String id, String user_id, String schedule_date, String schedule_time,
                       String pick_address, String drop_address, String status, String created,
                       double pick_lat, double pick_log, double drop_lat, double drop_log,
                       String username, String pic, String fname, String lname, int rating) {
        this.id = id;
        this.user_id = user_id;
        this.schedule_date = schedule_date;
        this.schedule_time = schedule_time;
        this.pick_address = pick_address;
        this.drop_address = drop_address;
        this.status = status;
        this.created = created;
        this.pick_lat = pick_lat;
        this.pick_log = pick_log;
        this.drop_lat = drop_lat;
        this.drop_log = drop_log;
        this.username = username;
        this.profile = pic;
        this.fname = fname;
        this.lname = lname;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSchedule_date() {
        return schedule_date;
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

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
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

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }
}

package com.directparking.app.ui.home.model;

public class RatingData {
    private String id;
    private String user_id;
    private String ride_id;
    private String accept_user_id;
    private String arrivaltime;
    private double distance;
    private String pick_address;
    private String drop_address;
    private String parking_no;
    private String fname;
    private String lname;
    private String username;
    private String profile;
    private String isRider;

    public String getIsRider() {
        return isRider;
    }

    public void setIsRider(String isRider) {
        this.isRider = isRider;
    }

    public String getPick_address() {
        return pick_address;
    }

    public void setPick_address(String pick_address) {
        this.pick_address = pick_address;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public void setDrop_address(String drop_address) {
        this.drop_address = drop_address;
    }

    public String getParking_no() {
        return parking_no;
    }

    public void setParking_no(String parking_no) {
        this.parking_no = parking_no;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRide_id() {
        return ride_id;
    }

    public void setRide_id(String ride_id) {
        this.ride_id = ride_id;
    }

    public String getAccept_user_id() {
        return accept_user_id;
    }

    public void setAccept_user_id(String accept_user_id) {
        this.accept_user_id = accept_user_id;
    }


    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

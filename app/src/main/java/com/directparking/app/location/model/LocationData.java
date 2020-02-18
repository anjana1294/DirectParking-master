package com.directparking.app.location.model;

public class LocationData {

    private double lat;
    private double lng;
    private float bearing;
//    private long interval;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

//    public l/ong getInterval() {
//        return interval;
//    }
}
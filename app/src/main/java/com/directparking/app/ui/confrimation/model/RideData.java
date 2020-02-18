package com.directparking.app.ui.confrimation.model;

/**
 * Created by root on 26/7/18.
 */

public class RideData {
    private String address;
    private double latitude;
    private double longitude;

    public RideData(String address, double lat, double lng) {
        this.address = address;
        this.latitude = lat;
        this.longitude = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public static class Builder {
        private String address;
        private double latitude;
        private double longitude;

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RideData build() {
            return new RideData(address, latitude, longitude);
        }
    }
}

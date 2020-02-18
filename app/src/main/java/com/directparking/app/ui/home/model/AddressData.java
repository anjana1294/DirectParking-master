package com.directparking.app.ui.home.model;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

@Parcel
public class AddressData {

    String addressLine1;
    double lat;
    double lng;
    String time;

    public AddressData() {
    }

    public AddressData(String addressLine1, double lat, double lng, String time) {
        this.addressLine1 = addressLine1;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    public String getTime() {
        return time;
    }

    public static class Builder {

        private String addressLine1;

        private double lat;
        private double lng;
        private String time;

        public Builder setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public Builder setLng(double lng) {
            this.lng = lng;
            return this;
        }

        public AddressData build() {
            return new AddressData(addressLine1, lat, lng, time);
        }
    }
}
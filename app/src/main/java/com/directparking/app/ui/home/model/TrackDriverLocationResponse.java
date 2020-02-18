package com.directparking.app.ui.home.model;

import com.directparking.app.location.model.LocationData;

import java.util.List;

/**
 * Created by root on 8/10/18.
 */

public class TrackDriverLocationResponse {

    private String msg;
    private boolean status;

    List<Data> data;

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {
        private AddressData pickupAddress;
        private AddressData deliveryAddress;
        private LocationData driverLocation;

        public AddressData getPickupAddress() {
            return pickupAddress;
        }

        public AddressData getDeliveryAddress() {
            return deliveryAddress;
        }

        public LocationData getDriverLocation() {
            return driverLocation;
        }
    }
}
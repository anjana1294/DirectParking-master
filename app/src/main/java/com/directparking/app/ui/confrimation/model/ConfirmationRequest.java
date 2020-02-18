package com.directparking.app.ui.confrimation.model;

/**
 * Created by root on 26/7/18.
 */

public class ConfirmationRequest {
    private String userId;
    private String schedule;
    private RideData pickupPoint;
    private RideData dropPoint;
    private String parking_no;

    public ConfirmationRequest(String userId, String time, RideData pickup, RideData drop,
                               String parking_no) {
        this.userId = userId;
        this.schedule = time;
        this.pickupPoint = pickup;
        this.dropPoint = drop;
        this.parking_no = parking_no;
    }

    public static class Builder {
        private String userId;
        private String schedule;
        private RideData pickupPoint;
        private RideData dropPoint;
        private String parking_no;

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setSchedule(String schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setPickupPoint(RideData pickupPoint) {
            this.pickupPoint = pickupPoint;
            return this;
        }

        public Builder setDropPoint(RideData dropPoint) {
            this.dropPoint = dropPoint;
            return this;
        }

        public Builder setParking_no(String parking_no) {
            this.parking_no = parking_no;
            return this;
        }

        public ConfirmationRequest build() {
            return new ConfirmationRequest(userId, schedule, pickupPoint, dropPoint, parking_no);
        }
    }
}

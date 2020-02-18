package com.directparking.app.ui.accept.model;

/**
 * Created by root on 1/8/18.
 */

public class RideData {
    private String id;
    private String user_id;
    private String schedule;
    private String pick_address;
    private String pick_lat;
    private String pick_log;
    private String drop_address;
    private String drop_lat;
    private String drop_log;
    private String created;
    private int rating;



    public RideData(String id, String user_id, String schedule, String pick_address,
                    String pick_lat, String pick_log, String drop_address, String drop_lat,
                    String drop_log, String created,int rating) {
        this.id = id;
        this.user_id = user_id;
        this.schedule = schedule;
        this.pick_address = pick_address;
        this.pick_lat = pick_lat;
        this.pick_log = pick_log;
        this.drop_address = drop_address;
        this.drop_lat = drop_lat;
        this.drop_log = drop_log;
        this.created = created;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getPick_address() {
        return pick_address;
    }

    public String getPick_lat() {
        return pick_lat;
    }

    public String getPick_log() {
        return pick_log;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public String getDrop_lat() {
        return drop_lat;
    }

    public String getDrop_log() {
        return drop_log;
    }

    public String getCreated() {
        return created;
    }

    public int getRating() {
        return rating;
    }

    public static class Builder {
        private String id;
        private String user_id;
        private String schedule;
        private String pick_address;
        private String pick_lat;
        private String pick_log;
        private String drop_address;
        private String drop_lat;
        private String drop_log;
        private String created;
        private int rating;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUser_id(String user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder setSchedule(String schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setPick_address(String pick_address) {
            this.pick_address = pick_address;
            return this;
        }

        public Builder setPick_lat(String pick_lat) {
            this.pick_lat = pick_lat;
            return this;
        }

        public Builder setPick_log(String pick_log) {
            this.pick_log = pick_log;
            return this;
        }

        public Builder setDrop_address(String drop_address) {
            this.drop_address = drop_address;
            return this;
        }

        public Builder setDrop_lat(String drop_lat) {
            this.drop_lat = drop_lat;
            return this;
        }

        public Builder setDrop_log(String drop_log) {
            this.drop_log = drop_log;
            return this;
        }

        public Builder setCreated(String created) {
            this.created = created;
            return this;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public RideData build() {
            return new RideData(id, user_id, schedule, pick_address,
                    pick_lat, pick_log, drop_address, drop_lat,
                    drop_log, created,rating);
        }
    }
}

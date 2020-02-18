package com.directparking.app.ui.map.model;

import java.util.List;

class Leg {

    private TextValue distance;
    private TextValue duration;
    private String end_address;
    private LatLng end_location;
    private String start_address;
    private LatLng start_location;
    private List<Step> steps;
    private List<String> traffic_speed_entry;
    private List<String> via_waypoint;

    public TextValue getDistance() {
        return distance;
    }

    public void setDistance(TextValue distance) {
        this.distance = distance;
    }

    public TextValue getDuration() {
        return duration;
    }

    public void setDuration(TextValue duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public LatLng getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LatLng end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public LatLng getStart_location() {
        return start_location;
    }

    public void setStart_location(LatLng start_location) {
        this.start_location = start_location;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<String> getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    public void setTraffic_speed_entry(List<String> traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    public List<String> getVia_waypoint() {
        return via_waypoint;
    }

    public void setVia_waypoint(List<String> via_waypoint) {
        this.via_waypoint = via_waypoint;
    }
}
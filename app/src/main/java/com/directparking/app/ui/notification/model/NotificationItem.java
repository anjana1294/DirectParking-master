package com.directparking.app.ui.notification.model;

public class NotificationItem {


    private String id;
    private String title;
    private String description;

    private String created;
    private String ride_id;
    private String user_id;


    public NotificationItem(String title, String id, String description, String created, String ride_id, String user_id) {
        this.user_id = user_id;
        this.ride_id = ride_id;
        this.created = created;
        this.description = description;
        this.id = id;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public String getCreated() {
        return created;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public NotificationItem setId(String id) {
        this.id = id;
        return this;
    }

    public NotificationItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public NotificationItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public NotificationItem setCreated(String created) {
        this.created = created;
        return this;
    }

    public NotificationItem setRide_id(String ride_id) {
        this.ride_id = ride_id;
        return this;
    }

    public NotificationItem setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }
}
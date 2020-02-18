package com.directparking.app.ui.chat.messages.model;

/**
 * Created by root on 29/8/18.
 */

public class MessageItem {
    private String id;
    private String message;
    private String created;
    private String profile;
    private String fromUserId;
    private String toUserId;
    private String rideId;

    public MessageItem(String id, String message, String created, String profile,
                       String fromUserId, String toUserId, String rideId) {
        this.id = id;
        this.message = message;
        this.created = created;
        this.profile = profile;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.rideId = rideId;
    }

    public String getId() {
        return id;
    }


    public String getMessage() {
        return message;
    }

    public String getTime() {
        return created;
    }

    public String getProfile() {
        return profile;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getRideId() {
        return rideId;
    }

    public MessageItem setId(String id) {
        this.id = id;
        return this;
    }

    public MessageItem setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageItem setTime(String created) {
        this.created = created;
        return this;
    }

    public MessageItem setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public MessageItem setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public MessageItem setToUserId(String toUserId) {
        this.toUserId = toUserId;
        return this;
    }

    public MessageItem setRideId(String rideId) {
        this.rideId = rideId;
        return this;
    }
}

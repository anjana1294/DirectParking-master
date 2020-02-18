package com.directparking.app.ui.chat.chatlist.model;

import java.io.Serializable;

/**
 * Created by root on 29/8/18.
 */

public class ChatItem implements Serializable {
    private String id;
    private String name;
    private String message;
    private String time;
    private String profile;
    private String fromUserId;
    private String toUserId;

    public ChatItem(String id, String name, String message, String time, String profile,
                    String fromUserId, String toUserId) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.time = time;
        this.profile = profile;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
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

    public ChatItem setId(String id) {
        this.id = id;
        return this;
    }

    public ChatItem setName(String name) {
        this.name = name;
        return this;
    }

    public ChatItem setMessage(String message) {
        this.message = message;
        return this;
    }

    public ChatItem setTime(String time) {
        this.time = time;
        return this;
    }

    public ChatItem setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public ChatItem setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public ChatItem setToUserId(String toUserId) {
        this.toUserId = toUserId;
        return this;
    }
}

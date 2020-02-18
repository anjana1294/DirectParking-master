package com.directparking.app.ui.notification.model;

import java.util.List;

public class NotificationListResponse {

    private String msg;
    private boolean status;
    private List<NotificationItem> data;

    public boolean isStatus() {
        return status;
    }

    public List<NotificationItem> getNotificationList() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
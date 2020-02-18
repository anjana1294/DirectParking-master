package com.directparking.app.ui.notification;

import com.directparking.app.ui.notification.model.NotificationItem;

import java.util.List;

import io.reactivex.Single;

public interface NotificationInteractor {

    Single<List<NotificationItem>> fetchNotifications();

    Single<String> clearNotifications();

}
package com.directparking.app.ui.notification;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.notification.model.NotificationItem;
import com.directparking.app.ui.notification.model.NotificationRequest;

import java.util.List;

import io.reactivex.Single;

public class NotificationInteractorImpl implements NotificationInteractor {

    private RestService restService;
    private UserManager userManager;

    public NotificationInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<NotificationItem>> fetchNotifications() {
        return restService.notificationList(new NotificationRequest(userManager.getUserId()))
                .map(NotificationParser::parse);
    }

    @Override
    public Single<String> clearNotifications() {
        return restService.clearNotification(new NotificationRequest(userManager.getUserId()))
                .map(NotificationParser::clear);
    }
}
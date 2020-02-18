package com.directparking.app.ui.notification;

import com.directparking.app.ui.base.BasePresenter;

public abstract class NotificationPresenter extends BasePresenter<NotificationView> {

    abstract void fetchNotifications();

    abstract void clearNotifications();

}
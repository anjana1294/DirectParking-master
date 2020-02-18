package com.directparking.app.ui.notification;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.notification.model.NotificationItem;

import java.util.List;

interface NotificationView extends BaseView {

    void showList(List<NotificationItem> notifications);

    void onItemClicked(NotificationItem item);

    void clearNotifications();

}
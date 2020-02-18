package com.directparking.app.ui.notification;

import android.support.annotation.NonNull;

import com.directparking.app.ui.notification.model.ClearNotificationResponse;
import com.directparking.app.ui.notification.model.NotificationItem;
import com.directparking.app.ui.notification.model.NotificationListResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class NotificationParser {

    @NonNull
    static List<NotificationItem> parse(Response<NotificationListResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            NotificationListResponse body = response.body();

            if (body.isStatus()) {
                List<NotificationItem> notifications = body.getNotificationList();
                if (notifications != null && !notifications.isEmpty()) {
                    return notifications;
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }

    @NonNull
    public static String clear(Response<ClearNotificationResponse> response) {

        if (response.isSuccessful()) {
            ClearNotificationResponse body = response.body();

            if (body.isStatus()) {
                return body.getMsg();
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
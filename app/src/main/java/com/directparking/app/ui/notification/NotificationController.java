package com.directparking.app.ui.notification;

import com.directparking.app.ui.base.BaseEpoxy;
import com.directparking.app.ui.notification.model.NotificationItem;

import java.util.List;

public class NotificationController extends BaseEpoxy {

    private NotificationView view;
    private List<NotificationItem> notifications;

    NotificationController(NotificationView view) {
        this.view = view;
    }

    void setList(List<NotificationItem> notifications) {
        this.notifications = notifications;
        requestModelBuild();
    }

    @Override
    protected void buildModels() {
        for (NotificationItem item : notifications) {
            new NotificationItemModel_()
                    .id(item.getId())
                    .view(view)
                    .title(item.getTitle())
                    .desc(item.getDescription())
                    .timestamp(item.getCreated())
                    .clickListener(itemView -> view.onItemClicked(item))
                    .addTo(this);
        }
    }
}
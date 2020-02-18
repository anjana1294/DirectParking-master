package com.directparking.app.ui.notification.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.notification.NotificationInteractor;
import com.directparking.app.ui.notification.NotificationInteractorImpl;
import com.directparking.app.ui.notification.NotificationPresenter;
import com.directparking.app.ui.notification.NotificationPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Provides
    NotificationInteractor provideNotificationInteractor(RestService restService, UserManager userManager) {
        return new NotificationInteractorImpl(restService, userManager);
    }

    @Provides
    NotificationPresenter provideNotificationPresenter(NotificationInteractor interactor) {
        return new NotificationPresenterImpl(interactor);
    }
}
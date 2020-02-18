package com.directparking.app.ui.history.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.history.HistoryInteractor;
import com.directparking.app.ui.history.HistoryInteractorImpl;
import com.directparking.app.ui.history.HistoryPresenter;
import com.directparking.app.ui.history.HistoryPresenterImpl;
import com.directparking.app.ui.notification.NotificationInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryModule {

    @Provides
    HistoryInteractor provideHistoryInteractor(RestService restService, UserManager userManager) {
        return new HistoryInteractorImpl(restService, userManager);
    }

    @Provides
    HistoryPresenter provideHistoryPresenter(HistoryInteractor interactor) {
        return new HistoryPresenterImpl(interactor);
    }
}
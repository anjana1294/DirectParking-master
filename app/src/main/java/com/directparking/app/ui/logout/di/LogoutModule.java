package com.directparking.app.ui.logout.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.logout.LogoutInteractor;
import com.directparking.app.ui.logout.LogoutInteractorImpl;
import com.directparking.app.ui.logout.LogoutPresenter;
import com.directparking.app.ui.logout.LogoutPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LogoutModule {

    @Provides
    LogoutInteractor provideLogoutInteractor(RestService restService, UserManager userManager) {
        return new LogoutInteractorImpl(restService, userManager);
    }

    @Provides
    LogoutPresenter provideLogoutPresenter(LogoutInteractor interactor) {
        return new LogoutPresenterImpl(interactor);
    }
}
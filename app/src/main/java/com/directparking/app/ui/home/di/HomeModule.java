package com.directparking.app.ui.home.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.home.HomeInteractor;
import com.directparking.app.ui.home.HomeInteractorImpl;
import com.directparking.app.ui.home.HomePresenter;
import com.directparking.app.ui.home.HomePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    HomeInteractor provideHomeInteractor(RestService restService, UserManager userManager) {
        return new HomeInteractorImpl(restService, userManager);
    }

    @Provides
    HomePresenter provideHomePresenter(HomeInteractor interactor) {
        return new HomePresenterImpl(interactor);
    }
}
package com.directparking.app.ui.accept.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.accept.AcceptInteractor;
import com.directparking.app.ui.accept.AcceptInteractorImpl;
import com.directparking.app.ui.accept.AcceptPresenter;
import com.directparking.app.ui.accept.AcceptPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class AcceptModule {

    @Provides
    AcceptInteractor provideAcceptInteractor(RestService restService, UserManager userManager) {
        return new AcceptInteractorImpl(restService, userManager);
    }

    @Provides
    AcceptPresenter provideAcceptPresenter(AcceptInteractor interactor) {
        return new AcceptPresenterImpl(interactor);
    }
}
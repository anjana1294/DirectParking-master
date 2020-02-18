package com.directparking.app.ui.confrimation.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.confrimation.ConfirmationInteractor;
import com.directparking.app.ui.confrimation.ConfirmationInteractorimpl;
import com.directparking.app.ui.confrimation.ConfirmationPresenter;
import com.directparking.app.ui.confrimation.ConfirmationPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class ConfirmationModule {

    @Provides
    ConfirmationInteractor provideConfirmationInteractor(RestService restService, UserManager userManager) {
        return new ConfirmationInteractorimpl(restService,userManager);
    }

    @Provides
    ConfirmationPresenter provideConfirmationPresenter(ConfirmationInteractor interactor) {
        return new ConfirmationPresenterImpl(interactor);
    }
}

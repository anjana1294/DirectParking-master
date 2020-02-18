package com.directparking.app.ui.signup.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.signup.SignupInteractor;
import com.directparking.app.ui.signup.SignupInteractorImpl;
import com.directparking.app.ui.signup.SignupPresenter;
import com.directparking.app.ui.signup.SignupPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SignupModule {

    @Provides
    SignupInteractor provideSignupInteractor(RestService restService, UserManager userManager) {
        return new SignupInteractorImpl(restService, userManager);
    }

    @Provides
    SignupPresenter provideSignupPresenter(SignupInteractor interactor) {
        return new SignupPresenterImpl(interactor);
    }
}
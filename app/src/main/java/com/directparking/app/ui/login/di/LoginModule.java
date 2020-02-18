package com.directparking.app.ui.login.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.login.LoginInteractor;
import com.directparking.app.ui.login.LoginInteractorImpl;
import com.directparking.app.ui.login.LoginPresenter;
import com.directparking.app.ui.login.LoginPresenterImpl;
import com.directparking.app.ui.password.forgot.ForgotPasswordInteractor;
import com.directparking.app.ui.password.forgot.ForgotPasswordInteractorImpl;
import com.directparking.app.ui.password.forgot.ForgotPasswordPresenter;
import com.directparking.app.ui.password.forgot.ForgotPasswordPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginInteractor provideLoginInteractor(RestService restService, UserManager userManager) {
        return new LoginInteractorImpl(restService, userManager);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginInteractor interactor) {
        return new LoginPresenterImpl(interactor);
    }

    @Provides
    ForgotPasswordInteractor provideForgotPasswordInteractor(RestService restService) {
        return new ForgotPasswordInteractorImpl(restService);
    }

    @Provides
    ForgotPasswordPresenter provideForgotPasswordPresenter(ForgotPasswordInteractor interactor) {
        return new ForgotPasswordPresenterImpl(interactor);
    }
}
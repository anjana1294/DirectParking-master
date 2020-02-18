package com.directparking.app.ui.profile.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.logout.LogoutInteractor;
import com.directparking.app.ui.password.change.ChangePasswordInteractor;
import com.directparking.app.ui.password.change.ChangePasswordInteractorImpl;
import com.directparking.app.ui.password.change.ChangePasswordPresenter;
import com.directparking.app.ui.password.change.ChangePasswordPresenterImpl;
import com.directparking.app.ui.profile.edit.ProfileInteractor;
import com.directparking.app.ui.profile.edit.ProfileInteractorImpl;
import com.directparking.app.ui.profile.edit.ProfilePresenter;
import com.directparking.app.ui.profile.edit.ProfilePresenterImpl;
import com.directparking.app.util.FileCacher;

import java.io.File;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    @Provides
    ProfileInteractor provideProfileInteractor(RestService restService, UserManager userManager, FileCacher<File> fileCacher) {
        return new ProfileInteractorImpl(restService, userManager, fileCacher);
    }

    @Provides
    ProfilePresenter provideProfilePresenter(ProfileInteractor interactor) {
        return new ProfilePresenterImpl(interactor);
    }

    @Provides
    ChangePasswordInteractor provideChangePasswordInteractor(RestService restService, UserManager userManager) {
        return new ChangePasswordInteractorImpl(restService, userManager);
    }

    @Provides
    ChangePasswordPresenter provideChangePasswordPresenter(ChangePasswordInteractor changeInteractor, LogoutInteractor logoutInteractor) {
        return new ChangePasswordPresenterImpl(changeInteractor, logoutInteractor);
    }
}
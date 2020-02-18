package com.directparking.app.data.user;


import com.directparking.app.data.prefs.BooleanPreference;
import com.directparking.app.data.prefs.StringPreference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    @Singleton
    UserManager provideUserManager(@Named("isLoggedIn") BooleanPreference isLoggedInPref,
                                   @Named("userId") StringPreference userIdPref,
                                   @Named("fcmToken") StringPreference fcmTokenPref,
                                   @Named("userData") StringPreference userDataPref,
                                   @Named("carData") StringPreference carDataPref,
                                   @Named("locationConfig") StringPreference locationConfigPref,
                                   @Named("profilePath") StringPreference profilePathPref) {
        return new UserManager(isLoggedInPref, userIdPref, fcmTokenPref, userDataPref, carDataPref, locationConfigPref, profilePathPref);
    }
}
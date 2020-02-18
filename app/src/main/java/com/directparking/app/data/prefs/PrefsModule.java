package com.directparking.app.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class PrefsModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("direct_parking", MODE_PRIVATE);
    }

    @Provides
    @Named("fcmToken")
    @Singleton
    StringPreference provideFcmToken(SharedPreferences prefs) {
        return new StringPreference(prefs, "fcmToken");
    }

    @Provides
    @Named("userId")
    @Singleton
    StringPreference provideUserId(SharedPreferences prefs) {
        return new StringPreference(prefs, "userId");
    }

    @Provides
    @Named("isLoggedIn")
    @Singleton
    BooleanPreference provideIsLoggedIn(SharedPreferences prefs) {
        return new BooleanPreference(prefs, "isLoggedIn");
    }

    @Provides
    @Named("userData")
    @Singleton
    StringPreference provideUserData(SharedPreferences prefs) {
        return new StringPreference(prefs, "userData");
    }

    @Provides
    @Named("carData")
    @Singleton
    StringPreference provideCarData(SharedPreferences prefs) {
        return new StringPreference(prefs, "carData");
    }

    @Provides
    @Named("locationConfig")
    @Singleton
    StringPreference provideLocationConfig(SharedPreferences prefs) {
        return new StringPreference(prefs, "locationConfig");
    }

    @Provides
    @Named("profilePath")
    @Singleton
    StringPreference provideProfilePath(SharedPreferences prefs) {
        return new StringPreference(prefs, "profilePath");
    }
}
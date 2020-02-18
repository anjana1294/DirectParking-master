package com.directparking.app.location;

import android.content.Context;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation2.ReactiveLocationProviderConfiguration;

@Module
public class LocationModule {

    @Provides
    ReactiveLocationProvider provideReactiveLocationProvider(Context context) {
        return new ReactiveLocationProvider(context, ReactiveLocationProviderConfiguration.builder()
                .setRetryOnConnectionSuspended(true)
                .build());
    }

    @Provides
    LocationInteractor provideLocationInteractor(RestService restService, UserManager userManager) {
        return new LocationInteractorImpl(restService, userManager);
    }

    @Provides
    LocationPresenter provideLocationPresenter(LocationInteractor interactor) {
        return new LocationPresenterImpl(interactor);
    }
}
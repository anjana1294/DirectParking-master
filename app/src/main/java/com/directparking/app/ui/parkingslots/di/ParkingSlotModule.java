package com.directparking.app.ui.parkingslots.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.parkingslots.ParkingSlotInteractor;
import com.directparking.app.ui.parkingslots.ParkingSlotInteractorimpl;
import com.directparking.app.ui.parkingslots.ParkingSlotPresenter;
import com.directparking.app.ui.parkingslots.ParkingSlotPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class ParkingSlotModule {

    @Provides
    ParkingSlotInteractor provideParkingSlotInteractor(RestService restService, UserManager userManager) {
        return new ParkingSlotInteractorimpl(restService,userManager);
    }

    @Provides
    ParkingSlotPresenter provideParkingSlotPresenter(ParkingSlotInteractor interactor) {
        return new ParkingSlotPresenterImpl(interactor);
    }
}

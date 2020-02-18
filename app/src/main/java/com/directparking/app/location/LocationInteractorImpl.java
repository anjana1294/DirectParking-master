package com.directparking.app.location;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.location.model.LocationData;
import com.directparking.app.location.model.UpdateLocationRequest;
import com.directparking.app.location.model.UpdateLocationResponse;
import com.directparking.app.network.RestService;

import io.reactivex.Single;

public class LocationInteractorImpl implements LocationInteractor {

    private RestService restService;
    private UserManager userManager;

    LocationInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<UpdateLocationResponse> updateLocation(LocationData locationData) {
        return restService.updateLocation(new UpdateLocationRequest(userManager.getUserId(), locationData))
                .map(LocationParser::parse);
    }
}
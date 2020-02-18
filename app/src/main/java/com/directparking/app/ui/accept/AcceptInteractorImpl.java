package com.directparking.app.ui.accept;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.ui.accept.model.AcceptRequest;
import com.directparking.app.ui.accept.model.ChangeRideStatusRequest;

import java.util.List;

import io.reactivex.Single;

public class  AcceptInteractorImpl implements AcceptInteractor {

    private RestService restService;
    private UserManager userManager;

    public AcceptInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<AcceptItem>> fetchRides() {
        return restService.ridesList(new AcceptRequest(userManager.getUserId()))
                .map(AcceptParser::parse);
    }

    @Override
    public Single<String> clearRides() {
        return restService.clearRides(new AcceptRequest(userManager.getUserId()))
                .map(AcceptParser::clear);
    }

    @Override
    public Single<String> changeRideStatus(String userId, String rideId, String status) {

        ChangeRideStatusRequest rideStatusRequest =
                new ChangeRideStatusRequest(userId, rideId, userManager.getUserId(), status);
        return restService.rideStatus(rideStatusRequest)
                .map(RideStatusParser::parse);

    }

}
package com.directparking.app.ui.accept;

import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.ui.accept.model.ChangeRideStatusRequest;
import com.directparking.app.ui.accept.model.ChangeRideStatusResponse;

import java.util.List;

import io.reactivex.Single;

public interface AcceptInteractor {

    Single<List<AcceptItem>> fetchRides();

    Single<String> clearRides();
    Single<String> changeRideStatus(String userId, String rideId, String status);

}
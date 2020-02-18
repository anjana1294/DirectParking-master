package com.directparking.app.ui.confrimation;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.confrimation.model.CancelRequest;
import com.directparking.app.ui.confrimation.model.CancelResponse;
import com.directparking.app.ui.confrimation.model.ConfirmationRequest;
import com.directparking.app.ui.confrimation.model.ConfirmationResponse;
import com.directparking.app.ui.confrimation.model.RideData;

import io.reactivex.Single;

/**
 * Created by root on 24/7/18.
 */

public class ConfirmationInteractorimpl implements ConfirmationInteractor {

    private RestService restService;
    private UserManager userManager;

    public ConfirmationInteractorimpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<ConfirmationResponse> confirmRequest(RideData pickup, RideData drop, String rideTime, String parkingNameNumber) {

        ConfirmationRequest confirmationRequest = new ConfirmationRequest.Builder()
                .setPickupPoint(pickup)
                .setDropPoint(drop)
                .setSchedule(rideTime)
                .setParking_no(parkingNameNumber)
                .setUserId(userManager.getUserId())
                .build();
        return restService.requestRide(confirmationRequest).map(ConfirmationResponseParser::parse);
    }

    @Override
    public Single<CancelResponse> cancelRequest(String ride_id, String user_id) {
        CancelRequest cancelRequest = new CancelRequest.Builder().setRideId(ride_id).setUserId(user_id).build();
        return restService.riderRequestCancel(cancelRequest).map(CancelResponseParser::parse);
    }
}

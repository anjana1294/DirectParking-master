package com.directparking.app.ui.confrimation;

import com.directparking.app.ui.confrimation.model.CancelResponse;
import com.directparking.app.ui.confrimation.model.ConfirmationResponse;
import com.directparking.app.ui.confrimation.model.RideData;

import io.reactivex.Single;

/**
 * Created by root on 24/7/18.
 */

public interface ConfirmationInteractor {
    Single<ConfirmationResponse> confirmRequest(RideData pickup, RideData drop, String rideTime, String parkingNameNumber);

    Single<CancelResponse> cancelRequest(String ride_id, String user_id);
}

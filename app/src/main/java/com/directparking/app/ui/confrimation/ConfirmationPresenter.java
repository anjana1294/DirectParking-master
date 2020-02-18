package com.directparking.app.ui.confrimation;

import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.confrimation.model.RideData;

/**
 * Created by root on 24/7/18.
 */

public abstract class ConfirmationPresenter extends BasePresenter<ConfirmationView> {
    abstract void callConfirmationRequest(RideData pickup, RideData drop, String rideTime, String parkingNameNumber);
    abstract void callCancelPresenter(String ride_id, String user_id);
}

package com.directparking.app.ui.accept;

import com.directparking.app.ui.base.BasePresenter;

public abstract class AcceptPresenter extends BasePresenter<AcceptView> {

    abstract void fetchRides();

    abstract void clearRides();

    abstract void clearRides(String userId, String rideId, String status, int position);

    public abstract void changeRideStatus(String user_id, String id, String status, int position);
}
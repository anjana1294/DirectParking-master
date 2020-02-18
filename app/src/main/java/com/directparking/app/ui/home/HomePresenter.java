package com.directparking.app.ui.home;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.base.HomeView;
import com.google.android.gms.maps.model.LatLng;

public abstract class HomePresenter extends BasePresenter<HomeView> {

    public abstract void fetchDirections(LatLng pickupPoint, LatLng dropPoint);

    public abstract void fetchRideDetails();

    public abstract void callCancelPresenter(String ride_id);

    public abstract void actionAfterAccept(String action, String rideId, String cancelReason, int isRider);

    public abstract void updateLocation(LocationData locationData);

    public abstract void fetchDriverLocation(String rideId, String driverId);


}
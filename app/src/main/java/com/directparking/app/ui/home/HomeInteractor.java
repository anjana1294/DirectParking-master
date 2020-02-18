package com.directparking.app.ui.home;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.location.model.UpdateLocationResponse;
import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.home.model.RideDetailResponse;
import com.directparking.app.ui.home.model.TrackDriverLocationResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface HomeInteractor {

    Observable<String> getDirection(List<LatLng> path);

    Single<RideDetailResponse> getRideDetails();

    Single<String> cancelRequest(String ride_id);

    Single<AfterAcceptActionResponse> actionAfterAccept(String action, String rideId, String cancelReason, int isRider);

    Single<UpdateLocationResponse> updateLocation(LocationData locationData);

    Single<List<TrackDriverLocationResponse.Data>> fetchDriverLocation(String rideId, String driverId);

}
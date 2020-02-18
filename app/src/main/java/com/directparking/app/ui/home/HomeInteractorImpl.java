package com.directparking.app.ui.home;

import android.text.TextUtils;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.location.UpdateLocationParser;
import com.directparking.app.location.model.LocationData;
import com.directparking.app.location.model.UpdateLocationRequest;
import com.directparking.app.location.model.UpdateLocationResponse;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.confrimation.model.CancelRequest;
import com.directparking.app.ui.home.model.AfterAcceptActionRequest;
import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.home.model.RideDetailRequest;
import com.directparking.app.ui.home.model.RideDetailResponse;
import com.directparking.app.ui.home.model.TrackDriverLocationRequest;
import com.directparking.app.ui.home.model.TrackDriverLocationResponse;
import com.directparking.app.ui.map.DirectionParser;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;

public class HomeInteractorImpl implements HomeInteractor {

    private RestService restService;
    private UserManager userManager;

    public HomeInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Observable<String> getDirection(List<LatLng> path) {
        String origin = !path.isEmpty() ? path.get(0).latitude + "," + path.get(0).longitude : "";

        String destination = path.size() > 1 ? path.get(path.size() - 1).latitude + "," + path.get(path.size() - 1).longitude : "";

        List<String> wayPoints = new ArrayList<>();

        for (int i = 1; i < path.size() - 1; i++) {
            wayPoints.add(path.get(i).latitude + "," + path.get(i).longitude);
        }

        Map<String, String> query = new HashMap<>();
        query.put("origin", origin);
        query.put("waypoints", TextUtils.join("|", wayPoints));
        query.put("destination", destination);
        query.put("key", "AIzaSyDzXvyJVHpVjnLD76V1iMyEDigUn1SQ0Cs");

        return restService.directions(query)
                .map(DirectionParser::parse);
    }

    @Override
    public Single<RideDetailResponse> getRideDetails() {
        return restService.rideDetails(new RideDetailRequest(userManager.getUserId()))
                .map(response -> {
                    RideDetailResponse detailResponse = RideDetailParser.parse(response);
                    return detailResponse;
                });
    }

    @Override
    public Single<String> cancelRequest(String ride_id) {
        CancelRequest cancelRequest = new CancelRequest.Builder().setRideId(ride_id).setUserId(userManager.getUserId()).build();
        return restService.riderRequestCancel(cancelRequest).map(CancelMyRideParser::parse);
    }

    @Override
    public Single<AfterAcceptActionResponse> actionAfterAccept(String action, String rideId, String cancelReason, int isRider) {
        return restService.afterAcceptStatus(new AfterAcceptActionRequest(userManager.getUserId(), rideId,
                cancelReason, isRider, action))
                .map(response -> {
                    AfterAcceptActionResponse actionResponse = AfterAcceptActionParser.parse(response);
                    return actionResponse;
                });
    }

    @Override
    public Single<UpdateLocationResponse> updateLocation(LocationData locationData) {
        return restService.updateLocation(new UpdateLocationRequest(userManager.getUserId(), locationData))
                .map(UpdateLocationParser::parse);
    }

    @Override
    public Single<List<TrackDriverLocationResponse.Data>> fetchDriverLocation(String rideId, String driverId) {
        return restService.trackOrder(new TrackDriverLocationRequest(driverId, rideId))
                .map(TrackDriverLocationParser::parse);
    }
}
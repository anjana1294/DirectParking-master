package com.directparking.app.ui.base;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.home.model.RideDetailResponse;

public interface HomeView extends BaseView {

    void drawPath(String path);

    void showRideDetails(RideDetailResponse rideDetailResponse);

    void showViewAfterAccept(AfterAcceptActionResponse acceptActionResponse, String action, int isRider);

    void hideBottomView();

    void showDriverLocation(LocationData location);

    void destroyJobSchedule();

}
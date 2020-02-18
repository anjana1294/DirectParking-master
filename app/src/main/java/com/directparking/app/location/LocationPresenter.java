package com.directparking.app.location;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.base.HomeView;

public abstract class LocationPresenter extends BasePresenter<HomeView> {

    public abstract void updateLocation(LocationData locationData);

}
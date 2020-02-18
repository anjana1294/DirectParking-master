package com.directparking.app.location;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.location.model.UpdateLocationResponse;

import io.reactivex.Single;

public interface LocationInteractor {

    Single<UpdateLocationResponse> updateLocation(LocationData locationData);

}
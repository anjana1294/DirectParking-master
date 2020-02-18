package com.directparking.app.data.user;

import com.directparking.app.data.prefs.BooleanPreference;
import com.directparking.app.data.prefs.StringPreference;

import javax.inject.Inject;
import javax.inject.Named;

public class UserManager {

    @Inject
    @Named("isLoggedIn")
    BooleanPreference isLoggedInPref;

    @Inject
    @Named("userId")
    StringPreference userIdPref;

    @Inject
    @Named("fcmToken")
    StringPreference fcmTokenPref;

    @Inject
    @Named("userData")
    StringPreference userDataPref;

    @Inject
    @Named("carData")
    StringPreference carDataPref;

    @Inject
    @Named("locationConfig")
    StringPreference locationConfigPref;

    @Inject
    @Named("profilePath")
    StringPreference profilePathPref;

    @Inject
    @Named("isRideStarted")
    BooleanPreference isRideStartedPref;

    UserManager(BooleanPreference isLoggedInPref,
                StringPreference userIdPref,
                StringPreference fcmTokenPref,
                StringPreference userDataPref,
                StringPreference carDataPref,
                StringPreference locationConfigPref,
                StringPreference profilePathPref) {
        this.isLoggedInPref = isLoggedInPref;
        this.userIdPref = userIdPref;
        this.fcmTokenPref = fcmTokenPref;
        this.userDataPref = userDataPref;
        this.carDataPref = carDataPref;
        this.locationConfigPref = locationConfigPref;
        this.profilePathPref = profilePathPref;
//        this.isRideStartedPref = isRideStartedPref;
    }

    public boolean isLoggedIn() {
        return isLoggedInPref != null && isLoggedInPref.get();
    }

    public void login(String userData, String carData) {
        isLoggedInPref.set(true);
        updateUserData(userData);
        updateCarData(carData);
    }

    public void updateUserData(String userData) {
        userDataPref.set(userData);
    }

    public String getUserData() {
        return userDataPref.get();
    }

    public void updateCarData(String carData) {
        carDataPref.set(carData);
    }

    public String getCarData() {
        return carDataPref.get();
    }

    public void updateLocationConfig(String locationConfig) {
        locationConfigPref.set(locationConfig);
    }

    public String getLocationConfig() {
        return locationConfigPref.get();
    }

    public String getUserId() {
        return userIdPref.get();
    }

    public void setUserId(String id) {
        userIdPref.set(id);
    }

    public String getFcmToken() {
        return fcmTokenPref.get();
    }

    public void setFcmToken(String fcmToken) {
        fcmTokenPref.set(fcmToken);
    }

    public String getProfilePath() {
        return profilePathPref.get();
    }

    public void setProfilePath(String path) {
        profilePathPref.set(path);
    }

//    public boolean isRideStarted() {
//        return isRideStartedPref != null && isRideStartedPref.get();
//    }
//
//    public void rideStarted() {
//        isRideStartedPref.set(true);
//    }

    public void clearData() {
        userIdPref.delete();
        userDataPref.delete();
        carDataPref.delete();
        isLoggedInPref.delete();
        profilePathPref.delete();
        locationConfigPref.delete();
//        isRideStartedPref.delete();
    }
}
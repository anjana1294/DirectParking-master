package com.directparking.app.ui.profile.edit;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.profile.view.model.MyProfileRequest;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;

import io.reactivex.Single;

public interface ProfileInteractor {

    Single<String> updateProfile(UserData userData, CarData carData);
    Single<MyProfileResponse> getProfile();


}
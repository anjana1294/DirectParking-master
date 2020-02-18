package com.directparking.app.ui.profile.edit;

import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

public abstract class ProfilePresenter extends BasePresenter<EditProfileView> {

    abstract void updateProfile(UserData userData, CarData carData);
    public abstract void getProfile();
}
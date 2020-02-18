package com.directparking.app.ui.signup;

import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

public abstract class SignupPresenter extends BasePresenter<SignupView> {

    abstract void fetchUniversities();

    abstract void callSignup(UserData data, CarData carData);

//    abstract void suggestionUniversities(String name);

}
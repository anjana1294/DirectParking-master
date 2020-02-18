package com.directparking.app.ui.signup;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.signup.model.IdNameItem;

import java.util.List;

import io.reactivex.Single;

public interface SignupInteractor {

    Single<List<IdNameItem>> fetchUniversities();

    Single<String> signup(UserData userData, CarData carData);

//    Single<List<IdNameItem>> suggestionUniversities(String name);


}
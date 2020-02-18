package com.directparking.app.ui.signup;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.signup.model.IdNameItem;
import com.directparking.app.ui.signup.model.SignupRequest;
import com.directparking.app.ui.signup.model.UniversityRequest;

import java.util.List;

import io.reactivex.Single;

public class SignupInteractorImpl implements SignupInteractor {

    private RestService restService;
    private UserManager userManager;

    public SignupInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<IdNameItem>> fetchUniversities() {
        return restService.universityList(new UniversityRequest(userManager.getUserId()))
                .map(UniversityParser::parse);
    }

    @Override
    public Single<String> signup(UserData userData, CarData carData) {
        return restService.signup(new SignupRequest(userManager.getFcmToken(), userData, carData))
                .map(SignupParser::parse);
    }
//
//    @Override
//    public Single<List<IdNameItem>> suggestionUniversities(String name) {
//        return restService.universitySuggestionList(new UniversityRequest(userManager.getUserId(), name))
//                .map(UniversityParser::parse);
//    }
}
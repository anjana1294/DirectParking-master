package com.directparking.app.ui.login;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.LocationConfig;
import com.directparking.app.ui.login.model.LoginRequest;
import com.directparking.app.ui.login.model.LoginResponse;
import com.directparking.app.ui.login.model.UserData;
import com.google.gson.Gson;

import io.reactivex.Single;

public class LoginInteractorImpl implements LoginInteractor {

    private RestService restService;
    private UserManager userManager;

    public LoginInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> login(String username, String password) {

        return restService.login(new LoginRequest(userManager.getFcmToken(), username, password))
                .map(response -> {
                    LoginResponse loginResponse = LoginParser.parse(response);

                    UserData userData = loginResponse.getUserDetails();
                    CarData carData = loginResponse.getCarDetails();
                    userManager.login(new Gson().toJson(userData), new Gson().toJson(carData));
                    userManager.setUserId(userData.getUserId());
                    userManager.setProfilePath(userData.getProfile());

                    LocationConfig config = loginResponse.getLocationConfig();
                    userManager.updateLocationConfig(new Gson().toJson(config));

                    return loginResponse.getMsg();
                });
    }
}
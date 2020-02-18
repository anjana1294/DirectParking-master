package com.directparking.app.ui.logout;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.logout.model.LogoutRequest;

import io.reactivex.Single;

public class  LogoutInteractorImpl implements LogoutInteractor {

    private RestService restService;
    private UserManager userManager;

    public LogoutInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> logout() {

        String userId = userManager.getUserId();
        userManager.clearData();

        return restService.logout(new LogoutRequest(userId))
                .map(LogoutParser::parse);
    }
}
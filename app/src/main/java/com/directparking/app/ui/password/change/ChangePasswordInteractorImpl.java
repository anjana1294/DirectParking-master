package com.directparking.app.ui.password.change;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.password.change.model.ChangePasswordRequest;

import io.reactivex.Single;

public class ChangePasswordInteractorImpl implements ChangePasswordInteractor {

    private RestService restService;
    private UserManager userManager;

    public ChangePasswordInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> changePassword(String oldPassword, String newPassword) {
        return restService.changePassword(new ChangePasswordRequest(userManager.getUserId(), oldPassword, newPassword))
                .map(ChangePasswordParser::parse);
    }
}
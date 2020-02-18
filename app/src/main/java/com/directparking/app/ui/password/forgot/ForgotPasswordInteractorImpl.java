package com.directparking.app.ui.password.forgot;

import com.directparking.app.network.RestService;
import com.directparking.app.ui.password.forgot.model.ForgotPasswordRequest;

import io.reactivex.Single;

public class ForgotPasswordInteractorImpl implements ForgotPasswordInteractor {

    private RestService restService;

    public ForgotPasswordInteractorImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<String> forgotPassword(String username) {

        return restService.forgotPassword(new ForgotPasswordRequest(username))
                .map(ForgotPasswordParser::parse);
    }
}
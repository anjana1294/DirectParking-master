package com.directparking.app.ui.login;

import android.support.annotation.NonNull;

import com.directparking.app.ui.login.model.LoginResponse;

import retrofit2.Response;

public class LoginParser {

    @NonNull
    public static LoginResponse parse(Response<LoginResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            LoginResponse body = response.body();

            if (body != null) {
                if (body.isStatus()) {
                    if (body.getUserDetails() != null) {
                        return body;
                    } else {
                        throw new RuntimeException("User details is empty!");
                    }
                } else {
                    throw new RuntimeException(body.getMsg());
                }
            } else {
                throw new RuntimeException("Response payload is empty!");
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
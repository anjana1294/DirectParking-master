package com.directparking.app.ui.signup;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.signup.model.SignupResponse;

import retrofit2.Response;

public class SignupParser {

    @NonNull
    public static String parse(Response<SignupResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            SignupResponse body = response.body();

            if (body != null) {
                if (body.isStatus()) {
                    if (!TextUtils.isEmpty(body.getMsg())) {
                        return body.getMsg();
                    } else {
                        throw new RuntimeException("Message is empty!");
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
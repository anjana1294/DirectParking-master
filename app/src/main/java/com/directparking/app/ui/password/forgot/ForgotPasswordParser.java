package com.directparking.app.ui.password.forgot;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.password.forgot.model.ForgotPasswordResponse;

import java.io.IOException;

import retrofit2.Response;

class ForgotPasswordParser {

    @NonNull
    static String parse(Response<ForgotPasswordResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            ForgotPasswordResponse body = response.body();

            if (body.isStatus()) {
                if (!TextUtils.isEmpty(body.getMsg())) {
                    return body.getMsg();
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
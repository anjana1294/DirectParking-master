package com.directparking.app.ui.password.change;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.password.change.model.ChangePasswordResponse;

import java.io.IOException;

import retrofit2.Response;

class ChangePasswordParser {

    @NonNull
    static String parse(Response<ChangePasswordResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            ChangePasswordResponse body = response.body();

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
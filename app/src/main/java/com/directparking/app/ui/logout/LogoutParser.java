package com.directparking.app.ui.logout;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.logout.model.LogoutResponse;

import java.io.IOException;

import retrofit2.Response;

class LogoutParser {

    @NonNull
    static String parse(Response<LogoutResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            LogoutResponse body = response.body();

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
package com.directparking.app.ui.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.confrimation.model.CancelResponse;

import retrofit2.Response;

class CancelMyRideParser {

    @NonNull
    static String parse(Response<CancelResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            CancelResponse body = response.body();

            if (body.getStatus()) {
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
package com.directparking.app.ui.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.home.model.RideDetailResponse;

import retrofit2.Response;

public class RideDetailParser {

    @NonNull
    public static RideDetailResponse parse(Response<RideDetailResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            RideDetailResponse body = response.body();

            if (body != null) {
                if (body.isStatus()) {
                    if (!TextUtils.isEmpty(body.getMsg())) {
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
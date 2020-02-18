package com.directparking.app.ui.profile.edit;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.profile.edit.model.UpdateProfileResponse;

import retrofit2.Response;

class ProfileParser {

    @NonNull
    static String parse(Response<UpdateProfileResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            UpdateProfileResponse body = response.body();

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
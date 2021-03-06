package com.directparking.app.ui.confrimation;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.confrimation.model.ConfirmationResponse;
import com.directparking.app.ui.profile.edit.model.UpdateProfileResponse;

import retrofit2.Response;

class ConfirmationResponseParser {

    @NonNull
    static ConfirmationResponse parse(Response<ConfirmationResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            ConfirmationResponse body = response.body();

            if (body.isStatus()) {
                if (!TextUtils.isEmpty(body.getMsg())) {
                    return body;
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
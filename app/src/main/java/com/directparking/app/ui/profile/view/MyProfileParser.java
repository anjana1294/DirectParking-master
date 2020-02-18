package com.directparking.app.ui.profile.view;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.profile.edit.model.UpdateProfileResponse;
import com.directparking.app.ui.profile.view.model.MyProfileRequest;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;

import retrofit2.Response;

public class MyProfileParser {

    @NonNull
    public static MyProfileResponse parse(Response<MyProfileResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            MyProfileResponse body = response.body();

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
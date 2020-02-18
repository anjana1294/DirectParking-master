package com.directparking.app.ui.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.profile.edit.model.UpdateProfileResponse;

import retrofit2.Response;

/**
 * Created by root on 6/8/18.
 */

public class AfterAcceptActionParser {

    @NonNull
    static AfterAcceptActionResponse parse(Response<AfterAcceptActionResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            AfterAcceptActionResponse body = response.body();

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

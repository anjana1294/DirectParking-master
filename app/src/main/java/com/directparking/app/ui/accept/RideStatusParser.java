package com.directparking.app.ui.accept;

import android.support.annotation.NonNull;

import com.directparking.app.ui.accept.model.ChangeRideStatusResponse;
import com.directparking.app.ui.accept.model.ClearAcceptResponse;

import java.io.IOException;

import retrofit2.Response;

public class RideStatusParser {

    @NonNull
    public static String parse(Response<ChangeRideStatusResponse> response) {

        if (response.isSuccessful()) {
            ChangeRideStatusResponse body = response.body();

            if (body.isStatus()) {
                return body.getMsg();
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }

}
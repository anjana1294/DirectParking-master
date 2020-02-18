package com.directparking.app.location;

import android.support.annotation.NonNull;


import com.directparking.app.location.model.UpdateLocationResponse;

import java.io.IOException;

import retrofit2.Response;

public class UpdateLocationParser {

    @NonNull
   public static UpdateLocationResponse parse(Response<UpdateLocationResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            UpdateLocationResponse body = response.body();

            if (body.isStatus()) {
                return body;
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
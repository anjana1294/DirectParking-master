package com.directparking.app.ui.home;

import android.support.annotation.NonNull;

import com.directparking.app.ui.home.model.TrackDriverLocationResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

class TrackDriverLocationParser {

    @NonNull
    static List<TrackDriverLocationResponse.Data> parse(Response<TrackDriverLocationResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            TrackDriverLocationResponse body = response.body();

            if (body.isStatus()) {
                if (body.getData() != null) {
                    return body.getData();
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
package com.directparking.app.ui.map;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.map.model.DirectionResponse;

import retrofit2.Response;

public class DirectionParser {

    @NonNull
    public static String parse(Response<DirectionResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (response.body().getStatus() != null && response.body().getStatus().equalsIgnoreCase("ZERO_RESULTS")) {
                    throw new RuntimeException("No result found!");
                } else {
                    return response.body().getRoutes().get(0).getOverview_polyline().getPoints();
                }
            } else {
                throw new RuntimeException("Something went wrong");
            }
        } else {
            if (!TextUtils.isEmpty(response.message())) {
                throw new RuntimeException(response.message());
            } else {
                throw new RuntimeException("Something went wrong");
            }
        }
    }
}
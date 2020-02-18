package com.directparking.app.ui.review.post;

import android.support.annotation.NonNull;

import com.directparking.app.ui.review.post.model.RideDetailResponse;

import retrofit2.Response;

public class RideDetailParser {

    @NonNull
    public static RideDetailResponse parse(Response<RideDetailResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            RideDetailResponse body = response.body();

            if (body != null) {
                if (body.isStatus()) {
                    if (body.getUserDetails() != null) {
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
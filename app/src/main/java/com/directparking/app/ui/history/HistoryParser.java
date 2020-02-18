package com.directparking.app.ui.history;

import android.support.annotation.NonNull;

import com.directparking.app.ui.history.model.HistoryRideResponse;

import java.io.IOException;

import retrofit2.Response;

public class HistoryParser {

    @NonNull
    static HistoryRideResponse parse(Response<HistoryRideResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            HistoryRideResponse body = response.body();

            if (body.isStatus()) {
                if (body != null) {
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
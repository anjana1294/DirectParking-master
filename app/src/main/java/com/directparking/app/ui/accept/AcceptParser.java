package com.directparking.app.ui.accept;

import android.support.annotation.NonNull;

import com.directparking.app.ui.accept.model.ClearAcceptResponse;
import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.ui.accept.model.AcceptListResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class AcceptParser {

    @NonNull
    static List<AcceptItem> parse(Response<AcceptListResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            AcceptListResponse body = response.body();

            if (body.isStatus()) {
                List<AcceptItem> acceptItems = body.getAcceptItems();
                if (acceptItems != null && !acceptItems.isEmpty()) {
                    return acceptItems;
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

    @NonNull
    public static String clear(Response<ClearAcceptResponse> response) {

        if (response.isSuccessful()) {
            ClearAcceptResponse body = response.body();

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
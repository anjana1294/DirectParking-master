package com.directparking.app.ui.review.view;

import android.support.annotation.NonNull;

import com.directparking.app.ui.review.view.model.ReviewItem;
import com.directparking.app.ui.review.view.model.ReviewListResponse;

import java.util.List;

import retrofit2.Response;

class ViewReviewResponseParser {

    @NonNull
    static List<ReviewItem> parse(Response<ReviewListResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            ReviewListResponse body = response.body();

            if (body.isStatus()) {
                if (body.getData() != null && !body.getData().isEmpty()) {
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
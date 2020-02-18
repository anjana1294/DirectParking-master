package com.directparking.app.ui.review.post;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.review.post.model.ReviewRatingResponse;

import retrofit2.Response;

class ReviewResponseParser {

    @NonNull
    static String parse(Response<ReviewRatingResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            ReviewRatingResponse body = response.body();

            if (body.isStatus()) {
                if (!TextUtils.isEmpty(body.getMsg())) {
                    return body.getMsg();
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
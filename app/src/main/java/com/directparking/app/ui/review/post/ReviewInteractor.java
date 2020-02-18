package com.directparking.app.ui.review.post;

import com.directparking.app.ui.review.post.model.RideDetailResponse;

import io.reactivex.Single;

/**
 * Created by root on 10/8/18.
 */

public interface ReviewInteractor {
    Single<String> callRatingSubmit(String relatedID, String rating, String review, String rideId);

    Single<RideDetailResponse> callRideDetail(String rideID, String isRider);

}

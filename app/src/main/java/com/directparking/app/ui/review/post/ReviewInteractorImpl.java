package com.directparking.app.ui.review.post;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.review.post.model.ReviewRatingRequest;
import com.directparking.app.ui.review.post.model.RideDetailRequest;
import com.directparking.app.ui.review.post.model.RideDetailResponse;

import io.reactivex.Single;

/**
 * Created by root on 10/8/18.
 */

public class ReviewInteractorImpl implements ReviewInteractor {


    private RestService restService;
    private UserManager userManager;

    public ReviewInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> callRatingSubmit(String relatedID, String rating, String review, String rideId) {

        return restService.addRating(new ReviewRatingRequest(userManager.getUserId(), relatedID, rating, review, rideId))
                .map(response -> {
                    String msg = ReviewResponseParser.parse(response);
                    return msg;
                });
    }

    @Override
    public Single<RideDetailResponse> callRideDetail(String rideID, String isRider) {

        return restService.rideDetail(new RideDetailRequest(userManager.getUserId(), rideID, isRider))
                .map(response -> {
                    RideDetailResponse rideDetailResponse = RideDetailParser.parse(response);
                    return rideDetailResponse;
                });
    }
}

package com.directparking.app.ui.review.view;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.review.view.model.ReviewItem;
import com.directparking.app.ui.review.view.model.ReviewListRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 10/8/18.
 */

public class ViewReviewInteractorImpl implements ViewReviewInteractor {


    private RestService restService;
    private UserManager userManager;

    public ViewReviewInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<ReviewItem>> fetchReviewList(String driverId) {
        return restService.reviewList(new ReviewListRequest(driverId))
                .map(ViewReviewResponseParser::parse);
    }
}

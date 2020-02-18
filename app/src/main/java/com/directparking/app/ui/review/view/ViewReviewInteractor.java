package com.directparking.app.ui.review.view;

import com.directparking.app.ui.review.view.model.ReviewItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 10/8/18.
 */

public interface ViewReviewInteractor {
    Single<List<ReviewItem>> fetchReviewList(String driverId);


}

package com.directparking.app.ui.review.post;

import com.directparking.app.ui.base.BasePresenter;

/**
 * Created by root on 10/8/18.
 */

public abstract class ReviewPresenter extends BasePresenter<ReviewView> {
    abstract void callRatingSubmit(String relatedID, String rating, String review, String rideId);
    abstract void getRideDetail(String rideId, String isRider);

}

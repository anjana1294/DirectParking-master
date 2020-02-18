package com.directparking.app.ui.review.view;

import com.directparking.app.ui.base.BasePresenter;

/**
 * Created by root on 10/8/18.
 */

public abstract class ViewReviewPresenter extends BasePresenter<ViewReviewView> {
    abstract void fetchReviewList(String driverId);
}

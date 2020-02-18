package com.directparking.app.ui.review.post;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.review.post.model.RideDetailResponse;

/**
 * Created by root on 10/8/18.
 */

public interface ReviewView extends BaseView {
    void showSuccessMessage(String msg);
    void showRideData(RideDetailResponse data);

}

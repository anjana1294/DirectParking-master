package com.directparking.app.ui.review.view;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.review.view.model.ReviewItem;

import java.util.List;

/**
 * Created by root on 10/8/18.
 */

public interface ViewReviewView extends BaseView {

    void showReviewList(List<ReviewItem> reviewItemList);

}

package com.directparking.app.ui.review.view.di;

import com.directparking.app.ui.review.view.ViewReviewActivity;

import dagger.Subcomponent;

/**
 * Created by root on 24/7/18.
 */

@ViewReviewScope
@Subcomponent(modules = {ViewReviewModule.class})
public interface ViewReviewComponent {
    void inject(ViewReviewActivity activity);
}

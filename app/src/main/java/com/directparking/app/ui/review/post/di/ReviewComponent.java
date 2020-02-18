package com.directparking.app.ui.review.post.di;

import com.directparking.app.ui.review.post.ReviewActivity;

import dagger.Subcomponent;

/**
 * Created by root on 24/7/18.
 */

@ReviewScope
@Subcomponent(modules = {ReviewModule.class})
public interface ReviewComponent {
    void inject(ReviewActivity activity);
}

package com.directparking.app.ui.review.post.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.review.post.ReviewInteractor;
import com.directparking.app.ui.review.post.ReviewInteractorImpl;
import com.directparking.app.ui.review.post.ReviewPresenter;
import com.directparking.app.ui.review.post.ReviewPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class ReviewModule {

    @Provides
    ReviewInteractor provideReviewInteractor(RestService restService, UserManager userManager) {
        return new ReviewInteractorImpl(restService,userManager);
    }

    @Provides
    ReviewPresenter provideReviewPresenter(ReviewInteractor interactor) {
        return new ReviewPresenterImpl(interactor);
    }
}

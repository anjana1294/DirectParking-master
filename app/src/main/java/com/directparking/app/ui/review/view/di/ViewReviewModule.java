package com.directparking.app.ui.review.view.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.review.view.ViewReviewInteractor;
import com.directparking.app.ui.review.view.ViewReviewInteractorImpl;
import com.directparking.app.ui.review.view.ViewReviewPresenter;
import com.directparking.app.ui.review.view.ViewReviewPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class ViewReviewModule {

    @Provides
    ViewReviewInteractor provideViewReviewInteractor(RestService restService, UserManager userManager) {
        return new ViewReviewInteractorImpl(restService, userManager);
    }

    @Provides
    ViewReviewPresenter provideViewReviewPresenter(ViewReviewInteractor interactor) {
        return new ViewReviewPresenterImpl(interactor);
    }
}

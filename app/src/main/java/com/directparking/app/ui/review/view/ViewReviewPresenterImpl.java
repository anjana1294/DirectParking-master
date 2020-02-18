package com.directparking.app.ui.review.view;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 10/8/18.
 */

public class ViewReviewPresenterImpl extends ViewReviewPresenter {

    private ViewReviewView view;
    private ViewReviewInteractor interactor;

    public ViewReviewPresenterImpl(ViewReviewInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ViewReviewView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void fetchReviewList(String driverId) {
        showProgressBar();
        disposable = interactor.fetchReviewList(driverId)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(reviewItems -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showReviewList(reviewItems);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                    showEmptyView();
                });
    }
}

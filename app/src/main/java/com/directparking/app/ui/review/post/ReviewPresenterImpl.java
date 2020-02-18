package com.directparking.app.ui.review.post;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 10/8/18.
 */

public class ReviewPresenterImpl extends ReviewPresenter {

    private ReviewView view;
    private ReviewInteractor interactor;

    public ReviewPresenterImpl(ReviewInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ReviewView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void callRatingSubmit(String relatedID, String rating, String review, String rideId) {
        showProgressBar();

        disposable = interactor.callRatingSubmit(relatedID, rating, review,rideId)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showSuccessMessage(msg);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

    @Override
    void getRideDetail(String rideId, String isRider) {
        showProgressBar();

        disposable = interactor.callRideDetail(rideId,isRider)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(data -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showRideData(data);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }
}

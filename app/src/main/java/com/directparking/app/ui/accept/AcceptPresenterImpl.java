package com.directparking.app.ui.accept;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

 public class AcceptPresenterImpl extends AcceptPresenter {

    private AcceptView view;
    private AcceptInteractor interactor;

    public AcceptPresenterImpl(AcceptInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(AcceptView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    public void fetchRides() {
        showProgressBar();

        disposable = interactor.fetchRides()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(rides -> {
                    hideProgressBar();

                    if (isViewAttached()) {
                        view.showList(rides);
                    }
                }, error -> {
                    hideProgressBar();
                   showMessage(error.getMessage());
                    showEmptyView();
                });
    }

    @Override
    public void clearRides() {
        showProgressDialog();

        disposable = interactor.clearRides()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    showMessage(msg);
                    showEmptyView();
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }

     @Override
     void clearRides(String userId, String rideId, String status, int position) {

     }


     @Override
     public void changeRideStatus(String userId, String rideId, String status, int position) {
        showProgressDialog();
        disposable = interactor.changeRideStatus(userId, rideId, status)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    this.view.onStatusChanged(msg, status, position);
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }


}
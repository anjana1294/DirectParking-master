package com.directparking.app.ui.confrimation;


import com.directparking.app.ui.confrimation.model.RideData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 24/7/18.
 */

public class ConfirmationPresenterImpl extends ConfirmationPresenter {

    private ConfirmationView view;
    private ConfirmationInteractor interactor;

    public ConfirmationPresenterImpl(ConfirmationInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ConfirmationView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void callConfirmationRequest(RideData pickup, RideData drop, String rideTime, String parkingNameNumber) {
        showProgressBar();

        disposable = interactor.confirmRequest(pickup, drop, rideTime, parkingNameNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    view.showSuccessAlert(msg);
                }, error -> {
                    hideProgressBar();
                });
    }

    @Override
    void callCancelPresenter(String ride_id, String user_id) {
        showProgressBar();
        disposable = interactor.cancelRequest(ride_id, user_id).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(msg -> {
            hideProgressBar();
            view.showCancelAlert(msg);
        }, error -> {
            hideProgressBar();
        });
    }
}

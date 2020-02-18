package com.directparking.app.location;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.base.HomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class             LocationPresenterImpl extends LocationPresenter {

    private LocationInteractor interactor;

    LocationPresenterImpl(LocationInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(HomeView view) {
        super.setView(view);
    }

    @Override
    public void updateLocation(LocationData locationData) {
        disposable = interactor.updateLocation(locationData)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                }, error -> showMessage(error.getMessage()));
    }
}
package com.directparking.app.ui.home;

import com.directparking.app.location.model.LocationData;
import com.directparking.app.ui.base.HomeView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl extends HomePresenter {

    private HomeView view;
    private HomeInteractor interactor;

    public HomePresenterImpl(HomeInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(HomeView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    public void fetchDirections(LatLng pickupPoint, LatLng dropPoint) {
        showProgressBar();
        List<LatLng> orderPath = new ArrayList<>();
        orderPath.add(pickupPoint);
        orderPath.add(dropPoint);
        disposable = interactor.getDirection(orderPath)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(path -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.drawPath(path);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

    @Override
    public void fetchRideDetails() {
        showProgressBar();
        disposable = interactor.getRideDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(data -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showRideDetails(data);
                    }
                }, error -> {
                    hideProgressBar();
                    view.hideBottomView();
                });
    }

    @Override
    public void callCancelPresenter(String ride_id) {
        showProgressBar();
        disposable = interactor.cancelRequest(ride_id)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showMessage(msg);
                        view.hideBottomView();
                    }
                }, error -> {
                    hideProgressBar();
                    view.hideBottomView();
                });
    }

    @Override
    public void actionAfterAccept(String action, String rideId, String cancelReason, int isRider) {
        showProgressBar();
        disposable = interactor.actionAfterAccept(action, rideId, cancelReason, isRider)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showViewAfterAccept(msg, action, isRider);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

    @Override
    public void updateLocation(LocationData locationData) {
        disposable = interactor.updateLocation(locationData)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                }, error -> showMessage(error.getMessage()));
    }

    @Override
    public void fetchDriverLocation(String rideId, String driverId) {
        disposable = interactor.fetchDriverLocation(rideId, driverId)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (isViewAttached()) {
                        view.showDriverLocation(response.get(0).getDriverLocation());
                    }
                }, error -> {

                    showMessage(error.getMessage());
                    view.destroyJobSchedule();
                });
    }

}
package com.directparking.app.ui.parkingslots;


/**
 * Created by root on 24/7/18.
 */

public class ParkingSlotPresenterImpl extends ParkingSlotPresenter {

    private ParkingSlotView view;
    private ParkingSlotInteractor interactor;

    public ParkingSlotPresenterImpl(ParkingSlotInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ParkingSlotView view) {
        super.setView(view);
        this.view = view;
    }

}

package com.directparking.app.ui.profile.edit;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.profile.view.model.MyProfileRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenterImpl extends ProfilePresenter {

    private EditProfileView view;
    private ProfileInteractor interactor;

    public ProfilePresenterImpl(ProfileInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(EditProfileView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void updateProfile(UserData userData, CarData carData) {
        showProgressDialog();

        disposable = interactor.updateProfile(userData, carData)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    if (isViewAttached()) {
                        view.showHomeScreen(msg);
                    }
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }

    @Override
    public void getProfile() {
        showProgressDialog();

        disposable = interactor.getProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    if (isViewAttached()) {
                       view.myProfileData(msg);
                    }
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }


}
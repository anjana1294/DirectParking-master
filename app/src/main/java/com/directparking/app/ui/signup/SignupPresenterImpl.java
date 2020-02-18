package com.directparking.app.ui.signup;

import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignupPresenterImpl extends SignupPresenter {

    private SignupView view;
    private SignupInteractor interactor;

    public SignupPresenterImpl(SignupInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(SignupView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void fetchUniversities() {
        showProgressDialog();

        disposable = interactor.fetchUniversities()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(universities -> {
                    hideProgressDialog();
                    if (isViewAttached()) {
                        view.showUniversities(universities);
                    }
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }

    @Override
    public void callSignup(UserData userData, CarData carData) {
        showProgressBar();

        disposable = interactor.signup(userData, carData)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    showMessage(msg);
                    if (isViewAttached()) {
                        view.showLoginScreen(msg);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

//    @Override
//    void suggestionUniversities(String name) {
////        showProgressDialog();
//
//        disposable = interactor.suggestionUniversities(name)
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(universities -> {
//                    hideProgressDialog();
//                    if (isViewAttached()) {
//                        view.setUniversitiesAdapter(universities);
//                    }
//                }, error -> {
//                    hideProgressDialog();
//                    showMessage(error.getMessage());
//                });
//    }
}
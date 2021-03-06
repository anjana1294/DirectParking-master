package com.directparking.app.ui.password.change;

import com.directparking.app.ui.logout.LogoutInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenterImpl extends ChangePasswordPresenter {

    private ChangePasswordView view;
    private ChangePasswordInteractor passwordInteractor;
    private LogoutInteractor logoutInteractor;

    public ChangePasswordPresenterImpl(ChangePasswordInteractor passwordInteractor, LogoutInteractor logoutInteractor) {
        this.passwordInteractor = passwordInteractor;
        this.logoutInteractor = logoutInteractor;
    }

    @Override
    public void setView(ChangePasswordView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void changePassword(String oldPassword, String newPassword) {
        showProgressDialog();

        disposable = passwordInteractor.changePassword(oldPassword, newPassword)
                .flatMap(msg -> logoutInteractor.logout())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    showMessage(msg);
                    if (isViewAttached()) {
                        view.showLoginScreen();
                    }
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }
}
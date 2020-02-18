package com.directparking.app.ui.password.change;

import com.directparking.app.ui.base.BasePresenter;

public abstract class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {

    abstract void changePassword(String oldPassword, String newPassword);

}
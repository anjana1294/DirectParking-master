package com.directparking.app.ui.password.forgot;

import com.directparking.app.ui.base.BasePresenter;

public abstract class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {

    abstract void callForgotPassword(String username);

}
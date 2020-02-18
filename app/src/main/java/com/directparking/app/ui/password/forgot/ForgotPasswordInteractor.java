package com.directparking.app.ui.password.forgot;

import io.reactivex.Single;

public interface ForgotPasswordInteractor {

    Single<String> forgotPassword(String username);

}
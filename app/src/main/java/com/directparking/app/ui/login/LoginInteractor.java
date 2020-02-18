package com.directparking.app.ui.login;

import io.reactivex.Single;

public interface LoginInteractor {

    Single<String> login(String username, String password);

}
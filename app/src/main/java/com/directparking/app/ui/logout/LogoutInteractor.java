package com.directparking.app.ui.logout;

import io.reactivex.Single;

public interface LogoutInteractor {

    Single<String> logout();

}
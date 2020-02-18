package com.directparking.app.ui.signup;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.signup.model.IdNameItem;

import java.util.List;

interface SignupView extends BaseView {

    void showUniversities(List<IdNameItem> universities);

    void setUniversitiesAdapter(List<IdNameItem> universities);


    void showLoginScreen(String msg);

}
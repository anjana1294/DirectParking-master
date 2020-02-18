package com.directparking.app.ui.profile.edit;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;

public interface EditProfileView extends BaseView {

    void showHomeScreen(String msg);
    void myProfileData(MyProfileResponse myProfileResponse);
}
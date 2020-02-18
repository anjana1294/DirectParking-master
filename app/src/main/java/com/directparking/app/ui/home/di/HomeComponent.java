package com.directparking.app.ui.home.di;

import com.directparking.app.location.LocationModule;
import com.directparking.app.ui.accept.AcceptFragment;
import com.directparking.app.ui.accept.di.AcceptModule;
import com.directparking.app.ui.base.HomeBase;
import com.directparking.app.ui.history.HistoryFragment;
import com.directparking.app.ui.history.di.HistoryModule;
import com.directparking.app.ui.home.HomeFragment;
import com.directparking.app.ui.logout.LogoutDialog;
import com.directparking.app.ui.logout.di.LogoutModule;
import com.directparking.app.ui.notification.NotificationFragment;
import com.directparking.app.ui.notification.di.NotificationModule;
import com.directparking.app.ui.parkingslots.ParkingSlotFragment;
import com.directparking.app.ui.parkingslots.di.ParkingSlotModule;
import com.directparking.app.ui.password.change.ChangePasswordFragment;
import com.directparking.app.ui.profile.di.ProfileModule;
import com.directparking.app.ui.profile.edit.EditProfileFragment;
import com.directparking.app.ui.profile.view.MyProfileFragment;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = {HomeModule.class, ProfileModule.class, LocationModule.class, ParkingSlotModule.class, NotificationModule.class, AcceptModule.class, HistoryModule.class, LogoutModule.class})
public interface HomeComponent {

    void inject(HomeBase activity);

    void inject(HomeFragment fragment);

    void inject(MyProfileFragment fragment);

    void inject(EditProfileFragment fragment);

    void inject(ChangePasswordFragment fragment);

    void inject(NotificationFragment fragment);

    void inject(AcceptFragment fragment);

    void inject(HistoryFragment fragment);

    void inject(ParkingSlotFragment fragment);

    void inject(LogoutDialog dialog);
}
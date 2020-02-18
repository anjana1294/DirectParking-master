package com.directparking.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.ui.aboutus.AboutUsFragment;
import com.directparking.app.ui.accept.AcceptFragment;
import com.directparking.app.ui.history.HistoryFragment;
import com.directparking.app.ui.home.HomeFragment;
import com.directparking.app.ui.login.model.LocationConfig;
import com.directparking.app.ui.notification.NotificationFragment;
import com.directparking.app.ui.parkingslots.ParkingSlotFragment;
import com.directparking.app.ui.password.change.ChangePasswordFragment;
import com.directparking.app.ui.profile.edit.EditProfileFragment;
import com.directparking.app.ui.profile.view.MyProfileFragment;
import com.directparking.app.ui.settings.SettingsFragment;
import com.google.gson.Gson;

import javax.inject.Inject;

import timber.log.Timber;

import static com.directparking.app.util.Constants.LOCATION_UPDATE_INTERVAL;

public class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Inject
    protected UserManager userManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                if (this instanceof HomeFragment) {
                    toolbar.setTitle(getString(R.string.title_request_ride));
                } else if (this instanceof MyProfileFragment) {
                    toolbar.setTitle(getString(R.string.title_my_profile));
                } else if (this instanceof EditProfileFragment) {
                    toolbar.setTitle(getString(R.string.title_edit_profile));
                } else if (this instanceof NotificationFragment) {
                    toolbar.setTitle(getString(R.string.nav_menu_notifications));
                } else if (this instanceof AcceptFragment) {
                    toolbar.setTitle(getString(R.string.nav_menu_accept));
                } else if (this instanceof ParkingSlotFragment) {
                    toolbar.setTitle(getString(R.string.nav_menu_parking_lots));
                } else if (this instanceof HistoryFragment) {
                    toolbar.setTitle(getString(R.string.title_history));
                } else if (this instanceof AboutUsFragment) {
                    toolbar.setTitle(getString(R.string.title_about_us));
                } else if (this instanceof ChangePasswordFragment) {
                    toolbar.setTitle(getString(R.string.title_password_change));
                } else if (this instanceof SettingsFragment) {
                    toolbar.setTitle(getString(R.string.title_settings));
                }
            }
          }
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.processing_msg));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
    }

    protected void fragmentTransition(Fragment fragment, String tag) {
        if (!fragment.isVisible() && getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
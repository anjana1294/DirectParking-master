package com.directparking.app.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;

import com.directparking.app.BaseApplication;
import com.directparking.app.data.prefs.StringPreference;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.location.LocationPresenter;
import com.directparking.app.ui.login.LoginActivity;
import com.directparking.app.ui.login.model.LocationConfig;
import com.directparking.app.ui.logout.LogoutDialog;
import com.directparking.app.util.AlertUtil;
import com.google.gson.Gson;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;

import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

import static com.directparking.app.util.Constants.BACK_PRESS_INTERVAL;
import static com.directparking.app.util.Constants.LOCATION_UPDATE_INTERVAL;

public abstract class HomeBase extends BaseActivity implements LogoutDialog.Callback {

    protected long interval = 0;
    protected long frequency = 0;

    @Inject
    protected Picasso picasso;

    @Inject
    protected UserManager userManager;

    @Inject
    protected LocationPresenter presenter;

    @Inject
    @Named("baseUrl")
    protected StringPreference apiEndpoint;

    @Inject
    protected ReactiveLocationProvider locationProvider;

    private Handler backPressHandler = new Handler();
    private boolean doubleBackToExitPressedOnce = false;
    private final Runnable backPressRunnable = () -> doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).getHomeComponent().inject(this);

        String jsonData = userManager.getLocationConfig();
        LocationConfig config = new Gson().fromJson(jsonData, LocationConfig.class);
        if (config != null) {
            frequency = config.getFrequency();
            interval = config.getInterval();
            frequency = frequency > LOCATION_UPDATE_INTERVAL ? frequency : LOCATION_UPDATE_INTERVAL;
        } else {
            Timber.e("Location config not found!");
        }
    }

    protected void setEndpointAndRelaunch(String endpoint) {
        Timber.d("Setting network endpoint to %s", endpoint);
        apiEndpoint.set(endpoint);

        ProcessPhoenix.triggerRebirth(this);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
            return;
        } else {
            doubleBackToExitPressedOnce = true;
        }

        AlertUtil.showToast(getApplicationContext(), "Press back again to exit!", BACK_PRESS_INTERVAL);
        backPressHandler.postDelayed(backPressRunnable, BACK_PRESS_INTERVAL);
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (backPressHandler != null) {
            backPressHandler.removeCallbacks(backPressRunnable);
        }

        ((BaseApplication) getApplication()).releaseHomeComponent();
    }
}
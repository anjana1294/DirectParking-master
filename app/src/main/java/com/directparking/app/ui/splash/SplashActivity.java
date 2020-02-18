package com.directparking.app.ui.splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.fcm.FetchFcmTokenService;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.home.HomeActivity;
import com.directparking.app.ui.login.LoginActivity;
import com.directparking.app.util.RxUtils;
import com.directparking.app.util.Util;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.directparking.app.util.Constants.PLAY_SERVICES_RESOLUTION_REQUEST;
import static com.directparking.app.util.Constants.SPLASH_TIMEOUT_IN_SECONDS;

public class SplashActivity extends BaseActivity {

    private Disposable splashTimer;

    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT));
            } else {
                Timber.e("Notification Manager is null");
            }
        }

        fetchFcmToken();
        splashTimer = Single.timer(SPLASH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(success -> {
                    Intent intent;
                    if (userManager.isLoggedIn()) {
                        intent = new Intent(this, HomeActivity.class);
                    } else {
                        intent = new Intent(this, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                });
    }

    public void fetchFcmToken() {
        Timber.d("Fetching fcm token...!");
        if (Util.isGooglePlayServicesAvailable(this, PLAY_SERVICES_RESOLUTION_REQUEST)) {
            Intent intent = new Intent(this, FetchFcmTokenService.class);
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.dispose(splashTimer);
    }
}
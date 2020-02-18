package com.directparking.app.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.directparking.app.BaseApplication;
import com.directparking.app.data.user.UserManager;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import timber.log.Timber;

public class FetchFcmTokenService extends IntentService {

    private static final String TAG = "FetchFcmTokenService";

    @Inject
    UserManager userManager;

    public FetchFcmTokenService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((BaseApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        FirebaseInstanceId instanceId = FirebaseInstanceId.getInstance();
//        String senderId = getResources().getString(R.string.gcm_defaultSenderId);

        String token = instanceId.getToken();
        Timber.d("Fcm token: %s", token);

        if (!TextUtils.isEmpty(token)) {
            Timber.d("Storing fcm token...");
            userManager.setFcmToken(token);
        }
    }
}
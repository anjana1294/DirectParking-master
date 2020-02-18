package com.directparking.app.fcm;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class TokenRefreshListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, FetchFcmTokenService.class);
        startService(intent);
    }
}
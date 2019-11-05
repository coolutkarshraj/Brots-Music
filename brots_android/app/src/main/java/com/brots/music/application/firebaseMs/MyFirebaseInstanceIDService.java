package com.brots.music.application.firebaseMs;

import android.annotation.SuppressLint;
import android.util.Log;

import com.brots.music.application.localStorage.PreferenceManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService
{
PreferenceManager sharedPreferences;

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        sharedPreferences = new PreferenceManager(this); // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sharedPreferences.putString(PreferenceManager.REGID, refreshedToken);
        System.out.print(refreshedToken);
    }


}

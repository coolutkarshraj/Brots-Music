package com.brots.music.application.activity.splashScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.activity.HomeActivity.MainScreen;
import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.R;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.userOnlineInfo;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private String name, deviceToken, password, email1,userType,userId;
    private ConnectivityManager cm;
    private NetworkInfo info;
    Activity activity;
    private String endpoint;
    userOnlineInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        setContentView(R.layout.activity_splash_screen);
        init();
        startWorking();
    }

    private void startWorking() {
        getPackageHash();
        checkInternet();
        getSharedPreferanceData();
        goToMainScreen();
    }

    private void navigateScreen() {
        if(preferenceManager.getString(PreferenceManager.isFirstLaunch).equals("")){
            goToInTroSliderScreen();
        }else if (preferenceManager.getString(PreferenceManager.loginData).equals("")){
           gotoLoginScreen();
        }else {
            if(preferenceManager.getString(PreferenceManager.email).equals("")){
                gotoLoginScreen();
            }else {
                UserExistOrNot();
            }

        }


    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(activity, After_Splash.class);
        startActivity(intent);
        finish();
    }

    private void goToInTroSliderScreen() {
        Intent intent = new Intent(activity,MainScreen.class);
        startActivity(intent);
        finish();
    }

    private void goToMainScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateScreen();
            }
        }, 3000);

    }

    private void getSharedPreferanceData() {
        try {
            Gson gson = new Gson();
            String json = preferenceManager.getString(PreferenceManager.loginData);
            LoginResponseModel obj = gson.fromJson(json, LoginResponseModel.class);
            name = obj.getData().getName();
            userId = String.valueOf(obj.getData().getId());
            deviceToken = obj.getData().getDeviceToken();
            password = obj.getData().getPassword();
            email1 = obj.getData().getEmail();
            userType = obj.getData().getUserType();

            preferenceManager.putString(PreferenceManager.name, name);
            preferenceManager.putString(PreferenceManager.id, userId);
            preferenceManager.putString(PreferenceManager.name, name);
            preferenceManager.putString(PreferenceManager.email, email1);
            preferenceManager.putString(PreferenceManager.userType, userType);
            preferenceManager.putString(PreferenceManager.password, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkInternet() {
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            Toast.makeText(SplashActivity.this, "wifi", Toast.LENGTH_LONG).show();
            Log.e("test0", "  wifi");
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                Toast.makeText(SplashActivity.this, "mobile 100kbps", Toast.LENGTH_LONG).show();
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                Toast.makeText(SplashActivity.this, "mobile 50-100kbps", Toast.LENGTH_LONG).show();
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                Toast.makeText(SplashActivity.this, "mobile 400-1000kbps", Toast.LENGTH_LONG).show();
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                Toast.makeText(SplashActivity.this, "mobile 600-1400kbps", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void init() {
        activity = SplashActivity.this;
        preferenceManager = new PreferenceManager(activity);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cm.getActiveNetworkInfo();
        user = new userOnlineInfo();
    }

    private void getPackageHash() {
        try {

            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.brots.music.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }





    }
    private void UserExistOrNot(){
        if (user.isOnline(activity)) {
            setEndPoint();
            Apicaller.isUserExistOrNot(activity, endpoint,
                    new FutureCallback<IsUserExistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, IsUserExistResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            System.out.print(result);
                            System.out.print(result.getData());
                            NavigateScreenAcToUserExistOrNot(result);


                        }
                    }
            );
        }
    }

    private void NavigateScreenAcToUserExistOrNot(IsUserExistResponseModel result) {
       if(result.getData().get(0).getIsLoggedIn().equals("0")){
           moveScrreenAccordingToUserType();
       }else{
           gotoLoginScreen();
       }

    }

    private void moveScrreenAccordingToUserType() {
        if (userType.equals("1")) {
            Intent intent = new Intent(activity, MainActivityTabs.class);
            startActivity(intent);
            finish();
        } else if (userType.equals("2")) {
            Intent intent = new Intent(activity, AfterArtistLogin.class);
            startActivity(intent);
            finish();
        }
    }

    private void setEndPoint() {
        if (userType.equals("1")) {
            endpoint = Config.Url.isUserExist;
        } else if (userType.equals("2")) {
            endpoint = Config.Url.isArtistExist;
        }
    }

}

package com.brots.music.application.activity.HomeActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.brots.music.application.fragment.ExploreFragment;
import com.brots.music.application.fragment.Notification;
import com.brots.music.application.fragment.ProfileFragment;
import com.brots.music.application.fragment.LibraryFragment;



import com.brots.music.application.R;
import com.brots.music.application.util.Font;


public class MainActivityTabs extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ExploreFragment exploreFragment;
    LibraryFragment libraryFragment;
    Notification messageFragment;
    ProfileFragment profileFragment;
    Dialog backPresseddialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        init();
    }

    private void init() {
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        exploreFragment = new ExploreFragment();
        libraryFragment = new LibraryFragment();
        messageFragment = new Notification();
        profileFragment = new ProfileFragment();
        setFragment(exploreFragment);
        bottomNavigationViewSetup();

    }

    private void bottomNavigationViewSetup() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(exploreFragment);
                        return true;
                    case R.id.navigation_dashboard:
                        setFragment(libraryFragment);
                        return true;
                    case R.id.navigation_notifications:
                        setFragment(profileFragment);
                        return true;
                    case R.id.navigation_message:
                        setFragment(messageFragment);

                        return true;

                    default:
                        return false;
                }

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {
        Backpress();
    }

    public void Backpress() {
        backPresseddialog = new Dialog(MainActivityTabs.this);
        backPresseddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        backPresseddialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        backPresseddialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        backPresseddialog.setContentView(R.layout.reallyexit);
        backPresseddialog.setTitle("");
        final TextView Yes = (TextView) backPresseddialog.findViewById(R.id.yes);
        final TextView No = (TextView) backPresseddialog.findViewById(R.id.no);
        final ImageView Clear = (ImageView) backPresseddialog.findViewById(R.id.clear);
        final TextView Logout = (TextView) backPresseddialog.findViewById(R.id.textlogout);
        final TextView textView = (TextView) backPresseddialog.findViewById(R.id.txt_name);
        Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), "Proxima_Nova_Thin.otf");
        Yes.setTypeface(face);
        No.setTypeface(face);
        Logout.setTypeface(face);
        textView.setTypeface(face);

        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                backPresseddialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityTabs.super.onBackPressed();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                backPresseddialog.dismiss();
            }
        });
        backPresseddialog.show();

    }
}

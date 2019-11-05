package com.brots.music.application.activity.AfterLogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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

import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.fragment.ExploreArtist;
import com.brots.music.application.fragment.Notification;
import com.brots.music.application.fragment.ProfileArtist;
import com.brots.music.application.R;
import com.brots.music.application.util.Font;

public class AfterArtistLogin extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ExploreArtist exploreArtist;
    ProfileArtist profileArtist;
    Notification notification;
    Dialog dialogExit;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_artist_login);
        activity = AfterArtistLogin.this;
        Font font=new Font(getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        exploreArtist=new ExploreArtist();
        profileArtist=new ProfileArtist();
        notification=new Notification();
        setFragment(exploreArtist);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.navigation_home:
                        setFragment(exploreArtist);

                        return true;
                    case R.id.navigation_dashboard:
                        setFragment(notification);
                        return true;
                    case R.id.navigation_notifications:
                        setFragment(profileArtist);
                        return true;

                    default:return false;
                }

            }
        });
    }

    private  void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        openExistDialog();
        super.onBackPressed();

    }

    private void openExistDialog() {
        dialogExit = new Dialog(activity);
        dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialogExit.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogExit.setContentView(R.layout.reallyexit);
        dialogExit.setTitle("");
        final TextView Yes = (TextView) dialogExit.findViewById(R.id.yes);
        final TextView No = (TextView) dialogExit.findViewById(R.id.no);
        final ImageView Clear = (ImageView) dialogExit.findViewById(R.id.clear);
        final TextView Logout=(TextView)dialogExit.findViewById(R.id.textlogout);
        final TextView textView=(TextView)dialogExit.findViewById(R.id.txt_name) ;
        Typeface face=Typeface.createFromAsset(getApplicationContext().getAssets(),"Proxima_Nova_Thin.otf");
        Yes.setTypeface(face);
        No.setTypeface(face);
        Logout.setTypeface(face);
        textView.setTypeface(face);

        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                dialogExit.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterArtistLogin.super.onBackPressed();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialogExit.dismiss();
            }
        });
        dialogExit.show();
    }

}

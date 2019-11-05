package com.brots.music.application.activity.AfterSplash;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brots.music.application.activity.afterrSelectUser.After_Select_User;
import com.brots.music.application.R;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.util.Font;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class After_Splash extends AppCompatActivity {
    @BindView(R.id.user)
    Button user;
    @BindView(R.id.artist)
    Button artist;
    Dialog onBackExistDialog;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__splash);
        ButterKnife.bind(this);
        Font font=new Font(getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        preferenceManager = new PreferenceManager(this);

    }

    @OnClick({R.id.user, R.id.artist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user:
                openUserLogin();
                break;
            case R.id.artist:
                 openArtistLogin();
                break;
        }
    }

    private void openArtistLogin() {
        artist.setBackgroundColor(Color.parseColor("#02a9f4"));
        artist.setBackground(getDrawable(R.drawable.button_shape_green));
        Intent intent1=new Intent(After_Splash.this,After_Select_User.class);
        intent1.putExtra("usertype","2");
        preferenceManager.putString(PreferenceManager.userType,"2");
        startActivity(intent1);
        finish();
    }

    private void openUserLogin() {
        user.setBackground(getDrawable(R.drawable.button_shape_green));
        Intent intent=new Intent(After_Splash.this, After_Select_User.class);
        intent.putExtra("usertype","1");
        preferenceManager.putString(PreferenceManager.userType,"1");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
     Backpress();
    }

    public void Backpress()
    {
       openExistDialog();

    }

    private void openExistDialog() {
        onBackExistDialog = new Dialog(After_Splash.this);
        onBackExistDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        onBackExistDialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        onBackExistDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        onBackExistDialog.setContentView(R.layout.reallyexit);
        onBackExistDialog.setTitle("");
        final TextView Yes = (TextView) onBackExistDialog.findViewById(R.id.yes);
        final TextView No = (TextView) onBackExistDialog.findViewById(R.id.no);
        final ImageView Clear = (ImageView) onBackExistDialog.findViewById(R.id.clear);
        final TextView Logout=(TextView)onBackExistDialog.findViewById(R.id.textlogout);
        final TextView textView=(TextView)onBackExistDialog.findViewById(R.id.txt_name) ;
        Typeface face=Typeface.createFromAsset(getApplicationContext().getAssets(),"Proxima_Nova_Thin.otf");
        Yes.setTypeface(face);
        No.setTypeface(face);
        Logout.setTypeface(face);
        textView.setTypeface(face);

        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                onBackExistDialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                After_Splash.super.onBackPressed();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                onBackExistDialog.dismiss();
            }
        });
        onBackExistDialog.show();
    }


}

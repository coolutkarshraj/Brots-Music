package com.brots.music.application.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.activity.AboutUs.AboutUs;
import com.brots.music.application.activity.Profile.ArtisteditProfileActivity;
import com.brots.music.application.activity.loginRegistraitionFlow.ChangePassword;
import com.brots.music.application.activity.termsAndCondition.ContactQuery;
import com.brots.music.application.model.response.ArtistGetProfileResponseModel;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.GetUserProfileResponseModel;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.activity.remberScreen.Remember_Screen;
import com.brots.music.application.activity.notifications.SendnotificationActivity;
import com.brots.music.application.activity.Profile.ListSongArtistProfileActivity;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileArtist extends Fragment {
    FloatingActionButton editprofile, upload, Notice, editsong, Follow, About;
    userOnlineInfo user;
    ImageView linearLayout;
    Dialog dialog2;
    NewProgressBar dialog;
    Activity activity;
    PreferenceManager preferenceManager;
    String name, id, deviceToken, password1, userType, email1,endpoint,imageurl,cityget,countryget,name1;
    CircleImageView Picture;
    TextView Name,City;
    String R_name,R_email,R_profilepic,R_usertyper;


    public ProfileArtist() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.artistprofilenew, container, false);
        init(v);
        bindListner();
        return v;
    }

    private void init(View v) {
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        user = new userOnlineInfo();
        localStorage();
        activity=getActivity();
        profileViewArtist();
        Name=(TextView)v.findViewById(R.id.name);
        City=(TextView)v.findViewById(R.id.city);
        Picture=(CircleImageView)v.findViewById(R.id.img);
        editprofile = (FloatingActionButton) v.findViewById(R.id.editprofile);
        upload = (FloatingActionButton) v.findViewById(R.id.f1);
        Notice = (FloatingActionButton) v.findViewById(R.id.f2);
        editsong = (FloatingActionButton) v.findViewById(R.id.f11);
        Follow = (FloatingActionButton) v.findViewById(R.id.follo);
        linearLayout = (ImageView) v.findViewById(R.id.logout);
        About = (FloatingActionButton) v.findViewById(R.id.about);
    }
    private void localStorage()
    {
        preferenceManager = new PreferenceManager(getActivity());
        name = preferenceManager.getString(PreferenceManager.name);
        id = preferenceManager.getString(PreferenceManager.id);
        deviceToken = preferenceManager.getString(PreferenceManager.deviceToken);
        password1 = preferenceManager.getString(PreferenceManager.password);
        userType = preferenceManager.getString(PreferenceManager.userType);
        email1 = preferenceManager.getString(PreferenceManager.email);
    }

    private void bindListner() {
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUs.class));
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ArtisteditProfileActivity.class));
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListSongArtistProfileActivity.class));
            }
        });
        Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SendnotificationActivity.class));
            }
        });
        editsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });
        Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(activity, ContactQuery.class));
            }
});
    }

    private void profileViewArtist() {          // Artist profile get

        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            urlapi();
            Apicaller.artistGetProfile(activity, endpoint, id, userType, new
                    FutureCallback<ArtistGetProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistGetProfileResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            profileData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(getActivity());
        }
    }

    private void profileData(ArtistGetProfileResponseModel result) {
        String API_Status = result.getStatus();
        if (API_Status.equals("true")) {
            dialog.dismiss();
            imageurl=  result.getData().getImageUrl();
            cityget=  result.getData().getCity();
            countryget= result.getData().getCountry();
            name1=result.getData().getFirstName() +" "+result.getData().getLastName();
            Glide.with(getActivity()).load(imageurl).into(Picture);
            City.setText(cityget);
            Name.setText(name1);
        } else {
            Toast.makeText(activity, "server error", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void urlapi()
    {
        endpoint = Config.Url.artistGetProfile;

    }


    public void Logout() {
        dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog2.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.logoutdialog);
        dialog2.setTitle("");
        Font font = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) getActivity().findViewById(android.R.id.content));

        final TextView Heading = (TextView) dialog2.findViewById(R.id.textlogout);
        final TextView sub = (TextView) dialog2.findViewById(R.id.txt_name);
        final TextView Yes = (TextView) dialog2.findViewById(R.id.yes);
        final TextView No = (TextView) dialog2.findViewById(R.id.no);
        final ImageView Clear = (ImageView) dialog2.findViewById(R.id.clear);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        Heading.setTypeface(typeface);
        sub.setTypeface(typeface);
        Yes.setTypeface(typeface);
        No.setTypeface(typeface);

        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = preferenceManager.getString(PreferenceManager.loginData);
                LoginResponseModel obj = gson.fromJson(json, LoginResponseModel.class);
                R_name = obj.getData().getName();
                R_email = obj.getData().getEmail();
                R_profilepic = obj.getData().getImageUrl();
                R_usertyper = obj.getData().getUserType();

                preferenceManager.putString(PreferenceManager.id,"");
                preferenceManager.putString(PreferenceManager.savedSongMemory,"");
                preferenceManager.putString(PreferenceManager.InstaMixMemory,"");
                preferenceManager.putString(PreferenceManager.deviceToken,"");
                preferenceManager.putString(PreferenceManager.password,"");
                preferenceManager.putString(PreferenceManager.userType,"");
                preferenceManager.putString(PreferenceManager.ISLOGGEDIN,"");
                preferenceManager.putString(PreferenceManager.loginData,"");

                Intent logout_intent1 = new Intent(getActivity(), Remember_Screen.class);
                logout_intent1.putExtra("name",R_name);
                logout_intent1.putExtra("email",R_email);
                logout_intent1.putExtra("pic",R_profilepic);
                logout_intent1.putExtra("usertype",R_usertyper);
                logout_intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(logout_intent1);
                ((Activity) getActivity()).finish();

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        dialog2.show();

    }
}

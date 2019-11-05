package com.brots.music.application.activity.AboutUs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ArtistGetProfileResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUs extends AppCompatActivity {

   CircleImageView Image;
   Activity activity;
   String id,userType,endpoint;
   userOnlineInfo user;
   NewProgressBar dialog;
   PreferenceManager preferenceManager;
   TextView Name,Email,Phone,DOB,City,Country,Gender,Follow,Total_Like;
   ImageButton txt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        intializeViews();
        bindListner();
        localStorage();
        profileViewArtist();
    }

    private void bindListner() {
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intializeViews()
    {
         activity=AboutUs.this;
         user = new userOnlineInfo();
         Image = (CircleImageView)findViewById(R.id.img);
         Name = (TextView)findViewById(R.id.name);
         Email = (TextView)findViewById(R.id.email);
         Phone = (TextView)findViewById(R.id.phone);
         DOB = (TextView)findViewById(R.id.dob);
         City = (TextView)findViewById(R.id.city);
         Country = (TextView)findViewById(R.id.country);
         Gender = (TextView)findViewById(R.id.gender);
         Follow = (TextView)findViewById(R.id.follow);
         Total_Like = (TextView)findViewById(R.id.totallike);
        txt_back = (ImageButton) findViewById(R.id.txt_back);

    }

    private void localStorage()
    {
        preferenceManager = new PreferenceManager(activity);
        id = preferenceManager.getString(PreferenceManager.id);
        userType = preferenceManager.getString(PreferenceManager.userType);

    }

    private void urlapi()
    {
        endpoint = Config.Url.artistGetProfile;

    }
    private void profileViewArtist() {          // Artist profile get

        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
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
            comdialog.dialogbox(activity);
        }
    }

    private void profileData(ArtistGetProfileResponseModel result) {

        String API_Status = result.getStatus();
        if(API_Status.equals("true"))
        {
            dialog.dismiss();
            Name.setText(result.getData().getName());
            Email.setText(result.getData().getEmail());
            Phone.setText(result.getData().getPhone());
            DOB.setText(result.getData().getDOB());
            City.setText(result.getData().getCity());
            Country.setText(result.getData().getCountry());
            Gender.setText(result.getData().getGender());
            Follow.setText(result.getData().getFollowerCount());
            Total_Like.setText(result.getData().getTotalLike());
            Glide.with(activity).load(result.getData().getImageUrl()).into(Image);
        }else
            {
                Toast.makeText(activity, ""+result.getMessage(), Toast.LENGTH_SHORT).show();

            }
    }


}

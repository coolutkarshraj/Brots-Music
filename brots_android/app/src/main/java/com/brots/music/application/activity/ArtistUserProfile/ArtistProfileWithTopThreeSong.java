package com.brots.music.application.activity.ArtistUserProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.R;
import com.brots.music.application.adapter.Top3SongAdapter;
import com.brots.music.application.model.response.AtristProfileWithTop3SongsResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistProfileWithTopThreeSong extends AppCompatActivity {
    RecyclerView recyclerView;
    Top3SongAdapter adapter;
    userOnlineInfo user;
    NewProgressBar dialog;
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.city)
    TextView city;
    String artistId;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile_with_top_three_song);
        initView();
        bindListner();
        statrtWorking();
    }

    private void bindListner() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void statrtWorking() {
        getIntentData();
        getArtistProfile();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        artistId = intent.getStringExtra("artistId");
    }

    private void initView() {
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        user = new userOnlineInfo();
        img = findViewById(R.id.img);
        iv_back =  findViewById(R.id.iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        ButterKnife.bind(this);

    }

    public void getArtistProfile() {
        if (user.isOnline(ArtistProfileWithTopThreeSong.this)) {
            Apicaller.getTop3SongWitArtistProfile(this, Config.Url.artistProfileWithTop3Song,artistId,
                    new FutureCallback<AtristProfileWithTop3SongsResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AtristProfileWithTop3SongsResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            if(result.getError().equals("false")){
                                return;
                            }
                            result.getProfile();
                            System.out.print(result);
                            setDataToView(result);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ArtistProfileWithTopThreeSong.this));
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setHasFixedSize(false);
                            adapter = new Top3SongAdapter(ArtistProfileWithTopThreeSong.this, result.getSong());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    }
            );
        }
    }

    private void setDataToView(AtristProfileWithTop3SongsResponseModel result) {
        Picasso.get().load(result.getProfile().getImageUrl()).into(img);
        city.setText(result.getProfile().getCity() + " "+ result.getProfile().getCountry());
     //   about.setText(String.valueOf(result.getProfile().getAbout()));
        name.setText(result.getProfile().getName());
    }

}





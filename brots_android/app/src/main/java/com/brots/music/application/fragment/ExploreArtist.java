package com.brots.music.application.fragment;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.Profile.ListSongArtistProfileActivity;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ArtistGetProfileResponseModel;
import com.brots.music.application.model.response.ArtistGetSongResponseModel;
import com.brots.music.application.model.responseData.ArtistGetSongDataModel;
import com.brots.music.application.util.Utilities;
import com.bumptech.glide.Glide;
import com.brots.music.application.adapter.SongListAdapter;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreArtist extends Fragment implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    RecyclerView recyclerView;
    public static SongListAdapter adapter;
    List<ArtistGetSongDataModel> list=new ArrayList<>();
    SearchView searchVieww;
    userOnlineInfo user;
    NewProgressBar dialog;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.city)
    TextView city;
    Unbinder unbinder;
    String cityget,countryget,imageurl,msg;
    Activity activity;
    String name1,userType,id,deviceToken,password1,email1,endpoint,endpointartistsong;
    PreferenceManager sharedPreferences;
    ArtistGetSongResponseModel data;
    public static String songUrl ="";
    public static int Clickedpostion;
    static SeekBar seekBar;
    public static TextView currentTime,totalTime,songName,artistName;
    public static  ImageView playMusic,pauseMusic,nextPlay;
    public static CircleImageView playerImage;
    public static MediaPlayer mediaPlayer;
    static int mediaFileLengthInMilliseconds;
    static Handler handler = new Handler();
    static Utilities utils = new Utilities();

    public ExploreArtist() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exploreartist, container, false);
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        unbinder = ButterKnife.bind(this, v);
        initView(v);
        localStorage();
        ProfileViewUser();
        artistsong();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void initView(View v) {
        activity=getActivity();
        Config.musiPlayerScreenType="ExploreArtist";
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        searchVieww = (SearchView) v.findViewById(R.id.serach);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

         /*Text views */
        currentTime = (TextView)v.findViewById(R.id.currenttime);
        totalTime = (TextView)v.findViewById(R.id.totaltime);
        songName = (TextView)v.findViewById(R.id.titleHead);
        artistName = (TextView)v.findViewById(R.id.titlesub);

        /*Image views */
        playerImage = (CircleImageView)v.findViewById(R.id.imageshow);
        playMusic = (ImageView)v.findViewById(R.id.playBtnnn);
        pauseMusic = (ImageView)v.findViewById(R.id.pauseBtnnn);
        nextPlay = (ImageView)v.findViewById(R.id.nextplay);

        /* Seek bar*/
        seekBar = (SeekBar)v.findViewById(R.id.seekBar1);
        seekBar.setMax(99);
        seekBar.setOnTouchListener(this);
        playMusic.setOnClickListener(this);
        pauseMusic.setOnClickListener(this);
        nextPlay.setOnClickListener(this);
        checkforMusicPlayerIsPlayingOrNot();


    }
    private void checkforMusicPlayerIsPlayingOrNot() {
        if(mediaPlayer == null){
            initializeMusicPlayer();
            playMusic.setVisibility(View.VISIBLE);
            pauseMusic.setVisibility(View.GONE);
        }else{
            if(mediaPlayer.isPlaying()){
                playMusic.setVisibility(View.GONE);
                pauseMusic.setVisibility(View.VISIBLE);
            }else {
                initializeMusicPlayer();
                playMusic.setVisibility(View.VISIBLE);
                pauseMusic.setVisibility(View.GONE);
            }
        }
    }
    private void initializeMusicPlayer() {
        seekBar.setMax(99);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

    }
    private void localStorage() {
        sharedPreferences = new PreferenceManager(getActivity());
        name1 = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
    }

    /*--------------------------------------------- Artist profile get---------------------------------------*/
    private void ProfileViewUser() {          //
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            dialog = new NewProgressBar(getActivity());
            dialog.show();
            urlapi();
            Log.e("idinter",id);
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
            Glide.with(getActivity()).load(imageurl).into(img);
            city.setText(cityget);
            name.setText(result.getData().getFirstName() + " " +result.getData().getLastName());
        } else {
            dialog.dismiss();
            Toast.makeText(activity, "server error", Toast.LENGTH_SHORT).show();
        }
    }

    private void urlapi()
    {
        endpoint = Config.Url.artistGetProfile;
        endpointartistsong = Config.Url.getSingleArtistSongs;
    }

    /*---------------------------------single artist all song---------------------------------------- */

    private void artistsong() {
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            urlapi();
            Apicaller.getSingleArtistAllSongs(activity, endpointartistsong, id, "", new
                    FutureCallback<ArtistGetSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistGetSongResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            if(result.getData().size() == 0){
                                return;
                            }
                            singleArtistSongs(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(getActivity());
        }
    }

    private void singleArtistSongs(ArtistGetSongResponseModel result) {
        data = result;
        list.clear();
        String API_Status=result.getStatus();
         if(API_Status.equals("true"))
        {
            songUrl=result.getData().get(Clickedpostion).getFullSongUrl();
            songName.setText(result.getData().get(Clickedpostion).getSongName());
            artistName.setText(result.getData().get(Clickedpostion).getArtistName());
            Glide.with(activity).load(result.getData().get(Clickedpostion).getCoverImage()).into(playerImage);
            for (int i=0 ;i < result.getData().size() ;i++)
            {
                ArtistGetSongDataModel dataModel=new ArtistGetSongDataModel();
                dataModel.setSongName(result.getData().get(i).getSongName());
                dataModel.setArtistName(result.getData().get(i).getArtistName());
                dataModel.setFullSongUrl(result.getData().get(i).getFullSongUrl());
                dataModel.setLike(result.getData().get(i).getLike());
                dataModel.setDisLike(result.getData().get(i).getDisLike());
                dataModel.setCoverImage(result.getData().get(i).getCoverImage());
                dataModel.setImageUrl(result.getData().get(i).getImageUrl());
                dataModel.setId(result.getData().get(i).getId());
                dataModel.setArtistId(String.valueOf(result.getData().get(i).getArtistId()));
                list.add(dataModel);
            }
            adapter = new SongListAdapter(getContext(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

/* ----------------------------------------------------Click Views ---------------------------------------------*/
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playBtnnn){
            if(songUrl.equals("")){
                Toast.makeText(activity, "Empty songs", Toast.LENGTH_SHORT).show();
            }else {
                playSong();
            }
        }
        if(v.getId() == R.id.pauseBtnnn){
            if(songUrl.equals("")){
                Toast.makeText(activity, "Empty songs", Toast.LENGTH_SHORT).show();
            }
            else {
                pauseMusic.setVisibility(View.GONE);
                playMusic.setVisibility(View.VISIBLE);
                playSong();
            }
        }
        if(v.getId() == R.id.nextplay){
            if(songUrl.equals("")){
                Toast.makeText(activity, "Empty songs", Toast.LENGTH_SHORT).show();
            }else {
                nextPlaySong();
            }
        }
    }

    /* -------------------------------------------Song play ----------------------------------------------------------- */
    private void playSong() {
        if(ListSongArtistProfileActivity.mediaPlayer == null){
           PlayMusic();

        }else {
            if(ListSongArtistProfileActivity.mediaPlayer.isPlaying()){
                ListSongArtistProfileActivity.mediaPlayer.reset();
                ListSongArtistProfileActivity.mediaPlayer.stop();
                PlayMusic();
            }else {
                PlayMusic();
            }
        }
       }

    private void PlayMusic() {
        try {
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Glide.with(activity).load(R.drawable.pause).into(playMusic);
        } else {
            mediaPlayer.pause();
            Glide.with(activity).load(R.drawable.playnew).into(playMusic);
        }

        primarySeekBarProgressUpdater();
    }

    /*--------------------------------------------  Skip Song-----------------------------------------------*/
    private void nextPlaySong() {
        mediaPlayer.reset();
        Clickedpostion = ((Clickedpostion + 1) % data.getData().size());
        playMusic.setVisibility(View.GONE);
        pauseMusic.setVisibility(View.VISIBLE);
        songName.setText(data.getData().get(Clickedpostion).getSongName());
        artistName.setText(data.getData().get(Clickedpostion).getArtistName());
        songUrl = data.getData().get(Clickedpostion).getFullSongUrl();
        Glide.with(activity).load(data.getData().get(Clickedpostion).getCoverImage()).into(playerImage);
        playSong();

    }

    /* --------------------------------------------Recycler view click player start-----------------------------------*/
    public static void playSongByRecyclerViewClick(String Songurl) {
        if(ListSongArtistProfileActivity.mediaPlayer == null){

        }else{
            if(ListSongArtistProfileActivity.mediaPlayer.isPlaying()){
                ListSongArtistProfileActivity.mediaPlayer.reset();
                ListSongArtistProfileActivity.mediaPlayer.stop();
            }
        }

        try {
            mediaPlayer.setDataSource(Songurl);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playMusic.setVisibility(View.GONE);
            pauseMusic.setVisibility(View.VISIBLE);
            Config.recyclerPoisitioninArtistListClicked = "";

        } else {
            mediaPlayer.pause();
            playMusic.setVisibility(View.VISIBLE);
            pauseMusic.setVisibility(View.GONE);
            Config.recyclerPoisitioninArtistListClicked = "";
        }
        adapter.notifyDataSetChanged();

        primarySeekBarProgressUpdater();
    }

    private static void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                    long totalDuration = mediaPlayer.getDuration();
                    long currentDuration = mediaPlayer.getCurrentPosition();
                    totalTime.setText("" + utils.milliSecondsToTimer(totalDuration));
                    currentTime.setText("" + utils.milliSecondsToTimer(currentDuration));
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar1) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (mediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                Log.e("playp",""+utils.milliSecondsToTimer(playPositionInMillisecconds));
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (Config.musiPlayerScreenType.equals("ExploreArtist")) {
            mediaPlayer.reset();
            try {
                Clickedpostion = ((Clickedpostion + 1) % data.getData().size());
                String song = data.getData().get(Clickedpostion).getFullSongUrl();
                songName.setText(data.getData().get(Clickedpostion).getSongName());
                artistName.setText(data.getData().get(Clickedpostion).getArtistName());
                Glide.with(activity).load(data.getData().get(Clickedpostion).getCoverImage()).into(playerImage);
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                primarySeekBarProgressUpdater();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }

    }


}

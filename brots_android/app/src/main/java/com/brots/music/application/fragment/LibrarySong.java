package com.brots.music.application.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.LibrarySongsResponseModel;
import com.brots.music.application.model.responseData.LibrarySongDataModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.Utilities;
import com.brots.music.application.adapter.RecyclerNewAdapter;
import com.brots.music.application.R;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class LibrarySong extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    public static Context context;
    public static int IsPlay = 0;
    public static int Clickedpostion;
    RecyclerView recyclerView;
    public static RecyclerNewAdapter adapter;
    public static TextView songName, songArtistName;
    static SeekBar seekBar;
    public static List<LibrarySongDataModel> item = new ArrayList<>();
    PreferenceManager preferenceManager;
    String name, id, endPoint;
    userOnlineInfo user;
    public static CircleImageView Image;
    static TextView totalTime, currentTime;
    public static ImageView Play, Pause, Nextplaysong;
    public static String Songurl= "";
    public static MediaPlayer mediaPlayer;
    static int mediaFileLengthInMilliseconds;
    static Handler handler = new Handler();
    static Utilities utils = new Utilities();
    String Songid;


    public LibrarySong() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.librarysong, container, false);
        intializeviews(v);
        librarySongs();
        return v;
    }

    public void intializeviews(View v) {
        Config.musiPlayerScreenType = "LibrarySong";
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);

        user = new userOnlineInfo();   // intialize user is connect to Internet or not
       // intialize media player

        /* Shared prefrenced*/
        preferenceManager = new PreferenceManager(getActivity());
        name = preferenceManager.getString(PreferenceManager.name);
        id = preferenceManager.getString(PreferenceManager.id);

        /*Text Views*/
        songName = (TextView) v.findViewById(R.id.titleHead);
        songArtistName = (TextView) v.findViewById(R.id.titlesub);
        totalTime = (TextView) v.findViewById(R.id.totaltime);
        currentTime = (TextView) v.findViewById(R.id.currenttime);

        /*ImageView & CircleImage Views*/
        Image = (CircleImageView) v.findViewById(R.id.imageshow);
        Play = (ImageView) v.findViewById(R.id.playBtnnn);
        Pause = (ImageView) v.findViewById(R.id.playBtnnn1);
        Nextplaysong = (ImageView) v.findViewById(R.id.nextplay);

        /*Recycler View*/
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewdata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        seekBar = (SeekBar) v.findViewById(R.id.seekBar1);
        seekBar.setMax(99);
        seekBar.setOnSeekBarChangeListener(this);
        Play.setOnClickListener(this);
        Pause.setOnClickListener(this);
        Nextplaysong.setOnClickListener(this);
        checkforMusicPlayerIsPlayingOrNot();

    }

    private void checkforMusicPlayerIsPlayingOrNot() {
        if(mediaPlayer == null){
            initializeMusicPlayer();
            Play.setVisibility(View.VISIBLE);
            Pause.setVisibility(View.GONE);
        }else{
            if(mediaPlayer.isPlaying()){
                Play.setVisibility(View.GONE);
                Pause.setVisibility(View.VISIBLE);
            }else {
                initializeMusicPlayer();
                Play.setVisibility(View.VISIBLE);
                Pause.setVisibility(View.GONE);
            }
        }
    }

    private void initializeMusicPlayer() {

        seekBar.setMax(99);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

    }

    private void librarySongs() {

        if (user.isOnline(getActivity())) {
            setEndPoint();
            String userid = preferenceManager.getString(PreferenceManager.id);
            Apicaller.librarySavedSongs(getActivity(), endPoint, userid, new
                    FutureCallback<LibrarySongsResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LibrarySongsResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            sendData(result);
                            adapter = new RecyclerNewAdapter(getActivity(), result.getData());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(getActivity());
        }
    }

    private void setEndPoint() {
        endPoint = Config.Url.getSavedSongs;
    }

    private void sendData(LibrarySongsResponseModel result) {

        for (int i = 0; i < result.getData().size(); i++) {
            Songid = String.valueOf(result.getData().get(Clickedpostion).getId());
            Songurl = result.getData().get(Clickedpostion).getFullSongImageUrl();
            songName.setText(result.getData().get(Clickedpostion).getSongName());
            songArtistName.setText(result.getData().get(Clickedpostion).getArtistName());
            Glide.with(getActivity()).load(result.getData().get(Clickedpostion).getBannerImage()).into(Image);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.playBtnnn) {  // Play Song
            if(Songurl.equals("")){
                Toast.makeText(getActivity(), "No Song saved in Library", Toast.LENGTH_SHORT).show();
            }else {
                playSong();
            }

        }

        if (v.getId() == R.id.playBtnnn1) {     //Pause Song
            if(Songurl.equals("")){
                Toast.makeText(getActivity(), "No Song saved in Library", Toast.LENGTH_SHORT).show();
            }else {
                Pause.setVisibility(View.GONE);
                Play.setVisibility(View.VISIBLE);
                playSong();
            }

        }
        if (v.getId() == R.id.nextplay) {        // Play Next Song or Skip Song
            if(Songurl.equals("")){
                Toast.makeText(getActivity(), "No Song saved in Library", Toast.LENGTH_SHORT).show();
            }else {
                nextPlaySong();
            }


        }
    }

    public void playSong() {
        if(LibraryInstantMix.mediaPlayer.isPlaying()){
            LibraryInstantMix.mediaPlayer.reset();
            LibraryInstantMix.mediaPlayer.stop();
        }
        if(ExploreFragment.mediaPlayer.isPlaying()){
            ExploreFragment.mediaPlayer.reset();
            ExploreFragment.mediaPlayer.stop();
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

            Glide.with(getActivity()).load(R.drawable.pause).into(Play);
        } else {
            mediaPlayer.pause();

            Glide.with(getActivity()).load(R.drawable.playnew).into(Play);
        }

        primarySeekBarProgressUpdater();
    }

    private void nextPlaySong() {
        if(LibraryInstantMix.mediaPlayer.isPlaying()){
            LibraryInstantMix.mediaPlayer.reset();
            LibraryInstantMix.mediaPlayer.stop();
        }
        if(ExploreFragment.mediaPlayer.isPlaying()){
            ExploreFragment.mediaPlayer.reset();
            ExploreFragment.mediaPlayer.stop();
        }
        mediaPlayer.reset();
        Clickedpostion = ((Clickedpostion + 1) % RecyclerNewAdapter.listitem.size());
        LibrarySongDataModel productnew = RecyclerNewAdapter.listitem.get(Clickedpostion);
        Play.setVisibility(View.GONE);
        Pause.setVisibility(View.VISIBLE);
        songName.setText(productnew.getSongName());
        songArtistName.setText(productnew.getArtistName());
        Songurl = productnew.getFullSongImageUrl();
        RecyclerNewAdapter.currentSongPlayId = String.valueOf(RecyclerNewAdapter.listitem.get(Clickedpostion).getSongId());
        Glide.with(getActivity()).load(productnew.getBannerImage()).into(Image);
        playSong();

    }

    public static void playSongByRecyclerViewClick(String Songurl) {
       try{
           if(LibraryInstantMix.mediaPlayer.isPlaying()){
               LibraryInstantMix.mediaPlayer.reset();
               LibraryInstantMix.mediaPlayer.stop();
           }
           if(ExploreFragment.mediaPlayer.isPlaying()){
               ExploreFragment.mediaPlayer.reset();
               ExploreFragment.mediaPlayer.stop();
           }
       }catch (Exception e){
           e.printStackTrace();
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
            Play.setVisibility(View.GONE);
            Pause.setVisibility(View.VISIBLE);
        } else {
            mediaPlayer.pause();
            Play.setVisibility(View.VISIBLE);
            Pause.setVisibility(View.GONE);
        }

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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mediaPlayer.isPlaying()) {
            int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * seekBar.getProgress();
            mediaPlayer.seekTo(playPositionInMillisecconds);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (Config.musiPlayerScreenType.equals("LibrarySong")) {
            if (LibraryInstantMix.mediaPlayer.isPlaying()) {
                LibraryInstantMix.mediaPlayer.reset();
                LibraryInstantMix.mediaPlayer.stop();
            }

            mediaPlayer.reset();
            try {
                Clickedpostion = ((Clickedpostion + 1) % RecyclerNewAdapter.listitem.size());
                String song = RecyclerNewAdapter.listitem.get(Clickedpostion).getFullSongImageUrl();
                songName.setText(RecyclerNewAdapter.listitem.get(Clickedpostion).getSongName());
                songArtistName.setText(RecyclerNewAdapter.listitem.get(Clickedpostion).getArtistName());
                RecyclerNewAdapter.currentSongPlayId = String.valueOf(RecyclerNewAdapter.listitem.get(Clickedpostion).getSongId());
                Glide.with(getContext()).load(RecyclerNewAdapter.listitem.get(Clickedpostion).getBannerImage()).into(Image);
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

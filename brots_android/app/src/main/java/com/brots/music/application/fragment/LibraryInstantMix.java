package com.brots.music.application.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.adapter.RecyclerNewAdapter;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.LibraryInstanceSongResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.Utilities;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class LibraryInstantMix extends Fragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
{
    private SeekBar InstantSeek;
    ImageView Play,Pause,FullImage,SkipSong;
    TextView currentTime,totalTime,songName,artistName;
    CircleImageView Image;
    private PreferenceManager sharedPreferences;
    String name,id,Songurl = "";
    public static MediaPlayer mediaPlayer;
    int mediaFileLengthInMilliseconds,i,clickedPostion=0,IsPlay=0;
    Handler handler = new Handler();
    Utilities utils=new Utilities();
    userOnlineInfo user;
    private String endPoint;
    LibraryInstanceSongResponseModel library;

    public LibraryInstantMix()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.libraryinstantmix,container,false);
        try{
            intializeviews(v);
            GetArtistInstanceSong();
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;

    }

    private void intializeviews(View v) {

        Config.musiPlayerScreenType = "LibraryInstaMix";
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        user=new userOnlineInfo();
        InstantSeek =(SeekBar)v.findViewById(R.id.SeekBarTestPlay); // Seekbar
        Play=(ImageView)v.findViewById(R.id.instantplayBtn);        // ImageView
        Pause=(ImageView)v.findViewById(R.id.instantpause);
        FullImage=(ImageView)v.findViewById(R.id.imageback);
        SkipSong=(ImageView) v.findViewById(R.id.instantnext);
        currentTime=(TextView)v.findViewById(R.id.elapsedTimeLabel);// Text Views
        totalTime=(TextView)v.findViewById(R.id.remainingTimeLabel);
        songName=(TextView)v.findViewById(R.id.instantsongname);
        artistName=(TextView)v.findViewById(R.id.instantartistname);
        Image=(CircleImageView)v.findViewById(R.id.instantcircle);
        sharedPreferences = new PreferenceManager(getActivity()); // Shared Prefrebnces
        name = sharedPreferences.getString(PreferenceManager.name);
        id = sharedPreferences.getString(PreferenceManager.id);
        IsPlay=2131296598;
        Play.setOnClickListener(this);  // Play Button
        InstantSeek.setMax(99);
        InstantSeek.setOnSeekBarChangeListener(this);
        Pause.setOnClickListener(this);
        SkipSong.setOnClickListener(this);   //Skip Song

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

        InstantSeek.setMax(99);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

    }

    private void GetArtistInstanceSong() {
        if (user.isOnline(getActivity()))
        {
            setEndPoint();
            String userid=sharedPreferences.getString(PreferenceManager.id);
            Apicaller.libraryInstanceSong(getActivity(), endPoint, userid, new
                    FutureCallback<LibraryInstanceSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LibraryInstanceSongResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            setdata(result);
                        }
                    });

        }else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(getActivity());
        }
    }

    private void setdata(LibraryInstanceSongResponseModel result) {
        library=result;
        String msg=result.getMessage();
        if(msg.equals("500"))
        {
            Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
        }else {
        for (i= 0; i < result.getData().size(); i++) {
            Songurl = result.getData().get(clickedPostion).getFullSongImageUrl();
            songName.setText(result.getData().get(clickedPostion).getSongName());
            artistName.setText(result.getData().get(clickedPostion).getArtistName());
            Glide.with(getActivity()).load(result.getData().get(clickedPostion).getBannerImage()).into(Image);
            Glide.with(getActivity()).load(result.getData().get(clickedPostion).getBannerImage()).into(FullImage);
        }
        }
    }

    private void setEndPoint() {
        endPoint = Config.Url.getInstanceSong;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.instantplayBtn)                // Play Song
        {
             if(Songurl.equals("")){
                 Toast.makeText(getActivity(), "No Song Saved till in the library", Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(getActivity(), "Please wait while song is playing", Toast.LENGTH_SHORT).show();
                 playSong();
             }

        }
            if (v.getId() == R.id.instantpause)                // Pause Song
            {

                if(Songurl.equals("")){
                    Toast.makeText(getActivity(), "No Song Saved till in the library", Toast.LENGTH_SHORT).show();
                }else {
                    Pause.setVisibility(View.GONE);
                    Play.setVisibility(View.VISIBLE);
                    playSong();
                }

            }
                if (v.getId() == R.id.instantnext)          // Next Play Song
                {
                    if(Songurl.equals("")){
                        Toast.makeText(getActivity(), "No Song Saved till in the librarry", Toast.LENGTH_SHORT).show();
                    }else {
                        nextSong();
                    }


                 }
    }
    private void playSong()
    {
        Toast.makeText(getActivity(), "Please wait while song is playing", Toast.LENGTH_SHORT).show();
        if(LibrarySong.mediaPlayer.isPlaying()){
            RecyclerNewAdapter.currentSongPlayId= "";
            LibrarySong.mediaPlayer.reset();
            LibrarySong.mediaPlayer.stop();
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
                 if (!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();

                        Play.setVisibility(View.GONE);
                        Pause.setVisibility(View.VISIBLE);
                    }else {
                              mediaPlayer.pause();

                              Pause.setVisibility(View.GONE);
                              Play.setVisibility(View.VISIBLE);
                          }

        primarySeekBarProgressUpdater();
    }

    private void nextSong()
    {
        if(LibrarySong.mediaPlayer.isPlaying()){
            RecyclerNewAdapter.currentSongPlayId = "";
            LibrarySong.mediaPlayer.reset();
            LibrarySong.mediaPlayer.stop();
        }
        if(ExploreFragment.mediaPlayer.isPlaying()){
            ExploreFragment.mediaPlayer.reset();
            ExploreFragment.mediaPlayer.stop();
        }
        mediaPlayer.reset();
        clickedPostion = ((clickedPostion + 1) %library.getData().size());
        Play.setVisibility(View.GONE);
        Pause.setVisibility(View.VISIBLE);
        songName.setText(library.getData().get(clickedPostion).getSongName());
        artistName.setText(library.getData().get(clickedPostion).getArtistName());
        Songurl = library.getData().get(clickedPostion).getFullSongImageUrl();
        Glide.with(getActivity()).load(library.getData().get(clickedPostion).getBannerImage()).into(Image);
        Glide.with(getContext()).load(library.getData().get(clickedPostion).getBannerImage()).into(FullImage);
        playSong();
    }

    private void primarySeekBarProgressUpdater() {
        InstantSeek.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                    long totalDuration = mediaPlayer.getDuration();
                    long currentDuration = mediaPlayer.getCurrentPosition();
                    totalTime.setText(""+utils.milliSecondsToTimer(totalDuration));
                    currentTime.setText(""+utils.milliSecondsToTimer(currentDuration));
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
       if(Config.musiPlayerScreenType.equals("LibraryInstaMix")) {
           mediaPlayer.reset();
           try {

               clickedPostion = ((clickedPostion + 1) % library.getData().size());
               Log.e("cu", "" + clickedPostion + "song " + library.getData().get(clickedPostion).getFullSongImageUrl());
               String song = library.getData().get(clickedPostion).getFullSongImageUrl();
               songName.setText(library.getData().get(clickedPostion).getSongName());
               artistName.setText(library.getData().get(clickedPostion).getArtistName());
               Glide.with(getContext()).load(library.getData().get(clickedPostion).getBannerImage()).into(Image);
               Glide.with(getContext()).load(library.getData().get(clickedPostion).getBannerImage()).into(FullImage);
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

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        InstantSeek.setSecondaryProgress(percent);
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
            int playPositionInMillisecconds = ( mediaFileLengthInMilliseconds/ 100) * seekBar.getProgress();
            mediaPlayer.seekTo(playPositionInMillisecconds);
            Log.e("PP",""+utils.milliSecondsToTimer(playPositionInMillisecconds));
        }
    }
}

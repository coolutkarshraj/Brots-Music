package com.brots.music.application.activity.Profile;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.adapter.ArtistPlayerAdapter;
import com.brots.music.application.adapter.RecyclerNewAdapter;
import com.brots.music.application.adapter.SongListAdapter;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.fragment.ExploreArtist;
import com.brots.music.application.fragment.ExploreFragment;
import com.brots.music.application.fragment.LibraryInstantMix;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ArtistGetSongResponseModel;
import com.brots.music.application.model.responseData.ArtistGetSongDataModel;
import com.brots.music.application.model.responseData.LibrarySongDataModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.Utilities;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListSongArtistProfileActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    RecyclerView recyclerView;
    List<ArtistGetSongDataModel> item;
    public static ArtistPlayerAdapter adapter;
    Activity activity;
    String msg;
    userOnlineInfo user;
    PreferenceManager sharedPreferences;
    String name,userType,id,deviceToken,password1,email1,endpoint;
    static SeekBar seekBar;
    public static String songUrl=" ";
    public static int Clickedpostion;
    public static CircleImageView playerImage;
    public static  TextView currentTime,totalTime ,songName,artistName;
    public static ImageView playMusic,pauseMusic,Nextplaysong;
    public static MediaPlayer mediaPlayer;
    static int mediaFileLengthInMilliseconds;
    static Handler handler = new Handler();
    static Utilities utils = new Utilities();
    private ArtistGetSongResponseModel data;
    ImageButton txt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_song_artist_profile_activity);
        fontStyle();
        intializeViews();
        localStroage();
        artistsong();

    }

    private  void fontStyle() {
        Config.musiPlayerScreenType = "ListSongArtistProfileActivity";
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    private void  intializeViews(){
        user = new userOnlineInfo();
       // mediaPlayer=new MediaPlayer();
        activity = ListSongArtistProfileActivity.this;
        recyclerView = (RecyclerView)findViewById(R.id.artistrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        item=new ArrayList<>();

        /*  Text view */
        songName = (TextView) findViewById(R.id.titleHead);
        artistName = (TextView) findViewById(R.id.titlesub);
        totalTime = (TextView) findViewById(R.id.totaltime);
        currentTime = (TextView) findViewById(R.id.currenttime);

        /*Image View */
        playerImage = (CircleImageView)findViewById(R.id.imageshow);
        playMusic = (ImageView) findViewById(R.id.playBtnnn);
        pauseMusic = (ImageView) findViewById(R.id.pauseBtnnn);
        Nextplaysong = (ImageView) findViewById(R.id.nextplay);
        txt_back = (ImageButton) findViewById(R.id.txt_back);

        /*Seek Bar*/
        seekBar = (SeekBar)findViewById(R.id.seekBar1);

        /* Click Listner*/
        seekBar.setOnTouchListener(this);
        playMusic.setOnClickListener(this);
        pauseMusic.setOnClickListener(this);
        Nextplaysong.setOnClickListener(this);
        checkforMusicPlayerIsPlayingOrNot();
        bindListner();

    }

    private void bindListner() {
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    private void localStroage() {
        sharedPreferences = new PreferenceManager(activity);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
    }

    private void apiUrl(){
        endpoint = Config.Url.getSingleArtistSongs;
    }

    private void artistsong() {              // single artist all song

        if (user.isOnline(activity)) {
            apiUrl();
            Apicaller.getSingleArtistAllSongs(activity, endpoint, id, "", new
                    FutureCallback<ArtistGetSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistGetSongResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            singleArtistSongs(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void singleArtistSongs(ArtistGetSongResponseModel result) {
        data = result;
        String API_Status=result.getStatus();
        msg=result.getMessage();
        if(msg.equals("500"))
        {
            Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
        }
        else if(API_Status.equals("true"))
        {

            if(result.getData().isEmpty())
            {
                Toast.makeText(activity, "No Songs" , Toast.LENGTH_SHORT).show();
            }
            else{
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
                dataModel.setCoverImage(result.getData().get(i).getCoverImage());
                dataModel.setImageUrl(result.getData().get(i).getImageUrl());
                dataModel.setId(result.getData().get(i).getId());
                dataModel.setArtistId(result.getData().get(i).getArtistId());
                item.add(dataModel);
            }
            adapter = new ArtistPlayerAdapter(activity, item);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }}
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playBtnnn){
            if(songUrl.equals("")){
                Toast.makeText(activity, "Empty songs", Toast.LENGTH_SHORT).show();
            }else if(msg.equals("500")) {
                Toast.makeText(activity, "server error", Toast.LENGTH_SHORT).show();
            }else {
                playSong();
            }
        }
        if(v.getId() == R.id.pauseBtnnn){
            if(songUrl.equals("")){
                Toast.makeText(activity, "Empty songs", Toast.LENGTH_SHORT).show();
            }else if(msg.equals("500")) {
                Toast.makeText(activity, "server error", Toast.LENGTH_SHORT).show();
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
            }else if(msg.equals("500")) {
                Toast.makeText(activity, "server error", Toast.LENGTH_SHORT).show();
            }else {
                nextPlaySong();
            }
        }

    }
        /* Song play  */
    private void playSong() {
        if(ExploreArtist.mediaPlayer.isPlaying()){
            ExploreArtist.mediaPlayer.reset();
            ExploreArtist.mediaPlayer.stop();
        }


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

    /*
    *  Skip Song*/
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

    public static void playSongByRecyclerViewClick(String Songurl) {
        if(ExploreArtist.mediaPlayer.isPlaying()){
            ExploreArtist.mediaPlayer.reset();
            ExploreArtist.mediaPlayer.stop();
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
        if(data.getData().isEmpty())
        {
            Toast.makeText(activity, "No songs", Toast.LENGTH_SHORT).show();
        }
         else if(Config.musiPlayerScreenType.equals("ListSongArtistProfileActivity")){
        mediaPlayer.reset();
        try {
            Clickedpostion = ((Clickedpostion + 1) %data.getData().size());
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
    }}
}

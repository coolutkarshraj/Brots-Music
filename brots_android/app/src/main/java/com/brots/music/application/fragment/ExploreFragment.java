package com.brots.music.application.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.adapter.HoneAdapter;
import com.brots.music.application.adapter.HtwoAdapter;
import com.brots.music.application.adapter.RecyclerNewAdapter;
import com.brots.music.application.interfaces.AdapterInterface;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.request.SongCategoriesModel;
import com.brots.music.application.model.request.SongSubCatModel;
import com.brots.music.application.model.response.ArtistSongResponseModel;
import com.brots.music.application.model.response.CategorySubCategoryResponseModel;
import com.brots.music.application.model.response.IsSongPlayedModel;
import com.brots.music.application.model.response.LikeSongResponseModel;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.model.response.RemoveSongResponseModel;
import com.brots.music.application.model.response.SaveSongResponseModel;
import com.brots.music.application.model.responseData.SubcategoryData;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.RelativeLayoutTouchListener;
import com.brots.music.application.util.Utilities;
import com.brots.music.application.util.InsetsPercentRelativeLayout;
import com.brots.music.application.apiInterface.AppController;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import com.koushikdutta.async.future.FutureCallback;
import static android.view.View.GONE;
import static com.brots.music.application.Config.Clickedpostion;


public class ExploreFragment extends Fragment
        implements AdapterInterface,
        View.OnClickListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener {

    String categoryid;
    public String subcategoryid;
    RecyclerView Rone, Rtwo;
    HtwoAdapter htwoAdapter;
    HoneAdapter honeAdapter;
    List<SongCategoriesModel> list1 = new ArrayList<>();
    List<SongSubCatModel> list2 = new ArrayList<>();
    RequestQueue queue;
    AdapterInterface ad_interface;
    PreferenceManager sharedPreferences;
    String id;
    public static Activity activity;
    String name, Songurl;
    int postionsubcategory;
    int postionfori = 0, postionfinali = 0;
    // Media Player Views
    ImageView Share, Save, remove;
    TextView Songname,tv_noSong, Artistname, CurrentSong, TotalSong, tv_artist_name, textdislike, txtlike, textpoint;
    public static  TextView textLike,textDislike;
    private CircularSeekBar Seekbar,SeekDummy;
    public CircleImageView ButtonTestpause,PlayDummy;
    RelativeLayout LikeDislikelayout;
    CircleImageView Image;
    String ShareSonggurl;
    RelativeLayout layout,ll_music_layout;
    userOnlineInfo user;
    private String endpoint;
    public static MediaPlayer mediaPlayer;
    int mediaFileLengthInMilliseconds;
    Utilities utils = new Utilities();
    int IsPlay = 0;
    Handler handler = new Handler();
    public  static ArtistSongResponseModel instantplay;
    String BannerImage, savedSongMemory, Imageurl, ArtistName, SongName, Duration, Albumname, ArtistId;
    String Fullurl, totallike = "0", songid, disLikes = "0";
    public static String SingleSavedMemory = "";
    RelativeLayout relation;
    private String songName;
    private ImageView imgdislike, imglike;
    public static ImageView S_ImageLike,S_ImageDislike;
    public static RelativeLayout LikeLayout,DislikeLayout;
    String songType=""; // if song type is 1 then play 30 second song,else full song
    String song; // this song for autoplay music
    NewProgressBar dialog;
    String c_songId,t_likes,t_disLikes;
    ArtistSongResponseModel resultfinal;
    public static int isLikedValue,IsDisLikeValue;
    public static String totalnoLikes,totalno_dislike = "0";
    public ExploreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        intializeview(view);
        getCatSubCat();
        bindListner();
        return view;
    }

    private void bindListner() {
        Seekbar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                if (mediaPlayer.isPlaying()) {
                    int playPositionInMillisecconds = (int) ((mediaFileLengthInMilliseconds / 100) * seekBar.getProgress());
                    Log.e("playp",""+utils.milliSecondsToTimer(playPositionInMillisecconds));
                    mediaPlayer.seekTo(playPositionInMillisecconds);
                }
            }
        });

try{
    relation.setOnTouchListener(new RelativeLayoutTouchListener(getActivity()));
}catch (Exception e){
    e.printStackTrace();
}


        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    if(mediaPlayer.getCurrentPosition()/1000 <= 20){
                        Toast.makeText(getActivity(), "Atleast you have to listen the song 15 seconds full song will be played after 15 sec", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog=new NewProgressBar(activity);
                        dialog.show();
                        mediaPlayer.reset();
                        PlayMusicfull(instantplay.getData().get(Clickedpostion).getFullSongUrl());
                        songType = "1";
                    }


                }else {
                    Toast.makeText(getActivity(), "Atleast you have to listen the song 15 seconds full song will be played after 15 sec", Toast.LENGTH_SHORT).show();
                    songType = "2";
                }

            }
        });

        imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.getCurrentPosition()/1000 >= 20){
                    Config.exploreTabClicked = "1";
                    likeSong();
                }else if(mediaPlayer == null){
                    Config.exploreTabClicked = "1";
                    Toast.makeText(getActivity(), "You cannot Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
                }
                else {
                    Config.exploreTabClicked = "1";
                    Toast.makeText(getActivity(), "You cannot Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.getCurrentPosition()/1000 >= 20){
                    Config.exploreTabClicked = "1";
                    disLikeSong();
                }
                else if(mediaPlayer == null){
                    Config.exploreTabClicked = "1";
                    Toast.makeText(getActivity(), "You cannot Dis-Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
                }else {
                    Config.exploreTabClicked = "1";
                    Toast.makeText(getActivity(), "You cannot Dis-Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /**
     * This method initialise all the views in project
     */

    @SuppressLint("WrongViewCast")
    public void intializeview(View view) {
        Config.musiPlayerScreenType ="exploreFragment";
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) view);

        ad_interface = this;
        activity = getActivity();
        sharedPreferences = new PreferenceManager(getActivity());
        name = sharedPreferences.getString(PreferenceManager.name);
        id = sharedPreferences.getString(PreferenceManager.id);
        savedSongMemory = sharedPreferences.getString(PreferenceManager.savedSongMemory);
        queue = AppController.getInstance().getRequestQueue();
        Rone = (RecyclerView) view.findViewById(R.id.recyclerone);
        Rtwo = (RecyclerView) view.findViewById(R.id.recyclertwo);

        //Text Views for Player

        Songname = (TextView) view.findViewById(R.id.titleHead);
        tv_noSong = (TextView) view.findViewById(R.id.tv_noSong);
        Artistname = (TextView) view.findViewById(R.id.titlesub);
        TotalSong = (TextView) view.findViewById(R.id.songTotalDurationLabel);
        CurrentSong = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        tv_artist_name = (TextView) view.findViewById(R.id.txt);
        txtlike = (TextView) view.findViewById(R.id.txtlike);
        textdislike = (TextView) view.findViewById(R.id.textdislike);
        textpoint = (TextView) view.findViewById(R.id.textpoint);
        imgdislike = (ImageView) view.findViewById(R.id.imgdislike);
        imglike = (ImageView) view.findViewById(R.id.imglike);
        relation = (RelativeLayout) view.findViewById(R.id.relation);
        ll_music_layout = (RelativeLayout) view.findViewById(R.id.ll_music_layout);

        // Image Views for Player

        Save = (ImageView) view.findViewById(R.id.save);
        remove = (ImageView) view.findViewById(R.id.remove);
        Share = (ImageView) view.findViewById(R.id.share);
        Share.setOnClickListener(this);
        Save.setOnClickListener(this);
        remove.setOnClickListener(this);

        // FloatingActionButton for Player

        //Playimage = (FloatingActionButton) view.findViewById(R.id.ButtonTestPlay);
        ButtonTestpause = (CircleImageView) view.findViewById(R.id.ButtonTestpause);
        PlayDummy = (CircleImageView) view.findViewById(R.id.playtemp);


        Seekbar = (CircularSeekBar) view.findViewById(R.id.SeekBarTestPlay);
        SeekDummy=(CircularSeekBar)view.findViewById(R.id.seekplay) ;
        LikeDislikelayout = (RelativeLayout) view.findViewById(R.id.layoutlikedislike);
        LikeLayout = (RelativeLayout) view.findViewById(R.id.likeLayout);
        DislikeLayout = (RelativeLayout) view.findViewById(R.id.dislikelayout);


        Image = (CircleImageView) view.findViewById(R.id.imageshow);
        layout = (InsetsPercentRelativeLayout) view.findViewById(R.id.libshape);
       // Playimage.setOnClickListener(this);
        ButtonTestpause.setOnClickListener(this);
        PlayDummy.setOnClickListener(this);
        checkforMusicPlayerIsPlayingOrNot();


    }

    @SuppressLint("RestrictedApi")
    private void checkforMusicPlayerIsPlayingOrNot() {
        if(mediaPlayer == null){
            initializeMusicPlayer();
            getCatSubCat();
            PlayDummy.setVisibility(View.VISIBLE);
            ButtonTestpause.setVisibility(View.GONE);
        }else{
            if(mediaPlayer.isPlaying()){
                getCatSubCat();
                PlayDummy.setVisibility(View.GONE);
                ButtonTestpause.setVisibility(View.VISIBLE);
            }else {
                initializeMusicPlayer();
                getCatSubCat();
                PlayDummy.setVisibility(View.VISIBLE);
                ButtonTestpause.setVisibility(View.GONE);
            }
        }

    }

    private void initializeMusicPlayer() {
        IsPlay = 2131296598;
        Seekbar.setMax(99);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

    }


    public void getCategorypostion(int postioni, String categoryId) {
        postionfinali = postioni;
        postionsubcategory = postioni;
        categoryid = categoryId;
        Log.e("first2", "first");
    }


    private void getCatSubCat() {

            setEndPoint();
            Apicaller.getCatSubCat(getActivity(), endpoint,
                    new FutureCallback<CategorySubCategoryResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, CategorySubCategoryResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            Config.CatSubCat = result;
                            setTabLayout(result);


                        }
                    }
            );
        }


    private void setTabLayout(CategorySubCategoryResponseModel result) {
        list1.clear();
        list2.clear();
//        categoryid = String.valueOf(result.getData().get(0).getId());
        for (int i = 0; i < result.getData().size(); i++) {
            String id = String.valueOf(result.getData().get(i).getId());
            String catName = String.valueOf(result.getData().get(i).getName());
            list1.add(new SongCategoriesModel(catName, id));
            if (i == postionfinali) {
                categoryid = String.valueOf(result.getData().get(i).getId());
                List<SubcategoryData> subcat = result.getData().get(i).getSubcategories();
                subcategoryid = String.valueOf(subcat.get(Config.subcatPosition).getId());
                for (int j = 0; j < subcat.size(); j++) {
                    String subCatId = String.valueOf(subcat.get(j).getId());
                    String name = String.valueOf(subcat.get(j).getName());
                    list2.add(new SongSubCatModel(name, subCatId));
                }

            }

        }
        setAdapterTab();
    }

    private void setAdapterTab() {
        Rone.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Rtwo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Rone.setHasFixedSize(true);
        honeAdapter = new HoneAdapter(list1, ad_interface, getActivity());
        Rone.setAdapter(honeAdapter);
        honeAdapter.notifyDataSetChanged();
        Rone.scrollToPosition(HoneAdapter.row_index);

        htwoAdapter = new HtwoAdapter(list2, ad_interface, getActivity());
        Rtwo.setAdapter(htwoAdapter);
        Rtwo.setHasFixedSize(true);
        htwoAdapter.notifyDataSetChanged();
        Rtwo.scrollToPosition(HtwoAdapter.rowindex2);
        try{
            ArtistgetallsongList();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void ArtistgetallsongList() {
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            try {

                setArtistEndPoint();
                String userId = sharedPreferences.getString(PreferenceManager.id);
                Apicaller.getAllArtistSong(getActivity(), endpoint, categoryid, subcategoryid, userId,
                        new FutureCallback<ArtistSongResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, ArtistSongResponseModel result) {
                                if (e != null) {
                                    return;
                                }
                                if (result.getData().size() == 0) {
                                    ll_music_layout.setVisibility(GONE);
                                    tv_noSong.setVisibility(View.VISIBLE);
                                } else {
                                    tv_noSong.setVisibility(View.GONE);
                                    ll_music_layout.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < result.getData().size(); i++) {
                                        int songHearrSize = result.getData().get(i).getSongHearedByUser().size();
                                        if (songHearrSize == 0) {

                                        } else {
                                            result.getData().remove(i);
                                            i--;
                                        }
                                    }
                                    if (result.getData().size() == 0) {
                                        ll_music_layout.setVisibility(GONE);
                                        tv_noSong.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_noSong.setVisibility(View.GONE);
                                        ll_music_layout.setVisibility(View.VISIBLE);
                                        try {
                                            setDataToMusicPlayer(result);
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }

                                    }


                                }
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDataToMusicPlayer(ArtistSongResponseModel result) {
        instantplay = result;
        Songname.setText(result.getData().get(Clickedpostion).getSongName());
        Picasso.get().load(result.getData().get(Clickedpostion).getCoverImage()).into(Image);
        tv_artist_name.setText(result.getData().get(Clickedpostion).getArtistName());
        textdislike.setText(String.valueOf(result.getData().get(Clickedpostion).getDisLike()));
        totalno_dislike = String.valueOf(result.getData().get(Clickedpostion).getDisLike());
        totalnoLikes = String.valueOf(result.getData().get(Clickedpostion).getLike());
        txtlike.setText(String.valueOf(result.getData().get(Clickedpostion).getLike()));
        textLike = txtlike;
        textDislike = textdislike;
        textpoint.setText("10");
        int LikedSongSize = result.getData().get(Clickedpostion).getLikedUser().size();
        if (LikedSongSize != 0) {
            if (result.getData().get(Clickedpostion).getLikedUser().get(0).getSongThrough().getLike() == 1) {
                try {
                    imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.green));
                    imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                    isLikedValue = 1;
                    IsDisLikeValue = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                    imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red));
                    isLikedValue = 0;
                    IsDisLikeValue = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
            imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
            isLikedValue = 0;
            IsDisLikeValue = 0;
        }

                S_ImageDislike = imgdislike;
                S_ImageLike = imglike;
                int saveSongSize = result.getData().get(Clickedpostion).getSaveAdminUser().size();
                if (saveSongSize != 0) {
                    if (result.getData().get(Clickedpostion).getSaveAdminUser().get(0).getAdminSaveSongThrough().getIsSaved().equals("1")) {
                        Save.setVisibility(GONE);
                        remove.setVisibility(View.VISIBLE);
                    }
                } else {
                    remove.setVisibility(View.GONE);
                    Save.setVisibility(View.VISIBLE);
                }

        }


    private void setArtistEndPoint() {
        endpoint = Config.Url.getArtistSong;
    }

    private void setEndPoint()   {
        endpoint = Config.Url.getCatSubcat;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


    // Category Clicked items
    @SuppressLint("RestrictedApi")
    @Override
    public void click(int postion, String id, String title) {  // category click
        Config.exploreTabClicked = "1";
        Config.subcatPosition = 0;
        Clickedpostion = 0;
        postionfori = postion;
        categoryid = id;
        getCategorypostion(postion, id);
        getCatSubCatandSetToRecyclerView(id);
        try {
            ArtistgetallsongList();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                PlayDummy.setVisibility(View.VISIBLE);
                ButtonTestpause.setVisibility(GONE);
                Seekbar.setVisibility(View.GONE);
                SeekDummy.setVisibility(View.VISIBLE);
            } else {
                PlayDummy.setVisibility(View.VISIBLE);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void clickn(int postion, String getid, String title) {  // sub category click
        Config.subcatPosition = postion;
        Config.exploreTabClicked = "1";
        Clickedpostion = 0;
        subcategoryid = getid;
        try {
            ArtistgetallsongList();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                //mediaPlayer.stop();
                Seekbar.setProgress(0);
                PlayDummy.setVisibility(View.VISIBLE);
                ButtonTestpause.setVisibility(GONE);
                Seekbar.setVisibility(View.GONE);
                SeekDummy.setVisibility(View.VISIBLE);

            } else {
                PlayDummy.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCatSubCatandSetToRecyclerView(String id) {
        for (int i = 0; i <
                Config.CatSubCat.getData().size(); i++) {
            String catId = String.valueOf(Config.CatSubCat.getData().get(i).getId());
            if (catId.equals(id)) {
                list2.clear();
                List<SubcategoryData> subcat = Config.CatSubCat.getData().get(i).getSubcategories();

                for (int j = 0; j < subcat.size(); j++) {
                    subcategoryid = String.valueOf(subcat.get(0).getId());
                    String subCatId = String.valueOf(subcat.get(j).getId());
                    String name = String.valueOf(subcat.get(j).getName());
                    list2.add(new SongSubCatModel(name, subCatId));
                }
            }


        }
        if(list2.size() == 0){
            Rtwo.setVisibility(GONE);
        }else {
            Rtwo.setVisibility(View.VISIBLE);
            Rtwo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            htwoAdapter = new HtwoAdapter(list2, ad_interface, getActivity());
            Rtwo.setAdapter(htwoAdapter);
            Rtwo.setHasFixedSize(true);
            htwoAdapter.notifyDataSetChanged();
        }


    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ButtonTestpause){
            //mediaPlayer.pause();
            Config.exploreTabClicked = "0";
            PlayMusic(instantplay.getData().get(Clickedpostion).getImageUrl());
            PlayDummy.setVisibility(View.VISIBLE);
            ButtonTestpause.setVisibility(View.GONE);
        }
        if (v.getId() == R.id.playtemp) // Music Play Button
        {
            Toast.makeText(getActivity(), "Please Wait Song will Start soon Song starts to buffer", Toast.LENGTH_SHORT).show();

            if(mediaPlayer.isPlaying()){


            }else {
                if( mediaPlayer.getCurrentPosition() > 1){
                  mediaPlayer.start();
                    songType="2";
                    PlayDummy.setVisibility(View.GONE);
                    ButtonTestpause.setVisibility(View.VISIBLE);
                    Seekbar.setVisibility(View.VISIBLE);
                    SeekDummy.setVisibility(View.GONE);
                    primarySeekBarProgressUpdater();

                }else {
                   mediaPlayer.reset();
                    PlayMusic(instantplay.getData().get(Clickedpostion).getImageUrl());
                    songType="2";
                    PlayDummy.setVisibility(View.GONE);
                    Seekbar.setVisibility(View.VISIBLE);
                    SeekDummy.setVisibility(View.GONE);

                }

            }


        }
        if (v.getId() == R.id.share) {
            try {
                ShareSonggurl = instantplay.getData().get(Clickedpostion).getFullSongUrl();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, ShareSonggurl);
                startActivity(Intent.createChooser(i, "Choose one"));
            } catch (Exception e) {
            }
        }
        if (v.getId() == R.id.save) {
            SaveSong();

        }
        if (v.getId() == R.id.remove) {
            RemoveSong();

        }

    }
/*    */

    @SuppressLint("RestrictedApi")
    private void PlayMusic(String Songurl) {
       try {
           if (LibrarySong.mediaPlayer.isPlaying()) {
               RecyclerNewAdapter.currentSongPlayId = "";
               LibrarySong.mediaPlayer.reset();
               LibrarySong.mediaPlayer.stop();
           }
           if (LibraryInstantMix.mediaPlayer.isPlaying()) {
               RecyclerNewAdapter.currentSongPlayId = "";
               LibraryInstantMix.mediaPlayer.reset();
               LibraryInstantMix.mediaPlayer.stop();
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
            PlayDummy.setVisibility(View.GONE);
            ButtonTestpause.setVisibility(View.VISIBLE);
        } else {
            PlayDummy.setVisibility(View.VISIBLE);
            ButtonTestpause.setVisibility(View.GONE);
            mediaPlayer.pause();

        }

        primarySeekBarProgressUpdater();
        callApiThatSongStarrtsToPlay();
    }


    @SuppressLint("RestrictedApi")
    private void PlayMusicfull(String fullSongUrl) {
        try {
            mediaPlayer.setDataSource(fullSongUrl);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            dialog.dismiss();
            PlayDummy.setVisibility(View.GONE);
            ButtonTestpause.setVisibility(View.VISIBLE);
        } else {
            PlayDummy.setVisibility(View.VISIBLE);
            ButtonTestpause.setVisibility(View.GONE);
            mediaPlayer.pause();
            Glide.with(getActivity()).load(R.drawable.playnew).into(PlayDummy);
        }

        primarySeekBarProgressUpdater();

    }

    private void callApiThatSongStarrtsToPlay() {
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            songid = String.valueOf(instantplay.getData().get(Clickedpostion).getId());
            String userId = sharedPreferences.getString(PreferenceManager.id);
            Apicaller.callApiThatSongHasPlayed(getActivity(), Config.Url.isSongPlayed, userId, songid,
                    new FutureCallback<IsSongPlayedModel>() {
                        @Override
                        public void onCompleted(Exception e, IsSongPlayedModel result) {
                            if (e != null) {
                                return;
                            }

                        }
                    }
            );

        }
    }

    private void primarySeekBarProgressUpdater() {
        Seekbar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                    long totalDuration = mediaPlayer.getDuration();       // gets the song length in milliseconds from URL
                    long currentDuration = mediaPlayer.getCurrentPosition();
                    // Displaying Total Duration time
                    TotalSong.setText("" + utils.milliSecondsToTimer(totalDuration));
                    // Displaying time completed playing
                    CurrentSong.setText("" + utils.milliSecondsToTimer(currentDuration));
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //activity.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        user = new userOnlineInfo();
        if (user.isOnline(getActivity())) {
            setArtistEndPoint();
            String userId = sharedPreferences.getString(PreferenceManager.id);
            Apicaller.getAllArtistSong(getActivity(), endpoint, categoryid, subcategoryid, userId,
                    new FutureCallback<ArtistSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistSongResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            if(result.getData().size() == 0){
                                ll_music_layout.setVisibility(GONE);
                                tv_noSong.setVisibility(View.VISIBLE);
                            }else {

                                tv_noSong.setVisibility(View.GONE);
                                ll_music_layout.setVisibility(View.VISIBLE);
                                for (int i = 0; i< result.getData().size();i++){
                                    int songHearrSize = result.getData().get(i).getSongHearedByUser().size();
                                    if(songHearrSize == 0){

                                    }else {
                                        result.getData().remove(0);
                                        i--;
                                    }
                                }
                                instantplay = result;
                                if(result.getData().size() == 0){
                                    ll_music_layout.setVisibility(GONE);
                                    tv_noSong.setVisibility(View.VISIBLE);
                                }else {
                                    ll_music_layout.setVisibility(View.VISIBLE);
                                    tv_noSong.setVisibility(View.GONE);
                                    playNextSongAfterCompletionSong();
                                }



                            }
                        }
                    }
            );
        }





    }

    private void playNextSongAfterCompletionSong() {
        if(Config.exploreTabClicked.equals("1")){
            Config.exploreTabClicked = "0";
        }else {
            songType =  "2";
            if (Config.musiPlayerScreenType.equals("exploreFragment")) {
                mediaPlayer.reset(); //Media Player reset When first song Complete
                try {
                    Clickedpostion = (Clickedpostion ) % instantplay.getData().size();  // index increase when song completed
                    //Log.e("cu", "" + Clickedpostion + "song " + instantplay.getData().get(Clickedpostion).getFullSongUrl();
                    if(songType.equals("1")) {
                        song = instantplay.getData().get(Clickedpostion).getFullSongUrl();
                    }
                    else {
                        song = instantplay.getData().get(Clickedpostion).getImageUrl();
                    }
                    // get next song url
                    Songname.setText(instantplay.getData().get(Clickedpostion).getSongName());// get next song name
                    tv_artist_name.setText(instantplay.getData().get(Clickedpostion).getArtistName());// get next song Artist name
                    Glide.with(getActivity()).load(instantplay.getData().get(Clickedpostion).getCoverImage()).into(Image);// get image and set into c
                    textdislike.setText(String.valueOf(instantplay.getData().get(Clickedpostion).getDisLike()));
                    txtlike.setText(String.valueOf(instantplay.getData().get(Clickedpostion).getLike()));
                    totalno_dislike = String.valueOf(instantplay.getData().get(Clickedpostion).getDisLike());
                    totalnoLikes = String.valueOf(instantplay.getData().get(Clickedpostion).getLike());
                    mediaPlayer.setDataSource(song);   // setup song from URL to mediaplayer data source
                    Log.e("newsong", song);
                    mediaPlayer.prepare();      // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                    primarySeekBarProgressUpdater();
                    /* Save and Remove Song as a autoplay song*/

                    int saveSongSize = instantplay.getData().get(Clickedpostion).getSaveAdminUser().size();
                    if (saveSongSize != 0) {
                        if (instantplay.getData().get(Clickedpostion).getSaveAdminUser().get(0).getAdminSaveSongThrough().getIsSaved().equals("1")) {
                            Save.setVisibility(GONE);
                            remove.setVisibility(View.VISIBLE);
                        }
                    } else {
                        remove.setVisibility(View.GONE);
                        Save.setVisibility(View.VISIBLE);
                    }
                    /* Like and Dislike Song as a autoplay song*/


                    int LikedSongSize = instantplay.getData().get(Clickedpostion).getLikedUser().size();
                    if (LikedSongSize != 0) {
                        if (instantplay.getData().get(Clickedpostion).getLikedUser().get(0).getSongThrough().getLike() == 1) {
                            imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.green));
                            imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                            isLikedValue = 1;
                            IsDisLikeValue = 0;

                        }
                        if (instantplay.getData().get(Clickedpostion).getLikedUser().get(0).getSongThrough().getDisLike() == 1) {
                            imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                            imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red));
                            isLikedValue = 1;
                            IsDisLikeValue = 0;

                        }
                    } else {
                        imglike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                        imgdislike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));
                        isLikedValue = 0;
                        IsDisLikeValue = 0;
                    }
                    S_ImageDislike=imgdislike;
                    S_ImageLike=imglike;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();

                //primarySeekBarProgressUpdater();
                callApiThatSongStarrtsToPlay();
            }
        }

    }


    private void SaveSong() {
        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        int userId;
        int totalSavedMemory = 0;
       try{
           userId = obj.getData().getId();
           totalSavedMemory = obj.getData().getSavedSongMemory();
       }catch (Exception e){
           userId = Integer.parseInt(PreferenceManager.getInstance(activity).getString(PreferenceManager.id));
           totalSavedMemory = Integer.parseInt(PreferenceManager.getInstance(activity).getString(PreferenceManager.instaMixMemory));
           e.printStackTrace();

       }

        songid = String.valueOf(instantplay.getData().get(Clickedpostion).getId());
        songName = instantplay.getData().get(Clickedpostion).getSongName();
        ArtistId = instantplay.getData().get(Clickedpostion).getArtistId();
        Imageurl = instantplay.getData().get(Clickedpostion).getImageUrl();
        totallike = String.valueOf(instantplay.getData().get(Clickedpostion).getLike());
        disLikes = String.valueOf(instantplay.getData().get(Clickedpostion).getDisLike());
        SongName = instantplay.getData().get(Clickedpostion).getSongName();
        ArtistName = instantplay.getData().get(Clickedpostion).getArtistName();
        Fullurl = instantplay.getData().get(Clickedpostion).getFullSongUrl();
        BannerImage = instantplay.getData().get(Clickedpostion).getCoverImage();
        Duration = instantplay.getData().get(Clickedpostion).getDuration();
        Albumname = instantplay.getData().get(Clickedpostion).getAlbumName();
        SingleSavedMemory = String.valueOf(instantplay.getData().get(Clickedpostion).getMemory());
        setEndPoint();
        Apicaller.saveSong(getActivity(), Config.Url.saveSong, totalSavedMemory, songid, SongName, Duration, ArtistId, Fullurl, Imageurl, savedSongMemory, userId,
                BannerImage, ArtistName, Albumname, SingleSavedMemory, new
                        FutureCallback<SaveSongResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, SaveSongResponseModel result) {
                                if (e != null) {
                                    return;
                                }
                                Save.setVisibility(GONE);
                                remove.setVisibility(View.VISIBLE);

                            }
                        });
    }

    private void RemoveSong() {
        songid = String.valueOf(instantplay.getData().get(Clickedpostion).getId());

        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        int userId;
        int totalSavedMemory = 0;
        try{
            userId = obj.getData().getId();
            totalSavedMemory = obj.getData().getSavedSongMemory();
        }catch (Exception e){
            userId = Integer.parseInt(PreferenceManager.getInstance(activity).getString(PreferenceManager.id));
            totalSavedMemory = Integer.parseInt(PreferenceManager.getInstance(activity).getString(PreferenceManager.instaMixMemory));
            e.printStackTrace();

        }
        String SingleSavedMemory = String.valueOf(Config.allArtistSong.getData().get(Clickedpostion).getMemory());
        Apicaller.removeSong(getActivity(), Config.Url.removeSong, userId, totalSavedMemory, SingleSavedMemory, songid, new
                FutureCallback<RemoveSongResponseModel>() {
                    @Override
                    public void onCompleted(Exception e, RemoveSongResponseModel result) {
                        if (e != null) {
                            return;
                        }
                        remove.setVisibility(View.GONE);
                        Save.setVisibility(View.VISIBLE);
                    }
                });

    }


    private void likeSong() {
        if (user.isOnline(activity)) {
            c_songId =  String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getId());
            t_likes =  String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getLike());
            t_disLikes = String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getDisLike());
            Apicaller.likeSong(activity,c_songId,t_likes,t_disLikes, Config.Url.likeSong,
                    new FutureCallback<LikeSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LikeSongResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            System.out.print(result);
                            System.out.print(result.getData());
                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                            ExploreFragment.LikeLayout.setVisibility(View.VISIBLE);
                            if(result.getError().equals("false"))
                            {
                                if(result.getMessage().equals("Already have Liked Song")){
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.green));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    Toast.makeText(activity, "Already have Liked Song", Toast.LENGTH_SHORT).show();
                                }else if(result.getMessage().equals("Already have Dis-Liked Song")){
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.red));
                                    Toast.makeText(activity, "Already have Dis-Liked Song", Toast.LENGTH_SHORT).show();
                                }else {
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.green));
                                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                ExploreFragment.textLike.setText(String.valueOf(result.getData().getLike()));
                                ExploreFragment.textDislike.setText(String.valueOf(result.getData().getDisLike()));
                                totalno_dislike = String.valueOf(result.getData().getDisLike());
                                totalnoLikes = String.valueOf(result.getData().getLike());
                                Toast.makeText(getActivity(), "Song Liked Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }

    }

    private void disLikeSong() {
        if (user.isOnline(activity)) {
            c_songId =  String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getId());
            t_likes =  String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getLike());
            t_disLikes = String.valueOf(Config.allArtistSong.getData().get(Config.Clickedpostion).getDisLike());
            Apicaller.disLikeSong(activity,c_songId,t_likes,t_disLikes, Config.Url.disLikeSong,
                    new FutureCallback<LikeSongResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LikeSongResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            System.out.print(result);
                            System.out.print(result.getData());
                            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                            ExploreFragment.DislikeLayout.setVisibility(View.VISIBLE);
                            if(result.getError().equals("false"))
                            {
                                if(result.getMessage().equals("Already have Dislike Song")){
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.red));
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    Toast.makeText(activity, "Already have Dislike Song", Toast.LENGTH_SHORT).show();
                                }else if(result.getMessage().equals("Already have like Song")){
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.green));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    Toast.makeText(activity, "Already have Like Song", Toast.LENGTH_SHORT).show();
                                }else {
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.red));
                                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(ExploreFragment.activity, R.color.white));
                                ExploreFragment.textLike.setText(String.valueOf(result.getData().getLike()));
                                ExploreFragment.textDislike.setText(String.valueOf(result.getData().getDisLike()));
                                totalno_dislike = String.valueOf(result.getData().getDisLike());
                                totalnoLikes = String.valueOf(result.getData().getLike());
                                Toast.makeText(getActivity(), "Song dis-Liked Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }

    }



}


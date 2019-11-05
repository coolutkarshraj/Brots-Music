package com.brots.music.application.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.R;
import com.brots.music.application.fragment.ExploreFragment;
import com.brots.music.application.model.response.LikeSongResponseModel;
import com.koushikdutta.async.future.FutureCallback;

public class RelativeLayoutTouchListener implements View.OnTouchListener {

    static final String logTag = "ActivitySwipeDetector";
    private Activity activity;
    static final int MIN_DISTANCE = 100;// TODO change this runtime based on screen resolution. for 1920x1080 is to small the 100 distance
    private float downX, downY, upX, upY;
    userOnlineInfo user;
    String c_songId,t_likes,t_disLikes;


    public RelativeLayoutTouchListener(Activity mainActivity) {
        activity = mainActivity;
        user = new userOnlineInfo();

    }

    public void onRightToLeftSwipe() {
        if(ExploreFragment.mediaPlayer.getCurrentPosition()/1000 >= 20){
            disLikeSong();
        } else if(ExploreFragment.mediaPlayer == null) {
            Config.exploreTabClicked = "1";
            Toast.makeText(activity, "You cannot dislike Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
            if (ExploreFragment.isLikedValue == 0 && ExploreFragment.IsDisLikeValue == 1) {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red)) ;
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            } else if (ExploreFragment.isLikedValue == 1 && ExploreFragment.IsDisLikeValue == 0) {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white)) ;
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            } else if (ExploreFragment.isLikedValue == 0 && ExploreFragment.IsDisLikeValue == 0) {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white)) ;
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            } else {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white)) ;
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }
        }
        else {
            Config.exploreTabClicked = "1";
            Toast.makeText(activity, "You cannot disLike Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
            if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==1){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red)) ;

                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==1 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }
        }
    }

    public void onLeftToRightSwipe() {
        Log.i(logTag, "LeftToRightSwipe!");
        if(ExploreFragment.mediaPlayer.getCurrentPosition()/1000 >= 20){
            likeSong();
        }
        else if(ExploreFragment.mediaPlayer == null){
            Config.exploreTabClicked = "1";
            Toast.makeText(activity, "You cannot Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
            if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==1){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==1 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }

        }else {
            Config.exploreTabClicked = "1";
            Toast.makeText(activity, "You cannot Like Song before hearing 20sec Song", Toast.LENGTH_SHORT).show();
            if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==1){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==1 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else if(ExploreFragment.isLikedValue ==0 && ExploreFragment.IsDisLikeValue==0){
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }else {
                ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                ExploreFragment.textLike.setText(ExploreFragment.totalnoLikes);
                ExploreFragment.textDislike.setText(ExploreFragment.totalno_dislike);
            }
        }


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
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                        Toast.makeText(activity, "Already have Liked Song", Toast.LENGTH_SHORT).show();
                                    }else if(result.getMessage().equals("Already have Dis-Liked Song")){
                                        ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                        ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red));
                                        Toast.makeText(activity, "Already have Dis-Liked Song", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                        ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }else
                                    {
                                        ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                                        ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                        ExploreFragment.textLike.setText(String.valueOf(result.getData().getLike()));
                                        ExploreFragment.textDislike.setText(String.valueOf(result.getData().getDisLike()));
                                        Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red));
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                    Toast.makeText(activity, "Already have Dislike Song", Toast.LENGTH_SHORT).show();
                                }else if(result.getMessage().equals("Already have like Song")){
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.green));
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                    Toast.makeText(activity, "Already have Like Song", Toast.LENGTH_SHORT).show();
                                }else {
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else
                                {
                                    ExploreFragment.S_ImageDislike.setColorFilter(ContextCompat.getColor(activity, R.color.red));
                                    ExploreFragment.S_ImageLike.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                                    ExploreFragment.textLike.setText(String.valueOf(result.getData().getLike()));
                                    ExploreFragment.textDislike.setText(String.valueOf(result.getData().getDisLike()));
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
            );
        }

    }

    public void onTopToBottomSwipe() {
//        Log.i(logTag, "onTopToBottomSwipe!");
//        Toast.makeText(activity, "onTopToBottomSwipe", Toast.LENGTH_SHORT).show();
    }

    public void onBottomToTopSwipe() {

        Toast.makeText(activity, "This Feature is under Maintainance we are comming soon", Toast.LENGTH_SHORT).show();
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                } else {
                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                return false; // no swipe horizontally and no swipe vertically
            }// case MotionEvent.ACTION_UP:
        }
        return false;
    }

}
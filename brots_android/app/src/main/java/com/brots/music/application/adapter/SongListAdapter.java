package com.brots.music.application.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Config;
import com.brots.music.application.activity.Profile.ListSongArtistProfileActivity;
import com.brots.music.application.fragment.ExploreArtist;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.responseData.ArtistGetSongDataModel;
import com.bumptech.glide.Glide;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    Context context;
    List<ArtistGetSongDataModel> item;
    PreferenceManager preferenceManager;
    String name, id, savedSongMemory, songUrl;
    String songUrl30Sce;
    public String ClickedPositionId ;
    public static String itemClicked ="";


    public SongListAdapter(Context context, List<ArtistGetSongDataModel> item) {
        this.context = context;
        this.item = item;
    }


    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exploreartistlist, viewGroup, false);
        Font font = new Font(context.getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) view);
        preferenceManager = new PreferenceManager(context);
        name = preferenceManager.getString(PreferenceManager.name);
        id = preferenceManager.getString(PreferenceManager.id);
        savedSongMemory = preferenceManager.getString(PreferenceManager.savedSongMemory);

        return new SongListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder viewHolder, final int i) {
        final ArtistGetSongDataModel songList = item.get(i);
        viewHolder.song.setText(songList.getSongName());
        viewHolder.By.setText(songList.getArtistName());
        Glide.with(context).load(songList.getCoverImage()).into(viewHolder.img);
        if(Config.recyclerPoisitioninArtistListClicked .equals(String.valueOf(i))){
            itemClicked = Config.recyclerPoisitioninArtistListClicked;
            viewHolder.play30SecSong.setBackgroundResource(R.drawable.thirtysec);
            if(String.valueOf(songList.getId()).equals(ClickedPositionId)){
                if(ExploreArtist.mediaPlayer.isPlaying()){
                    Toast.makeText(context, "Please wait while Song is Played", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Please wait while Song is Played", Toast.LENGTH_SHORT).show();
                }
            }
            if(String.valueOf(songList.getId()).equals(ClickedPositionId)){

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArtistGetSongDataModel artistGetSongDataModel = item.get(Integer.parseInt(Config.recyclerPoisitioninArtistListClicked));
                        songUrl30Sce = artistGetSongDataModel.getImageUrl();
                        if (ExploreArtist.mediaPlayer.isPlaying()) {
                           ExploreArtist.mediaPlayer.reset();
                           ExploreArtist.mediaPlayer.stop();
                           ExploreArtist.playSongByRecyclerViewClick(songUrl30Sce);

                        }
                        else {
                            ExploreArtist.playSongByRecyclerViewClick(songUrl30Sce);

                        }
                    }
                }, 3000);
            }else {

            }


        }else {
            try {
                if (ClickedPositionId.equals(String.valueOf(item.get(i).getId()))) {
                    viewHolder.play30SecSong.setBackgroundResource(R.drawable.thirtysec);
                } else {
                    viewHolder.play30SecSong.setBackgroundResource(R.drawable.thirtysecwhite);
                }

            }catch (Exception e){
                viewHolder.play30SecSong.setBackgroundResource(R.drawable.thirtysecwhite);
                e.printStackTrace();
            }
        }


        /* -----------------------------------------Recycler View Click song play --------------------------------------*/

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreArtist.Clickedpostion = i;
                ExploreArtist.mediaPlayer.stop();
                ExploreArtist.mediaPlayer.reset();
                //ListSongArtistProfileActivity.IsPlay=2131296598;
                ExploreArtist.playMusic.setVisibility(View.GONE);
                ExploreArtist.pauseMusic.setVisibility(View.VISIBLE);
                ExploreArtist.songName.setText(songList.getSongName());
                ExploreArtist.artistName.setText(songList.getArtistName());
                songUrl = songList.getFullSongUrl();
                ExploreArtist.songUrl = songUrl;
                Glide.with(context).load(songList.getCoverImage()).into(ExploreArtist.playerImage);
                ExploreArtist.playSongByRecyclerViewClick(songUrl);
                ExploreArtist.adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView song, By;
        Button play30SecSong,pause30;
        CircleImageView img;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.songname);
            By = (TextView) itemView.findViewById(R.id.songby);
            img = (CircleImageView) itemView.findViewById(R.id.profile);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);
            play30SecSong = (Button) itemView.findViewById(R.id.play30);


            /*-------------------------------------- play 30 second song------------------------------------------- */

            play30SecSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickedPositionId = String.valueOf(item.get(getAdapterPosition()).getId());
                    Config.recyclerPoisitioninArtistListClicked =  String.valueOf(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
    }
}

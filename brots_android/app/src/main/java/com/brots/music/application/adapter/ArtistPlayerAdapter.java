package com.brots.music.application.adapter;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.brots.music.application.R;
import com.brots.music.application.activity.Profile.ListSongArtistProfileActivity;
import com.brots.music.application.activity.termsAndCondition.ContactQuery;
import com.brots.music.application.fragment.ExploreArtist;
import com.brots.music.application.fragment.LibrarySong;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.responseData.ArtistGetSongDataModel;
import com.brots.music.application.model.responseData.LibrarySongDataModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.Utilities;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.brots.music.application.fragment.LibrarySong.Songurl;
import static com.brots.music.application.fragment.LibrarySong.adapter;

public class ArtistPlayerAdapter extends RecyclerView.Adapter<ArtistPlayerAdapter.ViewHolder> {

    Context context;
    public static List<ArtistGetSongDataModel> listitem;
    String songUrl;
    String songUrl30Sec;
    public String ClickedPositionId ;
    public static String itemClicked ="";
    String songUrl30Sce;


    public ArtistPlayerAdapter(Context context, List<ArtistGetSongDataModel> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artistsongplayerlist, viewGroup, false);
        Font font = new Font(context.getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) view);
        return new ArtistPlayerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final ArtistGetSongDataModel artistGetSongDataModel = listitem.get(i);
        viewHolder.SongName.setText(artistGetSongDataModel.getSongName());
        viewHolder.ArtistName.setText(artistGetSongDataModel.getArtistName());

        Glide.with(context).load(artistGetSongDataModel.getCoverImage()).into(viewHolder.Image);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListSongArtistProfileActivity.Clickedpostion = i;
                ListSongArtistProfileActivity.mediaPlayer.stop();
                ListSongArtistProfileActivity.mediaPlayer.reset();
                //ListSongArtistProfileActivity.IsPlay=2131296598;
                ListSongArtistProfileActivity.playMusic.setVisibility(View.GONE);
                ListSongArtistProfileActivity.pauseMusic.setVisibility(View.VISIBLE);
                ListSongArtistProfileActivity.songName.setText(artistGetSongDataModel.getSongName());
                ListSongArtistProfileActivity.artistName.setText(artistGetSongDataModel.getArtistName());
                songUrl = artistGetSongDataModel.getFullSongUrl();
                ListSongArtistProfileActivity.songUrl = songUrl;
                Glide.with(context).load(artistGetSongDataModel.getCoverImage()).into(ListSongArtistProfileActivity.playerImage);
                ListSongArtistProfileActivity.playSongByRecyclerViewClick(songUrl);
                ListSongArtistProfileActivity.adapter.notifyDataSetChanged();
            }
        });


        if(Config.recyclerPoisitioninArtistListClicked .equals(String.valueOf(i))){
            itemClicked = Config.recyclerPoisitioninArtistListClicked;
            viewHolder.PlaySong30.setBackgroundResource(R.drawable.thirtysec);
            if(String.valueOf(artistGetSongDataModel.getId()).equals(ClickedPositionId)){
                if(ListSongArtistProfileActivity.mediaPlayer.isPlaying()){
                    Toast.makeText(context, "Please wait while Song is Played", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Please wait while Song is Played", Toast.LENGTH_SHORT).show();
                }
            }
            if(ExploreArtist.mediaPlayer.isPlaying()){
                ExploreArtist.mediaPlayer.reset();
                ExploreArtist.mediaPlayer.stop();
            }else {
            }
            if(String.valueOf(artistGetSongDataModel.getId()).equals(ClickedPositionId)){

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArtistGetSongDataModel artistGetSongDataModel = listitem.get(Integer.parseInt(Config.recyclerPoisitioninArtistListClicked));
                        songUrl30Sce = artistGetSongDataModel.getImageUrl();
                        if (ListSongArtistProfileActivity.mediaPlayer.isPlaying()) {
                            ListSongArtistProfileActivity.mediaPlayer.reset();
                            ListSongArtistProfileActivity.mediaPlayer.stop();
                            ListSongArtistProfileActivity.playSongByRecyclerViewClick(songUrl30Sec);

                        }
                        else {
                            ListSongArtistProfileActivity.playSongByRecyclerViewClick(songUrl30Sce);

                        }
                    }
                }, 3000);
            }else {

            }


        }else {
            try {
                if (ClickedPositionId.equals(String.valueOf(listitem.get(i).getId()))) {
                    viewHolder.PlaySong30.setBackgroundResource(R.drawable.thirtysec);
                } else {
                    viewHolder.PlaySong30.setBackgroundResource(R.drawable.thirtysecwhite);
                }

            }catch (Exception e){
                viewHolder.PlaySong30.setBackgroundResource(R.drawable.thirtysecwhite);
                e.printStackTrace();
            }
        }


    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView SongName, ArtistName;
        Button PlaySong30;
        ImageView Image;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SongName = (TextView) itemView.findViewById(R.id.titleHead);
            ArtistName = (TextView) itemView.findViewById(R.id.titlesub);
            Image = (ImageView) itemView.findViewById(R.id.imageshow);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);
            PlaySong30 = (Button) itemView.findViewById(R.id.play30);
            PlaySong30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickedPositionId = String.valueOf(listitem.get(getAdapterPosition()).getId());
                    Config.recyclerPoisitioninArtistListClicked =  String.valueOf(getAdapterPosition());
                    notifyDataSetChanged();

                }
            });
        }

    }
}

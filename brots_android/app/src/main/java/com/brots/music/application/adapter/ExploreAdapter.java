package com.brots.music.application.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.responseData.SongDataModel;
import com.brots.music.application.util.InsetsPercentRelativeLayout;
import com.bumptech.glide.Glide;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {
    Activity context;
    List<SongDataModel> listitem;
    PreferenceManager sharedPreferences;
    String id, name;
    int i;
    public static String Issaved , Memory = "";
    String savedSongMemory, InstaMixMemory;
    ExploreAdapter adapter;


    public ExploreAdapter(Activity context, List<SongDataModel> item) {
        this.context = context;
        this.listitem = item;
    }

    @NonNull
    @Override
    public ExploreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explorecard, parent, false);
        Font font = new Font(context.getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) view);

        sharedPreferences = new PreferenceManager(context);
        name = sharedPreferences.getString(PreferenceManager.name);
        id = sharedPreferences.getString(PreferenceManager.id);
        savedSongMemory = sharedPreferences.getString(PreferenceManager.savedSongMemory);
        InstaMixMemory = sharedPreferences.getString(PreferenceManager.InstaMixMemory);
        return new ExploreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExploreAdapter.ViewHolder holder, final int position) {
        final SongDataModel songData = listitem.get(position);
        holder.head.setText(songData.getSongName());
        holder.sub.setText(songData.getArtistName());
        holder.Textlike.setText(songData.getLike());
        holder.Textdislike.setText(songData.getDisLike());
        Glide.with(context).load(songData.getCoverImage()).into(holder.img);
        if (Issaved.equals("0")) {
            Glide.with(context).load(R.drawable.save).into(holder.save);
        } else {
            Glide.with(context).load(R.drawable.like).into(holder.save);

        }




        holder.Imgpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
    @Override
    public int getItemCount() {
        return listitem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView head;
        TextView sub;
        RelativeLayout widget31;
        LinearLayout linearLayout;
        ImageView Imgpoint, save, Imgdislike, Imglike, Share;
        TextView Textdislike, Textpoint, Textlike;
        InsetsPercentRelativeLayout libshape;
        public ViewHolder(View itemView) {
            super(itemView);
            head = (TextView) itemView.findViewById(R.id.titleHead);
            sub = (TextView) itemView.findViewById(R.id.titlesub);
            img = (CircleImageView) itemView.findViewById(R.id.imageshow);
            Imgdislike = (ImageView) itemView.findViewById(R.id.imgdislike);
            Imgpoint = (ImageView) itemView.findViewById(R.id.imgpoint);
            widget31 = (RelativeLayout) itemView.findViewById(R.id.widget31);
            save = (ImageView) itemView.findViewById(R.id.save);
            Share = (ImageView) itemView.findViewById(R.id.share);
            Imglike = (ImageView) itemView.findViewById(R.id.imglike);
            Textlike = (TextView) itemView.findViewById(R.id.txtlike);


        }

    }



}


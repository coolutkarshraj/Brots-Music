package com.brots.music.application.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brots.music.application.R;
import com.brots.music.application.model.response.AtristProfileWithTop3SongsResponseModel;
import com.brots.music.application.model.responseData.Top3SongDataModel;
import com.brots.music.application.util.Font;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Top3SongAdapter extends RecyclerView.Adapter<Top3SongAdapter.ViewHolder> {

    List<Top3SongDataModel>  item;
    Context context;


    public Top3SongAdapter(Context context,List<Top3SongDataModel> song) {
        this.context = context;
        this.item = song;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artistsonglist,viewGroup,false);
        Font font=new Font(context.getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)view);
        return new Top3SongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.song.setText(item.get(i).getSongName());
        viewHolder.By.setText(item.get(i).getArtistName());
        viewHolder.Like.setText(String.valueOf(item.get(i).getLike()));
        viewHolder.Dislike.setText(String.valueOf(item.get(i).getDisLike()));
        Glide.with(context).load(item.get(i).getCoverImage()).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView song,By;
        TextView Like,Dislike,Point;
        CircleImageView img;

        ImageView Cross,imgdislike,imglike,imgpoint;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song=(TextView)itemView.findViewById(R.id.songname);
            By=(TextView)itemView.findViewById(R.id.songby);
            img=(CircleImageView) itemView.findViewById(R.id.profile);
            Cross=(ImageView)itemView.findViewById(R.id.cross);
            imgdislike=(ImageView)itemView.findViewById(R.id.imgdislike);
            imglike=(ImageView)itemView.findViewById(R.id.imglike);
            imgpoint=(ImageView)itemView.findViewById(R.id.imgpoint);
            Like=(TextView)itemView.findViewById(R.id.like);
            Dislike=(TextView)itemView.findViewById(R.id.dislike);
            Point=(TextView)itemView.findViewById(R.id.point);

        }}
}

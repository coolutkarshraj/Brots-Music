package com.brots.music.application.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.activity.notifications.SendnotificationActivity;
import com.brots.music.application.fragment.ExploreFragment;
import com.brots.music.application.fragment.LibrarySong;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.model.response.RemoveSongResponseModel;
import com.brots.music.application.model.responseData.LibrarySongDataModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.NewProgressBar;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.brots.music.application.Config.Clickedpostion;
import static com.brots.music.application.fragment.LibrarySong.Songurl;
import static com.brots.music.application.fragment.LibrarySong.adapter;

public class RecyclerNewAdapter extends RecyclerView.Adapter<RecyclerNewAdapter.ViewHolder>
{
    Activity context;
    public static List<LibrarySongDataModel> listitem;
    PreferenceManager preferenceManager;
    String id,name,savedSongMemory;
    Dialog dialog;
    int i;
     String songid;
     String singleSavedMemory;
    public static String currentSongPlayId ;
    public RecyclerNewAdapter(FragmentActivity activity, List<LibrarySongDataModel> item) {

        this.context = activity;
        this.listitem = item;

    }

    @NonNull
    @Override
    public RecyclerNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.library_songlistnew,parent,false);
        Font font=new Font(context.getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)view);
        preferenceManager = new PreferenceManager(context);
        name = preferenceManager.getString(PreferenceManager.name);
        id = preferenceManager.getString(PreferenceManager.id);
        savedSongMemory = preferenceManager.getString(PreferenceManager.savedSongMemory);
        return new RecyclerNewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerNewAdapter.ViewHolder holder, final int position) {
        i= position;
        final LibrarySongDataModel librarySongDataModel=listitem.get(position);
        holder.head.setText(librarySongDataModel.getSongName());
        holder.sub.setText(librarySongDataModel.getArtistName());
        Glide.with(context).load(librarySongDataModel.getBannerImage()).into(holder.img);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LibrarySong.Clickedpostion=position;
                LibrarySong.mediaPlayer.stop();
                LibrarySong.mediaPlayer.reset();
                LibrarySong.IsPlay=2131296598;
                LibrarySong.Play.setVisibility(View.GONE);
                LibrarySong.Pause.setVisibility(View.VISIBLE);
                LibrarySong.songName.setText(librarySongDataModel.getSongName());
                LibrarySong.songArtistName.setText(librarySongDataModel.getArtistName());
                LibrarySong.Songurl=librarySongDataModel.getFullSongImageUrl();
                Glide.with(context).load(librarySongDataModel.getBannerImage()).into(LibrarySong.Image);
                currentSongPlayId = String.valueOf(librarySongDataModel.getSongId());
                LibrarySong.playSongByRecyclerViewClick(Songurl);
                adapter.notifyDataSetChanged();

            }
        });


    }

    /*-------------------------------------------- Delete song dialog-----------------------------------------------*/
    public void Deletedialog(final int position) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.delete_song_dialog);
        dialog.setTitle("");
        
        final TextView Head = (TextView) dialog.findViewById(R.id.delhead);
        final TextView Sub = (TextView) dialog.findViewById(R.id.txt_name);
        final TextView Yes = (TextView) dialog.findViewById(R.id.yes);
        final TextView No = (TextView) dialog.findViewById(R.id.no);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);

        Typeface face = Typeface.createFromAsset(context.getAssets(), "Proxima_Nova_Thin.otf");
        Head.setTypeface(face);
        Sub.setTypeface(face);
        Yes.setTypeface(face);
        No.setTypeface(face);
        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RemoveSong();


            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    /* Remove Song */

    private void RemoveSong() {

        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(context).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);

        int userId;
        int totalSavedMemory = 0;
        try{
            userId = obj.getData().getId();
            totalSavedMemory = obj.getData().getSavedSongMemory();
        }catch (Exception e){
            userId = Integer.parseInt(PreferenceManager.getInstance(context).getString(PreferenceManager.id));
            totalSavedMemory = Integer.parseInt(PreferenceManager.getInstance(context).getString(PreferenceManager.instaMixMemory));
            e.printStackTrace();

        }
        Apicaller.removeSong(context, Config.Url.removeSong, userId, totalSavedMemory, singleSavedMemory, songid, new
                FutureCallback<RemoveSongResponseModel>() {
                    @Override
                    public void onCompleted(Exception e, RemoveSongResponseModel result) {
                        if (e != null) {
                            return;
                        }

                        remove();

                    }
                });

    }

    private void remove() {

        listitem.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeRemoved(i,listitem.size());
    }


    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView head;
        TextView sub;
        LinearLayout linearLayout;
        ImageView Imgdislike,Imgpoint,save,Imglike;
        TextView Textdislike,Textpoint,Textlike;
        ImageView Delete;

        public ViewHolder(View itemView) {
            super(itemView);
            head = (TextView)itemView.findViewById(R.id.titleHead);
            sub=(TextView)itemView.findViewById(R.id.titlesub);
            img=(CircleImageView) itemView.findViewById(R.id.imageshow);
            Imgdislike=(ImageView)itemView.findViewById(R.id.imgdislike);
            Imgpoint=(ImageView)itemView.findViewById(R.id.imgpoint);
            save=(ImageView)itemView.findViewById(R.id.save);
            Imglike=(ImageView)itemView.findViewById(R.id.imglike);
            Textlike=(TextView)itemView.findViewById(R.id.txtlike);
            Textpoint=(TextView)itemView.findViewById(R.id.textpoint);
            Textdislike=(TextView)itemView.findViewById(R.id.textdislike);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.layout);
            Delete = (ImageView)itemView.findViewById(R.id.deletesong);
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    songid = String.valueOf(listitem.get(getAdapterPosition()).getSongId());
                    singleSavedMemory = String.valueOf(listitem.get(getAdapterPosition()).getMemory());
                    if(songid.equals(currentSongPlayId)){
                        Toast.makeText(context, "You cannot delete Song  While it is Playing or pause ", Toast.LENGTH_SHORT).show();
                    }else {
                        Deletedialog(getAdapterPosition());
                    }

                }
            });


        }
        
        
    }
}

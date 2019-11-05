package com.brots.music.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brots.music.application.util.Font;
import com.brots.music.application.model.Notification;
import com.brots.music.application.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<Notification> item;

    public NotificationAdapter(Context context, List<Notification> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull



    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notificationcard,viewGroup,false);
        Font font=new Font(context.getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)view);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder viewHolder, int i) {
        Notification songList=item.get(i);
        viewHolder.Name.setText(songList.getName());
        viewHolder.Mess.setText(songList.getMessage());
        viewHolder.Time.setText(songList.getTime());
        viewHolder.img.setBackgroundResource(songList.getImg());

viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
    }

    public void setFilter(List<Notification> newlist){
        item=new ArrayList<>();
        item.addAll(newlist);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Mess,Time;
        CircleImageView img;

        ImageView Cross;

        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);
            Mess=(TextView)itemView.findViewById(R.id.message);
            Time=(TextView)itemView.findViewById(R.id.time);
            img=(CircleImageView) itemView.findViewById(R.id.profile);

            linearLayout=(LinearLayout)itemView.findViewById(R.id.noti);
        }
    }
}


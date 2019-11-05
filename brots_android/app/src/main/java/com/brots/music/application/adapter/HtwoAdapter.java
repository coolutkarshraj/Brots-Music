package com.brots.music.application.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brots.music.application.Config;
import com.brots.music.application.model.request.SongSubCatModel;
import com.brots.music.application.R;
import com.brots.music.application.interfaces.AdapterInterface;
import java.util.List;

public class HtwoAdapter extends RecyclerView.Adapter<HtwoAdapter.ViewHolder> {


    public static List<SongSubCatModel> list;
    Context context;
    AdapterInterface ad_interface;
    public static int rowindex2;
    public static String subcategoryid = "";
    public static String selectedPosition;


    public HtwoAdapter(List<SongSubCatModel> list, AdapterInterface ad_interface, Context context) {
        this.list = list;
        this.context = context;
        this.ad_interface = ad_interface;
        try{
            selectedPosition = list.get(Config.subcatPosition).getid();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final SongSubCatModel mtwo = list.get(i);

        viewHolder.textView.setText(mtwo.getName());

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowindex2 = i;
                selectedPosition = list.get(rowindex2).getid();
                ad_interface.clickn(i, list.get(i).getid(), list.get(i).getName());
                notifyDataSetChanged();
            }
        });
        if (selectedPosition.equals(mtwo.getid())) {
            viewHolder.textView.setTextColor(Color.parseColor("#06a8f3"));
        } else {
            viewHolder.textView.setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public static LinearLayout linearLayout2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.texttwo);
            linearLayout2 = (LinearLayout) itemView.findViewById(R.id.reltwo);
        }
    }
}

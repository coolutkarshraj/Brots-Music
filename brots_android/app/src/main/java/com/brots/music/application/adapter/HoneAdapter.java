package com.brots.music.application.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.brots.music.application.model.request.SongCategoriesModel;
import com.brots.music.application.R;
import com.brots.music.application.interfaces.AdapterInterface;

import java.util.List;

public class HoneAdapter extends RecyclerView.Adapter<HoneAdapter.ViewHolder> {

    List<SongCategoriesModel> list;
    Context context;
    public static int row_index;
    public static int a;
    public static int c;
    AdapterInterface ad_interface;


    public HoneAdapter(List<SongCategoriesModel> list, Context context) {
        this.list = list;


        this.context = context;
    }

    public HoneAdapter(List<SongCategoriesModel> list, AdapterInterface ad_interface, Context context) {

        this.list = list;
        this.ad_interface = ad_interface;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final SongCategoriesModel mtwo = list.get(i);
        viewHolder.textView1.setText(mtwo.getName());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                row_index = i;
                ad_interface.click(i, list.get(i).getid(), list.get(i).getName());
                notifyDataSetChanged();
            }
        });

        if (row_index == i) {
            viewHolder.textView1.setTextColor(Color.parseColor("#06a8f3"));
            viewHolder.bar.setTextColor(Color.parseColor("#06a8f3"));
            viewHolder.bar.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textView1.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.bar.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView bar;


        LinearLayout linearLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.textone);
            bar = (TextView) itemView.findViewById(R.id.line);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.rel);

        }

    }

}
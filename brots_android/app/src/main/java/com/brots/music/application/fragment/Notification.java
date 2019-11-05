package com.brots.music.application.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brots.music.application.adapter.NotificationAdapter;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;

import java.util.ArrayList;
import java.util.List;

public class Notification  extends Fragment {
    RecyclerView recyclerView;
    NotificationAdapter adapter;

    SearchView searchVieww;

    List<com.brots.music.application.model.Notification> item;
    public Notification(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View v=inflater.inflate(R.layout.notificationartis,container,false); //user chat
        View v=inflater.inflate(R.layout.artistnotifiaction,container,false);
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup)v);
        item=new ArrayList<>();
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclernotification);
        searchVieww=(SearchView)v.findViewById(R.id.serach);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        item.add(new com.brots.music.application.model.Notification("Smanth","Nice Song","1m ago",R.drawable.img1));
        item.add(new com.brots.music.application.model.Notification("Flipoo","Next Song","10m ago",R.drawable.img2));
        item.add(new com.brots.music.application.model.Notification("Anna","Hello , I hope You are well. what are you doing now....","15m ago",R.drawable.img3));
        item.add(new com.brots.music.application.model.Notification("Issac","Up Comming song","17m ago",R.drawable.img4));
        item.add(new com.brots.music.application.model.Notification("Julie","Hello , I hope You are well. what are you doing now....","25m ago",R.drawable.img5));
        item.add(new com.brots.music.application.model.Notification("Calvin","Hello , I hope You are well. what are you doing now....","40m ago",R.drawable.img7));
        item.add(new com.brots.music.application.model.Notification("Bonobo-Black sands","Hello , I hope You are well. what are you doing now....","55m ago",R.drawable.img7));
        item.add(new com.brots.music.application.model.Notification("Bonobo-Black sands","Hello , I hope You are well. what are you doing now....","58m ago",R.drawable.img8));
        item.add(new com.brots.music.application.model.Notification("Bonobo-Black sands","Hello , I hope You are well. what are you doing now....","1h ago",R.drawable.img9));
        item.add(new com.brots.music.application.model.Notification("Bonobo-Black sands","Hello , I hope You are well. what are you doing now....","2h ago",R.drawable.img6));
        adapter=new NotificationAdapter(getContext(),item);
        recyclerView.setAdapter(adapter);


        searchVieww.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                s=s.toLowerCase();
                List<com.brots.music.application.model.Notification> newlist=new ArrayList<>();
                for(com.brots.music.application.model.Notification notification :item)
                {
                    String name= notification.getName().toLowerCase();
                    if(name.contains(s))
                        newlist.add(notification);
                }

                adapter.setFilter(newlist);
                return true;
            }
        });

        return v;
    }
}

package com.brots.music.application.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brots.music.application.adapter.RecyclerNewAdapter;
import com.brots.music.application.model.response.UserLibraryModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;


import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment  {


    ViewPager viewPager;
    TabLayout tabLayout;
    LibraryAdapter libraryAdapter;
    public static SearchView searchView;
    List<UserLibraryModel> item=new ArrayList<>();
    RecyclerNewAdapter adapter;

    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_library, container, false);
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);

        libraryAdapter=new LibraryAdapter(getActivity().getSupportFragmentManager());
        viewPager=(ViewPager)v.findViewById(R.id.container);
        setpage(viewPager);

        tabLayout=(TabLayout)v.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        searchView=(SearchView)v.findViewById(R.id.searchview);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "isVisibleToUser", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }
    private  void  setpage(ViewPager v1)
    {


        LibraryAdapter ob=new LibraryAdapter(getActivity().getSupportFragmentManager());
        ob.addFragment(new LibraryInstantMix(),"Instant Mix");
        ob.addFragment(new LibrarySong(),"Songs");
        //  ob.addFragment(new ArtistforInstantMix(),"Artist");
        v1.setAdapter(ob);
        Log.e("Log",""+ob);
        // Toast.makeText(getActivity(), "User", Toast.LENGTH_SHORT).show();
    }
}
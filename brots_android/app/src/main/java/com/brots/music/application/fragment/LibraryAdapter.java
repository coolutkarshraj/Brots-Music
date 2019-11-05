package com.brots.music.application.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mfragment=new ArrayList<>();
    List<String> mlistFragment=new ArrayList<>();

    public LibraryAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment f1,String s1)
    {
        mfragment.add(f1);
        mlistFragment.add(s1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mlistFragment.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mfragment.get(i);
    }

    @Override
    public int getCount() {
        return mfragment.size();
    }
}

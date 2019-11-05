package com.brots.music.application.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentStatePagerAdapter {


    List<Fragment> mfragment = new ArrayList<>();
    List<String> mlistFragment = new ArrayList<>();

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    public void addfragment(Fragment f1, String s1) {
        mfragment.add(f1);
        mlistFragment.add(s1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mlistFragment.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragment.get(position);
    }

    @Override
    public int getCount() {
        return mfragment.size();
    }

}
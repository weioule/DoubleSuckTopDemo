package com.example.doublesucktopdemo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by weioule
 * on 2019/6/26.
 */
public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mlist;


    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

}

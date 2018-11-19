package com.example.kicheol.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {



        switch (position) {
            case 0:
                First_Tap tab1 = new First_Tap();
                return tab1;
            case 1:
                frag2 tab2 = new frag2();
                return tab2;
            case 2:
                frag3 tab3 = new frag3();
                return tab3;
            case 3:
                frag4 tab4 = new frag4();
                return tab4;
            case 4:
                frag5 tab5 = new frag5();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
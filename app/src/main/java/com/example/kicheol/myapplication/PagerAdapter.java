package com.example.kicheol.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String id;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, String id) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();  //인자 넘겨주기
        bundle.putString("id", id);


        switch (position) {
            case 0:
                First_Tap tab1 = new First_Tap();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                frag2 tab2 = new frag2();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                Third_Tab tab3 = new Third_Tab();
                tab3.setArguments(bundle);
                return tab3;
            case 3:
                frag4 tab4 = new frag4();
                tab4.setArguments(bundle);
                return tab4;
            case 4:
                frag5 tab5 = new frag5();
                tab5.setArguments(bundle);
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
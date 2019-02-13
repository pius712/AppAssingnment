package com.example.pius712.appassingnment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.pius712.appassingnment.Activity.Tab_fragment1;
import com.example.pius712.appassingnment.Activity.Tab_fragment2;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTab) {
        super(fm);
        this.mNumOfTabs = NumOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Tab_fragment1 tab1= new Tab_fragment1();
                return tab1;
            case 1:
                Tab_fragment2 tab2 = new Tab_fragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

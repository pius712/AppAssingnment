package com.example.pius712.appassingnment.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pius712.appassingnment.PagerAdapter;
import com.example.pius712.appassingnment.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;


public class AccountFragment extends Fragment {

    ViewPager mViewPager;
    private NavigationTabStrip mTopNavigationTabStrip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        mViewPager = view.findViewById(R.id.container);
        mTopNavigationTabStrip = view.findViewById(R.id.nts_top);

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(), 2);
        mViewPager.setAdapter(pagerAdapter);
        mTopNavigationTabStrip.setViewPager(mViewPager);
        return view;
    }
}

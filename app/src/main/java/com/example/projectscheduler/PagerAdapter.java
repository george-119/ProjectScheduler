package com.example.projectscheduler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                ProfileFragment tab2 = new ProfileFragment();
                return tab2;
            case 2:
                GuideFragment tab3 = new GuideFragment();
                return tab3;
            case 3:
                ScheduleFragment tab4 = new ScheduleFragment();
                return tab4;
            case 4:
                ProgressviewFragment tab5 = new ProgressviewFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}

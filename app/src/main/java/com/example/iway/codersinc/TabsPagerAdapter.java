package com.example.iway.codersinc;

/**
 * Created by IWAY on 25-09-2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.iway.codersinc.Contests.CollegeContests;
import com.example.iway.codersinc.Contests.CurrentContests;
import com.example.iway.codersinc.Contests.UpcommingContests;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new CurrentContests();
            case 1:
                return new UpcommingContests();
            case 2:
                return new CollegeContests();
        }

        return new CurrentContests();
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
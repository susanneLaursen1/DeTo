package com.example.deto;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1();
            case 1:
                return new tab2();
            case 2:
                return new tab3();
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

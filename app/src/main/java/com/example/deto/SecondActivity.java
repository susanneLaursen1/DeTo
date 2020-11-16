package com.example.deto;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;



public class SecondActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;

    private tab1 tab1;
    private tab2 tab2;
    private tab3 tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        viewpager = findViewById(R.id.view_pager);
        tablayout = findViewById(R.id.tablay_out);

        tab1 = new tab1();
        tab2 = new tab2();
        tab3 = new tab3();

        tablayout.setupWithViewPager(viewpager);
        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewpagerAdapter.addFragment(tab1, "Historik");
        viewpagerAdapter.addFragment(tab2, "Grafisk Oversigt");
        viewpagerAdapter.addFragment(tab3, "Kalibering");

        viewpager.setAdapter(viewpagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}


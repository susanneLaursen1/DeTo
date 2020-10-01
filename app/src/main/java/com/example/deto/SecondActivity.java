package com.example.deto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SecondActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TabItem tab1, tab2, tab3;
    public PageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tablayout = (TabLayout)findViewById(R.id.tablayout);
        tab1 = (TabItem)findViewById(R.id.Tab1);
        tab2 = (TabItem)findViewById(R.id.Tab2);
        tab2 = (TabItem)findViewById(R.id.Tab3);
        viewpager = (ViewPager)findViewById(R.id.Viewpager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager(),tablayout.getTabCount());
        viewpager.setAdapter(pagerAdapter);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }
                    @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override //sætter menuen til at åbne den rigtige fane af alarm, graf eller vedligehold
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item2:
                Intent i = new Intent(SecondActivity.this, Alarm.class);
            startActivity(i);
            return true;
            case R.id.item3:
                Intent ii = new Intent(SecondActivity.this, GrafiskOversigt.class);
                startActivity(ii);
                return true;
            case R.id.item4:
                Intent iii = new Intent(SecondActivity.this, KalibreringOgVedligehold.class);
                startActivity(iii);
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}
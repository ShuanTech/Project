package com.shuan.Project.employer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.CacheFragmentStatePagerAdapter;
import com.shuan.Project.fragment.ActionFragment;
import com.shuan.Project.fragment.RejectedListFragment;
import com.shuan.Project.fragment.SelectedListFragment;

public class ShortListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_list);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(mApp.getPreference().getString("title", ""));

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public class ViewPagerAdapter extends CacheFragmentStatePagerAdapter {

        String[] tabs = new String[]{"Action", "Selected List", "Rejected List"};


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment createItem(int position) {
            Fragment f = null;

            switch (position) {
                case 0:
                    f = new ActionFragment();
                    break;
                case 1:
                    f = new SelectedListFragment();
                    break;
                case 2:
                    f = new RejectedListFragment();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position].toString();
        }
    }
}

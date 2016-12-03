package com.shuan.Project.search;

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
import com.shuan.Project.fragment.CompaniesFragment;
import com.shuan.Project.fragment.JobsFragment;

public class EmployeeSearchResultActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else {
            setTheme(R.style.Senior);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search_result2);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Search Result");

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

        String[] tabs = new String[]{"JObs", "Companies"};


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment createItem(int position) {
            Fragment f = null;
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    bundle.putString("loc", getIntent().getStringExtra("loc"));
                    f = new JobsFragment();
                    f.setArguments(bundle);
                    break;
                case 1:
                    bundle.putString("loc", getIntent().getStringExtra("loc"));
                    f = new CompaniesFragment();
                    f.setArguments(bundle);
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

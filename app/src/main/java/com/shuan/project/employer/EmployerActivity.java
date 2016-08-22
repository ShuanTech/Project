package com.shuan.project.employer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.CircleImageView;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.about.About;
import com.shuan.project.about.Help;
import com.shuan.project.fragment.EmployerAboutFragment;
import com.shuan.project.fragment.EmployerHome;
import com.shuan.project.fragment.FollowerFragment;
import com.shuan.project.fragment.FollowingFragment;
import com.shuan.project.launcher.LoginActivity;
import com.shuan.project.profile.JuniorProfile;
import com.shuan.project.search.SearchActivity;

public class EmployerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private FloatingActionButton button;
    private RelativeLayout root;
    private boolean exit = false;
    private AlertDialog.Builder builder;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RelativeLayout container;
    private Helper helper = new Helper();
    private static final String PREFERENCES = "pref";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;

    private TextView usrName;
    private CircleImageView proPic;
    private DisplayImageOptions options;
    private RelativeLayout lay1;
    private TextView follower, following;
    private TextView alertCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();

        if (mApp.getPreference().getString(Common.Version, "").equalsIgnoreCase("true")) {
            builder = new AlertDialog.Builder(EmployerActivity.this)
                    .setTitle("Update")
                    .setMessage("New Version of UdyoMitra-Beta Available.Do you want Update?");
            builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent("android.intent.action.VIEW")
                            .setData(Uri.parse("market://details?id=com.shuan.project"));
                    startActivity(in);
                    dialog.cancel();
                }
            }).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Home");
        root = (RelativeLayout) findViewById(R.id.root);


        lay1 = (RelativeLayout) findViewById(R.id.notification);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }


        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        View header = mNavigationView.inflateHeaderView(R.layout.header);
        container = (RelativeLayout) findViewById(R.id.container);

        usrName = (TextView) header.findViewById(R.id.usr_name);
        proPic = (CircleImageView) header.findViewById(R.id.profile);

        setImage(mApp.getPreference().getString(Common.PROPIC, ""), proPic);

        usrName.setText(mApp.getPreference().getString(Common.FULLNAME, ""));
        follower = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.follower));
        following = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.following));

        initializeCount();

        display(0);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home:
                        toolbar.setTitle("Home");
                        mDrawerLayout.closeDrawers();
                        display(0);
                        return true;
                    case R.id.follower:
                        toolbar.setTitle("Follower");
                        mDrawerLayout.closeDrawers();
                        display(1);
                        return true;
                    case R.id.following:
                        toolbar.setTitle("Following");
                        mDrawerLayout.closeDrawers();
                        display(2);
                        return true;
                    case R.id.abt:
                        toolbar.setTitle("About Company");
                        mDrawerLayout.closeDrawers();
                        display(5);
                        return true;
                    case R.id.logout:
                        mApp.getPreference().edit().putBoolean(Common.Login, false).commit();
                        mApp.getPreference().edit().putBoolean(Common.USRINFO, false).commit();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return true;
                    case R.id.about:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), About.class));
                        return true;
                    case R.id.help:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), Help.class));
                        return true;
                    default:
                        return true;
                }


            }
        });


        button = (FloatingActionButton) findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JobPostActivity.class));
            }
        });

        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JuniorProfile.class));
            }
        });
    }

    private void display(int i) {
        Fragment f = null;
        Bundle bundle = new Bundle();
        switch (i) {
            case 0:
                bundle.putString("u_id", mApp.getPreference().getString(Common.u_id, ""));
                f = new EmployerHome();
                f.setArguments(bundle);
                break;
            case 1:
                bundle.putString("u_id", mApp.getPreference().getString(Common.u_id, ""));
                f = new FollowerFragment();
                f.setArguments(bundle);
                break;
            case 2:
                f = new FollowingFragment();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                bundle.putString("u_id", mApp.getPreference().getString(Common.u_id, ""));
                f = new EmployerAboutFragment();
                f.setArguments(bundle);
                break;
            case 6:
                
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
    }


    private void setImage(String path, CircleImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.user_white)
                .showImageForEmptyUri(R.drawable.user_white)
                .showImageOnFail(R.drawable.user_white)
                .build();

        ImageLoader.getInstance().displayImage(path, img, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
    }

    private void initializeCount() {
        follower.setGravity(Gravity.CENTER_VERTICAL);
        follower.setTypeface(helper.droid(getApplicationContext()));
        follower.setTextColor(getResources().getColor(R.color.junAccent));
        follower.setText(mApp.getPreference().getString(Common.FOLLOWER, ""));


        following.setGravity(Gravity.CENTER_VERTICAL);
        following.setTypeface(helper.droid(getApplicationContext()));
        following.setTextColor(getResources().getColor(R.color.junAccent));
        following.setText(mApp.getPreference().getString(Common.FOLLOWING, ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employer_main, menu);
        MenuItem menuItem = menu.findItem(R.id.notify);
        MenuItemCompat.setActionView(menuItem, R.layout.toolbar_counter);
        lay1 = (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        alertCount= (TextView) lay1.findViewById(R.id.counter1);
        int cnt=0;
        if(cnt==0){alertCount.setVisibility(View.GONE);}else{alertCount.setText(""+cnt);}
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.drawer:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }
}

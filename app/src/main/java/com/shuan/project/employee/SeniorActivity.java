package com.shuan.project.employee;

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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
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
import com.shuan.project.fragment.ConnectionFragment;
import com.shuan.project.fragment.EmployerHome;
import com.shuan.project.launcher.LoginActivity;
import com.shuan.project.profile.JuniorProfile;
import com.shuan.project.resume.ExpResumeGenerate;
import com.shuan.project.resume.JuniorResumeGenerate;
import com.shuan.project.resume.ResumeEditActivity;
import com.shuan.project.search.SearchActivity;

import java.util.HashMap;

public class SeniorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private RelativeLayout root;
    private Snackbar snackbar;
    private Button gen;
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

    private TextView connect;
    private RelativeLayout lay1;
    private HashMap<String, String> junData;
    private TextView usrName;
    private CircleImageView proPic;
    private DisplayImageOptions options;
    private TextView alertCount, profileStrength, following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mApp = (Common) getApplicationContext();

        if (mApp.getPreference().getString(Common.Version, "").equalsIgnoreCase("true")) {
            builder = new AlertDialog.Builder(SeniorActivity.this)
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
        setContentView(R.layout.activity_senior);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Home");
        root = (RelativeLayout) findViewById(R.id.root);

        lay1 = (RelativeLayout) findViewById(R.id.notification);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        setUpNavDrawer();

        if (mApp.getPreference().getBoolean(Common.QUALIFICATION, false) == false) {

            snackbar.make(root, "Complete Your Qualification", Snackbar.LENGTH_LONG)
                    .setAction("DO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), JuniorResumeGenerate.class));
                        }
                    }).show();
        }

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        View header = mNavigationView.inflateHeaderView(R.layout.header);
        container = (RelativeLayout) findViewById(R.id.container);

        usrName = (TextView) header.findViewById(R.id.usr_name);
        proPic = (CircleImageView) header.findViewById(R.id.profile);

        setImage(mApp.getPreference().getString(Common.PROPIC, ""), proPic);

        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JuniorProfile.class));
            }
        });

        connect = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.connect));
        profileStrength = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.strength));
        following = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.following));

        usrName.setText(mApp.getPreference().getString(Common.FULLNAME, ""));

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
                    case R.id.strength:
                        startActivity(new Intent(getApplicationContext(), ResumeEditActivity.class));
                        return true;
                    case R.id.connect:
                        toolbar.setTitle("Connections");
                        mDrawerLayout.closeDrawers();
                        display(2);
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
                break;
            case 2:
                bundle.putString("u_id", mApp.getPreference().getString(Common.u_id, ""));
                f = new ConnectionFragment();
                f.setArguments(bundle);
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
        connect.setGravity(Gravity.CENTER_VERTICAL);
        connect.setTypeface(helper.droid(getApplicationContext()));
        connect.setTextColor(getResources().getColor(R.color.senAccent));
        connect.setText(mApp.getPreference().getString(Common.CONNECTION, ""));

        following.setGravity(Gravity.CENTER_VERTICAL);
        following.setTypeface(helper.droid(getApplicationContext()));
        following.setTextColor(getResources().getColor(R.color.senAccent));
        following.setText(mApp.getPreference().getString(Common.FOLLOWING, ""));

        profileStrength.setGravity(Gravity.CENTER_VERTICAL);
        profileStrength.setTypeface(helper.droid(getApplicationContext()));
        profileStrength.setTextColor(getResources().getColor(R.color.senAccent));

        profileStrength.setText(calculateStrength());


    }

    private String calculateStrength() {
        String s = null;

        boolean step1 = mApp.getPreference().getBoolean(Common.PROFILESUMMARY, false);
        boolean step2 = mApp.getPreference().getBoolean(Common.WORKEXPERIENCE, false);
        boolean step3 = mApp.getPreference().getBoolean(Common.WORKINFO, false);
        boolean step4 = mApp.getPreference().getBoolean(Common.SSLC, false);
        boolean step5 = mApp.getPreference().getBoolean(Common.HSC, false);
        boolean step6 = mApp.getPreference().getBoolean(Common.QUALIFICATION, false);
        boolean step7 = mApp.getPreference().getBoolean(Common.SKILL, false);
        boolean step8 = mApp.getPreference().getBoolean(Common.CERITIFY, false);
        boolean step9 = mApp.getPreference().getBoolean(Common.PERSONALINFO, false);

        if (step1 && step2 && step3 && step4 && step5 && step6 && step7 && step8 && step9) {
            s = "95";
        } else if (step1 || step2 || step3 || step4 || step5 || step6 || step7 || step8 || step9) {
            s = "34";
        } else if ((step1 || step2) || (step1 || step3) || (step1 || step4) || (step1 || step5) || (step1 || step6) || (step1 || step7) ||
                (step1 || step8) || (step1 || step9)) {
            s = "45";
        } else if ((step1 || step2 || step3) || (step4 || step5 || step6) || (step7 || step8 || step9)) {
            s = "55";
        } else if (step1 && step2 || !step3 || !step4 || !step5 || !step6 || !step7 || !step8 || !step9) {
            s = "44";
        } else if (step1 && step2 && step3 || !step4 || !step5 || !step6 || !step7 || !step8 || !step9) {
            s = "50";
        } else if (step1 || step2 || step3 || step4 && step5 || !step6 || !step7 || !step8 || !step9) {
            s = "63";
        } else if (step1 || step2 || step3 || step4 && step5 && step6 || !step7 || !step8 || !step9) {
            s = "72";
        } else if (step1 || step2 || step3 || step4 || step5 || step6 || step7 && step8 || !step9) {
            s = "84";
        } else {
            s = "67";
        }

        return s;
    }

    private void setUpNavDrawer() {

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.junior_main, menu);
        MenuItem menuItem = menu.findItem(R.id.notify);
        MenuItemCompat.setActionView(menuItem, R.layout.toolbar_counter);
        lay1 = (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        alertCount = (TextView) lay1.findViewById(R.id.counter1);
        if (mApp.getPreference().getString(Common.ALERT,"").equalsIgnoreCase("0")) {
            alertCount.setVisibility(View.GONE);
        } else {
            alertCount.setText(mApp.getPreference().getString(Common.ALERT,""));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), JuniorProfile.class));
                break;
            case R.id.resume:
                startActivity(new Intent(getApplicationContext(), ExpResumeGenerate.class));
                break;
            case R.id.logout:
                mApp.getPreference().edit().putBoolean(Common.Login, false).commit();
                mApp.getPreference().edit().putBoolean(Common.USRINFO, false).commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
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

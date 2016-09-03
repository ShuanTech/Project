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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.shuan.project.asyncTasks.Feedback;
import com.shuan.project.fragment.AppliedFragment;
import com.shuan.project.fragment.ConnectionFragment;
import com.shuan.project.fragment.EmployerHome;
import com.shuan.project.fragment.FollowerFragment;
import com.shuan.project.fragment.FollowingFragment;
import com.shuan.project.fragment.GetReadyFragment;
import com.shuan.project.fragment.ImportanceFragment;
import com.shuan.project.fragment.NotifyFragment;
import com.shuan.project.fragment.OffersFragment;
import com.shuan.project.fragment.ReferenceFragment;
import com.shuan.project.fragment.SuggestionFragment;
import com.shuan.project.launcher.LoginActivity;
import com.shuan.project.profile.ProfileActivity;
import com.shuan.project.resume.ExpResumeGenerate;
import com.shuan.project.resume.ResumeEditActivity;
import com.shuan.project.search.SearchActivity;
import com.shuan.project.setting.SettingActivity;

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
    private TextView alertCount, profileStrength, following, follower;
    int selected;

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

        display(0);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        setUpNavDrawer();

        if (mApp.getPreference().getBoolean(Common.WORKINFO, false) == false) {
            showAlert();
        }

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        View header = mNavigationView.inflateHeaderView(R.layout.header);
        container = (RelativeLayout) findViewById(R.id.container);

        usrName = (TextView) header.findViewById(R.id.usr_name);
        proPic = (CircleImageView) header.findViewById(R.id.profile);

        /*usrName.setTypeface(helper.droid(getApplicationContext()));*/

        setImage(mApp.getPreference().getString(Common.PROPIC, ""), proPic);

        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        connect = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.connect));
        profileStrength = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.strength));
        following = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.following));
        follower = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().findItem(R.id.follower));

        usrName.setText(mApp.getPreference().getString(Common.FULLNAME, ""));

        initializeCount();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home:
                        toolbar.setTitle("Home");
                        mDrawerLayout.closeDrawers();
                        selected = 0;
                        display(0);
                        return true;
                    case R.id.strength:
                        startActivity(new Intent(getApplicationContext(), ResumeEditActivity.class));
                        return true;
                    case R.id.connect:
                        toolbar.setTitle("Connections");
                        mDrawerLayout.closeDrawers();
                        display(2);
                        selected = 2;
                        return true;
                    case R.id.following:
                        toolbar.setTitle("Following");
                        mDrawerLayout.closeDrawers();
                        display(3);
                        selected = 3;
                        return true;
                    case R.id.follower:
                        toolbar.setTitle("Follower");
                        mDrawerLayout.closeDrawers();
                        display(4);
                        selected = 4;
                        return true;
                    case R.id.sugg:
                        toolbar.setTitle("Suggestions");
                        mDrawerLayout.closeDrawers();
                        display(5);
                        selected = 5;
                        return true;
                    case R.id.imp:
                        toolbar.setTitle("Importance");
                        mDrawerLayout.closeDrawers();
                        display(6);
                        selected = 6;
                        return true;
                    case R.id.ref:
                        toolbar.setTitle("Reference");
                        mDrawerLayout.closeDrawers();
                        display(7);
                        selected = 7;
                        return true;
                    case R.id.offer:
                        toolbar.setTitle("Offers");
                        mDrawerLayout.closeDrawers();
                        display(8);
                        selected = 8;
                        return true;
                    case R.id.apply:
                        toolbar.setTitle("Applied");
                        mDrawerLayout.closeDrawers();
                        display(9);
                        selected = 9;
                        return true;
                    case R.id.ready:
                        toolbar.setTitle("Get Ready");
                        mDrawerLayout.closeDrawers();
                        display(10);
                        selected = 10;
                        return true;
                    case R.id.feed:
                        mDrawerLayout.closeDrawers();
                        showFeedDialog();
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

    private void showAlert() {
        AlertDialog.Builder build = new AlertDialog.Builder(SeniorActivity.this);
        build.setTitle("Udyomitra");
        build.setMessage("Complete Profile, Build your Network!")
                .setPositiveButton("Build", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), ResumeEditActivity.class));
                        dialog.cancel();
                    }
                }).setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private void showFeedDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.intro_dialog, null, false);
        final EditText feed = (EditText) v.findViewById(R.id.intro);
        feed.setHint("Enter FeedBack");
        AlertDialog.Builder builder = new AlertDialog.Builder(SeniorActivity.this)
                .setView(v);
        builder.setTitle("FeedBack")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (feed.getText().toString().length() == 0) {
                            feed.setError("Filed Mandatory");
                        } else {
                            new Feedback(getApplicationContext(), mApp.getPreference().getString(Common.u_id, ""), feed.getText().toString()).execute();
                            dialog.cancel();

                        }
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
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
            case 3:
                f = new FollowingFragment();
                break;
            case 4:
                f = new FollowerFragment();
                break;
            case 5:
                f = new SuggestionFragment();
                break;
            case 6:
                f = new ImportanceFragment();
                break;
            case 7:
                f = new ReferenceFragment();
                break;
            case 8:
                f = new OffersFragment();
                break;
            case 9:
                f = new AppliedFragment();
                break;
            case 10:
                f = new GetReadyFragment();
                break;
            case 11:
                toolbar.setTitle("Notification");
                f = new NotifyFragment();
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

        profileStrength.setText(""+mApp.getPreference().getInt(Common.PROFILESTRENGTH,0));


        follower.setGravity(Gravity.CENTER_VERTICAL);
        follower.setTypeface(helper.droid(getApplicationContext()));
        follower.setTextColor(getResources().getColor(R.color.senAccent));
        follower.setText(mApp.getPreference().getString(Common.FOLLOWER, ""));

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
        if (mApp.getPreference().getString(Common.ALERT, "").equalsIgnoreCase("0")) {
            alertCount.setVisibility(View.GONE);
        } else {
            alertCount.setText(mApp.getPreference().getString(Common.ALERT, ""));
        }
        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle("Notification");
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(selected).setChecked(false);
                display(11);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.resume:
                mApp.getPreference().edit().putBoolean(Common.APPLY, false).commit();
                startActivity(new Intent(getApplicationContext(), ExpResumeGenerate.class));
                break;
            case R.id.setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
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

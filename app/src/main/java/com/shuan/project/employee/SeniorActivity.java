package com.shuan.Project.employee;

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
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.shuan.Project.R;
import com.shuan.Project.Utils.CircleImageView;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.about.About;
import com.shuan.Project.asyncTasks.Feedback;
import com.shuan.Project.fragment.AppliedFragment;
import com.shuan.Project.fragment.ConnectionFragment;
import com.shuan.Project.fragment.EmployerHome;
import com.shuan.Project.fragment.FavoriteFragment;
import com.shuan.Project.fragment.FollowerFragment;
import com.shuan.Project.fragment.FollowingFragment;
import com.shuan.Project.fragment.GetReadyFragment;
import com.shuan.Project.fragment.ImportanceFragment;
import com.shuan.Project.fragment.InviteFragment;
import com.shuan.Project.fragment.NotifyFragment;
import com.shuan.Project.fragment.OffersFragment;
import com.shuan.Project.fragment.PortfolioFragment;
import com.shuan.Project.fragment.ReferenceFragment;
import com.shuan.Project.fragment.SuggestionFragment;
import com.shuan.Project.launcher.LoginActivity;
import com.shuan.Project.profile.ProfileActivity;
import com.shuan.Project.resume.ExpResumeGenerate;
import com.shuan.Project.resume.ResumeEditActivity;
import com.shuan.Project.search.EmplyeeSearchActivity;
import com.shuan.Project.search.SearchActivity;
import com.shuan.Project.setting.SettingActivity;

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
    private int count =1;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mApp = (Common) getApplicationContext();
        if (mApp.getPreference().getBoolean("start", false) == false) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            showWelcome();
            showAlert();
        }
        // new GetInfo(SeniorActivity.this,mApp.getPreference().getString(Common.u_id,""));

        if (mApp.getPreference().getString(Common.Version, "").equalsIgnoreCase("true")) {
            builder = new AlertDialog.Builder(SeniorActivity.this)
                    .setTitle("Update")
                    .setCancelable(false)
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
                            .setData(Uri.parse("market://details?id=com.shuan.Project"));
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
                        count = 1;
                        selected = 0;
                        display(0);
                        return true;
                    case R.id.strength:
                        startActivity(new Intent(getApplicationContext(), ResumeEditActivity.class));
                        mDrawerLayout.closeDrawers();
                        count = 2;
                        return true;
                    case R.id.connect:
                        toolbar.setTitle("Connections");
                        mDrawerLayout.closeDrawers();
                        display(2);
                        count = 2;
                        selected = 2;
                        return true;
                    case R.id.following:
                        toolbar.setTitle("Following");
                        mDrawerLayout.closeDrawers();
                        display(3);
                        count = 2;
                        selected = 3;
                        return true;
                    case R.id.follower:
                        toolbar.setTitle("Followers");
                        mDrawerLayout.closeDrawers();
                        display(4);
                        count = 2;
                        selected = 4;
                        return true;
                    case R.id.sugg:
                        toolbar.setTitle("Suggestions");
                        mDrawerLayout.closeDrawers();
                        display(5);
                        selected = 5;
                        count = 2;
                        return true;
                    case R.id.imp:
                        toolbar.setTitle("Important");
                        mDrawerLayout.closeDrawers();
                        display(6);
                        selected = 6;
                        count = 2;
                        return true;
                    case R.id.ref:
                        toolbar.setTitle("Reference");
                        mDrawerLayout.closeDrawers();
                        display(7);
                        selected = 7;
                        count = 2;
                        return true;
                    case R.id.offer:
                        toolbar.setTitle("Offers");
                        mDrawerLayout.closeDrawers();
                        display(8);
                        selected = 8;
                        count = 2;
                        return true;
                    case R.id.apply:
                        toolbar.setTitle("Applied");
                        mDrawerLayout.closeDrawers();
                        display(9);
                        selected = 9;
                        count = 2;
                        return true;
                    case R.id.ready:
                        toolbar.setTitle("Get Ready");
                        mDrawerLayout.closeDrawers();
                        display(10);
                        count = 2;
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
                    case R.id.term:
                        mDrawerLayout.closeDrawers();
                        showTerm();
                        return true;
                    case R.id.invite:
                        toolbar.setTitle("Invitations");
                        mDrawerLayout.closeDrawers();
                        display(12);
                        count = 2;
                        selected = 12;
                        return true;
                    case R.id.port:
                        toolbar.setTitle("Portfolio");
                        mDrawerLayout.closeDrawers();
                        display(13);
                        count = 2;
                        selected = 13;
                        return true;
                    /*case R.id.help:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), Help.class));
                        return true;*/
                    case R.id.fav:
                        toolbar.setTitle("Favourites");
                        mDrawerLayout.closeDrawers();
                        display(11);
                        count = 2;
                        selected = 11;
                        return true;
                    default:
                        return true;
                }


            }
        });

    }

    private void showTerm() {
        WebView myWebView = new WebView(SeniorActivity.this);
        myWebView.loadUrl("file:///android_asset/privacy.html");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        new AlertDialog.Builder(SeniorActivity.this).setView(myWebView)
                .setTitle("Terms and Conditions")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }

                }).show();
    }

    private void showAlert() {
        AlertDialog.Builder build = new AlertDialog.Builder(SeniorActivity.this);
        build.setTitle("Udyomitra");
        build.setCancelable(false);
        build.setMessage("Complete Profile, Build your Network!")
                .setPositiveButton("Build", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SeniorActivity.this, "Keep your profile updated", Toast.LENGTH_SHORT).show();
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

    private void showWelcome() {
        AlertDialog.Builder build = new AlertDialog.Builder(SeniorActivity.this);
        build.setTitle("Welcome to Udyomitra");
        build.setIcon(R.drawable.logo);
        build.setCancelable(false);
        build.setMessage(" Greetings of the day!! Welcome to a whole new world of opportunities. With the New Year ahead of us, Udyomitra will help you write a new story by setting higher goals in your life. We exposed you to all the vacancies in and around your native location by default. You will be following all your college mates and their working firms by default. We generate a decent quality resume for you for free. We will help you find the work you enjoy since you are best suited for it.We wish you all success!")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
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
        feed.setHint("Enter Feedback");
        AlertDialog.Builder builder = new AlertDialog.Builder(SeniorActivity.this)
                .setCancelable(false)
                .setView(v);
        builder.setTitle("FeedBack")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (feed.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Feedback cannot be empty try again..!", Toast.LENGTH_SHORT).show();
                        } else {
                            new Feedback(getApplicationContext(), mApp.getPreference().getString(Common.u_id, ""), feed.getText().toString()).execute();
                            dialog.dismiss();

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
                f = new EmployerHome();
                break;
            case 1:
                break;
            case 2:
                f = new ConnectionFragment();
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
            case 15:
                toolbar.setTitle("Notifications");
                f = new NotifyFragment();
                break;
            case 12:
                f = new InviteFragment();
                break;
            case 13:
                f = new PortfolioFragment();
                //Toast.makeText(getApplicationContext(),"Use website for adding portfolio",Toast.LENGTH_SHORT).show();
                break;
            case 11:
                f = new FavoriteFragment();
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
        connect.setTextColor(getResources().getColor(R.color.senAccent));
        connect.setText(mApp.getPreference().getString(Common.CONNECTION, ""));

        following.setGravity(Gravity.CENTER_VERTICAL);

        following.setTextColor(getResources().getColor(R.color.senAccent));
        following.setText(mApp.getPreference().getString(Common.FOLLOWING, ""));

        profileStrength.setGravity(Gravity.CENTER_VERTICAL);
        profileStrength.setTextColor(getResources().getColor(R.color.senAccent));

        profileStrength.setText(String.valueOf(mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0)));


        follower.setGravity(Gravity.CENTER_VERTICAL);
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
                toolbar.setTitle("Notifications");
                mDrawerLayout.closeDrawers();
                //mNavigationView.getMenu().getItem(selected).setChecked(false);
                count=2;
                display(15);
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
            case R.id.job_ser:
                startActivity(new Intent(getApplicationContext(), EmplyeeSearchActivity.class));
                Toast.makeText(getApplicationContext(), "Please wait initializing...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                mApp.getPreference().edit().clear().commit();
                Intent intent = new Intent(SeniorActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
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
        if (count == 1) {
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
        }else {
            toolbar.setTitle("Home");
            mDrawerLayout.closeDrawers();
            count=1;
            display(0);
        }
    }


}

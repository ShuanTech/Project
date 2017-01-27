package com.shuan.Project.employer;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.shuan.Project.fragment.CompanyContactFragment;
import com.shuan.Project.fragment.EmployerAboutFragment;
import com.shuan.Project.fragment.EmployerHome;
import com.shuan.Project.fragment.FavoriteFragment;
import com.shuan.Project.fragment.FollowerFragment;
import com.shuan.Project.fragment.FollowingFragment;
import com.shuan.Project.fragment.NotifyFragment;
import com.shuan.Project.fragment.PortfolioFragment;
import com.shuan.Project.fragment.ReOpenPostFragment;
import com.shuan.Project.fragment.ServicesFragment;
import com.shuan.Project.fragment.ShortlistFragment;
import com.shuan.Project.fragment.TestmonialFragment;
import com.shuan.Project.launcher.LoginActivity;
import com.shuan.Project.profile.ProfileActivity;
import com.shuan.Project.search.EmployerSearchActivity;
import com.shuan.Project.search.SearchActivity;
import com.shuan.Project.setting.SettingActivity;

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
    private TextView follower;
    private TextView alertCount;
    private int selectd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        if (mApp.getPreference().getBoolean("start", false) == false) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            showWelcome();
        }
       // new GetInfo(EmployerActivity.this, mApp.getPreference().getString(Common.u_id, ""));
        if (mApp.getPreference().getString(Common.Version, "").equalsIgnoreCase("true")) {
            builder = new AlertDialog.Builder(EmployerActivity.this)
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
        setContentView(R.layout.activity_employer);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("Home");
        root = (RelativeLayout) findViewById(R.id.root);

        if (mApp.getPreference().getBoolean(Common.COMPANY, false) == false) {
            showAlert();
        }

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
                        selectd = 0;
                        return true;
                    case R.id.follower:
                        toolbar.setTitle("Followers");
                        mDrawerLayout.closeDrawers();
                        display(1);
                        selectd = 1;
                        return true;
                   /* case R.id.ourteam:
                        toolbar.setTitle("Our Team");
                        mDrawerLayout.closeDrawers();
                        display(2);
                        selectd = 2;
                        return true;*/
                    case R.id.action:
                        toolbar.setTitle("Action");
                        mDrawerLayout.closeDrawers();
                        display(3);
                        selectd = 3;
                        return true;
                    case R.id.abt:
                        toolbar.setTitle("About Company");
                        mDrawerLayout.closeDrawers();
                        display(5);
                        selectd = 5;
                        return true;
                    case R.id.service:
                        toolbar.setTitle("Services");
                        mDrawerLayout.closeDrawers();
                        display(6);
                        selectd = 6;
                        return true;
                    case R.id.port:
                        toolbar.setTitle("Portfolio");
                        mDrawerLayout.closeDrawers();
                        display(7);
                        selectd = 7;
                        return true;
                    /*case R.id.test:
                        toolbar.setTitle("Testimonial");
                        mDrawerLayout.closeDrawers();
                        display(8);
                        selectd = 8;
                        return true;*/
                    case R.id.contact:
                        toolbar.setTitle("Contact");
                        mDrawerLayout.closeDrawers();
                        display(9);
                        selectd = 9;
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(EmployerActivity.this, SettingActivity.class));
                        return true;
                    case R.id.logout:
                        mApp.getPreference().edit().clear().commit();
                        Intent intent = new Intent(EmployerActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                        return true;
                    case R.id.about:
                        mDrawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(), About.class));
                        return true;
                    case R.id.term:
                        mDrawerLayout.closeDrawers();
                        showTerm();
                        return true;
                    case R.id.fav:
                        toolbar.setTitle("Favourite");
                        mDrawerLayout.closeDrawers();
                        display(10);
                        selectd = 10;
                        return true;
                    case R.id.reopen:
                        toolbar.setTitle("Reopen Jobs");
                        mDrawerLayout.closeDrawers();
                        display(11);
                        selectd = 11;
                        return true;
                    case R.id.feed:
                        mDrawerLayout.closeDrawers();
                        showFeedDialog();
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
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    private void showTerm() {
        WebView myWebView = new WebView(EmployerActivity.this);
        myWebView.loadUrl("file:///android_asset/privacy.html");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        new AlertDialog.Builder(EmployerActivity.this).setView(myWebView)
                .setTitle("Terms and Conditions")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }

                }).show();
    }


    private void showAlert() {
        AlertDialog.Builder build = new AlertDialog.Builder(EmployerActivity.this);
        build.setTitle("Udyomitra");
        build.setMessage("Complete Profile, Build your Network!")
                .setPositiveButton("Build", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), AddCompanyInfoActivity.class));
                        dialog.cancel();
                    }
                }).setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private void showWelcome(){
        AlertDialog.Builder build = new AlertDialog.Builder(EmployerActivity.this);
        build.setTitle("Welcome to Udyomitra");
        build.setIcon(R.drawable.logo);
        build.setCancelable(false);
        build.setMessage(" Greetings of the day!! Welcome to a whole new world of opportunities. We help you connect the dots between individual roles and goals of your organization at ease. We do not restrict you to the number of vacancies you post at Udyomitra and we guarantee to display only qualified, active and apt candidates based on your requirement. We reduce your effort and time for hiring. We have no validity for your relationship with us. We wish you all success!")
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
        feed.setHint("Enter FeedBack");
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployerActivity.this)
                .setCancelable(false)
                .setView(v);
        builder.setTitle("FeedBack")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (feed.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(),"Please enter your feed back",Toast.LENGTH_SHORT).show();
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
                f = new EmployerHome();

                break;
            case 1:
                f = new FollowerFragment();

                break;
            case 2:
                f = new FollowingFragment();
                break;
            case 3:
                f = new ShortlistFragment();
                break;
            case 5:
                f = new EmployerAboutFragment();
                break;
            case 6:
                f = new ServicesFragment();
                break;
            case 7:
                f = new PortfolioFragment();
                break;
            case 8:
                f = new TestmonialFragment();
                break;
            case 9:
                f = new CompanyContactFragment();
                break;
            case 10:
                f = new FavoriteFragment();
                break;
            case 11:
                f = new ReOpenPostFragment();
                break;
            case 14:
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
        follower.setGravity(Gravity.CENTER_VERTICAL);
        follower.setTextColor(getResources().getColor(R.color.junAccent));
        follower.setText(mApp.getPreference().getString(Common.FOLLOWER, ""));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employer_main, menu);
        MenuItem menuItem = menu.findItem(R.id.notify);
        MenuItemCompat.setActionView(menuItem, R.layout.toolbar_counter);
        lay1 = (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        alertCount = (TextView) lay1.findViewById(R.id.counter1);
        //int cnt = 0;
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
                mNavigationView.getMenu().getItem(selectd).setChecked(false);
                display(14);
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
            case R.id.drawer:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.emp_search:
                startActivity(new Intent(getApplicationContext(), EmployerSearchActivity.class));
                Toast.makeText(getApplicationContext(), "Wait until the initialisation", Toast.LENGTH_SHORT).show();
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

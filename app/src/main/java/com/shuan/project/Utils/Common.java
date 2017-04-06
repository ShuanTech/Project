package com.shuan.Project.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Common extends Application {

    public static SharedPreferences preference = null;
    public static final SharedPreferences.Editor editor = null;
    public static final String PREF = "com.shuan.Project";
    public static final String u_id = "UID";
    public static final String LEVEL = "level";
    public static final String QUALIFICATION = "qualification";
    public static final String HSC = "hsc";
    public static final String SSLC = "sslc";
    public static final String SKILL = "skill";
    public static final String CERITIFY = "certify";
    public static final String PERSONALINFO = "personal_info";
    public static final String WORKINFO = "work_info";
    public static final String COMPANY = "company";
    public static final String HOBBIES = "hobbies";
    public static final String PROJECT = "project";
    public static final String RESUME = "resume";
    public static final String PROFILESUMMARY = "p_summary";
    public static final String WORKEXPERIENCE = "w_experience";
    public static final String Login = "login";
    public static final String OBJECTIVE = "objective";
    public static final String OBJDATA = "objective_data";
    public static final String USRINFO = "usr_info";
    public static final String FULLNAME = "name";
    public static final String PROPIC = "pic";
    public static final String COVER = "cover";
    public static final String CONNECTION = "connection";
    public static final String INTRO = "intro";
    public static final String FOLLOWER="follower";
    public static final String FOLLOWING="following";
    public static final String APPLY="apply";
    public static final String ALERT="alert";
    public static final String PROFILESTRENGTH="strenght";
    public static final String PASSWRD="passwrd";
    public static final String PAGE1="page1";
    public static final String PAGE2="page2";
    public static final String PAGE3="page3";
    public static final String ATTEND ="attend";
    public static final String OTP = "mail";
    public static final String IMPORTANT = "important";
    public Context mContext;
    private String update = null;
    public static final String Version = "version";
    public PackageInfo info = null;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoader mImageLoader;
    private ImageLoaderConfiguration mImageLoaderConfiguration;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        preference = this.getSharedPreferences(PREF, MODE_PRIVATE);

        mImageLoader = ImageLoader.getInstance();
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSizePercentage(13)
                .imageDownloader(new ByteArrayUniversalImageLoader(mContext))
                .build();
        mImageLoader.init(mImageLoaderConfiguration);

        PackageManager manager = mContext.getPackageManager();



        try {
            info = manager.getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new chkVersion().execute();

       /* if (getPreference().getBoolean(Common.USRINFO, false) == true) {
            *//*if (getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                new Connect(getApplicationContext(), getPreference().getString(Common.u_id, "")).execute();
            } else if (getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
                new Connect(getApplicationContext(), getPreference().getString(Common.u_id, "")).execute();
            } else {
                new Follower(getApplicationContext(), getPreference().getString(Common.u_id, "")).execute();
            }*//*

            new GetInfo(getApplicationContext(), getPreference().getString(Common.u_id, "")).execute();
        }*/
    }


    public static SharedPreferences getPreference() {
        return preference;
    }

    public SharedPreferences getSharedPreferences(Bundle savedInstanceState) {
        return preference;
    }

    public class chkVersion extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> verData = new HashMap<String, String>();
            verData.put("ver", info.versionName);


            try {
                JSONObject json = Connection.UrlConnection(php.chkVer, verData);

                int succ = json.getInt("success");

                if (succ == 0) {
                    update = "false";
                } else {
                    update = "true";
                }

            } catch (Exception e) {
            }

            return update;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                getPreference().edit().putString(Common.Version, update).commit();

            }
        }
    }


}

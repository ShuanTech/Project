package com.shuan.project.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.CircleImageView;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.Following;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private String u_id, level;
    private ImageView cover;
    private CircleImageView proPic;
    private TextView name, position, org, intro;
    private Button bu1, bu2,but3;
    private TextView abt, url, cate, sze, found;
    private ProgressBar progressBar;
    private RelativeLayout scroll;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private ArrayList<Sample> list;
    private LinearLayout noData, cmpny, employee, exprience, exp, education, edut, skill, skll, project, prjct, contact;
    private TextView mail, phNo;
    private String pro_pic, cover_pic, cmpny_name, c_type, landmark, country, year_of_establish, num_wrkers, c_desc, c_website, follow;
    private RelativeLayout msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        u_id = getIntent().getStringExtra("u_id");
        level = getIntent().getStringExtra("level");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setBackgroundColor(Color.TRANSPARENT);


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (RelativeLayout) findViewById(R.id.scroll);
        cover = (ImageView) findViewById(R.id.cover_img);
        proPic = (CircleImageView) findViewById(R.id.pro_img);
        name = (TextView) findViewById(R.id.name);
        position = (TextView) findViewById(R.id.position);
        org = (TextView) findViewById(R.id.company_name);
        bu1 = (Button) findViewById(R.id.but1);
        bu2 = (Button) findViewById(R.id.but2);
        noData = (LinearLayout) findViewById(R.id.no_data);
        cmpny = (LinearLayout) findViewById(R.id.cmpny);
        employee = (LinearLayout) findViewById(R.id.employee);
        exp = (LinearLayout) findViewById(R.id.exp);
        education = (LinearLayout) findViewById(R.id.education);
        edut = (LinearLayout) findViewById(R.id.edu);
        skill = (LinearLayout) findViewById(R.id.skill);
        skll = (LinearLayout) findViewById(R.id.skll);
        project = (LinearLayout) findViewById(R.id.project);
        prjct = (LinearLayout) findViewById(R.id.prjct);
        contact = (LinearLayout) findViewById(R.id.contact);
        mail = (TextView) findViewById(R.id.mail);
        phNo = (TextView) findViewById(R.id.ph_no);
        abt = (TextView) findViewById(R.id.abt);
        url = (TextView) findViewById(R.id.url);
        cate = (TextView) findViewById(R.id.cate);
        sze = (TextView) findViewById(R.id.sze);
        found = (TextView) findViewById(R.id.found);
        exprience = (LinearLayout) findViewById(R.id.exprience);
        msg= (RelativeLayout) findViewById(R.id.msg);
        but3= (Button) findViewById(R.id.but3);
        list = new ArrayList<Sample>();


        new Profile().execute();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bu1.getText().toString().equalsIgnoreCase("Follow")) {
                    new Following(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, ""), bu1).execute();
                    bu1.setText("PENDING");
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public class Profile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            pData = new HashMap<String, String>();
            pData.put("u_id", u_id);
            pData.put("level", level);
            pData.put("frm_id", mApp.getPreference().getString(Common.u_id, ""));

            try {

                JSONObject json = Connection.UrlConnection(php.profileView, pData);

                int succ = json.getInt("success");

                if (succ == 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noData.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    if (level.equalsIgnoreCase("1")) {

                        JSONArray jsonArray = json.getJSONArray("pro_view");
                        JSONObject child = jsonArray.getJSONObject(0);
                        final String following = child.optString("following");
                        final String follower=child.optString("follower");

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        final String fullname = data.optString("full_name");
                        final String email_id = data.optString("email_id");
                        final String ph_no = data.optString("ph_no");
                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        JSONObject cntInfo = child.getJSONObject("cnt");
                        JSONArray cntArray = cntInfo.getJSONArray("cnt");
                        JSONObject data1 = cntArray.getJSONObject(0);
                        final String city = data1.optString("city");
                        final String country = data1.optString("country");


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(fullname);
                                if (!city.equalsIgnoreCase("")) {
                                    position.setText(city + " ," + country);
                                }
                                mail.setText(email_id);
                                phNo.setText(ph_no);

                                if(following.equalsIgnoreCase("1") && follower.equalsIgnoreCase("1")){
                                    bu1.setText("Follow");
                                }else if(following.equalsIgnoreCase("0") && follower.equalsIgnoreCase("0")){
                                    bu1.setVisibility(View.GONE);
                                    bu2.setVisibility(View.VISIBLE);
                                }else if(following.equalsIgnoreCase("0") && follower.equalsIgnoreCase("1")){
                                    bu1.setText("Following");
                                }else {
                                    bu1.setText("Follow");
                                }
                            }
                        });

                        final JSONObject edu = child.getJSONObject("edu");
                        JSONArray eduArray = edu.getJSONArray("edu");

                        for (int i = 0; i < eduArray.length(); i++) {
                            JSONObject data2 = eduArray.getJSONObject(i);

                            final String concentration = data2.optString("concentration");
                            final String ins_name = data2.optString("ins_name");
                            final String location = data2.optString("location");
                            final String frmDat = data2.optString("frmDat");
                            final String toDat = data2.optString("toDat");

                            if (!concentration.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        education.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.degree);
                                        txt.setText(concentration + "\n" + ins_name + " , " + location + "\n" + frmDat + " - " + toDat);
                                        edut.addView(v);
                                    }
                                });
                            }


                        }

                        JSONObject skills = child.getJSONObject("skill");
                        JSONArray skillArray = skills.getJSONArray("skill");
                        for (int i = 0; i < skillArray.length(); i++) {
                            JSONObject data3 = skillArray.getJSONObject(i);
                            final String skls = data3.optString("skill");

                            if (!skls.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        skill.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(skls);
                                        skll.addView(v);
                                    }
                                });
                            }
                        }

                        JSONObject projects = child.getJSONObject("project");
                        JSONArray pjctArray = projects.getJSONArray("project");
                        for (int i = 0; i < pjctArray.length(); i++) {
                            JSONObject data4 = pjctArray.getJSONObject(i);
                            final String p_title = data4.optString("p_title");

                            if (!p_title.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        project.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(p_title);
                                        prjct.addView(v);
                                    }
                                });
                            }
                        }
                    } else if (level.equalsIgnoreCase("2")) {


                        JSONArray jsonArray = json.getJSONArray("pro_view");
                        JSONObject child = jsonArray.getJSONObject(0);
                        final String follow = child.optString("follow");

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        final String fullname = data.optString("full_name");
                        final String email_id = data.optString("email_id");
                        final String ph_no = data.optString("ph_no");
                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        JSONObject cntInfo = child.getJSONObject("cnt");
                        JSONArray cntArray = cntInfo.getJSONArray("cnt");
                        JSONObject data1 = cntArray.getJSONObject(0);
                        final String org_name = data1.optString("org_name");
                        final String pos = data1.optString("position");
                        final String loc = data1.optString("location");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(fullname);
                                if (!org_name.equalsIgnoreCase("")) {
                                    position.setText(pos);
                                    org.setText(org_name + " ," + loc);
                                }
                                mail.setText(email_id);
                                phNo.setText(ph_no);
                                if (follow.equalsIgnoreCase("1")) {
                                    bu1.setText("Follow");
                                } else {
                                    bu1.setText("Message");
                                }
                            }
                        });

                        JSONObject wrk = child.optJSONObject("wrk");
                        JSONArray wrkArray = wrk.getJSONArray("wrk");

                        for (int i = 0; i < wrkArray.length(); i++) {
                            JSONObject data6 = wrkArray.getJSONObject(i);
                            final String orgName = data6.optString("org_name");
                            final String poss = data6.optString("position");
                            final String locc = data6.optString("location");
                            final String frmDat = data6.optString("frmDat");
                            final String toDat = data6.optString("toDat");

                            if (!orgName.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        exprience.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_work);
                                        txt.setText(poss + "\n" + orgName + " , " + locc + "\n" + frmDat + " - " + toDat);
                                        exp.addView(v);
                                    }
                                });
                            }
                        }

                        final JSONObject edu = child.getJSONObject("edu");
                        JSONArray eduArray = edu.getJSONArray("edu");

                        for (int i = 0; i < eduArray.length(); i++) {
                            JSONObject data2 = eduArray.getJSONObject(i);

                            final String concentration = data2.optString("concentration");
                            final String ins_name = data2.optString("ins_name");
                            final String location = data2.optString("location");
                            final String frmDat = data2.optString("frmDat");
                            final String toDat = data2.optString("toDat");

                            if (!concentration.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        education.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.degree);
                                        txt.setText(concentration + "\n" + ins_name + " , " + location + "\n" + frmDat + " - " + toDat);
                                        edut.addView(v);
                                    }
                                });
                            }


                        }

                        JSONObject skills = child.getJSONObject("skill");
                        JSONArray skillArray = skills.getJSONArray("skill");
                        for (int i = 0; i < skillArray.length(); i++) {
                            JSONObject data3 = skillArray.getJSONObject(i);
                            final String skls = data3.optString("skill");

                            if (!skls.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        skill.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(skls);
                                        skll.addView(v);
                                    }
                                });
                            }
                        }

                        JSONObject projects = child.getJSONObject("project");
                        JSONArray pjctArray = projects.getJSONArray("project");
                        for (int i = 0; i < pjctArray.length(); i++) {
                            JSONObject data4 = pjctArray.getJSONObject(i);
                            final String p_title = data4.optString("p_title");

                            if (!p_title.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        project.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setVisibility(View.GONE);
                                        txt.setText(p_title);
                                        prjct.addView(v);
                                    }
                                });
                            }
                        }
                    } else {


                        JSONArray jsonArray = json.getJSONArray("pro_view");
                        JSONObject child = jsonArray.getJSONObject(0);
                        final String follow = child.optString("follow");

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        JSONObject data = infoArray.getJSONObject(0);
                        pro_pic = data.optString("pro_pic");
                        cover_pic = data.optString("cover_pic");
                        cmpny_name = data.optString("cmpny_name");
                        c_type = data.optString("c_type");
                        landmark = data.optString("landmark");
                        country = data.optString("country");
                        year_of_establish = data.optString("year_of_establish");
                        num_wrkers = data.optString("num_wrkers");
                        c_desc = data.optString("c_desc");
                        c_website = data.optString("c_website");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cmpny.setVisibility(View.VISIBLE);
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                name.setText(cmpny_name);
                                position.setText(c_type);
                                org.setText(landmark + "," + country);
                                abt.setText(c_desc);
                                url.setText(c_website);
                                cate.setText(c_type);
                                sze.setText(num_wrkers);
                                found.setText(year_of_establish);

                                if (follow.equalsIgnoreCase("1")) {
                                    bu1.setVisibility(View.VISIBLE);
                                    bu1.setText("Follow");
                                } else {
                                    bu1.setVisibility(View.GONE);
                                    bu2.setVisibility(View.VISIBLE);
                                    bu2.setText("Followed");
                                }
                            }
                        });


                    }


                }


            } catch (Exception e) {
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);

        }
    }


    private void setImage(String path, CircleImageView img) {

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
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


    private void setCover(String path, ImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.header)
                .showImageForEmptyUri(R.drawable.header)
                .showImageOnFail(R.drawable.header)
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


}

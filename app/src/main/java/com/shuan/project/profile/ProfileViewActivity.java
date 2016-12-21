package com.shuan.Project.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.shuan.Project.asyncTasks.AddFavorite;
import com.shuan.Project.asyncTasks.Following;
import com.shuan.Project.asyncTasks.GetInvitation;
import com.shuan.Project.employee.EventViewActivity;
import com.shuan.Project.employer.PostViewActivity;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.resume.ExpResumeGenerate;
import com.shuan.Project.resume.JuniorResumeGenerate;

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
    private Button bu1, bu2, but3;
    private TextView abt;
    private ProgressBar progressBar;
    private RelativeLayout scroll;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private ArrayList<Sample> list;
    private LinearLayout noData, cmpny, employee, exprience, exp, education, edut, skill, skll, project, prjct, contact, objective, web;
    private LinearLayout c_type, i_type, c_sze, c_fnd, c_mail, c_ph, cntDet, cntt, cnt_mail, cnt_ph, cnt_tm;
    private TextView url, cate, indus_type, sze, found, cmail, cph, cnt_per, cnt_mail_id, cnt_ph_no, cnt_tme;
    private TextView mail, phNo, obj;
    private RelativeLayout msg, call;
    private Button inivite, resume;
    private RelativeLayout followLay;
    private LinearLayout extrabut;
    private String fullname;
    private LinearLayout about, cmpntDet, ser, service, port, portfolio, job, jobs, evnt, events;
    private RelativeLayout flow, unflow, flows;
    private LinearLayout fms;

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
        noData = (LinearLayout) findViewById(R.id.no_data);
        cmpny = (LinearLayout) findViewById(R.id.cmpny);
        employee = (LinearLayout) findViewById(R.id.employee);
        objective = (LinearLayout) findViewById(R.id.objective);
        obj = (TextView) findViewById(R.id.obj);
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
        msg = (RelativeLayout) findViewById(R.id.msg);
        list = new ArrayList<Sample>();
        inivite = (Button) findViewById(R.id.invite);
        call = (RelativeLayout) findViewById(R.id.call);

        extrabut = (LinearLayout) findViewById(R.id.extra_but);
        resume = (Button) findViewById(R.id.resume);

        about = (LinearLayout) findViewById(R.id.about);
        ser = (LinearLayout) findViewById(R.id.ser);
        service = (LinearLayout) findViewById(R.id.service);
        port = (LinearLayout) findViewById(R.id.port);
        portfolio = (LinearLayout) findViewById(R.id.portfolio);
        job = (LinearLayout) findViewById(R.id.job);
        jobs = (LinearLayout) findViewById(R.id.jobs);
        evnt = (LinearLayout) findViewById(R.id.evnt);
        events = (LinearLayout) findViewById(R.id.events);
        cmpntDet = (LinearLayout) findViewById(R.id.cmpntDet);
        web = (LinearLayout) findViewById(R.id.web);
        url = (TextView) findViewById(R.id.url);
        c_type = (LinearLayout) findViewById(R.id.c_type);
        cate = (TextView) findViewById(R.id.cate);
        i_type = (LinearLayout) findViewById(R.id.i_type);
        indus_type = (TextView) findViewById(R.id.indus_type);
        c_sze = (LinearLayout) findViewById(R.id.c_sze);
        sze = (TextView) findViewById(R.id.sze);
        c_fnd = (LinearLayout) findViewById(R.id.c_fnd);
        found = (TextView) findViewById(R.id.found);
        c_mail = (LinearLayout) findViewById(R.id.c_mail);
        cmail = (TextView) findViewById(R.id.cmail);
        c_ph = (LinearLayout) findViewById(R.id.c_ph);
        cph = (TextView) findViewById(R.id.cph);
        cntDet = (LinearLayout) findViewById(R.id.cntDet);
        cntt = (LinearLayout) findViewById(R.id.cnt);
        cnt_per = (TextView) findViewById(R.id.cnt_per);
        cnt_mail = (LinearLayout) findViewById(R.id.cnt_mail);
        cnt_mail_id = (TextView) findViewById(R.id.cnt_mail_id);
        cnt_ph = (LinearLayout) findViewById(R.id.cnt_ph);
        cnt_ph_no = (TextView) findViewById(R.id.cnt_ph_no);
        cnt_tm = (LinearLayout) findViewById(R.id.cnt_tm);
        cnt_tme = (TextView) findViewById(R.id.cnt_tme);
        flow = (RelativeLayout) findViewById(R.id.folw);
        unflow = (RelativeLayout) findViewById(R.id.unflow);
        flows = (RelativeLayout) findViewById(R.id.flows);
        fms = (LinearLayout) findViewById(R.id.fmc);

        new Profile().execute();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
            if (level.equalsIgnoreCase("1") || level.equalsIgnoreCase("2")) {
                extrabut.setVisibility(View.VISIBLE);
            } else {
                extrabut.setVisibility(View.GONE);
            }

        } else {
            extrabut.setVisibility(View.GONE);
        }

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        fms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Following(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, ""), flow, unflow, level).execute();
            }
        });
        /*bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bu1.getText().toString().equalsIgnoreCase("Follow")) {
                    new Following(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, ""), bu1, level).execute();
                    bu1.setText("PENDING");
                }
            }
        });*/

        inivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetInvitation(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, "")).execute();

            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.getPreference().edit().putBoolean("download", true).commit();
                mApp.getPreference().edit().putString("Id", u_id).commit();
                mApp.getPreference().edit().putString("name", name.getText().toString()).commit();
                if (level.equalsIgnoreCase("1")) {
                    startActivity(new Intent(getApplicationContext(), JuniorResumeGenerate.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), ExpResumeGenerate.class));
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
                        final String follower = child.optString("follower");

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        final JSONObject data = infoArray.getJSONObject(0);

                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        final JSONObject sec = child.getJSONObject("sec");
                        JSONArray secArray = sec.getJSONArray("sec");
                        final JSONObject data1 = secArray.getJSONObject(0);


                        JSONObject cnt = child.getJSONObject("cnt");
                        JSONArray cntArray = cnt.getJSONArray("cnt");
                        final JSONObject data8 = cntArray.getJSONObject(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(data8.optString("full_name"));
                                if(mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("3")){
                                    if (!data.optString("status").equalsIgnoreCase("")) {
                                        org.setTextColor(getResources().getColor(R.color.stus));
                                        org.setTypeface(null, Typeface.BOLD);
                                        org.setText(data.optString("status"));
                                    }
                                }

                                position.setText(data1.optString("sec"));
                                if (!data8.optString("email_id").equalsIgnoreCase("") || !data8.optString("ph_no").equalsIgnoreCase("")) {
                                    contact.setVisibility(View.VISIBLE);
                                    if (!data8.optString("email_id").equalsIgnoreCase("")) {
                                        mail.setText(data8.optString("email_id"));
                                    }
                                    if (!data8.optString("ph_no").equalsIgnoreCase("")) {
                                        phNo.setText(data8.optString("ph_no"));
                                    }
                                }


                                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
                                    if (follower.equalsIgnoreCase("1")) {
                                        flows.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if (following.equalsIgnoreCase("1")) {
                                        unflow.setVisibility(View.VISIBLE);
                                    } else {
                                        flow.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (!data8.optString("objective").equalsIgnoreCase("")) {
                                    objective.setVisibility(View.VISIBLE);
                                    obj.setText(data8.optString("objective"));
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
                        final String following = child.optString("following");
                        final String follower = child.optString("follower");

                        JSONObject info = child.getJSONObject("info");
                        JSONArray infoArray = info.getJSONArray("info");
                        final JSONObject data = infoArray.getJSONObject(0);

                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        final JSONObject sec = child.getJSONObject("sec");
                        JSONArray secArray = sec.getJSONArray("sec");
                        final JSONObject data1 = secArray.getJSONObject(0);


                        JSONObject cnt = child.getJSONObject("cnt");
                        JSONArray cntArray = cnt.getJSONArray("cnt");
                        final JSONObject data8 = cntArray.getJSONObject(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                contact.setVisibility(View.VISIBLE);
                                name.setText(data8.optString("full_name"));
                                position.setText(data1.optString("sec"));
                                if(mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("3")){
                                    if (!data.optString("status").equalsIgnoreCase("")) {
                                        org.setTextColor(getResources().getColor(R.color.stus));
                                        org.setTypeface(null, Typeface.BOLD);
                                        org.setText(data.optString("status"));
                                    }
                                }
                                if (!data8.optString("email_id").equalsIgnoreCase("") || !data8.optString("ph_no").equalsIgnoreCase("")) {
                                    contact.setVisibility(View.VISIBLE);
                                    if (!data8.optString("email_id").equalsIgnoreCase("")) {
                                        mail.setText(data8.optString("email_id"));
                                    }
                                    if (!data8.optString("ph_no").equalsIgnoreCase("")) {
                                        phNo.setText(data8.optString("ph_no"));
                                    }
                                }


                                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
                                    if (follower.equalsIgnoreCase("1")) {
                                        flows.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if (following.equalsIgnoreCase("1")) {
                                        unflow.setVisibility(View.VISIBLE);
                                    } else {
                                        flow.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (!data8.optString("objective").equalsIgnoreCase("")) {
                                    objective.setVisibility(View.VISIBLE);
                                    obj.setText(data8.optString("objective"));
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
                        final String pro_pic = data.optString("pro_pic");
                        final String cover_pic = data.optString("cover_pic");

                        JSONObject cnt = child.getJSONObject("cnt");
                        JSONArray cntArray = cnt.getJSONArray("cnt");
                        final JSONObject data1 = cntArray.getJSONObject(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cmpny.setVisibility(View.VISIBLE);
                                setImage(pro_pic, proPic);
                                setCover(cover_pic, cover);
                                name.setText(data1.optString("cmpny_name"));
                                position.setText(data1.optString("i_type"));

                                org.setText(data1.optString("city") + "," + data1.optString("country"));


                                if (data1.optString("c_desc") != null && !data1.optString("c_desc").trim().isEmpty()) {
                                    about.setVisibility(View.VISIBLE);
                                    abt.setText(data1.optString("c_desc"));
                                }

                                if (!data1.optString("c_type").equalsIgnoreCase("") || !data1.optString("i_type").equalsIgnoreCase("") ||
                                        !data1.optString("mail").equalsIgnoreCase("") || !data1.optString("ph_no").equalsIgnoreCase("") ||
                                        !data1.optString("c_website").equalsIgnoreCase("") || !data1.optString("year_of_establish").equalsIgnoreCase("")) {

                                    cmpntDet.setVisibility(View.VISIBLE);
                                    if (!data1.optString("c_type").equalsIgnoreCase("")) {
                                        c_type.setVisibility(View.VISIBLE);
                                        cate.setText(data1.optString("c_type"));
                                    }
                                    if (!data1.optString("i_type").equalsIgnoreCase("")) {
                                        i_type.setVisibility(View.VISIBLE);
                                        indus_type.setText(data1.optString("i_type"));
                                    }
                                    if (!data1.optString("mail").equalsIgnoreCase("")) {
                                        c_mail.setVisibility(View.VISIBLE);
                                        cmail.setText(data1.optString("mail"));
                                    }
                                    if (!data1.optString("ph_no").equalsIgnoreCase("")) {
                                        c_ph.setVisibility(View.VISIBLE);
                                        cph.setText(data1.optString("ph_no"));
                                    }
                                    if (!data1.optString("c_website").equalsIgnoreCase("")) {
                                        web.setVisibility(View.VISIBLE);
                                        url.setText(data1.optString("c_website"));
                                    }

                                    if (!data1.optString("year_of_establish").equalsIgnoreCase("")) {
                                        c_fnd.setVisibility(View.VISIBLE);
                                        found.setText(data1.optString("year_of_establish"));
                                    }
                                }

                                if (!data1.optString("contact_person").equalsIgnoreCase("") || !data1.optString("contact_mail").equalsIgnoreCase("") ||
                                        !data1.optString("contact_ph").equalsIgnoreCase("") || !data1.optString("contact_time").equalsIgnoreCase("")) {
                                    cntDet.setVisibility(View.VISIBLE);
                                    if (!data1.optString("contact_person").equalsIgnoreCase("")) {
                                        cntt.setVisibility(View.VISIBLE);
                                        cnt_per.setText(data1.optString("contact_person"));
                                    }
                                    if (!data1.optString("contact_mail").equalsIgnoreCase("")) {
                                        cnt_mail.setVisibility(View.VISIBLE);
                                        cnt_mail_id.setText(data1.optString("contact_mail"));
                                    }

                                    if (!data1.optString("contact_ph").equalsIgnoreCase("")) {
                                        cnt_ph.setVisibility(View.VISIBLE);
                                        cnt_ph_no.setText(data1.optString("contact_ph"));
                                    }

                                    if (!data1.optString("contact_time").equalsIgnoreCase("")) {
                                        cnt_tm.setVisibility(View.VISIBLE);
                                        cnt_tme.setText(data1.optString("contact_time"));
                                    }

                                }

                                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3") && level.equalsIgnoreCase("3")) {
                                    fms.setVisibility(View.GONE);
                                } else {
                                    fms.setVisibility(View.VISIBLE);
                                    if (follow.equalsIgnoreCase("1")) {
                                        unflow.setVisibility(View.VISIBLE);
                                    } else {
                                        flow.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });

                        final JSONObject serv = child.getJSONObject("ser");
                        JSONArray serArray = serv.getJSONArray("ser");
                        for (int i = 0; i < serArray.length(); i++) {
                            JSONObject data2 = serArray.getJSONObject(i);

                            final String ser_name = data2.optString("ser_name");
                            final String uId = data2.optString(u_id);


                            if (!ser_name.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ser.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_services);
                                        txt.setText(ser_name);
                                        service.addView(v);
                                        TypedValue val = new TypedValue();
                                        getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, val, true);
                                        v.setBackgroundResource(val.resourceId);
                                     /*   v.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent in = new Intent(getApplicationContext(), ServiceViewActivity.class);
                                                in.putExtra("u_id",uId );
                                                startActivity(in);
                                            }
                                        });*/
                                    }
                                });
                            }

                        }

                        final JSONObject portf = child.getJSONObject("port");
                        JSONArray portArray = portf.getJSONArray("port");
                        for (int i = 0; i < portArray.length(); i++) {
                            JSONObject data2 = portArray.getJSONObject(i);

                            final String ser_name = data2.optString("p_title");
                            final String uId = data2.optString("u_id");


                            if (!ser_name.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        port.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_portfolio);
                                        txt.setText(ser_name);
                                        portfolio.addView(v);
                                        TypedValue val = new TypedValue();
                                        getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, val, true);
                                        v.setBackgroundResource(val.resourceId);
                                    /*    v.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent in = new Intent(getApplicationContext(), PortfolioViewActivity.class);
                                                in.putExtra("u_id",uId );
                                                startActivity(in);
                                            }
                                        });*/

                                    }
                                });
                            }


                        }


                        final JSONObject open = child.getJSONObject("job");
                        JSONArray openArray = open.getJSONArray("job");
                        for (int i = 0; i < openArray.length(); i++) {
                            JSONObject data2 = openArray.getJSONObject(i);

                            final String ser_name = data2.optString("title");
                            final String jobId = data2.optString("job_id");


                            if (!ser_name.equalsIgnoreCase("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        job.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay, null);
                                        ImageView img = (ImageView) v.findViewById(R.id.img);
                                        TextView txt = (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_work);
                                        txt.setText(ser_name);
                                        jobs.addView(v);
                                        TypedValue val = new TypedValue();
                                        getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, val, true);
                                        v.setBackgroundResource(val.resourceId);
                                        v.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent in = new Intent(getApplicationContext(), PostViewActivity.class);
                                                in.putExtra("jId", jobId);
                                                startActivity(in);
                                            }
                                        });
                                    }
                                });
                            }


                        }

                        final JSONObject event= child.getJSONObject("evnt");
                        JSONArray eventArray= event.getJSONArray("evnt");
                        for (int i=0;i<eventArray.length();i++){
                            JSONObject data2= eventArray.getJSONObject(i);

                            final String ser_name= data2.optString("name");
                            final String evntId= data2.optString("evnt_id");

                            if (!ser_name.equalsIgnoreCase("")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        evnt.setVisibility(View.VISIBLE);
                                        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View v = inflater.inflate(R.layout.wrk_lay,null);
                                        ImageView img= (ImageView) v.findViewById(R.id.img);
                                        TextView txt= (TextView) v.findViewById(R.id.wrk);
                                        img.setImageResource(R.drawable.ic_event);
                                        txt.setText(ser_name);
                                        events.addView(v);
                                        TypedValue val = new TypedValue();
                                        getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, val, true);
                                        v.setBackgroundResource(val.resourceId);
                                        v.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent in = new Intent(getApplicationContext(), EventViewActivity.class);
                                                in.putExtra("evntId",evntId);
                                                startActivity(in);
                                            }
                                        });


                                    }
                                });
                            }
                        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_view_menu, menu);
        MenuItem item = menu.findItem(R.id.fav);
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
            if (level.equalsIgnoreCase("3")) {
                item.setVisible(false);
            } else {
                item.setVisible(true);
            }

        } else {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fav) {
            new AddFavorite(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, "")).execute();
        }
        return super.onOptionsItemSelected(item);
    }
}

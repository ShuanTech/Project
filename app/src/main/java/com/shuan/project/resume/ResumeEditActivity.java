package com.shuan.project.resume;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.DeleteDetail;
import com.shuan.project.asyncTasks.UpdateStatus;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResumeEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Common mApp;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private RelativeLayout senior;
    private LinearLayout pro, disPro, wrkDet, wrk_detail, wrkExp, wrk_exprince, addClg, addSchool, qualifiy, project, prjct,
            skill, skll, cert, certify, ach, achieve, ex, extra, abt;
    private HashMap<String, String> rData;
    private ArrayList<Sample> list;
    private PopupMenu popupMenu;
    private ImageView cntEdt, bscEdt, abtEdt;
    private TextView email, phno, addr, cty, ste, cntry, dob, gen, fName, mName, rel, lang, hooby, abtTxt;
    private EditText about;
    private String disrct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            setTheme(R.style.Senior);
        } else {
            setTheme(R.style.AppBaseTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_edit);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Update");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        pro = (LinearLayout) findViewById(R.id.pro);
        disPro = (LinearLayout) findViewById(R.id.dis_pro);
        wrkDet = (LinearLayout) findViewById(R.id.wrk_det);
        wrk_detail = (LinearLayout) findViewById(R.id.wrk_detail);
        wrkExp = (LinearLayout) findViewById(R.id.wrk_exp);
        wrk_exprince = (LinearLayout) findViewById(R.id.wrk_exprince);
        addClg = (LinearLayout) findViewById(R.id.add_clg);
        addSchool = (LinearLayout) findViewById(R.id.add_school);
        qualifiy = (LinearLayout) findViewById(R.id.qualify);
        skill = (LinearLayout) findViewById(R.id.skill);
        skll = (LinearLayout) findViewById(R.id.skll);
        project = (LinearLayout) findViewById(R.id.project);
        prjct = (LinearLayout) findViewById(R.id.prjct);
        cert = (LinearLayout) findViewById(R.id.cert);
        certify = (LinearLayout) findViewById(R.id.certify);
        ach = (LinearLayout) findViewById(R.id.ach);
        achieve = (LinearLayout) findViewById(R.id.achieve);
        ex = (LinearLayout) findViewById(R.id.ex);
        extra = (LinearLayout) findViewById(R.id.extra);

        email = (TextView) findViewById(R.id.email_id);
        phno = (TextView) findViewById(R.id.ph_no);
        addr = (TextView) findViewById(R.id.addr);
        cty = (TextView) findViewById(R.id.cty);
        ste = (TextView) findViewById(R.id.ste);
        cntry = (TextView) findViewById(R.id.cntry);
        dob = (TextView) findViewById(R.id.dob);
        gen = (TextView) findViewById(R.id.gen);
        fName = (TextView) findViewById(R.id.father_name);
        mName = (TextView) findViewById(R.id.mother_name);
        rel = (TextView) findViewById(R.id.rel);
        lang = (TextView) findViewById(R.id.lang);
        hooby = (TextView) findViewById(R.id.hobby);
        abt = (LinearLayout) findViewById(R.id.abt);
        abtEdt = (ImageView) findViewById(R.id.abt_edt);
        abtTxt = (TextView) findViewById(R.id.abt_txt);
        cntEdt = (ImageView) findViewById(R.id.cnt_edt);
        bscEdt = (ImageView) findViewById(R.id.bsc_edt);

        list = new ArrayList<Sample>();
        new GetResumeData().execute();

        pro.setOnClickListener(this);
        wrkDet.setOnClickListener(this);
        wrkExp.setOnClickListener(this);
        addClg.setOnClickListener(this);
        addSchool.setOnClickListener(this);
        skill.setOnClickListener(this);
        project.setOnClickListener(this);
        cert.setOnClickListener(this);
        ach.setOnClickListener(this);
        ex.setOnClickListener(this);
        cntEdt.setOnClickListener(this);
        bscEdt.setOnClickListener(this);
        abtEdt.setOnClickListener(this);
        chkIntro();

    }

    private void chkIntro() {
        if (mApp.getPreference().getString(Common.INTRO, "").equalsIgnoreCase("")) {
            abt.setVisibility(View.GONE);
        } else {
            abtTxt.setText(mApp.getPreference().getString(Common.INTRO, ""));
        }
    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(ResumeEditActivity.this, UpdateResumeActivity.class);
        switch (v.getId()) {
            case R.id.pro:
                in.putExtra("what", "add");
                in.putExtra("which", "proSum");
                startActivity(in);
                break;
            case R.id.wrk_det:
                in.putExtra("what", "add");
                in.putExtra("which", "wrkDet");
                startActivity(in);
                break;
            case R.id.wrk_exp:
                in.putExtra("what", "add");
                in.putExtra("which", "wrkExp");
                startActivity(in);
                break;
            case R.id.add_clg:
                in.putExtra("what", "add");
                in.putExtra("which", "addClg");
                startActivity(in);
                break;
            case R.id.add_school:
                in.putExtra("what", "add");
                in.putExtra("which", "addSch");
                startActivity(in);
                break;
            case R.id.skill:
                in.putExtra("what", "add");
                in.putExtra("which", "skill");
                startActivity(in);
                break;
            case R.id.project:
                in.putExtra("what", "add");
                in.putExtra("which", "project");
                startActivity(in);
                break;
            case R.id.cert:
                in.putExtra("what", "add");
                in.putExtra("which", "cert");
                startActivity(in);
                break;
            case R.id.ach:
                in.putExtra("what", "add");
                in.putExtra("which", "ach");
                startActivity(in);
                break;
            case R.id.ex:
                in.putExtra("what", "add");
                in.putExtra("which", "ex");
                startActivity(in);
                break;
            case R.id.cnt_edt:
                in.putExtra("what", "add");
                in.putExtra("which", "cnt");
                mApp.getPreference().edit().putString("addr", addr.getText().toString()).commit();
                mApp.getPreference().edit().putString("city", cty.getText().toString()).commit();
                mApp.getPreference().edit().putString("state", ste.getText().toString()).commit();
                mApp.getPreference().edit().putString("distrct", disrct).commit();
                mApp.getPreference().edit().putString("country", cntry.getText().toString()).commit();
                startActivity(in);
                break;
            case R.id.bsc_edt:
                in.putExtra("what", "add");
                in.putExtra("which", "bsc");
                mApp.getPreference().edit().putString("dob", dob.getText().toString()).commit();
                mApp.getPreference().edit().putString("gen", gen.getText().toString()).commit();
                mApp.getPreference().edit().putString("fName", fName.getText().toString()).commit();
                mApp.getPreference().edit().putString("mName", mName.getText().toString()).commit();
                mApp.getPreference().edit().putString("rel", rel.getText().toString()).commit();
                mApp.getPreference().edit().putString("lang", lang.getText().toString()).commit();
                mApp.getPreference().edit().putString("hobby", hooby.getText().toString()).commit();
                startActivity(in);
                break;
            case R.id.abt_edt:
                getIntro();
                break;


        }

    }

    public class GetResumeData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            rData = new HashMap<String, String>();
            rData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            rData.put("level", mApp.getPreference().getString(Common.LEVEL, ""));

            try {
                JSONObject json = Connection.UrlConnection(php.resume_data, rData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });

                } else {


                    JSONArray jsonArray = json.getJSONArray("resume");

                    JSONObject child = jsonArray.getJSONObject(0);

                    final JSONObject pro = child.getJSONObject("pro");
                    JSONArray proArray = pro.getJSONArray("summary");
                    for (int i = 0; i < proArray.length(); i++) {
                        JSONObject data = proArray.getJSONObject(i);
                        final String profile = data.optString("summary");
                        final String pId = data.optString("id");

                        if (profile.equalsIgnoreCase("")) {

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(profile);
                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("data", profile).commit();
                                                            in.putExtra("what", "edit");
                                                            in.putExtra("which", "proSum");
                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "proSum").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });
                                    disPro.addView(v);
                                }
                            });
                        }


                    }
                    final JSONObject wrk = child.getJSONObject("wrk");
                    JSONArray wrkArray = wrk.getJSONArray("wrk");

                    for (int i = 0; i < wrkArray.length(); i++) {
                        JSONObject data = wrkArray.getJSONObject(i);
                        final String position = data.optString("position");
                        final String org_name = data.optString("org_name");
                        final String location = data.optString("location");
                        final String from_date = data.optString("from_date");
                        final String to_date = data.optString("to_date");
                        final String pId = data.optString("id");

                        if (pId.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(position + " at " + org_name + ", " + location + " . " + from_date + " - " + to_date);

                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("pos", position).commit();
                                                            mApp.getPreference().edit().putString("org", org_name).commit();
                                                            mApp.getPreference().edit().putString("loc", location).commit();
                                                            in.putExtra("what", "edit");
                                                            in.putExtra("which", "wrkDet");
                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "wrkDet").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });


                                    wrk_detail.addView(v);
                                }
                            });
                        }

                    }

                    JSONObject wrkExp = child.getJSONObject("wrk_exp");
                    JSONArray wrkExpArray = wrkExp.getJSONArray("wrk_exp");
                    for (int i = 0; i < wrkExpArray.length(); i++) {
                        JSONObject data = wrkExpArray.getJSONObject(i);
                        final String exp = data.optString("wrk_exp");
                        final String pId = data.optString("id");
                        if (exp.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(exp);

                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("data", exp).commit();
                                                            in.putExtra("what", "edit");
                                                            in.putExtra("which", "wrkExp");
                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "wrkExp").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });

                                    wrk_exprince.addView(v);
                                }
                            });
                        }
                    }

                    final JSONObject qualify = child.getJSONObject("edu");
                    JSONArray qualifyArray = qualify.getJSONArray("edu");
                    for (int i = 0; i < qualifyArray.length(); i++) {
                        JSONObject data = qualifyArray.getJSONObject(i);
                        final String pId = data.optString("id");
                        final String concentration = data.optString("concentration");
                        final String ins_name = data.optString("ins_name");
                        final String location = data.optString("location");
                        final String aggregate = data.optString("aggregate");
                        final String level = data.optString("level");
                        final String univ = data.optString("board");

                        if (pId.equalsIgnoreCase("")) {
                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(concentration + " , " + ins_name + " at " + location + " with " + aggregate + "%");


                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("level", level).commit();
                                                            mApp.getPreference().edit().putString("conCent", concentration).commit();
                                                            mApp.getPreference().edit().putString("insName", ins_name).commit();
                                                            mApp.getPreference().edit().putString("univ", univ).commit();
                                                            mApp.getPreference().edit().putString("location", location).commit();
                                                            mApp.getPreference().edit().putString("aggrt", aggregate).commit();

                                                            in.putExtra("what", "edit");
                                                            if (level.equalsIgnoreCase("1") || level.equalsIgnoreCase("2") ||
                                                                    level.equalsIgnoreCase("3")) {
                                                                in.putExtra("which", "addClg");
                                                            } else {
                                                                in.putExtra("which", "addSch");
                                                            }

                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "edu").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });

                                    qualifiy.addView(v);
                                }
                            });
                        }
                    }

                    JSONObject skill = child.getJSONObject("skill");
                    JSONArray skillArray = skill.getJSONArray("skill");
                    for (int i = 0; i < skillArray.length(); i++) {
                        JSONObject data = skillArray.getJSONObject(i);
                        final String lang_known = data.optString("skill");
                        final String pId = data.optString("id");

                        if (pId.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.skill_item, null);
                                    TextView txt = (TextView) v.findViewById(R.id.skill);
                                    ImageButton img = (ImageButton) v.findViewById(R.id.close);
                                    txt.setText(lang_known);
                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new DeleteDetail(ResumeEditActivity.this, pId, "skill").execute();
                                        }
                                    });


                                    skll.addView(v);
                                }
                            });
                        }
                    }

                    JSONObject pjct = child.getJSONObject("project");
                    JSONArray pjctArray = pjct.getJSONArray("project");
                    for (int i = 0; i < pjctArray.length(); i++) {
                        JSONObject data = pjctArray.getJSONObject(i);
                        final String pId = data.optString("id");
                        final String p_title = data.optString("p_title");
                        final String p_platform = data.optString("p_platform");
                        final String p_role = data.optString("p_role");
                        final String p_team_mem = data.optString("p_team_mem");
                        final String p_dur = data.optString("p_dur");
                        final String p_url = data.optString("p_url");
                        final String p_description = data.optString("p_description");
                        final String p_stus = data.optString("p_stus");

                        if (p_title.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(p_title);

                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("title", p_title).commit();
                                                            mApp.getPreference().edit().putString("platform", p_platform).commit();
                                                            mApp.getPreference().edit().putString("role", p_role).commit();
                                                            mApp.getPreference().edit().putString("team", p_team_mem).commit();
                                                            mApp.getPreference().edit().putString("dur", p_dur).commit();
                                                            mApp.getPreference().edit().putString("url", p_url).commit();
                                                            mApp.getPreference().edit().putString("desc", p_description).commit();
                                                            mApp.getPreference().edit().putString("stus", p_stus).commit();

                                                            in.putExtra("what", "edit");
                                                            in.putExtra("which", "project");

                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "prjct").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });

                                    prjct.addView(v);
                                }
                            });
                        }


                    }
                    final JSONObject cert = child.getJSONObject("cert");
                    JSONArray certArray = cert.getJSONArray("cert");
                    for (int i = 0; i < certArray.length(); i++) {
                        JSONObject data = certArray.getJSONObject(i);
                        final String certName = data.optString("cer_name");
                        final String certCentre = data.optString("cer_centre");
                        final String pId = data.optString("id");
                        final String certDur = data.optString("cer_dur");

                        if (certName.equalsIgnoreCase("")) {
                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.resume_data, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    txt.setText(certName + " at " + certCentre);

                                    final RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.col);

                                    lay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupMenu = new PopupMenu(ResumeEditActivity.this, lay);
                                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                @Override
                                                public boolean onMenuItemClick(MenuItem item) {
                                                    switch (item.getItemId()) {
                                                        case R.id.edit:
                                                            Intent in = new Intent(getApplicationContext(), UpdateResumeActivity.class);
                                                            mApp.getPreference().edit().putString("eId", pId).commit();
                                                            mApp.getPreference().edit().putString("certName", certName).commit();
                                                            mApp.getPreference().edit().putString("certCentre", certCentre).commit();
                                                            mApp.getPreference().edit().putString("certDur", certDur).commit();

                                                            in.putExtra("what", "edit");
                                                            in.putExtra("which", "cert");

                                                            startActivity(in);
                                                            break;
                                                        case R.id.del:
                                                            new DeleteDetail(ResumeEditActivity.this, pId, "cert").execute();
                                                            break;
                                                    }
                                                    return false;
                                                }
                                            });
                                            popupMenu.inflate(R.menu.resume_edit);
                                            popupMenu.show();
                                        }
                                    });


                                    certify.addView(v);
                                }
                            });
                        }

                    }

                    final JSONObject ach = child.getJSONObject("achieve");
                    JSONArray achArray = ach.getJSONArray("achieve");
                    for (int i = 0; i < achArray.length(); i++) {
                        JSONObject data = achArray.getJSONObject(i);
                        final String a_name = data.optString("a_name");
                        final String pId = data.optString("id");

                        if (a_name.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.skill_item, null);
                                    TextView txt = (TextView) v.findViewById(R.id.skill);
                                    ImageButton img = (ImageButton) v.findViewById(R.id.close);
                                    txt.setText(a_name);
                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new DeleteDetail(ResumeEditActivity.this, pId, "ach").execute();
                                        }
                                    });

                                    achieve.addView(v);
                                }
                            });
                        }


                    }

                    final JSONObject exta = child.getJSONObject("extra");
                    JSONArray extaArray = exta.getJSONArray("extra");
                    for (int i = 0; i < extaArray.length(); i++) {
                        JSONObject data = extaArray.getJSONObject(i);
                        final String extrac = data.optString("extra");
                        final String pId = data.optString("id");

                        if (extrac.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.skill_item, null);
                                    TextView txt = (TextView) v.findViewById(R.id.skill);
                                    ImageButton img = (ImageButton) v.findViewById(R.id.close);
                                    txt.setText(extrac);
                                    img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new DeleteDetail(ResumeEditActivity.this, pId, "extra").execute();
                                        }
                                    });
                                    extra.addView(v);
                                }
                            });
                        }


                    }

                    final JSONObject prsnl = child.getJSONObject("prsnl");
                    JSONArray prsnlArray = prsnl.getJSONArray("prsnl");

                    JSONObject data = prsnlArray.getJSONObject(0);
                    final String full_name = data.optString("full_name");
                    final String db = data.optString("dob");
                    final String gender = data.optString("gender");
                    final String address = data.optString("address");
                    final String city = data.optString("city");
                    disrct = data.optString("district");
                    final String state = data.optString("state");
                    final String country = data.optString("country");
                    final String email_id = data.optString("email_id");
                    final String ph_no = data.optString("ph_no");
                    final String father_name = data.optString("father_name");
                    final String mother_name = data.optString("mother_name");
                    final String married_status = data.optString("married_status");
                    final String language = data.optString("language");
                    final String hobbies = data.optString("hobbies");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            email.setText(email_id);
                            phno.setText(ph_no);
                            addr.setText(address);
                            cty.setText(city);
                            ste.setText(state);
                            cntry.setText(country);
                            dob.setText(db);
                            gen.setText(gender);
                            fName.setText(father_name);
                            mName.setText(mother_name);
                            rel.setText(married_status);
                            lang.setText(language);
                            hooby.setText(hobbies);

                        }
                    });
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

    private void getIntro() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.intro_dialog, null, false);
        about = (EditText) v.findViewById(R.id.intro);
        AlertDialog.Builder builder = new AlertDialog.Builder(ResumeEditActivity.this)
                .setView(v);
        builder.setTitle("Introduction")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (about.getText().toString().length() == 0) {
                            about.setError("Invalid Enter About You");
                        } else {
                            mApp.getPreference().edit().putString(Common.INTRO, about.getText().toString()).commit();
                            new UpdateStatus(getApplicationContext(), mApp.getPreference().getString(Common.u_id, ""), about.getText().toString()).execute();
                            dialog.cancel();
                            Intent in = getIntent();
                            finish();
                            startActivity(in);
                        }
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}

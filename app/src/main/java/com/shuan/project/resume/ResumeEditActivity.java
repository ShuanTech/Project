package com.shuan.project.resume;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.DeleteDetail;
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
    private LinearLayout pro, disPro, wrk_detail, wrk_exprince, qualifiy, prjct, skll, certify, achieve, extra, prsnal;
    private HashMap<String, String> rData;
    private ArrayList<Sample> list;
    private PopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_edit);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Update");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        senior = (RelativeLayout) findViewById(R.id.senior);
        pro = (LinearLayout) findViewById(R.id.pro);
        disPro = (LinearLayout) findViewById(R.id.dis_pro);
        wrk_detail = (LinearLayout) findViewById(R.id.wrk_detail);
        wrk_exprince = (LinearLayout) findViewById(R.id.wrk_exprince);
        qualifiy = (LinearLayout) findViewById(R.id.qualify);
        skll = (LinearLayout) findViewById(R.id.skll);
        prjct = (LinearLayout) findViewById(R.id.prjct);
        certify = (LinearLayout) findViewById(R.id.certify);
        achieve = (LinearLayout) findViewById(R.id.achieve);
        extra = (LinearLayout) findViewById(R.id.extra);
        prsnal = (LinearLayout) findViewById(R.id.prsnal);

        pro = (LinearLayout) findViewById(R.id.pro);

        if (!mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            senior.setVisibility(View.GONE);
        }

        list = new ArrayList<Sample>();
        new GetResumeData().execute();

        pro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(ResumeEditActivity.this, UpdateResumeActivity.class);
        switch (v.getId()) {
            case R.id.pro:
                in.putExtra("what", "add");
                in.putExtra("which", "pro");
                break;
        }
        startActivity(in);
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
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
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
                                                        //Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();

                                                        break;
                                                    case R.id.del:
                                                        //Toast.makeText(getApplicationContext(),pId,Toast.LENGTH_SHORT).show();
                                                        new DeleteDetail(ResumeEditActivity.this,pId,"proSum").execute();
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
                    final JSONObject wrk = child.getJSONObject("wrk");
                    JSONArray wrkArray = wrk.getJSONArray("wrk");

                    for (int i = 0; i < wrkArray.length(); i++) {
                        JSONObject data = wrkArray.getJSONObject(i);
                        final String position = data.optString("position");
                        final String org_name = data.optString("org_name");
                        final String location = data.optString("location");
                        final String from_date = data.optString("from_date");
                        final String to_date = data.optString("to_date");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.wrk_lay, null);
                                TextView txt = (TextView) v.findViewById(R.id.wrk);
                                ImageView img = (ImageView) v.findViewById(R.id.img);
                                img.setVisibility(View.GONE);
                                txt.setText(position + " at " + org_name + ", " + location + " . " + from_date + " - " + to_date);
                                wrk_detail.addView(v);
                            }
                        });
                    }

                    JSONObject wrkExp = child.getJSONObject("wrk_exp");
                    JSONArray wrkExpArray = wrkExp.getJSONArray("wrk_exp");
                    for (int i = 0; i < wrkExpArray.length(); i++) {
                        JSONObject data = wrkExpArray.getJSONObject(i);
                        final String exp = data.optString("wrk_exp");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.wrk_lay, null);
                                TextView txt = (TextView) v.findViewById(R.id.wrk);
                                ImageView img = (ImageView) v.findViewById(R.id.img);
                                img.setVisibility(View.GONE);
                                txt.setText(exp);
                                wrk_exprince.addView(v);
                            }
                        });
                    }

                    final JSONObject qualify = child.getJSONObject("edu");
                    JSONArray qualifyArray = qualify.getJSONArray("edu");
                    for (int i = 0; i < qualifyArray.length(); i++) {
                        JSONObject data = qualifyArray.getJSONObject(i);
                        final String concentration = data.optString("concentration");
                        final String ins_name = data.optString("ins_name");
                        final String location = data.optString("location");
                        final String aggregate = data.optString("aggregate");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.wrk_lay, null);
                                TextView txt = (TextView) v.findViewById(R.id.wrk);
                                ImageView img = (ImageView) v.findViewById(R.id.img);
                                img.setVisibility(View.GONE);
                                txt.setText(concentration + " , " + ins_name + " at " + location + " with " + aggregate + "%");
                                qualifiy.addView(v);
                            }
                        });
                    }

                    JSONObject skill = child.getJSONObject("skill");
                    JSONArray skillArray = skill.getJSONArray("skill");
                    for (int i = 0; i < skillArray.length(); i++) {
                        JSONObject data = skillArray.getJSONObject(i);
                        final String lang_known = data.optString("lang_known");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.wrk_lay, null);
                                TextView txt = (TextView) v.findViewById(R.id.wrk);
                                ImageView img = (ImageView) v.findViewById(R.id.img);
                                img.setVisibility(View.GONE);
                                txt.setText(lang_known);
                                skll.addView(v);
                            }
                        });
                    }

                    JSONObject pjct = child.getJSONObject("project");
                    JSONArray pjctArray = pjct.getJSONArray("project");
                    for (int i = 0; i < pjctArray.length(); i++) {
                        JSONObject data = pjctArray.getJSONObject(i);
                        final String p_title = data.optString("p_title");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.wrk_lay, null);
                                TextView txt = (TextView) v.findViewById(R.id.wrk);
                                ImageView img = (ImageView) v.findViewById(R.id.img);
                                img.setVisibility(View.GONE);
                                txt.setText(p_title);
                                prjct.addView(v);
                            }
                        });
                    }
                    final JSONObject cert = child.getJSONObject("cert");
                    JSONArray certArray = cert.getJSONArray("cert");
                    for (int i = 0; i < certArray.length(); i++) {
                        JSONObject data = certArray.getJSONObject(i);
                        final String certName = data.optString("cer_name");
                        final String certCentre = data.optString("cer_centre");

                        if (certName.equalsIgnoreCase("")) {
                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.wrk_lay, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    ImageView img = (ImageView) v.findViewById(R.id.img);
                                    img.setVisibility(View.GONE);
                                    txt.setText(certName + " at " + certCentre);
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

                        if (a_name.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.wrk_lay, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    ImageView img = (ImageView) v.findViewById(R.id.img);
                                    img.setVisibility(View.GONE);
                                    txt.setText(a_name);
                                    achieve.addView(v);
                                }
                            });
                        }


                    }

                    final JSONObject exta = child.getJSONObject("extra");
                    JSONArray extaArray = exta.getJSONArray("extra");
                    for (int i = 0; i < extaArray.length(); i++) {
                        JSONObject data = extaArray.getJSONObject(i);
                        final String extrac = data.optString("extra_curricular");

                        if (extrac.equalsIgnoreCase("")) {
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View v = inflater.inflate(R.layout.wrk_lay, null);
                                    TextView txt = (TextView) v.findViewById(R.id.wrk);
                                    ImageView img = (ImageView) v.findViewById(R.id.img);
                                    img.setVisibility(View.GONE);
                                    txt.setText(extrac);
                                    extra.addView(v);
                                }
                            });
                        }


                    }

                    final JSONObject prsnl = child.getJSONObject("prsnl");
                    JSONArray prsnlArray = prsnl.getJSONArray("prsnl");

                    JSONObject data = prsnlArray.getJSONObject(0);
                    final String full_name = data.optString("full_name");
                    final String dob = data.optString("dob");
                    final String address = data.optString("address");
                    final String father_name = data.optString("father_name");
                    final String mother_name = data.optString("mother_name");
                    final String language = data.optString("language");
                    final String hobbies = data.optString("hobbies");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.wrk_lay, null);
                            TextView txt = (TextView) v.findViewById(R.id.wrk);
                            ImageView img = (ImageView) v.findViewById(R.id.img);
                            img.setVisibility(View.GONE);
                            txt.setText("Name : " + full_name + "\n" +
                                    "Date Of Birth : " + dob + "\n" +
                                    "Father Name : " + father_name + "\n" +
                                    "Mother Name : " + mother_name + "\n" +
                                    "Address : " + address + "\n" +
                                    "Languages Known : " + language + "\n" +
                                    "Hobbies : " + hobbies);
                            prsnal.addView(v);
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
}

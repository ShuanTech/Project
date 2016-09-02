package com.shuan.project.signup.employee;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.adapter.InstitutionAdapter;
import com.shuan.project.asyncTasks.Connect;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class EducationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView clgName, univ, loc, conCent;
    private EditText fY, fM, fD, tY, tM, tD, agrt;
    private TextView frm, to, qfy;
    private Button q_next, q_skip;
    private HashMap<String, String> eData;
    private String[] mnth = new String[0];
    private String[] date = new String[0];
    private RelativeLayout frm_mnth, frm_date, to_mnth, to_date;
    private ProgressBar progressBar;
    private boolean ins = false;
    private boolean cIns = false;
    private ArrayList<Sample> list;
    private InstitutionAdapter adapter;
    private ScrollView scrollView;
    private String[] cours = new String[0];
    private String frmDate, toDate;
    private boolean exit = false;
    private String[] qulify = new String[]{"PG", "UG", "DIPLOMA"};
    private String q;
    private Spinner level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        list = new ArrayList<Sample>();
        new getInstitution().execute();
        new getCourse().execute();

        level = (Spinner) findViewById(R.id.level);
        clgName = (AutoCompleteTextView) findViewById(R.id.clg_name);
        univ = (AutoCompleteTextView) findViewById(R.id.univ);
        loc = (AutoCompleteTextView) findViewById(R.id.location);
        fY = (EditText) findViewById(R.id.f_year);
        fM = (EditText) findViewById(R.id.f_month);
        fD = (EditText) findViewById(R.id.f_date);
        tY = (EditText) findViewById(R.id.t_year);
        tM = (EditText) findViewById(R.id.t_month);
        tD = (EditText) findViewById(R.id.t_date);
        frm = (TextView) findViewById(R.id.frm);
        to = (TextView) findViewById(R.id.to);
        conCent = (AutoCompleteTextView) findViewById(R.id.concent);
        agrt = (EditText) findViewById(R.id.agrt);
        q_skip = (Button) findViewById(R.id.q_skip);
        q_next = (Button) findViewById(R.id.q_next);
        qfy = (TextView) findViewById(R.id.qfy);

        frm_mnth = (RelativeLayout) findViewById(R.id.frm_mnth);
        frm_date = (RelativeLayout) findViewById(R.id.frm_dat);
        to_mnth = (RelativeLayout) findViewById(R.id.to_mnth);
        to_date = (RelativeLayout) findViewById(R.id.to_dat);
        mnth = getResources().getStringArray(R.array.month);
        date = getResources().getStringArray(R.array.date);


        qfy.setTypeface(helper.droid(getApplicationContext()));
        clgName.setTypeface(helper.droid(getApplicationContext()));
        univ.setTypeface(helper.droid(getApplicationContext()));
        loc.setTypeface(helper.droid(getApplicationContext()));
        fY.setTypeface(helper.droid(getApplicationContext()));
        fM.setTypeface(helper.droid(getApplicationContext()));
        fD.setTypeface(helper.droid(getApplicationContext()));
        tY.setTypeface(helper.droid(getApplicationContext()));
        tM.setTypeface(helper.droid(getApplicationContext()));
        tD.setTypeface(helper.droid(getApplicationContext()));
        frm.setTypeface(helper.droid(getApplicationContext()));
        to.setTypeface(helper.droid(getApplicationContext()));
        conCent.setTypeface(helper.droid(getApplicationContext()));
        agrt.setTypeface(helper.droid(getApplicationContext()));
        q_next.setTypeface(helper.droid(getApplicationContext()));
        q_skip.setTypeface(helper.droid(getApplicationContext()));

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, qulify);

        level.setAdapter(adapter1);
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String get = level.getSelectedItem().toString();
                if (get.equalsIgnoreCase("PG")) {
                    q = "1";
                    clgName.requestFocus();
                } else if (get.equalsIgnoreCase("UG")) {
                    q = "2";
                    clgName.requestFocus();
                } else {
                    q = "3";
                    clgName.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showMnth("fm");
                }
                return false;
            }
        });

        fD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDate("fd");
                }
                return false;
            }
        });

        tM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showMnth("tm");
                }
                return false;
            }
        });

        tD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDate("td");
                }
                return false;
            }
        });


        fY.addTextChangedListener(this);
        tY.addTextChangedListener(this);

        clgName.addTextChangedListener(this);

        q_next.setOnClickListener(this);
        q_skip.setOnClickListener(this);


    }

    private void showDate(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EducationActivity.this);
        builder.setTitle("Select Date")
                .setSingleChoiceItems(date, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (val.equalsIgnoreCase("fd")) {
                            fD.setText(date[which]);

                        } else {
                            tD.setText(date[which]);

                        }

                        dialog.cancel();
                    }
                }).show();
    }

    private void showMnth(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EducationActivity.this);
        builder.setTitle("Select Month")
                .setSingleChoiceItems(mnth, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (val.equalsIgnoreCase("fm")) {
                            fM.setText(mnth[which]);
                            frm_date.setVisibility(View.VISIBLE);
                        } else {
                            tM.setText(mnth[which]);
                            to_date.setVisibility(View.VISIBLE);
                        }

                        dialog.cancel();
                    }
                }).show();

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.q_next:
                if (univ.getText().toString().length() == 0) {
                    univ.setError("University Mandatory");
                    univ.requestFocus();
                } else if (loc.getText().toString().length() == 0) {
                    loc.setError("City / Town / Location Mandatory");
                    loc.requestFocus();
                } else if (fY.getText().toString().length() == 0) {
                    fY.setError("Year Mandatory");
                    fY.requestFocus();
                } else if (fM.getText().toString().length() == 0) {
                    fM.setError("Month Mandatory");
                    fM.requestFocus();
                } else if (fD.getText().toString().length() == 0) {
                    fD.setError("Date Mandatory");
                    fD.requestFocus();
                } else if (tY.getText().toString().length() == 0) {
                    tY.setError("Year Mandatory");
                    tY.requestFocus();
                } else if (tM.getText().toString().length() == 0) {
                    tM.setError("Month Mandatory");
                    tM.requestFocus();
                } else if (tD.getText().toString().length() == 0) {
                    tD.setError("Date Mandatory");
                    tD.requestFocus();
                } else if (conCent.getText().toString().length() == 0) {
                    conCent.setError("Concentration Mandatory");
                    conCent.requestFocus();
                } else if (agrt.getText().toString().length() == 0) {
                    agrt.setError("Aggregate Mandatory");
                    agrt.requestFocus();
                } else {
                    frmDate = fY.getText().toString() + "-" + fM.getText().toString() + "-" + fD.getText().toString();
                    toDate = tY.getText().toString() + "-" + tM.getText().toString() + "-" + tD.getText().toString();

                    new Qualification().execute();
                }


                break;
            case R.id.q_skip:
                mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, false).commit();
                mApp.getPreference().edit().putBoolean(Common.HSC, false).commit();
                mApp.getPreference().edit().putBoolean(Common.SSLC, false).commit();
                mApp.getPreference().edit().putBoolean(Common.ACTIVITY1, true).commit();
                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                    startActivity(new Intent(getApplicationContext(), SkillActivity.class));
                } else {
                    mApp.getPreference().edit().putBoolean(Common.ACTIVITY3, true).commit();
                    startActivity(new Intent(getApplicationContext(), PersonalActivity.class));
                }
                finish();
                break;

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (fY.getText().toString().length() == 4) {
            frm_mnth.setVisibility(View.VISIBLE);
        }
        if (tY.getText().toString().length() == 4) {
            to_mnth.setVisibility(View.VISIBLE);
        }
        if (clgName.getText().toString().length() == 0) {
            q_skip.setVisibility(View.VISIBLE);
            q_next.setVisibility(View.GONE);
        } else {
            q_skip.setVisibility(View.GONE);
            q_next.setVisibility(View.VISIBLE);
        }
    }


    public class getInstitution extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("id", "institution");

            String[] locate = new String[0];
            try {
                JSONObject json = Connection.UrlConnection(php.getInstitution, eData);
                int succ = json.getInt("success");
                if (succ == 0) {

                } else {
                    JSONArray jsonArray = json.getJSONArray("institution");

                    locate = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String insName = child.optString("ins_name");
                        String board = child.optString("board");
                        String location = child.optString("location");

                        locate[i] = location;

                        list.add(new Sample(insName + "," + location, insName, board, location));
                    }


                    final String[] finalLocate = locate;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new InstitutionAdapter(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, list);
                            clgName.setThreshold(1);
                            clgName.setAdapter(adapter);
                            clgName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView txt = (TextView) view.findViewById(R.id.ins_name);
                                    TextView txt2 = (TextView) view.findViewById(R.id.univ);
                                    TextView txt3 = (TextView) view.findViewById(R.id.loc);
                                    clgName.setText(txt.getText().toString());
                                    univ.setText(txt2.getText().toString());
                                    loc.setText(txt3.getText().toString());
                                    ins = true;
                                    conCent.requestFocus();
                                }
                            });
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
            scrollView.setVisibility(View.VISIBLE);

        }
    }

    public class getCourse extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            eData = new HashMap<String, String>();
            eData.put("id", "course");
            try {

                JSONObject json = Connection.UrlConnection(php.getCourse, eData);
                int succ = json.getInt("success");

                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("course");
                    cours = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String course = child.optString("course");
                        cours[i] = course;

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, cours);
                            conCent.setThreshold(1);
                            conCent.setAdapter(adapter);
                            cIns = true;
                            fY.requestFocus();

                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }

    public class Qualification extends AsyncTask<String, String, String> {


        String uClgName = clgName.getText().toString();
        String uUniv = univ.getText().toString();
        String uLoc = loc.getText().toString();
        String uConcent = conCent.getText().toString();
        String uAgrt = agrt.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("level", q);
            eData.put("concent", uConcent);
            eData.put("clgName", uClgName);
            eData.put("univ", uUniv);
            eData.put("loc", uLoc);
            eData.put("frm", frmDate);
            eData.put("to", toDate);
            eData.put("agrt", uAgrt);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }

            if (cIns == true) {
                eData.put("cInsrt", "false");
            } else {
                eData.put("cInsrt", "true");
            }
            eData.put("type","add");

            try {
                JSONObject json = Connection.UrlConnection(php.qualify, eData);

                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error...Try Again!...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "College Added Successfully", Toast.LENGTH_SHORT).show();
                            mApp.getPreference().edit().putString(Common.CLGNAME, uClgName).commit();
                            mApp.getPreference().edit().putString(Common.COURSE, uConcent).commit();
                            new Connect(getApplicationContext(), mApp.getPreference().getString(Common.u_id, ""),
                                    mApp.getPreference().getString(Common.LEVEL,"")).execute();
                            mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.HSC, false).commit();
                            mApp.getPreference().edit().putBoolean(Common.SSLC, false).commit();
                            mApp.getPreference().edit().putBoolean(Common.ACTIVITY1, true).commit();
                            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                                startActivity(new Intent(getApplicationContext(), SkillActivity.class));
                                finish();
                            } else {
                                mApp.getPreference().edit().putBoolean(Common.ACTIVITY3, true).commit();
                                startActivity(new Intent(getApplicationContext(), PersonalActivity.class));
                                finish();
                            }

                        }
                    });
                }
            } catch (Exception e) {
            }
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.ACTIVITY1, false).commit();
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press Back again to Cancel Signup Process.", Toast.LENGTH_SHORT).show();
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

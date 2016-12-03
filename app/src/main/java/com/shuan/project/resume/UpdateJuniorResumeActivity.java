package com.shuan.Project.resume;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.adapter.InstitutionAdapter;
import com.shuan.Project.adapter.LocationAdapter;
import com.shuan.Project.asyncTasks.GetSkillSet;
import com.shuan.Project.fragment.DateDialog;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UpdateJuniorResumeActivity extends AppCompatActivity implements View.OnClickListener {

    /*Global Fields*/
    private Helper helper = new Helper();
    private Common mApp;
    private HashMap<String, String> eData;
    private RelativeLayout lay1, lay2, lay3, lay4, lay5, lay6, lay7;
    private int i = 0, j = 0, k = 0, l = 0;
    private ProgressDialog pDialog;
    private boolean ins = false;
    private ArrayList<Sample> list;
    private InstitutionAdapter adapter;
    private String frmDate, toDate;
    private ProgressBar progressBar;
    private ScrollView scroll;


    /* College Fields */
    private AutoCompleteTextView clgName, conCent;
    private EditText univ, loc, frm_yr, to_yr, agrt;
    private String[] cours = new String[0];
    private boolean cIns = false;
    private String[] qulify = new String[]{"PG", "UG", "DIPLOMA"};
    private String q;
    private Spinner level;
    private Button q_update;
    private TextView frm, to, qfy;

    /* Higher Secondary Fields */
    private AutoCompleteTextView h_name;
    private EditText board, cty, sFrmyr, sTYr, hAgrt;
    private Button h_update;
    private RelativeLayout scroll2;
    private TextView hsc, h_frm, h_to;

    /* Secondary Fields */
    private AutoCompleteTextView s_name;
    private EditText s_board, s_cty, ssFrmyr, ssTYr, sAgrt;
    private Button s_update;
    private TextView sslc, s_frm, s_to;

    /* Skill Fields */
    private MultiAutoCompleteTextView skill, workArea;
    private EditText cercourse, cerCentre, cerDur, dev_env, others;
    private Button sk_update;
    private TextView tex, tex1;

    /* Hobbies Field */
    private EditText ach, extra, hobby, lang;
    private TextView ah, extra_c, ot;
    private Button o_update;

    /* Project Field */
    private EditText title, platform, role, team_sze, duration, url, description;
    private Button p_update;
    private TextView pr;
    private CheckBox acd;
    private String isAcd = "0";

    /* Personal Field */
    public EditText dob, fName, mName, addr, pinNo,age;
    public AutoCompleteTextView locate, district, state, country;
    public RadioButton radio, r1, r2;
    public RadioGroup sex;
    public LocationAdapter adapter1;
    public Button pr_update;
    private TextView prsnl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_junior_resume_data);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        lay1 = (RelativeLayout) findViewById(R.id.lay1);
        lay2 = (RelativeLayout) findViewById(R.id.lay2);
        lay3 = (RelativeLayout) findViewById(R.id.lay3);
        lay4 = (RelativeLayout) findViewById(R.id.lay4);
        lay5 = (RelativeLayout) findViewById(R.id.lay5);
        lay6 = (RelativeLayout) findViewById(R.id.lay6);
        lay7 = (RelativeLayout) findViewById(R.id.lay7);

        checkView();


    }

    private void checkView() {

        if (mApp.getPreference().getBoolean(Common.QUALIFICATION, false) == false) {
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

            list = new ArrayList<Sample>();
            new getInstitution().execute();
            new getCourse().execute();

            level = (Spinner) findViewById(R.id.level);
            clgName = (AutoCompleteTextView) findViewById(R.id.clg_name);
            univ = (EditText) findViewById(R.id.univ);
            loc = (EditText) findViewById(R.id.c_location);
            frm = (TextView) findViewById(R.id.frm);
            to = (TextView) findViewById(R.id.to);
            conCent = (AutoCompleteTextView) findViewById(R.id.concent);
            frm_yr = (EditText) findViewById(R.id.frm_yr);
            to_yr = (EditText) findViewById(R.id.to_yr);
            agrt = (EditText) findViewById(R.id.agrt);


            q_update = (Button) findViewById(R.id.q_update);


            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, qulify);

            level.setAdapter(adapter1);
            level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String get = level.getSelectedItem().toString();
                    if (get.equalsIgnoreCase("PG")) {
                        q = "1";
                    } else if (get.equalsIgnoreCase("UG")) {
                        q = "2";
                    } else {
                        q = "3";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            q_update.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.HSC, false) == false) {

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

            list = new ArrayList<Sample>();
            new getSchool().execute();


            h_name = (AutoCompleteTextView) findViewById(R.id.h_name);
            board = (EditText) findViewById(R.id.board);
            cty = (EditText) findViewById(R.id.cty);
            sFrmyr = (EditText) findViewById(R.id.s_frm_yr);
            sTYr = (EditText) findViewById(R.id.s_to_yr);
            hAgrt = (EditText) findViewById(R.id.h_agrt);


            h_update = (Button) findViewById(R.id.h_update);


            h_update.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.SSLC, false) == false) {
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.VISIBLE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);


            s_name = (AutoCompleteTextView) findViewById(R.id.s_name);
            s_board = (EditText) findViewById(R.id.s_board);
            s_cty = (EditText) findViewById(R.id.s_cty);
            ssFrmyr = (EditText) findViewById(R.id.ss_frm_yr);
            ssTYr = (EditText) findViewById(R.id.ss_to_yr);
            sAgrt = (EditText) findViewById(R.id.s_agrt);

            list = new ArrayList<Sample>();
            new getSchool().execute();


            s_update = (Button) findViewById(R.id.s_update);


            s_update.setOnClickListener(this);


        } else if (mApp.getPreference().getBoolean(Common.SKILL, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.VISIBLE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

            skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);

            cercourse = (EditText) findViewById(R.id.cer_name);
            cerCentre = (EditText) findViewById(R.id.cer_centre);
            cerDur = (EditText) findViewById(R.id.cer_duration);
            sk_update = (Button) findViewById(R.id.sk_update);
            tex = (TextView) findViewById(R.id.tex);
            new GetSkillSet(UpdateJuniorResumeActivity.this, scroll, progressBar, skill).execute();

            sk_update.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.HOBBIES, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.VISIBLE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

            ach = (EditText) findViewById(R.id.ach);
            extra = (EditText) findViewById(R.id.extra);
            hobby = (EditText) findViewById(R.id.hobby);
            lang = (EditText) findViewById(R.id.lang);
            o_update = (Button) findViewById(R.id.o_update);
            ah = (TextView) findViewById(R.id.ah);
            extra_c = (TextView) findViewById(R.id.extra_c);
            ot = (TextView) findViewById(R.id.ot);
            o_update.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.PROJECT, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.VISIBLE);
            lay7.setVisibility(View.GONE);

            pr = (TextView) findViewById(R.id.pr);
            title = (EditText) findViewById(R.id.title);
            platform = (EditText) findViewById(R.id.platform);
            role = (EditText) findViewById(R.id.role);
            team_sze = (EditText) findViewById(R.id.team_sze);
            duration = (EditText) findViewById(R.id.dur);

            description = (EditText) findViewById(R.id.prjct_des);

            p_update = (Button) findViewById(R.id.p_update);

            p_update.setOnClickListener(this);
            acd.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.PERSONALINFO, false) == false) {

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.VISIBLE);

            list = new ArrayList<Sample>();

            new getLocation().execute();

            prsnl = (TextView) findViewById(R.id.prsnl);
            dob = (EditText) findViewById(R.id.dob);
            age= (EditText) findViewById(R.id.age);
            r1 = (RadioButton) findViewById(R.id.male);
            r2 = (RadioButton) findViewById(R.id.female);
            fName = (EditText) findViewById(R.id.f_name);
            mName = (EditText) findViewById(R.id.m_name);
            addr = (EditText) findViewById(R.id.door);
            locate = (AutoCompleteTextView) findViewById(R.id.locate);
            district = (AutoCompleteTextView) findViewById(R.id.district);
            state = (AutoCompleteTextView) findViewById(R.id.state);
            country = (AutoCompleteTextView) findViewById(R.id.cntry);
            pinNo = (EditText) findViewById(R.id.pin);
            sex = (RadioGroup) findViewById(R.id.sex);


            dob.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   /*InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*/
                        DateDialog dialog = new DateDialog(v);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        dialog.show(ft, "DataPicker");
                    }
                    return false;
                }
            });
            dob.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    calcAge(dob.getText().toString());
                }
            });

            pr_update = (Button) findViewById(R.id.pr_update);
            pr_update.setOnClickListener(this);


        } else {
            startActivity(new Intent(getApplicationContext(), JuniorResumeGenerate.class));
            finish();
        }

    }

    private void calcAge(String db) {
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        Date dt=new Date();
        try{
            dt=format.parse(db);
            Date today=new Date();
            int by=dt.getYear();
            int ty=today.getYear();
            age.setText(String.valueOf(ty-by));

        }catch (Exception e){}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.q_update:
                if (clgName.getText().toString().length() == 0) {
                    clgName.setError("College Mandatory");
                    clgName.requestFocus();
                } else if (univ.getText().toString().length() == 0) {
                    univ.setError("University Mandatory");
                    univ.requestFocus();
                } else if (loc.getText().toString().length() == 0) {
                    loc.setError("City / Town / Location Mandatory");
                    loc.requestFocus();
                } else if (frm_yr.getText().toString().length() == 0) {
                    frm_yr.setError("Field Mandatory");
                } else if (to_yr.getText().toString().length() == 0) {
                    to_yr.setError("Field Mandatory");
                } else if (conCent.getText().toString().length() == 0) {
                    conCent.setError("Concentration Mandatory");
                    conCent.requestFocus();
                } else if (agrt.getText().toString().length() == 0) {
                    agrt.setError("Aggregate Mandatory");
                    agrt.requestFocus();
                } else {

                    new updateQualification().execute();
                }
                break;
            case R.id.h_update:
                if (h_name.getText().toString().length() == 0) {
                    h_name.setError("Field Mandatory");
                } else if (board.getText().toString().length() == 0) {
                    board.setError("Field Mandatory");
                } else if (cty.getText().toString().length() == 0) {
                    cty.setError("Field Mandatory");
                } else if (sFrmyr.getText().toString().length() == 0) {
                    sFrmyr.setError("Field Mandatory");
                    sFrmyr.requestFocus();
                } else if (sTYr.getText().toString().length() == 0) {
                    sTYr.setError("Field Mandatory");
                    sTYr.requestFocus();
                } else if (hAgrt.getText().toString().length() == 0) {
                    hAgrt.setError("Field Mandatory");
                } else {
                    new updateHSC().execute();
                }
                break;
            case R.id.s_update:
                if (s_name.getText().toString().length() == 0) {
                    s_name.setError("Field Mandatory");
                } else if (s_board.getText().toString().length() == 0) {
                    s_board.setError("Field Mandatory");
                } else if (s_cty.getText().toString().length() == 0) {
                    s_cty.setError("Field Mandatory");
                } else if (ssFrmyr.getText().toString().length() == 0) {
                    ssFrmyr.setError("Field Mandatory");
                    ssFrmyr.requestFocus();
                } else if (ssTYr.getText().toString().length() == 0) {
                    ssTYr.setError("Field Mandatory");
                    ssTYr.requestFocus();
                } else if (sAgrt.getText().toString().length() == 0) {
                    sAgrt.setError("Field Mandatory");
                } else {

                    new updateSSLC().execute();
                }
                break;
            case R.id.sk_update:
                if (skill.getText().toString().length() == 0) {
                    skill.setError("Skill Mandatory");
                }  else {
                    if (cercourse.getText().toString().length() != 0) {

                        if (cerCentre.getText().toString().length() == 0) {
                            cerCentre.setError("Center Name Mandatory");
                            cerCentre.requestFocus();
                        } else if (cerDur.getText().toString().length() == 0) {
                            cerDur.setError("Course Duration Mandatory");
                            cerDur.requestFocus();
                        } else {
                            new Skill().execute();
                            new Certificate().execute();
                        }
                    } else {
                        new Skill().execute();
                    }
                }
                break;
            case R.id.o_update:
                new achievement().execute();
                new extraCurricular().execute();
                new hobby().execute();
                break;
            case R.id.p_update:
                if (title.getText().toString().length() == 0) {
                    title.setError("Field Mandatory");
                } else if (platform.getText().toString().length() == 0) {
                    platform.setError("Field Mandatory");
                } else if (duration.getText().toString().length() == 0) {
                    duration.setError("Field Mandatory");
                } else if (description.getText().toString().length() == 0) {
                    description.setError("Field Mandatory");
                } else {
                    new updateProject().execute();
                }

                break;
            case R.id.pr_update:
                int getSex = sex.getCheckedRadioButtonId();
                radio = (RadioButton) findViewById(getSex);
                if (fName.getText().toString().length() == 0) {
                    fName.setError("Father Name Mandatory");
                    fName.requestFocus();
                } else if (mName.getText().toString().length() == 0) {
                    mName.setError("Mother Name Mandatory");
                    mName.requestFocus();
                } else if (addr.getText().toString().length() == 0) {
                    addr.setError("Address Mandatory");
                    addr.requestFocus();
                } else if (locate.getText().toString().length() == 0) {
                    locate.setError("City / Town / Location Mandatory");
                    locate.requestFocus();
                } else if (district.getText().toString().length() == 0) {
                    district.setError("District Mandatory");
                    district.requestFocus();
                } else if (state.getText().toString().length() == 0) {
                    state.setError("State Mandatory");
                    state.requestFocus();
                } else if (country.getText().toString().length() == 0) {
                    country.setError("Country Mandatory");
                    country.requestFocus();
                } else if (pinNo.getText().toString().length() == 0) {
                    pinNo.setError("Pincode Mandatory");
                    pinNo.requestFocus();
                } else {
                    new Personal().execute();
                }
                break;
        }
    }


    public class Personal extends AsyncTask<String, String, String> {

        String uDob = dob.getText().toString();
        String uAge=age.getText().toString();
        String usex = radio.getText().toString();
        String ufName = fName.getText().toString();
        String umName = mName.getText().toString();
        String udno = addr.getText().toString();
        String uct = locate.getText().toString();
        String udistirct = district.getText().toString();
        String ustate = state.getText().toString();
        String ucnt = country.getText().toString();
        String upin = pinNo.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Project Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("dob", uDob);
            eData.put("age",uAge);
            eData.put("gender", usex);
            eData.put("address", udno);
            eData.put("city", uct);
            eData.put("district", udistirct);
            eData.put("state", ustate);
            eData.put("country", ucnt);
            eData.put("pincode", upin);
            eData.put("father_name", ufName);
            eData.put("mother_name", umName);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }
            try {
                JSONObject json = Connection.UrlConnection(php.persnal_info, eData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error... Try Again!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                            mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, true).commit();
                            checkView();
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
            pDialog.cancel();
        }
    }

    public class getLocation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("id", "city");
            try {

                JSONObject json = Connection.UrlConnection(php.getCity, eData);
                int succ = json.getInt("success");

                if (succ == 0) {
                } else {

                    JSONArray jsonArray = json.getJSONArray("city");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String xcty = child.optString("city");
                        String distrt = child.optString("district");
                        String stea = child.optString("state");
                        String con = child.optString("country");


                        list.add(new Sample(xcty + "," + distrt, xcty, distrt, stea, con));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter1 = new LocationAdapter(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, list);
                            locate.setThreshold(1);
                            locate.setAdapter(adapter1);
                            locate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                                    TextView txt2 = (TextView) view.findViewById(R.id.univ);
                                    TextView txt3 = (TextView) view.findViewById(R.id.loc);
                                    TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                                    locate.setText(txt1.getText().toString());
                                    district.setText(txt2.getText().toString());
                                    state.setText(txt3.getText().toString());
                                    country.setText(txt4.getText().toString());
                                    ins = true;

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
        }
    }

    public class updateProject extends AsyncTask<String, String, String> {
        String uTitle = title.getText().toString();
        String uPlatform = platform.getText().toString();
        String uRole = role.getText().toString();
        String uTeamSze = team_sze.getText().toString();
        String uDur = duration.getText().toString();
        String udesc = description.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Project Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("title", uTitle);
            eData.put("platform", uPlatform);
            eData.put("role", uRole);
            eData.put("teamSze", uTeamSze);
            eData.put("dur", uDur);
            eData.put("desc", udesc);
            eData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.project, eData);
                int succ = json.getInt("success");
                if (succ == 0) {

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.PROJECT, true).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Updated Project Details", Toast.LENGTH_SHORT).show();


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
            pDialog.cancel();
            checkView();
        }
    }


    public class hobby extends AsyncTask<String, String, String> {
        String uHobby = hobby.getText().toString();
        String uLang = lang.getText().toString();

        @Override
        protected String doInBackground(String... params) {

            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("hobby", uHobby);
            eData.put("lang", uLang);
            try {
                JSONObject json = Connection.UrlConnection(php.hobbyLang, eData);

            } catch (Exception e) {
            }

            return null;
        }
    }


    public class extraCurricular extends AsyncTask<String, String, String> {

        String ex = extra.getText().toString();

        @Override
        protected String doInBackground(String... params) {


            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("extra", ex);
            try {
                JSONObject json = Connection.UrlConnection(php.extra, eData);
                int succ = json.getInt("success");


            } catch (Exception e) {
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                    mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 4).commit();
                    mApp.getPreference().edit().putBoolean(Common.HOBBIES, true).commit();
                    Toast.makeText(getApplicationContext(), "Extra Curricular Activities Saved Successfully!...", Toast.LENGTH_SHORT).show();


                }
            });
            pDialog.cancel();
            checkView();
        }
    }

    public class achievement extends AsyncTask<String, String, String> {

        String ac = ach.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


                eData = new HashMap<String, String>();
                eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                eData.put("a_name", ac);
                try {
                    JSONObject json = Connection.UrlConnection(php.achievement, eData);
                    int succ = json.getInt("success");


                } catch (Exception e) {
                }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }
    }

    public class Skill extends AsyncTask<String, String, String> {

        String sSkill = skill.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("skill", sSkill);

            try {
                JSONObject json = Connection.UrlConnection(php.skill, eData);
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
                            mApp.getPreference().edit().putBoolean(Common.SKILL, true).commit();
                            Toast.makeText(getApplicationContext(), "Skill Updated Successfully!...", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            } catch (JSONException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            checkView();
        }
    }

    public class Certificate extends AsyncTask<String, String, String> {

        String cName = cercourse.getText().toString();
        String cCentre = cerCentre.getText().toString();
        String cDuration = cerDur.getText().toString();

        @Override
        protected String doInBackground(String... params) {

            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("cer_name", cName);
            eData.put("cer_centre", cCentre);
            eData.put("cer_dur", cDuration);
            eData.put("type", "add");
            try {
                JSONObject json = Connection.UrlConnection(php.certification, eData);
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
                            Toast.makeText(getApplicationContext(), "Certification Details Saved", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            } catch (JSONException e) {

            }

            return null;
        }
    }

    public class updateSSLC extends AsyncTask<String, String, String> {
        String usName = s_name.getText().toString();
        String usBoard = s_board.getText().toString();
        String usCty = s_cty.getText().toString();
        String usPercent = sAgrt.getText().toString();
        String uFrm = ssFrmyr.getText().toString();
        String uTo = ssTYr.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating SSLC!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("level", "5");
            eData.put("concent", "SSLC");
            eData.put("hName", usName);
            eData.put("bName", usBoard);
            eData.put("cty", usCty);
            eData.put("frmDat", uFrm);
            eData.put("toDat", uTo);
            eData.put("agrt", usPercent);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }
            eData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.schooling, eData);

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
                            mApp.getPreference().edit().putBoolean(Common.SSLC, true).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Completed!...Education Information", Toast.LENGTH_SHORT).show();
                            ins = false;
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
            pDialog.cancel();
            checkView();
        }
    }


    public class updateHSC extends AsyncTask<String, String, String> {

        String uhname = h_name.getText().toString();
        String uhbname = board.getText().toString();
        String ucty = cty.getText().toString();
        String upercent = hAgrt.getText().toString();
        String uFrm = sFrmyr.getText().toString();
        String uTo = sTYr.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating HSC!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("level", "4");
            eData.put("concent", "HSC");
            eData.put("hName", uhname);
            eData.put("bName", uhbname);
            eData.put("cty", ucty);
            eData.put("frmDat", uFrm);
            eData.put("toDat", uTo);
            eData.put("agrt", upercent);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }
            eData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.schooling, eData);

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
                            mApp.getPreference().edit().putBoolean(Common.HSC, true).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Update HSC Details!...", Toast.LENGTH_SHORT).show();
                            ins = false;
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
            pDialog.cancel();
            checkView();

        }
    }

    public class getSchool extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            list.clear();
            eData = new HashMap<String, String>();
            eData.put("id", "school");

            try {
                JSONObject json = Connection.UrlConnection(php.getSchool, eData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {

                    JSONArray jsonArray = json.getJSONArray("school");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        String insName = child.optString("ins_name");
                        String board = child.optString("board");
                        String location = child.optString("location");

                        list.add(new Sample(insName + "," + location, insName, board, location));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mApp.getPreference().getBoolean(Common.HSC, false) == false) {
                                adapter = new InstitutionAdapter(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, list);
                                h_name.setThreshold(1);
                                h_name.setAdapter(adapter);
                                h_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView txt = (TextView) view.findViewById(R.id.ins_name);
                                        TextView txt2 = (TextView) view.findViewById(R.id.univ);
                                        TextView txt3 = (TextView) view.findViewById(R.id.loc);
                                        h_name.setText(txt.getText().toString());
                                        board.setText(txt2.getText().toString());
                                        cty.setText(txt3.getText().toString());
                                        ins = true;
                                        sFrmyr.requestFocus();

                                    }
                                });
                            } else {
                                adapter = new InstitutionAdapter(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, list);
                                s_name.setThreshold(1);
                                s_name.setAdapter(adapter);
                                s_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView txt = (TextView) view.findViewById(R.id.ins_name);
                                        TextView txt2 = (TextView) view.findViewById(R.id.univ);
                                        TextView txt3 = (TextView) view.findViewById(R.id.loc);
                                        s_name.setText(txt.getText().toString());
                                        s_board.setText(txt2.getText().toString());
                                        s_cty.setText(txt3.getText().toString());
                                        ins = true;
                                        ssFrmyr.requestFocus();
                                    }
                                });
                            }
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
            scroll.setVisibility(View.VISIBLE);
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
                            frm_yr.requestFocus();

                        }
                    });
                }

            } catch (Exception e) {
            }
            return null;
        }
    }


    public class updateQualification extends AsyncTask<String, String, String> {

        String uClgName = clgName.getText().toString();
        String uUniv = univ.getText().toString();
        String uLoc = loc.getText().toString();
        String uConcent = conCent.getText().toString();
        String uAgrt = agrt.getText().toString();
        String uFrm = frm_yr.getText().toString();
        String uTo = to_yr.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Qualification!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            eData = new HashMap<String, String>();
            eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            eData.put("level", q);
            eData.put("concent", uConcent);
            eData.put("clgName", uClgName);
            eData.put("univ", uUniv);
            eData.put("loc", uLoc);
            eData.put("frm", uFrm);
            eData.put("to", uTo);
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
            eData.put("type", "add");


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
                            Toast.makeText(getApplicationContext(), "Successfully Updated Qualification!...", Toast.LENGTH_SHORT).show();
                            mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, true).commit();
                            ins = false;

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
            pDialog.cancel();
            checkView();
        }
    }


}

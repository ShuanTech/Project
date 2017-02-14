package com.shuan.Project.resume;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.Utils.MonthYearPicker;
import com.shuan.Project.Utils.MonthYearPicker1;
import com.shuan.Project.asyncTasks.AddAchieve;
import com.shuan.Project.asyncTasks.AddBasicInfo;
import com.shuan.Project.asyncTasks.AddCert;
import com.shuan.Project.asyncTasks.AddCollegedetail;
import com.shuan.Project.asyncTasks.AddContactInfo;
import com.shuan.Project.asyncTasks.AddExtra;
import com.shuan.Project.asyncTasks.AddProject;
import com.shuan.Project.asyncTasks.AddSchool;
import com.shuan.Project.asyncTasks.AddWrkDetail;
import com.shuan.Project.asyncTasks.EditDetail;
import com.shuan.Project.asyncTasks.EditWrkDetail;
import com.shuan.Project.asyncTasks.GetCollege;
import com.shuan.Project.asyncTasks.GetCourse;
import com.shuan.Project.asyncTasks.GetLocation;
import com.shuan.Project.asyncTasks.GetOrg;
import com.shuan.Project.asyncTasks.GetSchool;
import com.shuan.Project.asyncTasks.GetSkillSet;
import com.shuan.Project.asyncTasks.UpdateStatus;
import com.shuan.Project.asyncTasks.profileSummaryUpdate;
import com.shuan.Project.fragment.DateDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UpdateResumeActivity extends AppCompatActivity implements View.OnClickListener {

    private String what, which;
    private Common mApp;
    private int i = 0, j = 0, k = 0, l = 0;
    private Helper helper = new Helper();
    private LinearLayout psEdt, wrkDet, wrkExp, clg, sch, skill, prjct, cert, ach, exc, cntInfo, bsc, objec;
    private HashMap<String, String> seniorData;
    private ProgressDialog pDialog;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private boolean ins = false;
    private boolean sIns = false;
    private String frmDate;


    /* Profile Summary Field */
    private Button psEdtBut;
    private TextView ps;
    private EditText psEdtTxt;

    /* Work Details Field */
    private AutoCompleteTextView orgname;
    private EditText postition, location, fYr, tYr, present;
    private Button wkDetails;
    private CheckBox wrking;
    private TextView add_wrk;
    private boolean visible = false;
    private String toDate;
    private MonthYearPicker myp;
    private MonthYearPicker1 myp1;
    private boolean Ins = false;
    private int yr1, yr2;
    /* Work Summary Field */
    private EditText weEdtTxt;
    private Button weEdtBut;

    /* College Fields */
    private AutoCompleteTextView clgName, conCent;
    private EditText univ, loc, frm_yr, to_yr, agrt;
    private String[] cours = new String[0];
    private boolean cIns = false;
    private String[] qulify = new String[]{"Select Qualification", "PG", "UG", "DIPLOMA"};
    private String q, get;
    private Spinner level;
    private String abcd;
    private TextView tv1, tv2;
    private Button q_update;
    private TextView frm, to, qfy;

    /* School Fields */
    private AutoCompleteTextView h_name;
    private EditText board, cty, sFrmyr, sTYr, hAgrt;
    private Spinner mode;
    private String[] schl = new String[]{"Mode of School", "HSC", "SSLC"};
    private String s, set;
    private Button h_update;

    /* Skill Set */
    private MultiAutoCompleteTextView skll;
    private Button addSkll;

    /* Project Field */
    private EditText title, platform, role, team_sze, duration, url, description;
    private Button p_update;
    private TextView pr;
    private CheckBox acd;
    private String isAcd = "0";

    /* Certification */
    private EditText certName, certCentre, certDur;
    private Button certUpt;

    /* Achievement */
    private EditText acheieve;
    private Button addAch;

    /* Extra Curricular Activity */
    private EditText extra;
    private Button addEx;

    /* Contact Info */
    private EditText addr, district, state, country, pin;
    private AutoCompleteTextView city;
    private Button cntAdd;

    /* Basic Info */
    public EditText dob, fName, mName, rel, lang, hobby, age;
    public RadioButton radio, r1, r2;
    public RadioGroup sex;
    public Button bscAdd;

    /* Objective */
    public EditText obj;
    public Button objEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        what = getIntent().getStringExtra("what");
        which = getIntent().getStringExtra("which");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_resume);


        psEdt = (LinearLayout) findViewById(R.id.ps_edit);
        wrkDet = (LinearLayout) findViewById(R.id.wrk_det_edit);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        wrkExp = (LinearLayout) findViewById(R.id.wrk_exp);
        clg = (LinearLayout) findViewById(R.id.clg);
        sch = (LinearLayout) findViewById(R.id.add_school);
        skill = (LinearLayout) findViewById(R.id.skill);
        prjct = (LinearLayout) findViewById(R.id.project);
        cert = (LinearLayout) findViewById(R.id.cert);
        ach = (LinearLayout) findViewById(R.id.ach);
        exc = (LinearLayout) findViewById(R.id.extra);
        cntInfo = (LinearLayout) findViewById(R.id.cntInfo);
        bsc = (LinearLayout) findViewById(R.id.bsc);
        objec = (LinearLayout) findViewById(R.id.objec);

        AddDetail();


    }


    private void AddDetail() {
        if (which.equalsIgnoreCase("proSum")) {
            psEdt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ps = (TextView) findViewById(R.id.ps);
            psEdtTxt = (EditText) findViewById(R.id.ps_edit_txt);
            psEdtBut = (Button) findViewById(R.id.ps_edit_but);

            if (what.equalsIgnoreCase("edit")) {

                psEdtTxt.setText(mApp.getPreference().getString("data", ""));
            }

            psEdtBut.setOnClickListener(this);


        } else if (which.equalsIgnoreCase("wrkDet")) {

            scroll.setVisibility(View.GONE);
            wrkDet.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            orgname = (AutoCompleteTextView) findViewById(R.id.orgname);
            postition = (EditText) findViewById(R.id.position);
            location = (EditText) findViewById(R.id.location);
            fYr = (EditText) findViewById(R.id.f_yr);
            tYr = (EditText) findViewById(R.id.t_yr);

            wrking = (CheckBox) findViewById(R.id.wrking);
            present = (EditText) findViewById(R.id.present);
            frm = (TextView) findViewById(R.id.frm);
            to = (TextView) findViewById(R.id.to);

            myp = new MonthYearPicker(this);
            myp.build(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fYr.setText(myp.getSelectedMonthShortName() + ", " + myp.getSelectedYear());
                    yr1 = myp.getSelectedYear();
                }
            }, null);


            myp1 = new MonthYearPicker1(this);

            myp1.build(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tYr.setText(myp1.getSelectedMonthShortName() + ", " + myp1.getSelectedYear());
                    yr2 = myp1.getSelectedYear();
                }
            }, null);

            fYr.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        myp.show();
                    }
                    return false;
                }
            });

            tYr.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        myp1.show();
                    }
                    return false;
                }
            });

            wkDetails = (Button) findViewById(R.id.wk_detail);


            new GetOrg(UpdateResumeActivity.this, progressBar, scroll, orgname).execute();

            orgname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                    TextView txt4 = (TextView) view.findViewById(R.id.univ);

                    orgname.setText(txt1.getText().toString());
                    location.setText(txt4.getText().toString());
                    Ins = true;
                }
            });

            if (what.equalsIgnoreCase("edit")) {
                orgname.setText(mApp.getPreference().getString("org", ""));
                postition.setText(mApp.getPreference().getString("pos", ""));
                location.setText(mApp.getPreference().getString("loc", ""));
                fYr.setText(mApp.getPreference().getString("frm", ""));
                if (mApp.getPreference().getString("to", "").equalsIgnoreCase("present")) {
                    present.setVisibility(View.VISIBLE);
                    tYr.setVisibility(View.GONE);
                    visible = false;
                    //tYr.setText(mApp.getPreference().getString("to", ""));
                    wrking.setChecked(true);
                } else {
                    present.setVisibility(View.GONE);
                    tYr.setVisibility(View.VISIBLE);
                    visible = true;
                    tYr.setText(mApp.getPreference().getString("to", ""));
                    wrking.setChecked(false);
                }

            }

            wrking.setOnClickListener(this);
            wkDetails.setOnClickListener(this);


        } else if (which.equalsIgnoreCase("wrkExp")) {
            wrkExp.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            weEdtTxt = (EditText) findViewById(R.id.we_edit_txt);
            weEdtBut = (Button) findViewById(R.id.we_edit_but);

            if (what.equalsIgnoreCase("edit")) {
                weEdtTxt.setText(mApp.getPreference().getString("data", ""));
            }

            weEdtBut.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("addClg")) {
            scroll.setVisibility(View.GONE);
            clg.setVisibility(View.VISIBLE);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            level = (Spinner) findViewById(R.id.level);
            clgName = (AutoCompleteTextView) findViewById(R.id.clg_name);
            univ = (EditText) findViewById(R.id.univ);
            loc = (EditText) findViewById(R.id.locate);
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
                    get = level.getSelectedItem().toString();
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


            new GetCollege(UpdateResumeActivity.this, progressBar, scroll, clgName).execute();
            new GetCourse(UpdateResumeActivity.this, conCent).execute();

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

            conCent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt = (TextView) view.findViewById(R.id.display);
                    conCent.setText(txt.getText().toString());
                    cIns = true;
                    frm_yr.requestFocus();
                }
            });

            if (what.equalsIgnoreCase("edit")) {
                if (mApp.getPreference().getString("level", "").equalsIgnoreCase("1")) {
                    level.setSelection(1);
                } else if (mApp.getPreference().getString("level", "").equalsIgnoreCase("2")) {
                    level.setSelection(2);
                } else {
                    level.setSelection(3);
                }
                clgName.setText(mApp.getPreference().getString("insName", ""));
                univ.setText(mApp.getPreference().getString("univ", ""));
                loc.setText(mApp.getPreference().getString("location", ""));
                conCent.setText(mApp.getPreference().getString("conCent", ""));
                agrt.setText(mApp.getPreference().getString("aggrt", ""));
                frm_yr.setText(mApp.getPreference().getString("frm", ""));
                to_yr.setText(mApp.getPreference().getString("to", ""));

                i = Integer.parseInt(mApp.getPreference().getString("frm", ""));
                j = Integer.parseInt(mApp.getPreference().getString("to", ""));


            }

            frm_yr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /*abcd = frm_yr.getText().toString();
                    tv1.setText(abcd);*/


                }

                @Override
                public void afterTextChanged(Editable s) {
                    //i = Integer.parseInt(frm_yr.getText().toString());
                    if (frm_yr.getText().toString().length()!=0) {
                        i = Integer.parseInt(frm_yr.getText().toString());
                    }
                }
            });

            to_yr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   /* abcd = to_yr.getText().toString();
                    tv2.setText(abcd);*/
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //j = Integer.parseInt(to_yr.getText().toString());
                    if (to_yr.getText().toString().length() != 0) {
                        j = Integer.parseInt(to_yr.getText().toString());
                    }
                }
            });

            q_update.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("addSch")) {
            sch.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            mode = (Spinner) findViewById(R.id.mode);
            h_name = (AutoCompleteTextView) findViewById(R.id.h_name);
            board = (EditText) findViewById(R.id.board);
            cty = (EditText) findViewById(R.id.cty);
            sFrmyr = (EditText) findViewById(R.id.s_frm_yr);
            sTYr = (EditText) findViewById(R.id.s_to_yr);
            hAgrt = (EditText) findViewById(R.id.h_agrt);


            h_update = (Button) findViewById(R.id.h_update);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, schl);

            mode.setAdapter(adapter1);
            mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    set = mode.getSelectedItem().toString();
                    if (set.equalsIgnoreCase("HSC")) {
                        s = "4";
                    } else {
                        s = "5";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            new GetSchool(UpdateResumeActivity.this, scroll, progressBar, h_name).execute();
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


            if (what.equalsIgnoreCase("edit")) {
                if (mApp.getPreference().getString("level", "").equalsIgnoreCase("4")) {
                    mode.setSelection(1);
                } else {
                    mode.setSelection(2);
                }
                h_name.setText(mApp.getPreference().getString("insName", ""));
                board.setText(mApp.getPreference().getString("univ", ""));
                cty.setText(mApp.getPreference().getString("location", ""));
                hAgrt.setText(mApp.getPreference().getString("aggrt", ""));
                sFrmyr.setText(mApp.getPreference().getString("frm", ""));
                sTYr.setText(mApp.getPreference().getString("to", ""));

                k = Integer.parseInt(mApp.getPreference().getString("frm", ""));
                l = Integer.parseInt(mApp.getPreference().getString("to", ""));


            }

            sFrmyr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (sFrmyr.getText().toString().length() != 0) {
                        k = Integer.parseInt(sFrmyr.getText().toString());
                    }
                }
            });

            sTYr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (sTYr.getText().toString().length() != 0) {
                        l = Integer.parseInt(sTYr.getText().toString());
                    }
                }
            });

            h_update.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("skill")) {
            skill.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            skll = (MultiAutoCompleteTextView) findViewById(R.id.skills);


            addSkll = (Button) findViewById(R.id.sk_add);

            if (mApp.getPreference().getString("skill", "") != null && !mApp.getPreference().getString("skill", "").trim().isEmpty()) {
                skll.setText(mApp.getPreference().getString("skill", "") + ", ");
            }


            new GetSkillSet(UpdateResumeActivity.this, scroll, progressBar, skll).execute();

            addSkll.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("project")) {

            prjct.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            pr = (TextView) findViewById(R.id.pr);
            title = (EditText) findViewById(R.id.title);
            platform = (EditText) findViewById(R.id.platform);
            role = (EditText) findViewById(R.id.role);
            team_sze = (EditText) findViewById(R.id.team_sze);
            duration = (EditText) findViewById(R.id.dur);

            description = (EditText) findViewById(R.id.prjct_des);


            p_update = (Button) findViewById(R.id.p_update);

            if (what.equalsIgnoreCase("edit")) {
                title.setText(mApp.getPreference().getString("title", ""));
                platform.setText(mApp.getPreference().getString("platform", ""));
                role.setText(mApp.getPreference().getString("role", ""));
                team_sze.setText(mApp.getPreference().getString("team", ""));
                duration.setText(mApp.getPreference().getString("dur", ""));
                description.setText(mApp.getPreference().getString("desc", ""));


            }

            p_update.setOnClickListener(this);


        } else if (which.equalsIgnoreCase("cert")) {
            cert.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            certName = (EditText) findViewById(R.id.cer_name);
            certCentre = (EditText) findViewById(R.id.cer_centre);
            certDur = (EditText) findViewById(R.id.cer_duration);
            certUpt = (Button) findViewById(R.id.cert_upt);

            if (what.equalsIgnoreCase("edit")) {
                certName.setText(mApp.getPreference().getString("certName", ""));
                certCentre.setText(mApp.getPreference().getString("certCentre", ""));
                certDur.setText(mApp.getPreference().getString("certDur", ""));
            }

            certUpt.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("ach")) {
            ach.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            acheieve = (EditText) findViewById(R.id.achieve);
            addAch = (Button) findViewById(R.id.ach_add);

            addAch.setOnClickListener(this);
        } else if (which.equalsIgnoreCase("ex")) {

            exc.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            extra = (EditText) findViewById(R.id.extra_c);
            addEx = (Button) findViewById(R.id.ex_add);

            addEx.setOnClickListener(this);
        } else if (which.equalsIgnoreCase("cnt")) {

            cntInfo.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            addr = (EditText) findViewById(R.id.addr);
            city = (AutoCompleteTextView) findViewById(R.id.city);
            district = (EditText) findViewById(R.id.district);
            state = (EditText) findViewById(R.id.state);
            country = (EditText) findViewById(R.id.country);
            pin = (EditText) findViewById(R.id.pin_code);
            cntAdd = (Button) findViewById(R.id.cnt_add);

            addr.setText(mApp.getPreference().getString("addr", ""));
            city.setText(mApp.getPreference().getString("city", ""));
            district.setText(mApp.getPreference().getString("distrct", ""));
            state.setText(mApp.getPreference().getString("state", ""));
            country.setText(mApp.getPreference().getString("country", ""));
            pin.setText(mApp.getPreference().getString("picode", ""));

            new GetLocation(UpdateResumeActivity.this, scroll, city, progressBar).execute();

            city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                    TextView txt2 = (TextView) view.findViewById(R.id.univ);
                    TextView txt3 = (TextView) view.findViewById(R.id.loc);
                    TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                    city.setText(txt1.getText().toString());
                    district.setText(txt2.getText().toString());
                    state.setText(txt3.getText().toString());
                    country.setText(txt4.getText().toString());
                    ins = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    pin.requestFocus();
                }
            });

            cntAdd.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("bsc")) {
            bsc.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            sex = (RadioGroup) findViewById(R.id.sex);
            dob = (EditText) findViewById(R.id.dob);
            r1 = (RadioButton) findViewById(R.id.male);
            r2 = (RadioButton) findViewById(R.id.female);
            fName = (EditText) findViewById(R.id.f_name);
            mName = (EditText) findViewById(R.id.m_name);
            rel = (EditText) findViewById(R.id.rel);
            lang = (EditText) findViewById(R.id.lang);
            hobby = (EditText) findViewById(R.id.hobby);
            age = (EditText) findViewById(R.id.age);
            bscAdd = (Button) findViewById(R.id.bc_add);

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

            dob.setText(mApp.getPreference().getString("dob", ""));
            age.setText(mApp.getPreference().getString("age", ""));
            fName.setText(mApp.getPreference().getString("fName", ""));
            mName.setText(mApp.getPreference().getString("mName", ""));
            rel.setText(mApp.getPreference().getString("rel", ""));
            lang.setText(mApp.getPreference().getString("lang", ""));
            hobby.setText(mApp.getPreference().getString("hobby", ""));

            if (mApp.getPreference().getString("gen", "").equalsIgnoreCase("male")) {
                r1.setChecked(true);
            } else {
                r2.setChecked(true);
            }

            bscAdd.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("obj")) {
            scroll.setVisibility(View.VISIBLE);
            objec.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            obj = (EditText) findViewById(R.id.obj);
            objEdt = (Button) findViewById(R.id.obj_edt);

            obj.setText(mApp.getPreference().getString("obj", ""));

            objEdt.setOnClickListener(this);

        }
    }

    private void calcAge(String db) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date();
        try {
            dt = format.parse(db);
            Date today = new Date();
            int by = dt.getYear();
            int ty = today.getYear();
            age.setText(String.valueOf(ty - by));

        } catch (Exception e) {
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ps_edit_but:
                if (what.equalsIgnoreCase("add")) {
                    if (psEdtTxt.getText().toString().length() == 0) {
                        psEdtTxt.setError("Field cannot be empty");
                        psEdtTxt.requestFocus();
                    } else {
                        new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                                psEdtTxt.getText().toString(), "proSum").execute();
                    }
                } else {
                    if (psEdtTxt.getText().toString().length() == 0) {
                        psEdtTxt.setError("Field cannot be empty");
                        psEdtTxt.requestFocus();
                    } else {
                        new EditDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                                psEdtTxt.getText().toString(), "proSum").execute();
                    }
                }
                break;
            case R.id.wrking:
                if (((CheckBox) v).isChecked()) {
                    present.setVisibility(View.VISIBLE);
                    tYr.setVisibility(View.GONE);
                    tYr.setText("");
                    visible = false;
                } else {
                    present.setVisibility(View.GONE);
                    tYr.setVisibility(View.VISIBLE);
                    visible = true;
                }
                break;
            case R.id.wk_detail:
                if (orgname.getText().toString().length() == 0) {
                    orgname.setError("Organization name is Mandatory");
                    orgname.requestFocus();
                } else if (postition.getText().toString().length() == 0) {
                    postition.setError("Position Mandatory");
                    postition.requestFocus();
                } else if (location.getText().toString().length() == 0) {
                    location.setError("Location Mandatory");
                    location.requestFocus();
                } else if (fYr.getText().toString().length() == 0) {
                    fYr.setError("Field Mandatory");
                    fYr.requestFocus();
                } else if (visible) {

                    if (tYr.getText().toString().length() == 0) {
                        tYr.setError("Field Mandatory");
                        tYr.requestFocus();
                    } else if (fYr.getText().toString().equalsIgnoreCase(tYr.getText().toString())) {
                        tYr.setError("From & To year are same.");
                        tYr.requestFocus();
                    } else if (yr1 > yr2) {
                        tYr.setError("Check From & To Year");
                        tYr.requestFocus();
                    } else {
                        toDate = tYr.getText().toString();

                        if (what.equalsIgnoreCase("add")) {
                            new AddWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                                    orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                    fYr.getText().toString(), toDate, Ins).execute();
                        } else {
                            new EditWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                                    orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                    fYr.getText().toString(), toDate).execute();
                        }
                    }
                } else if (!visible) {
                    toDate = "present";
                    if (what.equalsIgnoreCase("add")) {
                        new AddWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                                orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                fYr.getText().toString(), toDate, Ins).execute();
                    } else {
                        new EditWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                                orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                fYr.getText().toString(), toDate).execute();
                    }
                } else {
                }
                break;
            case R.id.we_edit_but:
                if (what.equalsIgnoreCase("add")) {
                    if (weEdtTxt.getText().toString().length() == 0) {
                        weEdtTxt.setError("Field cannot be empty");
                        weEdtTxt.requestFocus();
                    } else {
                        new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                                weEdtTxt.getText().toString(), "wrkExp").execute();
                    }
                } else {
                    if (weEdtTxt.getText().toString().length() == 0) {
                        weEdtTxt.setError("Field cannot be empty");
                        weEdtTxt.requestFocus();
                    } else {
                        new EditDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                                weEdtTxt.getText().toString(), "wrkExp").execute();
                    }
                }
                break;
            case R.id.q_update:
                if (get.equalsIgnoreCase("Select Qualification")) {
                    Toast.makeText(getApplicationContext(), "Select Qualification", Toast.LENGTH_SHORT).show();
                    level.requestFocus();
                } else if (clgName.getText().toString().length() == 0) {
                    clgName.setError("Field Mandatory");
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
                } else if (frm_yr.getText().toString().length() < 4) {
                    frm_yr.setError("Enter a valid year");
                    frm_yr.requestFocus();
                } else if (to_yr.getText().toString().length() < 4) {
                    to_yr.setError("Enter a valid year");
                    to_yr.requestFocus();
                } else if (conCent.getText().toString().length() == 0) {
                    conCent.setError("Concentration Mandatory");
                    conCent.requestFocus();
                } else if (i >= j) {
                    to_yr.setError("Check pass out year");
                    to_yr.requestFocus();
               /* }else if (agrt.getText().toString().length() == 0) {
                    agrt.setError("Aggregate Mandatory");
                    agrt.requestFocus();*/
                } else {
                    if (what.equalsIgnoreCase("add")) {
                        new AddCollegedetail(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), q, conCent.getText().toString(),
                                clgName.getText().toString(), univ.getText().toString(), loc.getText().toString(),
                                frm_yr.getText().toString(), to_yr.getText().toString(), agrt.getText().toString(), ins, cIns, "add").execute();
                    } else {
                        new AddCollegedetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), q, conCent.getText().toString(),
                                clgName.getText().toString(), univ.getText().toString(), loc.getText().toString(),
                                frm_yr.getText().toString(), to_yr.getText().toString(), agrt.getText().toString(), ins, cIns, "edit").execute();
                    }
                }
                break;
            case R.id.h_update:
                if (set.equalsIgnoreCase("Mode of School")) {
                    Toast.makeText(getApplicationContext(), "Select Mode of School", Toast.LENGTH_SHORT).show();
                    mode.requestFocus();
                } else if (h_name.getText().toString().length() == 0) {
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
                } else if (k >= l) {
                    sTYr.setError("Check pass out year");
                    sTYr.requestFocus();
                /*}else if (hAgrt.getText().toString().length() == 0) {
                    hAgrt.setError("Field Mandatory");*/
                } else {


                    if (what.equalsIgnoreCase("add")) {
                        new AddSchool(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), s,
                                set, h_name.getText().toString(), board.getText().toString(), cty.getText().toString(),
                                sFrmyr.getText().toString(), sTYr.getText().toString(), hAgrt.getText().toString(), "add", ins).execute();
                    } else {
                        new AddSchool(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), s,
                                set, h_name.getText().toString(), board.getText().toString(), cty.getText().toString(),
                                sFrmyr.getText().toString(), sTYr.getText().toString(), hAgrt.getText().toString(), "edit", ins).execute();
                    }

                }
                break;
            case R.id.sk_add:
                if (skll.getText().toString().length() == 0) {
                    skll.setError("Field Mandatory");
                    skll.requestFocus();
                } else {
                    //Toast.makeText(getApplicationContext(),skll.getText().toString(),Toast.LENGTH_SHORT).show();
                    new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), skll.getText().toString(), "skill").execute();
                }
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
                    if (what.equalsIgnoreCase("add")) {
                        new AddProject(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), title.getText().toString(),
                                platform.getText().toString(), role.getText().toString(), team_sze.getText().toString(), duration.getText().toString(),
                                description.getText().toString(), "add").execute();
                    } else {
                        new AddProject(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), title.getText().toString(),
                                platform.getText().toString(), role.getText().toString(), team_sze.getText().toString(), duration.getText().toString(),
                                description.getText().toString(), "edit").execute();

                    }

                }

                break;
            case R.id.cert_upt:
                if (certName.getText().toString().length() == 0) {
                    certName.setError("Field Mandatory");
                } else if (certCentre.getText().toString().length() == 0) {
                    certCentre.setError("Field Mandatory");
                } else if (certDur.getText().toString().length() == 0) {
                    certDur.setError("Field Mandatory");
                } else {
                    if (what.equalsIgnoreCase("add")) {
                        new AddCert(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), certName.getText().toString(),
                                certCentre.getText().toString(), certDur.getText().toString(), "add").execute();
                    } else {
                        new AddCert(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), certName.getText().toString(),
                                certCentre.getText().toString(), certDur.getText().toString(), "edit").execute();
                    }
                }
                break;
            case R.id.ach_add:
                if (acheieve.getText().toString().length() == 0) {
                    acheieve.setError("Field Mandatory");
                } else {
                    new AddAchieve(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            acheieve.getText().toString()).execute();
                }
                break;
            case R.id.ex_add:
                if (extra.getText().toString().length() == 0) {
                    extra.setError("Field Mandatory");
                    extra.requestFocus();
                } else {
                    new AddExtra(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            extra.getText().toString()).execute();
                }
                break;
            case R.id.cnt_add:
                if (addr.getText().toString().length() == 0) {
                    addr.setError("Field Mandatory");
                    addr.requestFocus();
                } else if (city.getText().toString().length() == 0) {
                    city.setError("Field Mandatory");
                    city.requestFocus();
                } else if (district.getText().toString().length() == 0) {
                    district.setError("Field Mandatory");
                    district.requestFocus();
                } else if (state.getText().toString().length() == 0) {
                    state.setError("Field Mandatory");
                    state.requestFocus();
                } else if (country.getText().toString().length() == 0) {
                    country.setError("Field Mandatory");
                    country.requestFocus();
                } else if (pin.getText().toString().length() == 0) {
                    pin.setError("Field Mandatory");
                    pin.requestFocus();
                } else {
                    new AddContactInfo(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            addr.getText().toString(), city.getText().toString(), district.getText().toString(), state.getText().toString(),
                            country.getText().toString(), pin.getText().toString(), ins).execute();
                }
                break;
            case R.id.bc_add:
                int getSex = sex.getCheckedRadioButtonId();
                radio = (RadioButton) findViewById(getSex);
                if (dob.getText().toString().length() == 0) {
                    dob.setError("Field Mandatory");
                    dob.requestFocus();
                } else if (fName.getText().toString().length() == 0) {
                    fName.setError("Field Mandatory");
                    fName.requestFocus();
                } else if (mName.getText().toString().length() == 0) {
                    mName.setError("Field Mandatory");
                    mName.requestFocus();
                } else if (rel.getText().toString().length() == 0) {
                    rel.setError("Field Mandatory");
                    rel.requestFocus();
                } else if (lang.getText().toString().length() == 0) {
                    lang.setError("Field Mandatory");
                    lang.requestFocus();
                } else if (hobby.getText().toString().length() == 0) {
                    hobby.setError("Field Mandatory");
                    hobby.requestFocus();
                } else {
                    new AddBasicInfo(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            dob.getText().toString(), radio.getText().toString(), fName.getText().toString(),
                            mName.getText().toString(), rel.getText().toString(), lang.getText().toString(), hobby.getText().toString(), age.getText().toString()).execute();
                }
                break;
            case R.id.obj_edt:
                if (obj.getText().toString().length() == 0) {
                    obj.setError("Field Mandatory");
                    obj.requestFocus();
                } else {
                    new UpdateStatus(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), obj.getText().toString()).execute();
                }
                break;


        }
    }


}

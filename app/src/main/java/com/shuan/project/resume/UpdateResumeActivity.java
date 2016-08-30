package com.shuan.project.resume;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.AddAchieve;
import com.shuan.project.asyncTasks.AddBasicInfo;
import com.shuan.project.asyncTasks.AddCert;
import com.shuan.project.asyncTasks.AddCollegedetail;
import com.shuan.project.asyncTasks.AddContactInfo;
import com.shuan.project.asyncTasks.AddExtra;
import com.shuan.project.asyncTasks.AddProject;
import com.shuan.project.asyncTasks.AddSchool;
import com.shuan.project.asyncTasks.AddWrkDetail;
import com.shuan.project.asyncTasks.EditDetail;
import com.shuan.project.asyncTasks.EditWrkDetail;
import com.shuan.project.asyncTasks.GetCollege;
import com.shuan.project.asyncTasks.GetCourse;
import com.shuan.project.asyncTasks.GetLocation;
import com.shuan.project.asyncTasks.GetOrg;
import com.shuan.project.asyncTasks.GetSchool;
import com.shuan.project.asyncTasks.GetSkillSet;
import com.shuan.project.asyncTasks.profileSummaryUpdate;
import com.shuan.project.fragment.DateDialog;

import java.util.HashMap;

public class UpdateResumeActivity extends AppCompatActivity implements View.OnClickListener {

    private String what, which;
    private Common mApp;
    private int i = 0, j = 0, k = 0, l = 0;
    private Helper helper = new Helper();
    private LinearLayout psEdt, wrkDet, wrkExp, clg, sch, skill, prjct, cert, ach, exc, cntInfo, bsc;
    private HashMap<String, String> seniorData;
    private ProgressDialog pDialog;
    private String frmDate, toDate;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private boolean ins = false;
    private boolean sIns = false;


    /* Profile Summary Field */
    private Button psEdtBut;
    private TextView ps;
    private EditText psEdtTxt;

    /* Work Details Field */
    private AutoCompleteTextView orgname, postition, location;
    private Button wkDetails;
    private EditText fY, fM, fD, tY, tM, tD, present;
    private String[] mnth = new String[0];
    private String[] date = new String[0];
    private RelativeLayout frm_mnth, frm_date, to_mnth, to_date;
    private LinearLayout to_year;
    private CheckBox wrking;
    private TextView add_wrk;
    private boolean visible = false;

    /* Work Summary Field */
    private EditText weEdtTxt;
    private Button weEdtBut;

    /* College Fields */
    private AutoCompleteTextView clgName, univ, loc, conCent;
    private EditText cfY, cfM, cfD, ctY, ctM, ctD, agrt;
    private String[] cours = new String[0];
    private RelativeLayout cfrm_mnth, cfrm_date, cto_mnth, cto_date;
    private boolean cIns = false;
    private String[] qulify = new String[]{"PG", "UG", "DIPLOMA"};
    private String q;
    private Spinner level;
    private Button q_update;
    private ProgressBar progressBar1;
    private RelativeLayout scroll1;
    private TextView frm, to, qfy;

    /* School Fields */
    private AutoCompleteTextView h_name, board, cty;
    private EditText mode, hfY, hfM, hfD, htY, htM, htD, hAgrt;
    private RelativeLayout h_frm_mnth, h_frm_date, h_to_mnth, h_to_date;
    private Button h_update;

    /* Skill Set */
    private AutoCompleteTextView skll;
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
    private EditText addr, district, state, country;
    private AutoCompleteTextView city;
    private Button cntAdd;

    /* Basic Info */
    public EditText dob, fName, mName, rel, lang, hobby;
    public RadioButton radio, r1, r2;
    public RadioGroup sex;
    public Button bscAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            add_wrk = (TextView) findViewById(R.id.add_wrk);
            orgname = (AutoCompleteTextView) findViewById(R.id.orgname);
            postition = (AutoCompleteTextView) findViewById(R.id.position);
            location = (AutoCompleteTextView) findViewById(R.id.location);
            to_year = (LinearLayout) findViewById(R.id.to_year);

            wrking = (CheckBox) findViewById(R.id.wrking);

            fY = (EditText) findViewById(R.id.f_year);
            fM = (EditText) findViewById(R.id.f_month);
            fD = (EditText) findViewById(R.id.f_date);
            tY = (EditText) findViewById(R.id.t_year);
            tM = (EditText) findViewById(R.id.t_month);
            tD = (EditText) findViewById(R.id.t_date);
            present = (EditText) findViewById(R.id.present);
            frm_mnth = (RelativeLayout) findViewById(R.id.frm_mnth);
            frm_date = (RelativeLayout) findViewById(R.id.frm_dat);
            to_mnth = (RelativeLayout) findViewById(R.id.to_mnth);
            to_date = (RelativeLayout) findViewById(R.id.to_dat);
            mnth = getResources().getStringArray(R.array.month);
            date = getResources().getStringArray(R.array.date);
            wkDetails = (Button) findViewById(R.id.wk_detail);
            wrking.setTypeface(helper.droid(getApplicationContext()));
            add_wrk.setTypeface(helper.droid(getApplicationContext()));
            fY.setTypeface(helper.droid(getApplicationContext()));
            fM.setTypeface(helper.droid(getApplicationContext()));
            fD.setTypeface(helper.droid(getApplicationContext()));
            tY.setTypeface(helper.droid(getApplicationContext()));
            tM.setTypeface(helper.droid(getApplicationContext()));
            tD.setTypeface(helper.droid(getApplicationContext()));
            present.setTypeface(helper.droid(getApplicationContext()));
            wkDetails.setTypeface(helper.droid(getApplicationContext()));


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


            fY.addTextChangedListener(new TextWatcher() {
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
                }
            });
            tY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (tY.getText().toString().length() == 4) {
                        to_mnth.setVisibility(View.VISIBLE);
                    }
                }
            });

            new GetOrg(UpdateResumeActivity.this, progressBar, scroll, orgname).execute();

            orgname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                    TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                    orgname.setText(txt1.getText().toString());
                    location.setText(txt4.getText().toString());
                }
            });

            if (what.equalsIgnoreCase("edit")) {
                orgname.setText(mApp.getPreference().getString("org", ""));
                postition.setText(mApp.getPreference().getString("pos", ""));
                location.setText(mApp.getPreference().getString("loc", ""));

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
            univ = (AutoCompleteTextView) findViewById(R.id.univ);
            loc = (AutoCompleteTextView) findViewById(R.id.c_location);
            cfY = (EditText) findViewById(R.id.c_f_year);
            cfM = (EditText) findViewById(R.id.c_f_month);
            cfD = (EditText) findViewById(R.id.c_f_date);
            ctY = (EditText) findViewById(R.id.c_t_year);
            ctM = (EditText) findViewById(R.id.c_t_month);
            ctD = (EditText) findViewById(R.id.c_t_date);
            conCent = (AutoCompleteTextView) findViewById(R.id.concent);
            agrt = (EditText) findViewById(R.id.agrt);

            cfrm_mnth = (RelativeLayout) findViewById(R.id.c_frm_mnth);
            cfrm_date = (RelativeLayout) findViewById(R.id.c_frm_dat);
            cto_mnth = (RelativeLayout) findViewById(R.id.c_to_mnth);
            cto_date = (RelativeLayout) findViewById(R.id.c_to_dat);
            mnth = getResources().getStringArray(R.array.month);
            date = getResources().getStringArray(R.array.date);
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


            cfM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("cfm");
                    }
                    return false;
                }
            });

            cfD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("cfd");
                    }
                    return false;
                }
            });

            ctM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("ctm");
                    }
                    return false;
                }
            });

            ctD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("ctd");
                    }
                    return false;
                }
            });


            cfY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (cfY.getText().toString().length() == 4) {
                        cfrm_mnth.setVisibility(View.VISIBLE);
                    }


                }
            });
            ctY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (ctY.getText().toString().length() == 4) {
                        cto_mnth.setVisibility(View.VISIBLE);
                    }

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
                    cfY.requestFocus();
                }
            });

            if (what.equalsIgnoreCase("edit")) {
                if (mApp.getPreference().getString("level", "").equalsIgnoreCase("1")) {
                    level.setSelection(0);
                } else if (mApp.getPreference().getString("level", "").equalsIgnoreCase("2")) {
                    level.setSelection(1);
                } else {
                    level.setSelection(2);
                }
                clgName.setText(mApp.getPreference().getString("insName", ""));
                univ.setText(mApp.getPreference().getString("univ", ""));
                loc.setText(mApp.getPreference().getString("location", ""));
                conCent.setText(mApp.getPreference().getString("conCent", ""));
                agrt.setText(mApp.getPreference().getString("aggrt", ""));
            }


            q_update.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("addSch")) {
            sch.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            mode = (EditText) findViewById(R.id.mode);
            h_name = (AutoCompleteTextView) findViewById(R.id.h_name);
            board = (AutoCompleteTextView) findViewById(R.id.board);
            cty = (AutoCompleteTextView) findViewById(R.id.cty);

            hfY = (EditText) findViewById(R.id.h_f_year);
            hfM = (EditText) findViewById(R.id.h_f_month);
            hfD = (EditText) findViewById(R.id.h_f_date);
            htY = (EditText) findViewById(R.id.h_t_year);
            htM = (EditText) findViewById(R.id.h_t_month);
            htD = (EditText) findViewById(R.id.h_t_date);
            hAgrt = (EditText) findViewById(R.id.h_agrt);

            h_frm_mnth = (RelativeLayout) findViewById(R.id.h_frm_mnth);
            h_frm_date = (RelativeLayout) findViewById(R.id.h_frm_dat);
            h_to_mnth = (RelativeLayout) findViewById(R.id.h_to_mnth);
            h_to_date = (RelativeLayout) findViewById(R.id.h_to_dat);

            mnth = getResources().getStringArray(R.array.month);
            date = getResources().getStringArray(R.array.date);
            h_update = (Button) findViewById(R.id.h_update);

            hfM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("hfm");
                    }
                    return false;
                }
            });

            hfD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("hfd");
                    }
                    return false;
                }
            });

            htM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("htm");
                    }
                    return false;
                }
            });

            htD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("htd");
                    }
                    return false;
                }
            });


            hfY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (hfY.getText().toString().length() == 4) {
                        h_frm_mnth.setVisibility(View.VISIBLE);
                    }

                }
            });
            htY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (htY.getText().toString().length() == 4) {
                        h_to_mnth.setVisibility(View.VISIBLE);
                    }

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
                    hfY.requestFocus();
                }
            });

            if (what.equalsIgnoreCase("edit")) {
                mode.setText(mApp.getPreference().getString("conCent", ""));
                h_name.setText(mApp.getPreference().getString("insName", ""));
                board.setText(mApp.getPreference().getString("univ", ""));
                cty.setText(mApp.getPreference().getString("location", ""));
                hAgrt.setText(mApp.getPreference().getString("aggrt", ""));
            }


            h_update.setOnClickListener(this);

        } else if (which.equalsIgnoreCase("skill")) {
            skill.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            skll = (AutoCompleteTextView) findViewById(R.id.skills);
            addSkll = (Button) findViewById(R.id.sk_add);

            new GetSkillSet(UpdateResumeActivity.this, scroll, progressBar, skll).execute();

            skll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt = (TextView) view.findViewById(R.id.display);
                    skll.setText(txt.getText().toString());
                    sIns = true;
                }
            });


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
            url = (EditText) findViewById(R.id.p_url);
            description = (EditText) findViewById(R.id.prjct_des);
            acd = (CheckBox) findViewById(R.id.acd);

            p_update = (Button) findViewById(R.id.p_update);

            if (what.equalsIgnoreCase("edit")) {
                title.setText(mApp.getPreference().getString("title", ""));
                platform.setText(mApp.getPreference().getString("platform", ""));
                role.setText(mApp.getPreference().getString("role", ""));
                team_sze.setText(mApp.getPreference().getString("team", ""));
                duration.setText(mApp.getPreference().getString("dur", ""));
                url.setText(mApp.getPreference().getString("url", ""));
                description.setText(mApp.getPreference().getString("desc", ""));

                if (mApp.getPreference().getString("stus", "").equalsIgnoreCase("0")) {
                    acd.setChecked(true);
                } else {
                    acd.setChecked(false);
                }
            }

            p_update.setOnClickListener(this);
            acd.setOnClickListener(this);

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
            cntAdd = (Button) findViewById(R.id.cnt_add);

            addr.setText(mApp.getPreference().getString("addr", ""));
            city.setText(mApp.getPreference().getString("city", ""));
            district.setText(mApp.getPreference().getString("distrct", ""));
            state.setText(mApp.getPreference().getString("state", ""));
            country.setText(mApp.getPreference().getString("country", ""));

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

            dob.setText(mApp.getPreference().getString("dob",""));
            fName.setText(mApp.getPreference().getString("fName",""));
            mName.setText(mApp.getPreference().getString("mName",""));
            rel.setText(mApp.getPreference().getString("rel",""));
            lang.setText(mApp.getPreference().getString("lang",""));
            hobby.setText(mApp.getPreference().getString("hobby",""));

            if(mApp.getPreference().getString("gen","").equalsIgnoreCase("male")){
                r1.setChecked(true);
            }else{
                r2.setChecked(true);
            }

            bscAdd.setOnClickListener(this);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ps_edit_but:
                if (what.equalsIgnoreCase("add")) {
                    new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            psEdtTxt.getText().toString(), "proSum").execute();
                } else {
                    new EditDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                            psEdtTxt.getText().toString(), "proSum").execute();
                }
                break;
            case R.id.wrking:
                if (((CheckBox) v).isChecked()) {
                    present.setVisibility(View.VISIBLE);
                    to_year.setVisibility(View.GONE);
                    visible = false;
                } else {
                    present.setVisibility(View.GONE);
                    to_year.setVisibility(View.VISIBLE);
                    visible = true;
                }
                break;
            case R.id.wk_detail:
                if (postition.getText().toString().length() == 0) {
                    postition.setError("Position Mandatory");
                    postition.requestFocus();
                } else if (location.getText().toString().length() == 0) {
                    location.setError("Location Mandatory");
                    location.requestFocus();
                } else if (fY.getText().toString().length() == 0) {
                    fY.setError("Year Mandatory");
                    fY.requestFocus();
                } else if (fM.getText().toString().length() == 0) {
                    fM.setError("Month Mandatory");
                    fM.requestFocus();
                } else if (fD.getText().toString().length() == 0) {
                    fD.setError("Date Mandatory");
                    fD.requestFocus();
                } else {
                    if (visible) {
                        if (tY.getText().toString().length() == 0) {
                            tY.setError("Year Mandatory");
                            tY.requestFocus();
                        } else if (tM.getText().toString().length() == 0) {
                            tM.setError("Month Mandatory");
                            tM.requestFocus();
                        } else if (tD.getText().toString().length() == 0) {
                            tD.setError("Date Mandatory");
                            tD.requestFocus();
                        }
                        toDate = tY.getText().toString() + "-" + tM.getText().toString() + "-" + tD.getText().toString();
                    } else {
                        toDate = "present";
                    }
                    frmDate = fY.getText().toString() + "-" + fM.getText().toString() + "-" + fD.getText().toString();
                    if (toDate.toString().equalsIgnoreCase("--")) {
                    } else {
                        if (what.equalsIgnoreCase("add")) {
                            new AddWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                                    orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                    frmDate, toDate).execute();
                        } else {
                            new EditWrkDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                                    orgname.getText().toString(), location.getText().toString(), postition.getText().toString(),
                                    frmDate, toDate).execute();
                        }

                    }
                }
                break;
            case R.id.we_edit_but:
                if (what.equalsIgnoreCase("add")) {
                    new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            weEdtTxt.getText().toString(), "wrkExp").execute();
                } else {
                    new EditDetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""),
                            weEdtTxt.getText().toString(), "wrkExp").execute();
                }
                break;
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
                } else if (cfY.getText().toString().length() == 0) {
                    cfY.setError("Year Mandatory");
                    cfY.requestFocus();
                } else if (cfM.getText().toString().length() == 0) {
                    cfM.setError("Month Mandatory");
                    cfM.requestFocus();
                } else if (cfD.getText().toString().length() == 0) {
                    cfD.setError("Date Mandatory");
                    cfD.requestFocus();
                } else if (ctY.getText().toString().length() == 0) {
                    ctY.setError("Year Mandatory");
                    ctY.requestFocus();
                } else if (ctM.getText().toString().length() == 0) {
                    ctM.setError("Month Mandatory");
                    ctM.requestFocus();
                } else if (ctD.getText().toString().length() == 0) {
                    ctD.setError("Date Mandatory");
                    ctD.requestFocus();
                } else if (conCent.getText().toString().length() == 0) {
                    conCent.setError("Concentration Mandatory");
                    conCent.requestFocus();
                } else if (agrt.getText().toString().length() == 0) {
                    agrt.setError("Aggregate Mandatory");
                    agrt.requestFocus();
                } else {
                    frmDate = cfY.getText().toString() + "-" + cfM.getText().toString() + "-" + cfD.getText().toString();
                    toDate = ctY.getText().toString() + "-" + ctM.getText().toString() + "-" + ctD.getText().toString();

                    if (what.equalsIgnoreCase("add")) {
                        new AddCollegedetail(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), q, conCent.getText().toString(),
                                clgName.getText().toString(), univ.getText().toString(), loc.getText().toString(),
                                frmDate, toDate, agrt.getText().toString(), ins, cIns, "add").execute();
                    } else {
                        new AddCollegedetail(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), q, conCent.getText().toString(),
                                clgName.getText().toString(), univ.getText().toString(), loc.getText().toString(),
                                frmDate, toDate, agrt.getText().toString(), ins, cIns, "edit").execute();
                    }
                }
                break;
            case R.id.h_update:
                if (mode.getText().toString().length() == 0) {
                    mode.setError("Field Mandatory");
                } else if (h_name.getText().toString().length() == 0) {
                    h_name.setError("Field Mandatory");
                } else if (board.getText().toString().length() == 0) {
                    board.setError("Field Mandatory");
                } else if (cty.getText().toString().length() == 0) {
                    cty.setError("Field Mandatory");
                } else if (hfY.getText().toString().length() == 0) {
                    hfY.setError("Field Mandatory");
                } else if (hfM.getText().toString().length() == 0) {
                    hfM.setError("Field Mandatory");
                } else if (hfD.getText().toString().length() == 0) {
                    hfD.setError("Field Mandatory");
                } else if (htY.getText().toString().length() == 0) {
                    htY.setError("Field Mandatory");
                } else if (htM.getText().toString().length() == 0) {
                    htM.setError("Field Mandatory");
                } else if (htD.getText().toString().length() == 0) {
                    htD.setError("Field Mandatory");
                } else if (hAgrt.getText().toString().length() == 0) {
                    hAgrt.setError("Field Mandatory");
                } else {
                    frmDate = hfY.getText().toString() + "-" + hfM.getText().toString() + "-" + hfD.getText().toString();
                    toDate = htY.getText().toString() + "-" + htM.getText().toString() + "-" + htD.getText().toString();
                    String sLevel;
                    if (mode.getText().toString().equalsIgnoreCase("hsc")) {
                        sLevel = "4";
                    } else {
                        sLevel = "5";
                    }

                    if (what.equalsIgnoreCase("add")) {
                        new AddSchool(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), sLevel,
                                mode.getText().toString(), h_name.getText().toString(), board.getText().toString(), cty.getText().toString(),
                                frmDate, toDate, hAgrt.getText().toString(), "add", ins).execute();
                    } else {
                        new AddSchool(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), sLevel,
                                mode.getText().toString(), h_name.getText().toString(), board.getText().toString(), cty.getText().toString(),
                                frmDate, toDate, hAgrt.getText().toString(), "edit", ins).execute();
                    }

                }
                break;
            case R.id.sk_add:
                if (skll.getText().toString().length() == 0) {
                    skll.setError("Field Mandatory");
                    skll.requestFocus();
                } else {
                    new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), skll.getText().toString(), "skill").execute();
                }
                break;
            case R.id.acd:
                if (((CheckBox) v).isChecked()) {
                    isAcd = "0";
                } else {
                    isAcd = "1";
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
                                url.getText().toString(), description.getText().toString(), isAcd, "add").execute();
                    } else {
                        new AddProject(UpdateResumeActivity.this, mApp.getPreference().getString("eId", ""), title.getText().toString(),
                                platform.getText().toString(), role.getText().toString(), team_sze.getText().toString(), duration.getText().toString(),
                                url.getText().toString(), description.getText().toString(), isAcd, "edit").execute();

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
                } else {
                    new AddContactInfo(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            addr.getText().toString(), city.getText().toString(), district.getText().toString(), state.getText().toString(),
                            country.getText().toString(), ins).execute();
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
                    new AddBasicInfo(UpdateResumeActivity.this,mApp.getPreference().getString(Common.u_id,""),
                            dob.getText().toString(),radio.getText().toString(),fName.getText().toString(),
                            mName.getText().toString(),rel.getText().toString(),lang.getText().toString(),hobby.getText().toString()).execute();
                }
                break;

        }
    }

    private void showDate(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateResumeActivity.this);
        builder.setTitle("Select Date")
                .setSingleChoiceItems(date, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (val.equalsIgnoreCase("fd")) {
                            fD.setText(date[which]);

                        } else if (val.equalsIgnoreCase("td")) {
                            tD.setText(date[which]);

                        } else if (val.equalsIgnoreCase("cfd")) {
                            cfD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("ctd")) {
                            ctD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("hfd")) {
                            hfD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("htd")) {
                            htD.setText(date[which]);
                        }
                        dialog.cancel();
                    }
                }).show();
    }

    private void showMnth(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateResumeActivity.this);
        builder.setTitle("Select Month")
                .setSingleChoiceItems(mnth, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (val.equalsIgnoreCase("fm")) {
                            fM.setText(mnth[which]);
                            frm_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("tm")) {
                            tM.setText(mnth[which]);
                            to_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("cfm")) {
                            cfM.setText(mnth[which]);
                            cfrm_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("ctm")) {
                            ctM.setText(mnth[which]);
                            cto_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("hfm")) {
                            hfM.setText(mnth[which]);
                            h_frm_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("htm")) {
                            htM.setText(mnth[which]);
                            h_to_date.setVisibility(View.VISIBLE);
                        }

                        dialog.cancel();
                    }
                }).show();

    }

}

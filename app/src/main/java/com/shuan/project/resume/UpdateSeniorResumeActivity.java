package com.shuan.project.resume;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.Utils.MonthYearPicker;
import com.shuan.project.adapter.InstitutionAdapter;
import com.shuan.project.adapter.LocationAdapter;
import com.shuan.project.asyncTasks.Connect;
import com.shuan.project.asyncTasks.GetInfo;
import com.shuan.project.asyncTasks.GetOrg;
import com.shuan.project.fragment.DateDialog;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateSeniorResumeActivity extends AppCompatActivity implements View.OnClickListener {

    /* General Field */
    private Common mApp;
    private ProgressDialog pDialog;
    private HashMap<String, String> seniorData;
    private RelativeLayout lay1, lay2, lay3, lay4, lay5, lay6, lay7, lay8, lay9, lay10;
    private int i = 0, j = 0, k = 0, l = 0;
    private boolean ins = false;
    private ArrayList<Sample> list;
    private InstitutionAdapter adapter;
    private String frmDate;
    private Helper helper = new Helper();
    private ProgressBar progressBar;
    private ScrollView scroll;

    /* Profile Summary Field */
    private LinearLayout psContainer;
    private Button addPS, psUpdate;
    private String[] profile = new String[0];
    private TextView ps;

    /* Work Summary Field*/
    private LinearLayout wkContainer;
    private Button addws, wsUpdate;
    private String[] work = new String[0];
    private TextView ws;

    /* Work Details Field */
    private AutoCompleteTextView orgname;
    private EditText postition, location, fYr, tYr, present;
    private Button wkDetails;
    private CheckBox wrking;
    private TextView add_wrk;
    private boolean visible = false;
    private String toDate;
    private MonthYearPicker myp;
    private MonthYearPicker myp1;
    private boolean Ins=false;


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
    private LinearLayout l1, l2, l3;
    private String[] achieve = new String[0];
    private String[] extra = new String[0];
    private EditText hobby, lang;
    private TextView ah, extra_c, ot;
    private Button add_achieve, add_extra, o_update;

    /* Project Field */
    private EditText title, platform, role, team_sze, duration, url, description;
    private Button p_update;
    private TextView pr;
    private CheckBox acd;
    private String isAcd = "0";

    /* Personal Field */
    public EditText dob, fName, mName, addr, pinNo;
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
        setContentView(R.layout.activity_update_senior_resume);

        lay1 = (RelativeLayout) findViewById(R.id.lay1);
        lay2 = (RelativeLayout) findViewById(R.id.lay2);
        lay3 = (RelativeLayout) findViewById(R.id.lay3);
        lay4 = (RelativeLayout) findViewById(R.id.lay4);
        lay5 = (RelativeLayout) findViewById(R.id.lay5);
        lay6 = (RelativeLayout) findViewById(R.id.lay6);
        lay7 = (RelativeLayout) findViewById(R.id.lay7);
        lay8 = (RelativeLayout) findViewById(R.id.lay8);
        lay9 = (RelativeLayout) findViewById(R.id.lay9);
        lay10 = (RelativeLayout) findViewById(R.id.lay10);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);

        checkView();


    }

    private void checkView() {

        if (mApp.getPreference().getBoolean(Common.PROFILESUMMARY, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

            psContainer = (LinearLayout) findViewById(R.id.ps_container);
            addPS = (Button) findViewById(R.id.add_ps);
            psUpdate = (Button) findViewById(R.id.ps_update);


            addPS.setOnClickListener(this);
            psUpdate.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.WORKEXPERIENCE, false) == false) {

            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

            wkContainer = (LinearLayout) findViewById(R.id.ws_container);
            addws = (Button) findViewById(R.id.add_ws);
            wsUpdate = (Button) findViewById(R.id.ws_update);


            addws.setOnClickListener(this);
            wsUpdate.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.WORKINFO, false) == false) {

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.VISIBLE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

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
            myp1 = new MonthYearPicker(this);


            wkDetails = (Button) findViewById(R.id.wk_detail);
            fYr.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        myp.build(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fYr.setText(myp.getSelectedYear() + "-" + myp.getSelectedMonthShortName());
                            }
                        }, null);
                        myp.show();

                    }
                    return false;
                }
            });

            tYr.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        myp1.build(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tYr.setText(myp.getSelectedYear() + "-" + myp.getSelectedMonthShortName());
                            }
                        }, null);
                        myp1.show();


                    }
                    return false;
                }
            });


            new GetOrg(UpdateSeniorResumeActivity.this, progressBar, scroll, orgname).execute();

            orgname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                    TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                    orgname.setText(txt1.getText().toString());
                    location.setText(txt4.getText().toString());
                    Ins=true;
                }
            });


            wrking.setOnClickListener(this);
            wkDetails.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.QUALIFICATION, false) == false) {

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.VISIBLE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

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
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.VISIBLE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

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
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.VISIBLE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

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
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.VISIBLE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

            skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);
            workArea = (MultiAutoCompleteTextView) findViewById(R.id.area);
            dev_env = (EditText) findViewById(R.id.dev_env);
            others = (EditText) findViewById(R.id.others);
            cercourse = (EditText) findViewById(R.id.cer_name);
            cerCentre = (EditText) findViewById(R.id.cer_centre);
            cerDur = (EditText) findViewById(R.id.cer_duration);
            sk_update = (Button) findViewById(R.id.sk_update);
            tex = (TextView) findViewById(R.id.tex);
            tex1 = (TextView) findViewById(R.id.tex1);


            sk_update.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.HOBBIES, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.VISIBLE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.GONE);

            l1 = (LinearLayout) findViewById(R.id.container);
            l2 = (LinearLayout) findViewById(R.id.extra_container);

            hobby = (EditText) findViewById(R.id.hobby);
            lang = (EditText) findViewById(R.id.lang);
            add_achieve = (Button) findViewById(R.id.add_achieve);
            o_update = (Button) findViewById(R.id.o_update);
            add_extra = (Button) findViewById(R.id.add_extra);
            ah = (TextView) findViewById(R.id.ah);
            extra_c = (TextView) findViewById(R.id.extra_c);
            ot = (TextView) findViewById(R.id.ot);


            add_achieve.setOnClickListener(this);
            o_update.setOnClickListener(this);
            add_extra.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.PROJECT, false) == false) {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.VISIBLE);
            lay10.setVisibility(View.GONE);
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
            p_update.setOnClickListener(this);
            acd.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.PERSONALINFO, false) == false) {

            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);

            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);
            lay8.setVisibility(View.GONE);
            lay9.setVisibility(View.GONE);
            lay10.setVisibility(View.VISIBLE);


            list = new ArrayList<Sample>();

            new getLocation().execute();

            dob = (EditText) findViewById(R.id.dob);
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
            prsnl = (TextView) findViewById(R.id.prsnl);

            dob.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        DateDialog dialog = new DateDialog(v);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        dialog.show(ft, "DataPicker");
                    }
                    return false;
                }
            });

            pr_update = (Button) findViewById(R.id.pr_update);
            pr_update.setOnClickListener(this);


        } else {
            startActivity(new Intent(getApplicationContext(), ExpResumeGenerate.class));
            finish();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_ps:
                addProfileSummary();
                break;
            case R.id.ps_update:
                getProfileSummary(psContainer);
                break;
            case R.id.add_ws:
                addWorkSummary();
                break;
            case R.id.ws_update:
                getWorkSummary(wkContainer);
                break;
            case R.id.wrking:
                if (((CheckBox) view).isChecked()) {
                    present.setVisibility(View.VISIBLE);
                    tYr.setVisibility(View.GONE);
                    visible = false;
                } else {
                    present.setVisibility(View.GONE);
                    tYr.setVisibility(View.VISIBLE);
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
                } else if (fYr.getText().toString().length() == 0) {
                    fYr.setError("Field Mandatory");
                    fYr.requestFocus();
                } else {
                    if (visible) {
                        if (tYr.getText().toString().length() == 0) {
                            tYr.setError("Field Mandatory");
                            tYr.requestFocus();
                        } else {
                            toDate = tYr.getText().toString();
                        }
                    } else {
                        toDate = "present";
                    }
                    if (toDate != null) {
                        new Wrk().execute();
                    }


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
                } else if (workArea.getText().toString().length() == 0) {
                    workArea.setError("Interest Area Mandatory");
                    workArea.requestFocus();
                } else {
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
            case R.id.add_achieve:
                addAchieve();
                break;
            case R.id.add_extra:
                addExtra();
                break;
            case R.id.o_update:
                getAchieve(l1, l2);
                break;
            case R.id.acd:
                if (((CheckBox) view).isChecked()) {
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
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Project Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("dob", uDob);
            seniorData.put("gender", usex);
            seniorData.put("address", udno);
            seniorData.put("city", uct);
            seniorData.put("district", udistirct);
            seniorData.put("state", ustate);
            seniorData.put("country", ucnt);
            seniorData.put("pincode", upin);
            seniorData.put("father_name", ufName);
            seniorData.put("mother_name", umName);
            if (ins == true) {
                seniorData.put("insrt", "false");
            } else {
                seniorData.put("insrt", "true");
            }
            try {
                JSONObject json = Connection.UrlConnection(php.persnal_info, seniorData);
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
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 4).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Update", Toast.LENGTH_LONG).show();
                            mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, true).commit();

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

    public class getLocation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("id", "city");
            try {

                JSONObject json = Connection.UrlConnection(php.getCity, seniorData);
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
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
        }
    }

    public class updateProject extends AsyncTask<String, String, String> {
        String uTitle = title.getText().toString();
        String uPlatform = platform.getText().toString();
        String uRole = role.getText().toString();
        String uTeamSze = team_sze.getText().toString();
        String uDur = duration.getText().toString();
        String udesc = description.getText().toString();
        String uUrl = url.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Project Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("title", uTitle);
            seniorData.put("platform", uPlatform);
            seniorData.put("role", uRole);
            seniorData.put("teamSze", uTeamSze);
            seniorData.put("dur", uDur);
            seniorData.put("desc", udesc);
            seniorData.put("url", uUrl);
            seniorData.put("isAcd", isAcd);
            seniorData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.project, seniorData);
                int succ = json.getInt("success");
                if (succ == 0) {

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 6).commit();
                            mApp.getPreference().edit().putBoolean(Common.PROJECT, true).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Update Project Details", Toast.LENGTH_SHORT).show();


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

    private void addExtra() {
        EditText et;
        if (l == 0) {
            et = new EditText(this);
            et.setHint("Extra Curricular Activity");
            et.setId(l);
            et.setTypeface(helper.droid(getApplicationContext()));
            et.requestFocus();
            l2.addView(et);
            l++;
        } else {
            int i = l - 1;
            et = (EditText) this.findViewById(i);
            if (et.getText().toString().length() == 0) {
                et.setError("Filed Mandatory");
            } else {
                et = new EditText(this);
                et.setHint("Extra Curricular Activity");
                et.setId(l);
                et.setTypeface(helper.droid(getApplicationContext()));
                et.requestFocus();
                l2.addView(et);
                l++;
            }
        }

    }

    private void getAchieve(ViewGroup parent, ViewGroup parent1) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            EditText et = (EditText) child;
            if (et.getText().toString().length() == 0) {
            } else {
                achieve = new String[]{et.getText().toString()};
            }

        }

        for (int j = 0; j < parent1.getChildCount(); j++) {
            View child = parent1.getChildAt(j);
            EditText et = (EditText) child;
            if (et.getText().toString().length() == 0) {
            } else {
                extra = new String[]{et.getText().toString()};
            }
        }
        if (hobby.getText().toString().length() == 0) {
            hobby.setError("Field Mandatory");
        } else if (lang.getText().toString().length() == 0) {
            lang.setError("Field Mandatory");
        } else {
            new achievement().execute();
            new extraCurricular().execute();
            new hobby().execute();
        }


    }

    private void addAchieve() {
        EditText et;
        if (k == 0) {
            et = new EditText(this);
            et.setHint("Achievement");
            et.setId(k);
            et.setTypeface(helper.droid(getApplicationContext()));
            et.requestFocus();
            l1.addView(et);
            k++;
        } else {
            int i = k - 1;
            et = (EditText) this.findViewById(i);
            if (et.getText().toString().length() == 0) {
                et.setError("Field Mandatory");
            } else {
                et = new EditText(this);
                et.setHint("Achievement");
                et.setId(k);
                et.setTypeface(helper.droid(getApplicationContext()));
                et.requestFocus();
                l1.addView(et);
                k++;
            }
        }

    }

    public class hobby extends AsyncTask<String, String, String> {
        String uHobby = hobby.getText().toString();
        String uLang = lang.getText().toString();

        @Override
        protected String doInBackground(String... params) {

            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("hobby", uHobby);
            seniorData.put("lang", uLang);
            try {
                JSONObject json = Connection.UrlConnection(php.hobbyLang, seniorData);

            } catch (Exception e) {
            }

            return null;
        }
    }


    public class extraCurricular extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < extra.length; i++) {
                seniorData = new HashMap<String, String>();
                seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                seniorData.put("extra", extra[i]);
                try {
                    JSONObject json = Connection.UrlConnection(php.extra, seniorData);
                    int succ = json.getInt("success");


                } catch (Exception e) {
                }
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < achieve.length; i++) {
                seniorData = new HashMap<String, String>();
                seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                seniorData.put("a_name", achieve[i]);
                try {
                    JSONObject json = Connection.UrlConnection(php.achievement, seniorData);
                    int succ = json.getInt("success");


                } catch (Exception e) {
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 3).commit();
        }
    }

    public class Skill extends AsyncTask<String, String, String> {

        String sSkill = skill.getText().toString();
        String sWorkArea = workArea.getText().toString();
        String sDevEnv = dev_env.getText().toString();
        String sOther = others.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("skill", sSkill);
            seniorData.put("area", sWorkArea);
            seniorData.put("devEnv", sDevEnv);
            seniorData.put("other", sOther);
            try {
                JSONObject json = Connection.UrlConnection(php.skill, seniorData);
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
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 4).commit();
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

            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("cer_name", cName);
            seniorData.put("cer_centre", cCentre);
            seniorData.put("cer_dur", cDuration);
            seniorData.put("type", "add");
            try {
                JSONObject json = Connection.UrlConnection(php.certification, seniorData);
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
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating SSLC!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("level", "5");
            seniorData.put("concent", "SSLC");
            seniorData.put("hName", usName);
            seniorData.put("bName", usBoard);
            seniorData.put("cty", usCty);
            seniorData.put("frmDat", uFrm);
            seniorData.put("toDat", uTo);
            seniorData.put("agrt", usPercent);
            if (ins == true) {
                seniorData.put("insrt", "false");
            } else {
                seniorData.put("insrt", "true");
            }
            seniorData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.schooling, seniorData);

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
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 6).commit();
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
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating HSC!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("level", "4");
            seniorData.put("concent", "HSC");
            seniorData.put("hName", uhname);
            seniorData.put("bName", uhbname);
            seniorData.put("cty", ucty);
            seniorData.put("frmDat", uFrm);
            seniorData.put("toDat", uTo);
            seniorData.put("agrt", upercent);
            if (ins == true) {
                seniorData.put("insrt", "false");
            } else {
                seniorData.put("insrt", "true");
            }

            seniorData.put("type", "add");

            try {
                JSONObject json = Connection.UrlConnection(php.schooling, seniorData);

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
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 6).commit();
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
            seniorData = new HashMap<String, String>();
            seniorData.put("id", "school");

            try {
                JSONObject json = Connection.UrlConnection(php.getSchool, seniorData);
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
            seniorData = new HashMap<String, String>();
            seniorData.put("id", "institution");

            String[] locate = new String[0];
            try {
                JSONObject json = Connection.UrlConnection(php.getInstitution, seniorData);
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
            scroll.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public class getCourse extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            seniorData = new HashMap<String, String>();
            seniorData.put("id", "course");
            try {

                JSONObject json = Connection.UrlConnection(php.getCourse, seniorData);
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
                            fYr.requestFocus();

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
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Qualification!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("level", q);
            seniorData.put("concent", uConcent);
            seniorData.put("clgName", uClgName);
            seniorData.put("univ", uUniv);
            seniorData.put("loc", uLoc);
            seniorData.put("frm", uFrm);
            seniorData.put("to", uTo);
            seniorData.put("agrt", uAgrt);
            if (ins == true) {
                seniorData.put("insrt", "false");
            } else {
                seniorData.put("insrt", "true");
            }

            if (cIns == true) {
                seniorData.put("cInsrt", "false");
            } else {
                seniorData.put("cInsrt", "true");
            }
            seniorData.put("type", "add");


            try {
                JSONObject json = Connection.UrlConnection(php.qualify, seniorData);

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
                            new GetInfo(UpdateSeniorResumeActivity.this, mApp.getPreference().getString(Common.u_id, "")).execute();
                            new Connect(UpdateSeniorResumeActivity.this, mApp.getPreference().getString(Common.u_id, ""), mApp.getPreference().getString(Common.LEVEL, ""),
                                    clgName.getText().toString(), conCent.getText().toString()).execute();

                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 8).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Update Qualification!...", Toast.LENGTH_SHORT).show();
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


    public class Wrk extends AsyncTask<String, String, String> {

        String uOrgname = orgname.getText().toString();
        String uPosition = postition.getText().toString();
        String uLocation = location.getText().toString();
        String uFrm = fYr.getText().toString();
        String uTo = tYr.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Work Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            seniorData.put("org_name", uOrgname);
            seniorData.put("position", uPosition);
            seniorData.put("loc", uLocation);
            seniorData.put("frm", uFrm);
            seniorData.put("to", uTo);
            seniorData.put("type", "add");
            if(Ins==true){
                seniorData.put("ins","false");
            }else{
                seniorData.put("ins","true");
            }


            try {
                JSONObject json = Connection.UrlConnection(php.work_info, seniorData);

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
                            new GetInfo(UpdateSeniorResumeActivity.this, mApp.getPreference().getString(Common.u_id, "")).execute();
                            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);

                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 6).commit();
                            mApp.getPreference().edit().putBoolean(Common.WORKINFO, true).commit();
                            Toast.makeText(getApplicationContext(), "Work Details Updated Successfully", Toast.LENGTH_SHORT).show();

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


    private void addWorkSummary() {
        EditText et;

        if (j == 0) {
            et = new EditText(this);
            et.setHint("Work Summary");
            et.setTypeface(helper.droid(getApplicationContext()));
            et.setId(j);
            et.requestFocus();
            wkContainer.addView(et);
            j++;
        } else {
            int i = j - 1;
            et = (EditText) this.findViewById(i);
            if (et.getText().toString().length() == 0) {
                et.setError("Field Mandatory");
            } else {
                et = new EditText(this);
                et.setHint("Work Summary");
                et.setTypeface(helper.droid(getApplicationContext()));
                et.requestFocus();
                et.setId(j);
                wkContainer.addView(et);
                j++;
            }
        }

    }

    private void getWorkSummary(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            EditText et = (EditText) child;

            if (et.getText().toString().length() == 0) {
            } else {
                work = new String[]{et.getText().toString()};
            }


        }

        if (work.length == 0) {
            Toast.makeText(getApplicationContext(), "Enter Work Summary", Toast.LENGTH_SHORT).show();
        } else {
            new workUpdate().execute();
        }


    }

    public class workUpdate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Work Summary");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < work.length; i++) {
                seniorData = new HashMap<String, String>();
                seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                seniorData.put("summary", work[i]);
                try {
                    JSONObject json = Connection.UrlConnection(php.work_experience, seniorData);

                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                    mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 4).commit();
                    Toast.makeText(getApplicationContext(), "Work Summary Updated", Toast.LENGTH_SHORT).show();
                    mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, true).commit();
                    checkView();
                }
            });
        }
    }


    private void addProfileSummary() {
        EditText et;
        if (i == 0) {
            et = new EditText(this);
            et.setHint("Profile Summary");
            et.setId(i);
            et.setTypeface(helper.droid(getApplicationContext()));
            et.requestFocus();
            psContainer.addView(et);
            i++;
        } else {
            int j = i - 1;
            et = (EditText) this.findViewById(j);
            if (et.getText().toString().length() == 0) {
                et.setError("Filed Mandatory");
            } else {
                et = new EditText(this);
                et.setHint("Profile Summary");
                et.setTypeface(helper.droid(getApplicationContext()));
                et.setId(i);
                psContainer.addView(et);
                i++;
            }

        }


    }

    private void getProfileSummary(ViewGroup psContainer) {

        for (int i = 0; i < psContainer.getChildCount(); i++) {
            View child = psContainer.getChildAt(i);
            EditText et = (EditText) child;
            if (et.getText().toString().length() == 0) {
            } else {
                profile = new String[]{et.getText().toString()};
            }

        }


        if (profile.length == 0) {
            Toast.makeText(getApplicationContext(), "Enter Profile Summary", Toast.LENGTH_SHORT).show();
        } else {
            new profileUpdate().execute();
        }

    }


    public class profileUpdate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateSeniorResumeActivity.this);
            pDialog.setMessage("Updating Profile Summary");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            for (int i = 0; i < profile.length; i++) {
                seniorData = new HashMap<String, String>();
                seniorData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                seniorData.put("summary", profile[i]);
                seniorData.put("table", "proSum");
                try {
                    JSONObject json = Connection.UrlConnection(php.profile_summary, seniorData);

                } catch (Exception e) {
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                    mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 5).commit();
                    Toast.makeText(getApplicationContext(), "Profile Summary Updated", Toast.LENGTH_SHORT).show();
                    mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, true).commit();
                    checkView();
                }
            });

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

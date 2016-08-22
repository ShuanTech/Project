package com.shuan.project.resume;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.adapter.InstitutionAdapter;
import com.shuan.project.adapter.LocationAdapter;
import com.shuan.project.fragment.DateDialog;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateJuniorResumeActivity extends AppCompatActivity implements View.OnClickListener {

    /*Global Fields*/

    private Helper helper = new Helper();
    private Common mApp;
    private HashMap<String, String> eData;
    private RelativeLayout lay1, lay2, lay3, lay4, lay5, lay6, lay7;
    private ProgressDialog pDialog;
    private String[] mnth = new String[0];
    private String[] date = new String[0];
    private boolean ins = false;
    private ArrayList<Sample> list;
    private InstitutionAdapter adapter;
    private String frmDate, toDate;
    private TextView qfy;

    /* College Fields */
    private AutoCompleteTextView clgName, univ, loc, conCent;
    private EditText fY, fM, fD, tY, tM, tD, agrt;
    private String[] cours = new  String[0];
    private RelativeLayout frm_mnth, frm_date, to_mnth, to_date;
    private boolean cIns = false;
    private Button q_update;
    private TextView frm,to;

    /* Higher Secondary Fields */
    private AutoCompleteTextView h_name, board, cty;
    private EditText hfY, hfM, hfD, htY, htM, htD, hAgrt;
    private RelativeLayout h_frm_mnth, h_frm_date, h_to_mnth, h_to_date;
    private Button h_update;
    private TextView hsc,h_frm,h_to;

    /* Secondary Fields */
    private AutoCompleteTextView s_name, s_board, s_cty;
    private EditText sfY, sfM, sfD, stY, stM, stD, sAgrt;
    private RelativeLayout s_frm_mnth, s_frm_date, s_to_mnth, s_to_date;
    private Button s_update;
    private TextView sslc,s_frm,s_to;

    /* Skill Fields */
    private MultiAutoCompleteTextView skill, workArea;
    private EditText cercourse, cerCentre, cerDur, dev_env, others;
    private Button sk_update;
    private TextView tex;

    /* Hobbies Field */
    private LinearLayout l1, l2, l3;
    private int k = 0, l = 0;
    private String[] achieve = new String[0];
    private TextView ah,extra_c,ot;
    private String[] extra = new String[0];
    private EditText hobby, lang;
    private Button add_achieve, add_extra, o_update;

    /* Project Field */
    private EditText title, platform, role, team_sze, duration, description;
    private Button p_update;
    private TextView pr;

    /* Personal Field */
    public EditText dob, fName, mName, addr, pinNo;
    private TextView prsnl;
    public AutoCompleteTextView locate, district, state, country;
    public RadioButton radio, r1, r2;
    public RadioGroup sex;
    public LocationAdapter adapter1;
    public Button pr_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_junior_resume_data);

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


            clgName = (AutoCompleteTextView) findViewById(R.id.clg_name);
            univ = (AutoCompleteTextView) findViewById(R.id.univ);
            loc = (AutoCompleteTextView) findViewById(R.id.c_location);
            fY = (EditText) findViewById(R.id.c_f_year);
            fM = (EditText) findViewById(R.id.c_f_month);
            fD = (EditText) findViewById(R.id.c_f_date);
            tY = (EditText) findViewById(R.id.c_t_year);
            tM = (EditText) findViewById(R.id.c_t_month);
            tD = (EditText) findViewById(R.id.c_t_date);
            conCent = (AutoCompleteTextView) findViewById(R.id.concent);
            agrt = (EditText) findViewById(R.id.agrt);

            frm_mnth = (RelativeLayout) findViewById(R.id.frm_mnth);
            frm_date = (RelativeLayout) findViewById(R.id.frm_dat);
            to_mnth = (RelativeLayout) findViewById(R.id.to_mnth);
            to_date = (RelativeLayout) findViewById(R.id.to_dat);
            mnth = getResources().getStringArray(R.array.month);
            date = getResources().getStringArray(R.array.date);
            frm = (TextView) findViewById(R.id.c_frm);
            to = (TextView) findViewById(R.id.c_to);
            qfy = (TextView) findViewById(R.id.qfy);

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
            qfy.setTypeface(helper.droid(getApplicationContext()));

            q_update = (Button) findViewById(R.id.q_update);
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
            board = (AutoCompleteTextView) findViewById(R.id.board);
            cty = (AutoCompleteTextView) findViewById(R.id.cty);

            hsc = (TextView) findViewById(R.id.hsc);
            h_frm = (TextView) findViewById(R.id.h_frm);
            h_to = (TextView) findViewById(R.id.h_to);
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

            hsc.setTypeface(helper.droid(getApplicationContext()));
            h_name.setTypeface(helper.droid(getApplicationContext()));
            h_frm.setTypeface(helper.droid(getApplicationContext()));
            h_to.setTypeface(helper.droid(getApplicationContext()));
            board.setTypeface(helper.droid(getApplicationContext()));
            cty.setTypeface(helper.droid(getApplicationContext()));
            hsc.setTypeface(helper.droid(getApplicationContext()));
            hfY.setTypeface(helper.droid(getApplicationContext()));
            hfM.setTypeface(helper.droid(getApplicationContext()));
            hfD.setTypeface(helper.droid(getApplicationContext()));
            htY.setTypeface(helper.droid(getApplicationContext()));
            htM.setTypeface(helper.droid(getApplicationContext()));
            htD.setTypeface(helper.droid(getApplicationContext()));
            hAgrt.setTypeface(helper.droid(getApplicationContext()));

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
            s_board = (AutoCompleteTextView) findViewById(R.id.s_board);
            s_cty = (AutoCompleteTextView) findViewById(R.id.s_cty);

            sslc = (TextView) findViewById(R.id.sslc);
            s_frm = (TextView) findViewById(R.id.s_frm);
            s_to = (TextView) findViewById(R.id.s_to);
            sfY = (EditText) findViewById(R.id.s_f_year);
            sfM = (EditText) findViewById(R.id.s_f_month);
            sfD = (EditText) findViewById(R.id.s_f_date);
            stY = (EditText) findViewById(R.id.s_t_year);
            stM = (EditText) findViewById(R.id.s_t_month);
            stD = (EditText) findViewById(R.id.s_t_date);
            sAgrt = (EditText) findViewById(R.id.s_agrt);


            sslc.setTypeface(helper.droid(getApplicationContext()));
            s_name.setTypeface(helper.droid(getApplicationContext()));
            s_board.setTypeface(helper.droid(getApplicationContext()));
            s_cty.setTypeface(helper.droid(getApplicationContext()));
            s_frm.setTypeface(helper.droid(getApplicationContext()));
            s_to.setTypeface(helper.droid(getApplicationContext()));
            sfY.setTypeface(helper.droid(getApplicationContext()));
            sfM.setTypeface(helper.droid(getApplicationContext()));
            sfD.setTypeface(helper.droid(getApplicationContext()));
            stY.setTypeface(helper.droid(getApplicationContext()));
            stM.setTypeface(helper.droid(getApplicationContext()));
            stD.setTypeface(helper.droid(getApplicationContext()));
            sAgrt.setTypeface(helper.droid(getApplicationContext()));

            s_frm_mnth = (RelativeLayout) findViewById(R.id.s_frm_mnth);
            s_frm_date = (RelativeLayout) findViewById(R.id.s_frm_dat);
            s_to_mnth = (RelativeLayout) findViewById(R.id.s_to_mnth);
            s_to_date = (RelativeLayout) findViewById(R.id.s_to_dat);

            mnth = getResources().getStringArray(R.array.month);
            date = getResources().getStringArray(R.array.date);

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
                }
            });


            s_update = (Button) findViewById(R.id.s_update);

            sfM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("sfm");
                    }
                    return false;
                }
            });

            sfD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("sfd");
                    }
                    return false;
                }
            });

            stM.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showMnth("stm");
                    }
                    return false;
                }
            });

            stD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showDate("std");
                    }
                    return false;
                }
            });


            sfY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (sfY.getText().toString().length() == 4) {
                        s_frm_mnth.setVisibility(View.VISIBLE);
                    }

                }
            });
            stY.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (stY.getText().toString().length() == 4) {
                        s_to_mnth.setVisibility(View.VISIBLE);
                    }
                }
            });


            s_update.setOnClickListener(this);

        } else if (mApp.getPreference().getBoolean(Common.SKILL, false) == false) {
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.VISIBLE);
            lay5.setVisibility(View.GONE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

            skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);
            workArea = (MultiAutoCompleteTextView) findViewById(R.id.area);
            dev_env = (EditText) findViewById(R.id.dev_env);
            others = (EditText) findViewById(R.id.others);
            cercourse = (EditText) findViewById(R.id.cer_name);
            cerCentre = (EditText) findViewById(R.id.cer_centre);
            cerDur = (EditText) findViewById(R.id.cer_duration);
            sk_update = (Button) findViewById(R.id.sk_update);
            tex = (TextView) findViewById(R.id.tex);


            tex.setTypeface(helper.droid(getApplicationContext()));
            skill.setTypeface(helper.droid(getApplicationContext()));
            dev_env.setTypeface(helper.droid(getApplicationContext()));
            others.setTypeface(helper.droid(getApplicationContext()));
            cercourse.setTypeface(helper.droid(getApplicationContext()));
            cerCentre.setTypeface(helper.droid(getApplicationContext()));
            cerDur.setTypeface(helper.droid(getApplicationContext()));

            sk_update.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.HOBBIES, false) == false) {
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.GONE);
            lay3.setVisibility(View.GONE);
            lay4.setVisibility(View.GONE);
            lay5.setVisibility(View.VISIBLE);
            lay6.setVisibility(View.GONE);
            lay7.setVisibility(View.GONE);

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

            ah.setTypeface(helper.droid(getApplicationContext()));
            extra_c.setTypeface(helper.droid(getApplicationContext()));
            ot.setTypeface(helper.droid(getApplicationContext()));
            hobby.setTypeface(helper.droid(getApplicationContext()));
            lang.setTypeface(helper.droid(getApplicationContext()));
            add_achieve.setTypeface(helper.droid(getApplicationContext()));
            o_update.setTypeface(helper.droid(getApplicationContext()));
            add_extra.setTypeface(helper.droid(getApplicationContext()));


            add_achieve.setOnClickListener(this);
            o_update.setOnClickListener(this);
            add_extra.setOnClickListener(this);
        } else if (mApp.getPreference().getBoolean(Common.PROJECT, false) == false) {
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

            pr.setTypeface(helper.droid(getApplicationContext()));
            title.setTypeface(helper.droid(getApplicationContext()));
            platform.setTypeface(helper.droid(getApplicationContext()));
            role.setTypeface(helper.droid(getApplicationContext()));
            team_sze.setTypeface(helper.droid(getApplicationContext()));
            duration.setTypeface(helper.droid(getApplicationContext()));
            description.setTypeface(helper.droid(getApplicationContext()));

            p_update = (Button) findViewById(R.id.p_update);

            p_update.setOnClickListener(this);
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

            prsnl.setTypeface(helper.droid(getApplicationContext()));
            dob.setTypeface(helper.droid(getApplicationContext()));
            r1.setTypeface(helper.droid(getApplicationContext()));
            r2.setTypeface(helper.droid(getApplicationContext()));
            fName.setTypeface(helper.droid(getApplicationContext()));
            mName.setTypeface(helper.droid(getApplicationContext()));
            addr.setTypeface(helper.droid(getApplicationContext()));
            locate.setTypeface(helper.droid(getApplicationContext()));
            district.setTypeface(helper.droid(getApplicationContext()));
            state.setTypeface(helper.droid(getApplicationContext()));
            country.setTypeface(helper.droid(getApplicationContext()));
            pinNo.setTypeface(helper.droid(getApplicationContext()));


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

            pr_update = (Button) findViewById(R.id.pr_update);
            pr_update.setOnClickListener(this);


        } else {
            startActivity(new Intent(getApplicationContext(), JuniorResumeGenerate.class));
            finish();
        }

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
                } else if (sfY.getText().toString().length() == 0) {
                    sfY.setError("Field Mandatory");
                } else if (sfM.getText().toString().length() == 0) {
                    sfM.setError("Field Mandatory");
                } else if (sfD.getText().toString().length() == 0) {
                    sfD.setError("Field Mandatory");
                } else if (stY.getText().toString().length() == 0) {
                    stY.setError("Field Mandatory");
                } else if (stM.getText().toString().length() == 0) {
                    stM.setError("Field Mandatory");
                } else if (stD.getText().toString().length() == 0) {
                    stD.setError("Field Mandatory");
                } else if (sAgrt.getText().toString().length() == 0) {
                    sAgrt.setError("Field Mandatory");
                } else {
                    frmDate = sfY.getText().toString() + "-" + sfM.getText().toString() + "-" + sfD.getText().toString();
                    toDate = stY.getText().toString() + "-" + stM.getText().toString() + "-" + stD.getText().toString();
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
                        mApp.getPreference().edit().putBoolean(Common.CERITIFY, false).commit();
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
                            Toast.makeText(getApplicationContext(), "Successfully Update", Toast.LENGTH_LONG).show();
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
            eData.put("type","1");

            try {
                JSONObject json = Connection.UrlConnection(php.project, eData);
                int succ = json.getInt("success");
                if (succ == 0) {

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.PROJECT, true).commit();
                            Toast.makeText(getApplicationContext(), "Successfully Update Project Details", Toast.LENGTH_SHORT).show();
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

    private void addExtra() {
        EditText et;
        if(l==0){
            et = new EditText(this);
            et.setHint("Extra Curricular Activity");
            et.setId(l);
            et.requestFocus();
            l2.addView(et);
            l++;
        }else{
            int i=l-1;
            et= (EditText) this.findViewById(i);
            if(et.getText().toString().length()==0){
                et.setError("Filed Mandatory");
            }else{
                et = new EditText(this);
                et.setHint("Extra Curricular Activity");
                et.setId(l);
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
            if (et.getText().toString().length() == 0) {} else {
                achieve = new String[]{et.getText().toString()};
            }

        }

        for (int j = 0; j < parent1.getChildCount(); j++) {
            View child = parent1.getChildAt(j);
            EditText et = (EditText) child;
            if (et.getText().toString().length() == 0) {} else {
                extra = new String[]{et.getText().toString()};
            }
        }
        if(hobby.getText().toString().length()==0){
            hobby.setError("Field Mandatory");
        }else if(lang.getText().toString().length()==0){
            lang.setError("Field Mandatory");
        }else{
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


        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < extra.length; i++) {
                eData = new HashMap<String, String>();
                eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                eData.put("extra", extra[i]);
                try {
                    JSONObject json = Connection.UrlConnection(php.extra, eData);
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
            pDialog = new ProgressDialog(UpdateJuniorResumeActivity.this);
            pDialog.setMessage("Updating Details!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < achieve.length; i++) {
                eData = new HashMap<String, String>();
                eData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
                eData.put("a_name", achieve[i]);
                try {
                    JSONObject json = Connection.UrlConnection(php.achievement, eData);
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
            eData.put("area", sWorkArea);
            eData.put("devEnv", sDevEnv);
            eData.put("other", sOther);
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
            eData.put("concent", "SSLC");
            eData.put("hName", usName);
            eData.put("bName", usBoard);
            eData.put("cty", usCty);
            eData.put("frmDat", frmDate);
            eData.put("toDat", toDate);
            eData.put("agrt", usPercent);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }

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
            eData.put("concent", "HSC");
            eData.put("hName", uhname);
            eData.put("bName", uhbname);
            eData.put("cty", ucty);
            eData.put("frmDat", frmDate);
            eData.put("toDat", toDate);
            eData.put("agrt", upercent);
            if (ins == true) {
                eData.put("insrt", "false");
            } else {
                eData.put("insrt", "true");
            }

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
                                }
                            });
                        }
                    });

                }
            } catch (Exception e) {
            }

            return null;
        }
    }


    private void showDate(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateJuniorResumeActivity.this);
        builder.setTitle("Select Date")
                .setSingleChoiceItems(date, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (val.equalsIgnoreCase("fd")) {
                            fD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("td")) {
                            tD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("hfd")) {
                            hfD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("htd")) {
                            htD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("sfd")) {
                            sfD.setText(date[which]);
                        } else {
                            stD.setText(date[which]);
                        }

                        dialog.cancel();
                    }
                }).show();
    }

    private void showMnth(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateJuniorResumeActivity.this);
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
                        } else if (val.equalsIgnoreCase("hfm")) {
                            hfM.setText(mnth[which]);
                            h_frm_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("htm")) {
                            htM.setText(mnth[which]);
                            h_to_date.setVisibility(View.VISIBLE);
                        } else if (val.equalsIgnoreCase("sfm")) {
                            sfM.setText(mnth[which]);
                            s_frm_date.setVisibility(View.VISIBLE);
                        } else {
                            stM.setText(mnth[which]);
                            s_to_date.setVisibility(View.VISIBLE);
                        }

                        dialog.cancel();
                    }
                }).show();

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


}

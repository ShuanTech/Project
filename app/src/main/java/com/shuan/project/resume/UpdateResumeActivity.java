package com.shuan.project.resume;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.AddCollegedetail;
import com.shuan.project.asyncTasks.AddWrkDetail;
import com.shuan.project.asyncTasks.EditDetail;
import com.shuan.project.asyncTasks.EditWrkDetail;
import com.shuan.project.asyncTasks.GetCollege;
import com.shuan.project.asyncTasks.GetCourse;
import com.shuan.project.asyncTasks.GetOrg;
import com.shuan.project.asyncTasks.profileSummaryUpdate;

import java.util.HashMap;

public class UpdateResumeActivity extends AppCompatActivity implements View.OnClickListener {

    private String what, which;
    private Common mApp;
    private int i = 0, j = 0, k = 0, l = 0;
    private Helper helper = new Helper();
    private LinearLayout psEdt, wrkDet, wrkExp, clg;
    private HashMap<String, String> seniorData;
    private ProgressDialog pDialog;
    private String frmDate, toDate;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private boolean ins = false;



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
                    cIns=true;
                    cfY.requestFocus();
                }
            });

            if(what.equalsIgnoreCase("edit")){
                if(mApp.getPreference().getString("level","").equalsIgnoreCase("1")){
                    level.setSelection(0);
                }else if(mApp.getPreference().getString("level","").equalsIgnoreCase("2")){
                    level.setSelection(1);
                }else{
                    level.setSelection(2);
                }
                clgName.setText(mApp.getPreference().getString("insName",""));
                univ.setText(mApp.getPreference().getString("univ",""));
                loc.setText(mApp.getPreference().getString("location",""));
                conCent.setText(mApp.getPreference().getString("conCent",""));
                agrt.setText(mApp.getPreference().getString("aggrt",""));
            }


            q_update.setOnClickListener(this);

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
                            new AddCollegedetail(UpdateResumeActivity.this,mApp.getPreference().getString(Common.u_id,""),q,conCent.getText().toString(),
                                    clgName.getText().toString(),univ.getText().toString(),loc.getText().toString(),
                                    frmDate,toDate,agrt.getText().toString(),ins,cIns,"add").execute();
                    } else {
                        new AddCollegedetail(UpdateResumeActivity.this,mApp.getPreference().getString("eId",""),q,conCent.getText().toString(),
                                clgName.getText().toString(),univ.getText().toString(),loc.getText().toString(),
                                frmDate,toDate,agrt.getText().toString(),ins,cIns,"edit").execute();
                    }
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
                        } /*else if (val.equalsIgnoreCase("hfd")) {
                            hfD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("htd")) {
                            htD.setText(date[which]);
                        } else if (val.equalsIgnoreCase("sfd")) {
                            sfD.setText(date[which]);
                        } else {
                            stD.setText(date[which]);
                        }*/

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
                        } /*else if (val.equalsIgnoreCase("hfm")) {
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
                        }*/

                        dialog.cancel();
                    }
                }).show();

    }

}

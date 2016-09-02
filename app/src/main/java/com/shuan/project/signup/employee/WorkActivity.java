package com.shuan.project.signup.employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView orgname, postition, location;
    private Button wk_skip, wk_next;
    private HashMap<String, String> wData;
    private TextView frm,to;
    private EditText fY, fM, fD, tY, tM, tD, present;
    private String[] mnth = new String[0];
    private String[] date = new String[0];
    private RelativeLayout frm_mnth, frm_date, to_mnth, to_date;
    private String frmDate, toDate;
    private LinearLayout to_year;
    private CheckBox wrking;
    private boolean visible = false;
    private boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

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
        frm = (TextView) findViewById(R.id.frm);
        to = (TextView) findViewById(R.id.to);


        frm_mnth = (RelativeLayout) findViewById(R.id.frm_mnth);
        frm_date = (RelativeLayout) findViewById(R.id.frm_dat);
        to_mnth = (RelativeLayout) findViewById(R.id.to_mnth);
        to_date = (RelativeLayout) findViewById(R.id.to_dat);
        mnth = getResources().getStringArray(R.array.month);
        date = getResources().getStringArray(R.array.date);

        orgname.setTypeface(helper.droid(getApplicationContext()));
        postition.setTypeface(helper.droid(getApplicationContext()));
        location.setTypeface(helper.droid(getApplicationContext()));
        wrking.setTypeface(helper.droid(getApplicationContext()));
        fY.setTypeface(helper.droid(getApplicationContext()));
        fM.setTypeface(helper.droid(getApplicationContext()));
        fD.setTypeface(helper.droid(getApplicationContext()));
        tY.setTypeface(helper.droid(getApplicationContext()));
        tM.setTypeface(helper.droid(getApplicationContext()));
        tD.setTypeface(helper.droid(getApplicationContext()));
        present.setTypeface(helper.droid(getApplicationContext()));
        frm.setTypeface(helper.droid(getApplicationContext()));
        to.setTypeface(helper.droid(getApplicationContext()));


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


        wk_next = (Button) findViewById(R.id.wk_next);
        wk_skip = (Button) findViewById(R.id.wk_skip);


        orgname.addTextChangedListener(this);
        wrking.setOnClickListener(this);
        wk_next.setOnClickListener(this);
        wk_skip.setOnClickListener(this);


    }

    private void showDate(final String val) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.wk_next:
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
                        new Wrk().execute();
                    }
                }

                break;
            case R.id.wk_skip:
                mApp.getPreference().edit().putBoolean(Common.WORKINFO, false).commit();
                mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, true).commit();
                startActivity(new Intent(getApplicationContext(), EducationActivity.class));
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
        if (orgname.getText().toString().length() == 0) {
            wk_skip.setVisibility(View.VISIBLE);
            wk_next.setVisibility(View.GONE);
        } else {
            wk_skip.setVisibility(View.GONE);
            wk_next.setVisibility(View.VISIBLE);
        }
    }

    public class Wrk extends AsyncTask<String, String, String> {

        String uOrgname = orgname.getText().toString();
        String uPosition = postition.getText().toString();
        String uLocation = location.getText().toString();


        @Override
        protected String doInBackground(String... params) {
            wData = new HashMap<String, String>();
            wData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            wData.put("org_name", uOrgname);
            wData.put("position", uPosition);
            wData.put("loc", uLocation);
            wData.put("frm", frmDate);
            wData.put("to", toDate);
            wData.put("type","add");


            try {
                JSONObject json = Connection.UrlConnection(php.work_info, wData);

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
                            mApp.getPreference().edit().putBoolean(Common.WORKINFO,true).commit();
                            mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, true).commit();
                            Toast.makeText(getApplicationContext(), "Work Details Added Successfully", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), EducationActivity.class));
                            finish();
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
            mApp.getPreference().edit().putBoolean(Common.ACTIVITY1, true).commit();
            mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, false).commit();
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

package com.shuan.project.signup.employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.Utils.MonthYearPicker;
import com.shuan.project.asyncTasks.GetOrg;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView orgname;
    private Button wk_skip, wk_next;
    private HashMap<String, String> wData;
    private TextView frm, to;
    private EditText postition, location, fYr, tYr, present;
    private CheckBox wrking;
    private boolean visible = false;
    private boolean exit = false;
    private String toDate;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private MonthYearPicker myp;
    private MonthYearPicker myp1;
    private boolean cIns = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
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

        wk_next = (Button) findViewById(R.id.wk_next);
        wk_skip = (Button) findViewById(R.id.wk_skip);


        orgname.addTextChangedListener(this);
        wrking.setOnClickListener(this);
        wk_next.setOnClickListener(this);
        wk_skip.setOnClickListener(this);

        new GetOrg(WorkActivity.this, progressBar, scroll, orgname).execute();

        orgname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                orgname.setText(txt1.getText().toString());
                location.setText(txt4.getText().toString());
                cIns=true;
            }
        });

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


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wrking:
                if (((CheckBox) v).isChecked()) {
                    present.setVisibility(View.VISIBLE);
                    tYr.setVisibility(View.GONE);
                    visible = false;
                } else {
                    present.setVisibility(View.GONE);
                    tYr.setVisibility(View.VISIBLE);
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


                    new Wrk().execute();

                }

                break;
            case R.id.wk_skip:
                mApp.getPreference().edit().putBoolean(Common.WORKINFO, false).commit();
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
        String uFrm = fYr.getText().toString();


        @Override
        protected String doInBackground(String... params) {
            wData = new HashMap<String, String>();
            wData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            wData.put("org_name", uOrgname);
            wData.put("position", uPosition);
            wData.put("loc", uLocation);
            wData.put("frm", uFrm);
            wData.put("to", toDate);
            wData.put("type", "add");
            if(cIns==true){
                wData.put("ins","false");
            }else{
                wData.put("ins","true");
            }


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
                            mApp.getPreference().edit().putBoolean(Common.WORKINFO, true).commit();
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

package com.shuan.project.signup.employer;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.AboutCompany;
import com.shuan.project.asyncTasks.GetInfo;
import com.shuan.project.employer.EmployerActivity;
import com.shuan.project.fragment.DateDialog;

public class AboutCompanyActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper = new Helper();
    private Common mApp;

    private EditText yr_st, no_wrkr, c_des;
    private Button ft_skip, ft_next;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company);

        yr_st = (EditText) findViewById(R.id.yr_st);
        no_wrkr = (EditText) findViewById(R.id.no_wrkr);
        c_des = (EditText) findViewById(R.id.c_des);
        ft_next = (Button) findViewById(R.id.ft_next);
        ft_skip = (Button) findViewById(R.id.ft_skip);


        yr_st.setTypeface(helper.droid(getApplicationContext()));
        no_wrkr.setTypeface(helper.droid(getApplicationContext()));
        c_des.setTypeface(helper.droid(getApplicationContext()));
        ft_next.setTypeface(helper.droid(getApplicationContext()));
        ft_skip.setTypeface(helper.droid(getApplicationContext()));

        yr_st.setOnTouchListener(new View.OnTouchListener() {
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

        ft_skip.setOnClickListener(this);
        ft_next.setOnClickListener(this);


        yr_st.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (yr_st.getText().toString().length() == 0) {
                    ft_skip.setVisibility(View.VISIBLE);
                    ft_next.setVisibility(View.GONE);
                } else {
                    ft_skip.setVisibility(View.GONE);
                    ft_next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ft_next:
                if (yr_st.getText().toString().length() == 0) {
                    yr_st.setError("Field Mandatory");
                    yr_st.requestFocus();
                } else if (no_wrkr.getText().toString().length() == 0) {
                    no_wrkr.setError("Field Mandatory");
                    no_wrkr.requestFocus();
                }  else {
                    new AboutCompany(AboutCompanyActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            yr_st.getText().toString(), no_wrkr.getText().toString(), c_des.getText().toString()).execute();
                }
                break;
            case R.id.ft_skip:
                mApp.getPreference().edit().putBoolean(Common.ABOUTCOMPNAY, false).commit();
                mApp.getPreference().edit().putBoolean(Common.Login, true).commit();
                mApp.getPreference().edit().putBoolean(Common.COMPANY3, true).commit();
                mApp.getPreference().edit().putBoolean(Common.USRINFO,true).commit();
                new GetInfo(getApplicationContext(),mApp.getPreference().getString(Common.u_id,"")).execute();
                startActivity(new Intent(getApplicationContext(), EmployerActivity.class));
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.COMPANY3, false).commit();
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
